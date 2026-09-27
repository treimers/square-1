package net.treimers.square1.view.misc;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.piece.AbstractPiece;
import net.treimers.square1.view.piece.Layer;

/**
 * Plays one Square-1 move on a {@link MeshGroup}.
 *
 * <p>A move turns the top and bottom layers together, then slices the right half
 * when the move ends with {@code /}. The bottom layer is drawn upside down, so its
 * positive turn is a negative rotation about Z. The slice is a half turn about the
 * cut through the middle layer, 15° off the X axis.
 */
public class MoveAnimator {
	/** Marker on groups created while a move is playing. */
	private static final String LAYER_MARKER = "move-layer";
	/** One move unit, in degrees. */
	private static final double DEGREES_PER_UNIT = 30.0;
	/** How long one unit of a layer turn takes. */
	private static final double MILLIS_PER_UNIT = 90.0;
	/** How long the slice takes. */
	private static final double SLICE_MILLIS = 420.0;
	/** Pause between the layer turn and the slice, so the two parts stay readable. */
	private static final double PAUSE_MILLIS = 70.0;
	/**
	 * Axis of the slice. A half turn about this axis swaps the right half of the
	 * top layer with the right half of the bottom layer.
	 */
	private static final Point3D SLICE_AXIS = new Point3D(Math.cos(Math.toRadians(15)), Math.sin(Math.toRadians(15)), 0);

	/** The timeline of the phase that is playing, or null. */
	private Timeline timeline;
	/** Invalidates callbacks of a move that was cancelled or replaced. */
	private int token;

	/**
	 * Returns whether a node is a temporary layer created for a move.
	 * 
	 * @param node the node.
	 * @return true if the node is a move layer.
	 */
	static boolean isMoveLayer(Node node) {
		return Boolean.TRUE.equals(node.getProperties().get(LAYER_MARKER));
	}

	/**
	 * Returns whether a move is still playing.
	 * 
	 * @return true if a move is playing.
	 */
	public boolean isRunning() {
		return timeline != null && timeline.getStatus() == javafx.animation.Animation.Status.RUNNING;
	}

	/**
	 * Stops the move that is playing. The mesh is left as it is; the caller
	 * restores a position with {@link MeshGroup#setContent(Position)}.
	 */
	public void cancel() {
		token++;
		stopTimeline();
	}

	/**
	 * Plays the move that links {@code before} and {@code after}.
	 *
	 * <p>The mesh must already show {@code before} when {@code forward} is true,
	 * and {@code after} when {@code forward} is false.
	 * 
	 * @param mesh the mesh that shows the cube.
	 * @param move the move.
	 * @param forward true to play the move, false to play it backwards.
	 * @param before the position before the move.
	 * @param after the position after the move.
	 * @param onFinished called on the JavaFX thread when the move has finished.
	 */
	public void play(MeshGroup mesh, Move move, boolean forward, Position before, Position after, Runnable onFinished) {
		cancel();
		int run = token;
		Position turned = turnedPosition(before, move);
		if (turned == null) {
			mesh.setContent(forward ? after : before);
			onFinished.run();
			return;
		}
		if (forward)
			playForward(mesh, move, before, turned, after, run, onFinished);
		else
			playBackward(mesh, move, turned, before, after, run, onFinished);
	}

	/**
	 * Poses the mesh at the end of the move without playing it.
	 * Used to check that the turn and the slice land on the target position.
	 * 
	 * @param mesh the mesh. It must show the start of this direction.
	 * @param move the move.
	 * @param forward true for the forward move, false for the reverse move.
	 * @param before the position before the move.
	 * @throws Square1Exception if the move cannot be applied.
	 */
	void pose(MeshGroup mesh, Move move, boolean forward, Position before) throws Square1Exception {
		Position turned = before.move(new Move(move.getTopRotation(), move.getBottomRotation(), false));
		if (forward) {
			poseTurns(mesh, before, degrees(move.getTopRotation()), -degrees(move.getBottomRotation()));
			flatten(mesh);
			if (move.isTwisted())
				poseSlice(mesh, turned);
		} else {
			if (move.isTwisted()) {
				poseSlice(mesh, before.move(move));
				flatten(mesh);
			}
			poseTurns(mesh, turned, -degrees(move.getTopRotation()), degrees(move.getBottomRotation()));
		}
	}

