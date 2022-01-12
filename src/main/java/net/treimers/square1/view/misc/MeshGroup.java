package net.treimers.square1.view.misc;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Animation.Status;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.piece.AbstractPiece;
import net.treimers.square1.view.piece.CornerPiece;
import net.treimers.square1.view.piece.EdgePiece;
import net.treimers.square1.view.piece.Layer;
import net.treimers.square1.view.piece.MiddlePiece;

/**
 * Instances of this class are used to visualize the pieces
 * in a position or a single piece only.
 */
public class MeshGroup extends Group {
	/** Indicator for correct middle layer. */
	private static final char MIDDLE_CORRECT = '-';
	/** Indicator for swapped middle layer. */
	private static final char MIDDLE_SWAPPED = '/';
	/** The name of piece M. */
	private static final char PIECE_M = 'M';
	/** The name of piece N. */
	private static final char PIECE_N = 'N';
	/** The map with all pieces. */
	private Map<Character, AbstractPiece> pieceMap;
	/** The color bean used to define the Square-1 colors. */
	private ColorBean colorBean;
	/** The visualized position. */
	private Position position;
	/** Rotate transition used to perform an animated 360° rotation of the Square-1. */
	private RotateTransition rotateTransition;
	
	/**
	 * Creates a new mesh group instance.
	 * 
	 * @param colorBean the color bean used to define the Square-1 colors.
	 */
	public MeshGroup(ColorBean colorBean) {
		this.colorBean = colorBean;
		pieceMap = new HashMap<>();
		Rotate rotateX = new Rotate(-60, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateZ = new Rotate(160, 0, 0, 0, Rotate.Z_AXIS);
		getTransforms().addAll(rotateX, rotateZ);
		// Setup Animation
		rotateTransition = new RotateTransition(Duration.seconds(2.000), this);
		rotateTransition.setCycleCount(1);
		rotateTransition.setAxis(Rotate.Y_AXIS);
		rotateTransition.setByAngle(360);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
	}

	/**
	 * Sets the content i.e. the position to be displayed.
	 * 
	 * @param position the visualized position.
	 */
	public void setContent(Position position) {
		this.position = position;
		pieceMap = new HashMap<>();
		ObservableList<Node> children = getChildren();
		// We cannot iterate over the list in a for-each loop and remove entries.
		// This will lead to a java.util.ConcurrentModificationException.
		// I found this https://www.baeldung.com/java-concurrentmodificationexception
		// although I am not very happy using lambdas :(
		children.removeIf(AbstractPiece.class::isInstance);
		Map<Layer, Character[]> layerMap = position.getPieces();
		Character[] topPieces = layerMap.get(Layer.TOP);
		addOuterLayer(topPieces, false);
		Character[] bottomPieces = layerMap.get(Layer.BOTTOM);
		addOuterLayer(bottomPieces, true);
		Character[] middlePieces = layerMap.get(Layer.MIDDLE);
		addMiddleLayer(middlePieces);
	}

	/**
	 * Sets the visibility of a piece.
	 * @param pieceName the peace name.
	 * @param visible the visibility (true or false).
	 */
	public void setPieceVisibility(char pieceName, boolean visible) {
		AbstractPiece piece = pieceMap.get(pieceName);
		if (piece != null)
			piece.setVisible(visible);
	}
	
	// https://www.youtube.com/watch?v=oaL8n1bmD78
	/**
	 * Performs an animated 360° rotation of the Square-1.
	 */
	public void animate() {
		if (rotateTransition.getStatus() != Status.RUNNING)
			rotateTransition.playFromStart();
	}

	/**
	 * Adds all pieces in a top or a bottom layer to this mesh group.
	 * 
	 * @param layerPieces the pieces in the layer.
	 * @param bottomLayer the bottom indicator (true = bottom, false = top).
	 */
	private void addOuterLayer(Character[] layerPieces, boolean bottomLayer) {
		int angle = 0;
		for (int i = 0; i < layerPieces.length; i++) {
			char c = layerPieces[i];
			AbstractPiece piece = null;
			if (Character.isLetter(c)) {
				piece = new CornerPiece(colorBean, c);
				if (bottomLayer) {
					piece.rotateByX(180);
					piece.rotateByZ(150);
				}
				piece.rotateByZ(angle);
				angle += 60;
			} else if (Character.isDigit(c)) {
				piece = new EdgePiece(colorBean, c);
				if (bottomLayer) {
					piece.rotateByX(180);
					piece.rotateByZ(30);
				}
				piece.rotateByZ(angle);
				angle += 30;
			}
			if (piece != null) {
				getChildren().add(piece);
				pieceMap.put(c, piece);
			}
		}
	}

	/**
	 * Adds all pieces in middle layer to this mesh group.
	 * 
	 * @param layerPieces the pieces in the layer.
	 */
	private void addMiddleLayer(Character[] layerPieces) {
		for (int i = 0; i < layerPieces.length; i++) {
			Character c = layerPieces[i];
			AbstractPiece pieceM = new MiddlePiece(colorBean, PIECE_M);
			getChildren().add(pieceM);
			pieceMap.put(PIECE_M, pieceM);
			AbstractPiece pieceN = new MiddlePiece(colorBean, PIECE_N);
			if (c == MIDDLE_CORRECT)
				pieceN.rotateByZ(180);
			else if (c == MIDDLE_SWAPPED) {
				pieceN.rotateByX(180);
				pieceN.rotateByZ(150);
			}
			getChildren().add(pieceN);
			pieceMap.put(PIECE_N, pieceN);
		}
	}
	
	@Override
	public String toString() {
		return position.toString();
	}
}
