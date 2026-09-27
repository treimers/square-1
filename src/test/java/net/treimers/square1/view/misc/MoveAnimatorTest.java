package net.treimers.square1.view.misc;

import java.beans.PropertyChangeListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.TriangleMesh;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.piece.AbstractPiece;

/**
 * Checks that a posed move lands on the same cube as applying the move in the model.
 */
public class MoveAnimatorTest {
	private static final double DELTA = 1e-4;
	private static final ColorBean COLORS = new ColorBean() {
		private final Color[] colors = new Color[] {
				Color.WHITE,
				Color.YELLOW,
				Color.ORANGE,
				Color.BLUE,
				Color.RED,
				Color.GREEN,
				Color.GRAY,
				Color.BLACK
		};

		@Override
		public void addColorChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public void removeColorChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public Color[] getDefaultColors() {
			return colors;
		}

		@Override
		public Color[] getColors() {
			return colors;
		}
	};

	@BeforeClass
	public static void startJavaFx() throws InterruptedException {
		// A previous test may have closed its last window. Keep the toolkit alive so
		// timelines still finish.
		Platform.setImplicitExit(false);
		CountDownLatch latch = new CountDownLatch(1);
		try {
			Platform.startup(() -> latch.countDown());
		} catch (IllegalStateException alreadyStarted) {
			latch.countDown();
		}
		if (!latch.await(10, TimeUnit.SECONDS))
			throw new IllegalStateException("JavaFX did not start");
	}

	@Test
	public void playingASliceMoveEndsOnTheTargetPosition() throws Exception {
		CountDownLatch done = new CountDownLatch(1);
		AtomicReference<Throwable> failure = new AtomicReference<>();
		Platform.runLater(() -> {
			try {
				Position before = new Position();
				Move move = new Move(0, 3, true);
				Position after = before.move(move);
				MeshGroup mesh = new MeshGroup(COLORS);
				mesh.setContent(before);
				new MoveAnimator().play(mesh, move, true, before, after, () -> {
					try {
						assertSameCube(mesh, after, "played 0,3/");
					} catch (Throwable t) {
						failure.set(t);
					} finally {
						done.countDown();
					}
				});
			} catch (Throwable t) {
				failure.set(t);
				done.countDown();
			}
		});
		if (!done.await(5, TimeUnit.SECONDS))
			throw new AssertionError("move animation did not finish");
		if (failure.get() != null)
			throw new AssertionError(failure.get().getMessage(), failure.get());
	}

	@Test
	public void layerTurnsAndSliceMatchTheTargetPosition() throws Exception {
		onFx(() -> {
			Position solved = new Position();
			assertLegalMoves(solved);
			Position scrambled = solved.move(new Move(0, 3, true));
			assertLegalMoves(scrambled);
		});
	}

	private static void assertLegalMoves(Position before) throws Square1Exception {
		for (int top = -5; top <= 5; top++) {
			for (int bottom = -5; bottom <= 5; bottom++) {
				for (int twist = 0; twist < 2; twist++) {
					Move move = new Move(top, bottom, twist == 1);
					try {
						before.move(move);
					} catch (Square1Exception illegal) {
						continue;
					}
					assertMove(before, move);
				}
			}
		}
	}

	private static void assertMove(Position before, Move move) throws Square1Exception {
		Position after = before.move(move);
		MoveAnimator animator = new MoveAnimator();
		MeshGroup forward = new MeshGroup(COLORS);
		forward.setContent(before);
		animator.pose(forward, move, true, before);
		assertSameCube(forward, after, move.toString() + " forward");
		MeshGroup backward = new MeshGroup(COLORS);
		backward.setContent(after);
		animator.pose(backward, move, false, before);
		assertSameCube(backward, before, move.toString() + " backward");
	}

	private static void assertSameCube(MeshGroup actual, Position expected, String label) {
		MeshGroup reference = new MeshGroup(COLORS);
		reference.setContent(expected);
		for (Character name : reference.pieces().keySet()) {
			AbstractPiece expectedPiece = reference.pieces().get(name);
			AbstractPiece actualPiece = actual.pieces().get(name);
			if (actualPiece == null)
				throw new AssertionError(label + " is missing piece " + name);
			TriangleMesh mesh = (TriangleMesh) expectedPiece.getMesh();
			for (int i = 0; i < 3; i++) {
				Point3D local = new Point3D(mesh.getPoints().get(i * 3), mesh.getPoints().get(i * 3 + 1),
						mesh.getPoints().get(i * 3 + 2));
				Point3D expectedPoint = inMesh(reference, expectedPiece, local);
				Point3D actualPoint = inMesh(actual, actualPiece, local);
				assertClose(label + " piece " + name + " point " + i, expectedPoint, actualPoint);
			}
		}
	}

	private static Point3D inMesh(MeshGroup mesh, AbstractPiece piece, Point3D local) {
		Point3D point = piece.localToParent(local);
		javafx.scene.Node parent = piece.getParent();
		while (parent != mesh) {
			if (parent == null)
				throw new AssertionError("piece is not in the mesh");
			point = parent.localToParent(point);
			parent = parent.getParent();
		}
		return point;
	}

	private static void assertClose(String label, Point3D expected, Point3D actual) {
		if (Math.abs(expected.getX() - actual.getX()) > DELTA
				|| Math.abs(expected.getY() - actual.getY()) > DELTA
				|| Math.abs(expected.getZ() - actual.getZ()) > DELTA)
			throw new AssertionError(label + " expected " + expected + " but was " + actual);
	}

	private static void onFx(FxTask task) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<Throwable> failure = new AtomicReference<>();
		Platform.runLater(() -> {
			try {
				task.run();
			} catch (Throwable t) {
				failure.set(t);
			} finally {
				latch.countDown();
			}
		});
		if (!latch.await(30, TimeUnit.SECONDS))
			throw new IllegalStateException("timed out on the JavaFX thread");
		if (failure.get() != null)
			throw new AssertionError(failure.get().getMessage(), failure.get());
	}

	private interface FxTask {
		void run() throws Exception;
	}
}
