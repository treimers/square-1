package net.treimers.square1.view.misc;

import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.piece.AbstractPiece;
import net.treimers.square1.view.piece.CornerPiece;
import net.treimers.square1.view.piece.EdgePiece;
import net.treimers.square1.view.piece.Layer;
import net.treimers.square1.view.piece.MiddlePiece;

public class MeshGroup extends Group {
	private ColorBean colorBean;
	private Position position;

	public MeshGroup(ColorBean colorBean) {
		this.colorBean = colorBean;
		Rotate rotateX = new Rotate(-60, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateZ = new Rotate(160, 0, 0, 0, Rotate.Z_AXIS);
		getTransforms().addAll(rotateX, rotateZ);
	}

	public void setContent(Position position) {
		this.position = position;
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
			if (piece != null)
				getChildren().add(piece);
		}
	}

	private void addMiddleLayer(Character[] middleLayerPieces) {
		for (int i = 0; i < middleLayerPieces.length; i++) {
			Character c = middleLayerPieces[i];
			AbstractPiece pieceM = new MiddlePiece(colorBean, 'M');
			getChildren().add(pieceM);
			AbstractPiece pieceN = new MiddlePiece(colorBean, 'N');
			if (c == '-')
				pieceN.rotateByZ(180);
			else if (c == '/') {
				pieceN.rotateByX(180);
				pieceN.rotateByZ(150);
			}
			getChildren().add(pieceN);
		}
	}
	
	@Override
	public String toString() {
		return position.toString();
	}
}
