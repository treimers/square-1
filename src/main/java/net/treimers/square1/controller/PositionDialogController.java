package net.treimers.square1.controller;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.misc.SmartGroup;
import net.treimers.square1.view.piece.AbstractPiece;
import net.treimers.square1.view.piece.corner.PieceA;
import net.treimers.square1.view.piece.corner.PieceB;
import net.treimers.square1.view.piece.corner.PieceC;
import net.treimers.square1.view.piece.corner.PieceD;
import net.treimers.square1.view.piece.corner.PieceE;
import net.treimers.square1.view.piece.corner.PieceF;
import net.treimers.square1.view.piece.corner.PieceG;
import net.treimers.square1.view.piece.corner.PieceH;
import net.treimers.square1.view.piece.edge.Piece1;
import net.treimers.square1.view.piece.edge.Piece2;
import net.treimers.square1.view.piece.edge.Piece3;
import net.treimers.square1.view.piece.edge.Piece4;
import net.treimers.square1.view.piece.edge.Piece5;
import net.treimers.square1.view.piece.edge.Piece6;
import net.treimers.square1.view.piece.edge.Piece7;
import net.treimers.square1.view.piece.edge.Piece8;
import net.treimers.square1.view.piece.middle.PieceM;
import net.treimers.square1.view.piece.middle.PieceN;

public class PositionDialogController {
	@FXML private SubScene subScene;
	@FXML private SubScene subScene1;
	@FXML private SubScene subScene2;
	@FXML private SubScene subScene3;
	@FXML private SubScene subScene4;
	@FXML private SubScene subScene5;
	@FXML private SubScene subScene6;
	@FXML private SubScene subScene7;
	@FXML private SubScene subScene8;
	@FXML private SubScene subSceneA;
	@FXML private SubScene subSceneB;
	@FXML private SubScene subSceneC;
	@FXML private SubScene subSceneD;
	@FXML private SubScene subSceneE;
	@FXML private SubScene subSceneF;
	@FXML private SubScene subSceneG;
	@FXML private SubScene subSceneH;
	@FXML private SubScene subSceneMinus;
	@FXML private SubScene subSceneSlash;
	private Map<String, SubScene> subSceneMap;
	private Position position;

	public void init(ColorBean colorBean) {
		position = new Position();
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		subScene.setRoot(smartGroup);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		subScene.setCamera(camera);
		// piece sub scenes
		subSceneMap = Map.ofEntries(
				// Scene A
				new AbstractMap.SimpleEntry<>("A", subSceneA),
				// Scene B
				new AbstractMap.SimpleEntry<>("B", subSceneB),
				// Scene C
				new AbstractMap.SimpleEntry<>("C", subSceneC),
				// Scene D
				new AbstractMap.SimpleEntry<>("D", subSceneD),
				// Scene E
				new AbstractMap.SimpleEntry<>("E", subSceneE),
				// Scene F
				new AbstractMap.SimpleEntry<>("F", subSceneF),
				// Scene G
				new AbstractMap.SimpleEntry<>("G", subSceneG),
				// Scene H
				new AbstractMap.SimpleEntry<>("H", subSceneH),
				// Scene 1
				new AbstractMap.SimpleEntry<>("1", subScene1),
				// Scene 2
				new AbstractMap.SimpleEntry<>("2", subScene2),
				// Scene 3
				new AbstractMap.SimpleEntry<>("3", subScene3),
				// Scene 4
				new AbstractMap.SimpleEntry<>("4", subScene4),
				// Scene 5
				new AbstractMap.SimpleEntry<>("5", subScene5),
				// Scene 6
				new AbstractMap.SimpleEntry<>("6", subScene6),
				// Scene 7
				new AbstractMap.SimpleEntry<>("7", subScene7),
				// Scene 8
				new AbstractMap.SimpleEntry<>("8", subScene8),
				// Scene -
				new AbstractMap.SimpleEntry<>("-", subSceneMinus),
				// Scene /
				new AbstractMap.SimpleEntry<>("/", subSceneSlash));
		createPieceScene(subSceneA, new PieceA(0, 1, colorBean));
		createPieceScene(subSceneB, new PieceB(0, 1, colorBean));
		createPieceScene(subSceneC, new PieceC(0, 1, colorBean));
		createPieceScene(subSceneD, new PieceD(0, 1, colorBean));
		createPieceScene(subSceneE, new PieceE(0, 1, colorBean));
		createPieceScene(subSceneF, new PieceF(0, 1, colorBean));
		createPieceScene(subSceneG, new PieceG(0, 1, colorBean));
		createPieceScene(subSceneH, new PieceH(0, 1, colorBean));
		createPieceScene(subScene1, new Piece1(0, 1, colorBean));
		createPieceScene(subScene2, new Piece2(0, 1, colorBean));
		createPieceScene(subScene3, new Piece3(0, 1, colorBean));
		createPieceScene(subScene4, new Piece4(0, 1, colorBean));
		createPieceScene(subScene5, new Piece5(0, 1, colorBean));
		createPieceScene(subScene6, new Piece6(0, 1, colorBean));
		createPieceScene(subScene7, new Piece7(0, 1, colorBean));
		createPieceScene(subScene8, new Piece8(0, 1, colorBean));
		createMiddleScene(subSceneMinus, new PieceM(colorBean), new PieceN(0, colorBean));
		createMiddleScene(subSceneSlash, new PieceM(colorBean), new PieceN(1, colorBean));
		Set<String> keySet = subSceneMap.keySet();
		for (String key : keySet) {
			SubScene pieceScene = subSceneMap.get(key);
			registerDragAndDrop(key, pieceScene);
		}
	}