	private void playForward(MeshGroup mesh, Move move, Position before, Position turned, Position after, int run,
			Runnable onFinished) {
		playTurns(mesh, before, degrees(move.getTopRotation()), -degrees(move.getBottomRotation()), run, () -> {
			flatten(mesh);
			if (!move.isTwisted()) {
				finish(mesh, after, run, onFinished);
				return;
			}
			pause(run, () -> playSlice(mesh, turned, run, () -> finish(mesh, after, run, onFinished)));
		});
	}

	private void playBackward(MeshGroup mesh, Move move, Position turned, Position before, Position after, int run,
			Runnable onFinished) {
		Runnable undoTurns = () -> playTurns(mesh, turned, -degrees(move.getTopRotation()), degrees(move.getBottomRotation()),
				run, () -> finish(mesh, before, run, onFinished));
		if (!move.isTwisted()) {
			undoTurns.run();
			return;
		}
		playSlice(mesh, after, run, () -> {
			flatten(mesh);
			pause(run, undoTurns);
		});
	}

	private void finish(MeshGroup mesh, Position position, int run, Runnable onFinished) {
		if (run != token)
			return;
		stopTimeline();
		mesh.setContent(position);
		onFinished.run();
	}

	private void playTurns(MeshGroup mesh, Position displayed, double topDegrees, double bottomDegrees, int run,
			Runnable onFinished) {
		if (run != token)
			return;
		Rotate top = beginTurn(mesh, names(displayed, Layer.TOP), topDegrees);
		Rotate bottom = beginTurn(mesh, names(displayed, Layer.BOTTOM), bottomDegrees);
		if (top == null && bottom == null) {
			onFinished.run();
			return;
		}
		Duration duration = Duration.millis(MILLIS_PER_UNIT * Math.max(Math.abs(topDegrees), Math.abs(bottomDegrees)) / DEGREES_PER_UNIT);
		List<KeyValue> values = new ArrayList<>();
		if (top != null)
			values.add(new KeyValue(top.angleProperty(), topDegrees, Interpolator.EASE_BOTH));
		if (bottom != null)
			values.add(new KeyValue(bottom.angleProperty(), bottomDegrees, Interpolator.EASE_BOTH));
		play(duration, values, run, onFinished);
	}

	private void playSlice(MeshGroup mesh, Position displayed, int run, Runnable onFinished) {
		if (run != token)
			return;
		Rotate slice = beginSlice(mesh, displayed);
		if (slice == null) {
			onFinished.run();
			return;
		}
		play(Duration.millis(SLICE_MILLIS), List.of(new KeyValue(slice.angleProperty(), 180.0, Interpolator.EASE_BOTH)), run,
				onFinished);
	}

	private void pause(int run, Runnable onFinished) {
		if (run != token)
			return;
		play(Duration.millis(PAUSE_MILLIS), List.of(), run, onFinished);
	}

	private void play(Duration duration, List<KeyValue> values, int run, Runnable onFinished) {
		stopTimeline();
		timeline = new Timeline(new KeyFrame(duration, values.toArray(KeyValue[]::new)));
		timeline.setOnFinished(event -> {
			if (run != token)
				return;
			onFinished.run();
		});
		timeline.play();
	}

	private void poseTurns(MeshGroup mesh, Position displayed, double topDegrees, double bottomDegrees) {
		Rotate top = beginTurn(mesh, names(displayed, Layer.TOP), topDegrees);
		Rotate bottom = beginTurn(mesh, names(displayed, Layer.BOTTOM), bottomDegrees);
		if (top != null)
			top.setAngle(topDegrees);
		if (bottom != null)
			bottom.setAngle(bottomDegrees);
	}