	public Position getPosition() {
		return position;
	}

	public void reset() {
		position.reset();
		Set<String> keySet = subSceneMap.keySet();
		for (String key : keySet) {
			SubScene pieceScene = subSceneMap.get(key);
			setVisibility(pieceScene, true);
		}
	}

	// drag and drop
	// https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
	private void registerDragAndDrop(String name, SubScene pieceScene) {
		pieceScene.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (position.accept(name)) {
					/* drag was detected, start a drag-and-drop gesture*/
					/* allow any transfer mode */
					Dragboard db = pieceScene.startDragAndDrop(TransferMode.ANY);
					/* Put a string on a dragboard */
					ClipboardContent content = new ClipboardContent();
					content.putString(name);
					db.setContent(content);
				}
				event.consume();
			}
		});
		subScene.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data is dragged over the target */
				/* accept it only if it is not dragged from the same node 
				 * and if it has a string data */
				if (event.getGestureSource() != subScene && event.getDragboard().hasString()
						&& position.accept(event.getDragboard().getString())) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();
			}
		});
		subScene.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture entered the target */
				/* show to the user that it is an actual gesture target */
				if (event.getGestureSource() != subScene && event.getDragboard().hasString()
						&& position.accept(event.getDragboard().getString())) {
					subScene.setFill(Color.GRAY);
				}
				event.consume();
			}
		});
		subScene.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* mouse moved away, remove the graphical cues */
				subScene.setFill(Color.SILVER);
				event.consume();
			}
		});
		subScene.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data dropped */
				/* if there is a string data on dragboard, read it and use it */
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasString()) {
					position.add(db.getString());
					success = true;
				}
				/* let the source know whether the string was successfully 
				 * transferred and used */
				event.setDropCompleted(success);
				event.consume();
			}
		});
		pieceScene.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag and drop gesture ended */
				/* if the data was successfully moved, clear it */
				if (event.getTransferMode() == TransferMode.MOVE)
					setVisibility(pieceScene, false);
				event.consume();
			}
		});
	}

	private void createPieceScene(SubScene subScene, AbstractPiece piece) {
		SmartGroup group = new SmartGroup();
		subScene.setRoot(group);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-5.5);
		subScene.setCamera(camera);
		// Piece
		Group meshGroup = new Group();
		meshGroup.getChildren().add(piece);
		group.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		// Mouse Events
		Rotate rotateX = new Rotate(-55, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Rotate rotateZ = new Rotate(126, 0, 0, 0, Rotate.Z_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
	}

	private void createMiddleScene(SubScene subScene, AbstractPiece pieceM, AbstractPiece pieceN) {
		SmartGroup group = new SmartGroup();
		subScene.setRoot(group);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-5.5);
		subScene.setCamera(camera);
		// Piece
		Group meshGroup = new Group();
		meshGroup.getChildren().addAll(pieceM, pieceN);
		group.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		// Mouse Events
		Rotate rotateX = new Rotate(-55, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Rotate rotateZ = new Rotate(126, 0, 0, 0, Rotate.Z_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
	}

	private void setVisibility(SubScene pieceScene, boolean visibility) {
		Group group = (Group) pieceScene.getRoot().getChildrenUnmodifiable().get(0);
		ObservableList<Node> children = group.getChildren();
		for (Node child : children)
			child.setVisible(visibility);
	}
}