	private void poseSlice(MeshGroup mesh, Position displayed) {
		Rotate slice = beginSlice(mesh, displayed);
		if (slice != null)
			slice.setAngle(180);
	}

	private Rotate beginTurn(MeshGroup mesh, List<Character> names, double degrees) {
		if (degrees == 0 || names.isEmpty())
			return null;
		return attach(mesh, names, new Rotate(0, Rotate.Z_AXIS));
	}

	private Rotate beginSlice(MeshGroup mesh, Position displayed) {
		List<Character> names = slicePieces(displayed);
		if (names.isEmpty())
			return null;
		return attach(mesh, names, new Rotate(0, SLICE_AXIS));
	}

	private Rotate attach(MeshGroup mesh, List<Character> names, Rotate rotate) {
		Group group = new Group();
		group.getProperties().put(LAYER_MARKER, Boolean.TRUE);
		int moved = 0;
		for (int i = 0; i < names.size(); i++) {
			AbstractPiece piece = mesh.pieces().get(names.get(i));
			if (piece == null)
				continue;
			Parent parent = piece.getParent();
			if (parent instanceof Group)
				((Group) parent).getChildren().remove(piece);
			group.getChildren().add(piece);
			moved++;
		}
		if (moved == 0)
			return null;
		group.getTransforms().add(rotate);
		mesh.getChildren().add(group);
		return rotate;
	}

	private void flatten(MeshGroup mesh) {
		List<Node> layers = new ArrayList<>();
		for (Node node : mesh.getChildren()) {
			if (isMoveLayer(node))
				layers.add(node);
		}
		for (int i = 0; i < layers.size(); i++) {
			Group group = (Group) layers.get(i);
			List<Node> pieces = new ArrayList<>(group.getChildren());
			for (int j = 0; j < pieces.size(); j++) {
				Node piece = pieces.get(j);
				Affine baked = new Affine(group.getLocalToParentTransform());
				baked.append(piece.getLocalToParentTransform());
				group.getChildren().remove(piece);
				piece.getTransforms().setAll(baked);
				mesh.getChildren().add(piece);
			}
			mesh.getChildren().remove(group);
		}
	}

	private void stopTimeline() {
		if (timeline != null) {
			timeline.stop();
			timeline = null;
		}
	}

	private static double degrees(int units) {
		return units * DEGREES_PER_UNIT;
	}

	private static Position turnedPosition(Position before, Move move) {
		try {
			return before.move(new Move(move.getTopRotation(), move.getBottomRotation(), false));
		} catch (Square1Exception e) {
			return null;
		}
	}

	private static List<Character> names(Position position, Layer layer) {
		Character[] pieces = position.getPieces().get(layer);
		List<Character> names = new ArrayList<>();
		if (pieces == null)
			return names;
		for (int i = 0; i < pieces.length; i++)
			names.add(pieces[i]);
		return names;
	}

	/**
	 * Pieces in the right half after the layers have been turned: top slots 6 to 11,
	 * bottom slots 0 to 5, and middle piece N.
	 */
	private static List<Character> slicePieces(Position position) {
		List<Character> names = new ArrayList<>();
		names.addAll(half(position.getPieces().get(Layer.TOP), false));
		names.addAll(half(position.getPieces().get(Layer.BOTTOM), true));
		names.add('N');
		return names;
	}

	/**
	 * Collects the pieces of one half of a layer.
	 * 
	 * @param layer the pieces in slot order.
	 * @param leading true for slots 0 to 5, false for slots 6 to 11.
	 */
	private static List<Character> half(Character[] layer, boolean leading) {
		List<Character> names = new ArrayList<>();
		if (layer == null)
			return names;
		int slot = 0;
		for (int i = 0; i < layer.length; i++) {
			Character name = layer[i];
			int width = Character.isLetter(name) ? 2 : 1;
			boolean selected = leading ? slot < 6 : slot >= 6;
			if (selected)
				names.add(name);
			slot += width;
		}
		return names;
	}
}
