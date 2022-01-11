package net.treimers.square1.controller;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.SmartGroup;

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
	@FXML private Label positionLabel;
	private Map<Character, SubScene> subSceneMap;
	private Position position;
	private Position lastPosition;
	private MeshGroup meshGroup;

	public void init(ColorBean colorBean) {
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		meshGroup = new MeshGroup(colorBean);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
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
				new AbstractMap.SimpleEntry<>('A', subSceneA),
				// Scene B
				new AbstractMap.SimpleEntry<>('B', subSceneB),
				// Scene C
				new AbstractMap.SimpleEntry<>('C', subSceneC),
				// Scene D
				new AbstractMap.SimpleEntry<>('D', subSceneD),
				// Scene E
				new AbstractMap.SimpleEntry<>('E', subSceneE),
				// Scene F
				new AbstractMap.SimpleEntry<>('F', subSceneF),
				// Scene G
				new AbstractMap.SimpleEntry<>('G', subSceneG),
				// Scene H
				new AbstractMap.SimpleEntry<>('H', subSceneH),
				// Scene 1
				new AbstractMap.SimpleEntry<>('1', subScene1),
				// Scene 2
				new AbstractMap.SimpleEntry<>('2', subScene2),
				// Scene 3
				new AbstractMap.SimpleEntry<>('3', subScene3),
				// Scene 4
				new AbstractMap.SimpleEntry<>('4', subScene4),
				// Scene 5
				new AbstractMap.SimpleEntry<>('5', subScene5),
				// Scene 6
				new AbstractMap.SimpleEntry<>('6', subScene6),
				// Scene 7
				new AbstractMap.SimpleEntry<>('7', subScene7),
				// Scene 8
				new AbstractMap.SimpleEntry<>('8', subScene8),
				// Scene -
				new AbstractMap.SimpleEntry<>('-', subSceneMinus),
				// Scene /
				new AbstractMap.SimpleEntry<>('/', subSceneSlash));
		//
		createPieceScene(subSceneA, 'A', colorBean);
		createPieceScene(subSceneB, 'B', colorBean);
		createPieceScene(subSceneC, 'C', colorBean);
		createPieceScene(subSceneD, 'D', colorBean);
		createPieceScene(subSceneE, 'E', colorBean);
		createPieceScene(subSceneF, 'F', colorBean);
		createPieceScene(subSceneG, 'G', colorBean);
		createPieceScene(subSceneH, 'H', colorBean);
		createPieceScene(subScene1, '1', colorBean);
		createPieceScene(subScene2, '2', colorBean);
		createPieceScene(subScene3, '3', colorBean);
		createPieceScene(subScene4, '4', colorBean);
		createPieceScene(subScene5, '5', colorBean);
		createPieceScene(subScene6, '6', colorBean);
		createPieceScene(subScene7, '7', colorBean);
		createPieceScene(subScene8, '8', colorBean);
		createPieceScene(subSceneMinus, '-', colorBean);
		createPieceScene(subSceneSlash, '/', colorBean);
		// Register Drag And Drop
		Set<Entry<Character, SubScene>> entrySet = subSceneMap.entrySet();
		for (Entry<Character, SubScene> entry : entrySet)
			registerDragAndDrop(entry.getKey(), entry.getValue());
	}

	public void doSetEmptyPosition(ActionEvent event) {
		this.position = Position.fromString("");
		displayPosition(this.position);
	}

	public void doSetSolvedPosition(ActionEvent event) {
		this.position = new Position();
		displayPosition(this.position);
	}

	public void doSetLastPosition(ActionEvent event) {
		this.position = new Position(this.lastPosition);
		displayPosition(this.position);
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = new Position(position);
		this.lastPosition = position;
		displayPosition(this.position);
	}

	private void displayPosition(Position position) {
		Set<Entry<Character, SubScene>> entrySet = this.subSceneMap.entrySet();
		for (Entry<Character, SubScene> entry : entrySet)
			setVisibility(entry.getValue(), position.isAvailable(entry.getKey()));
		this.positionLabel.setText(position.toString());
		meshGroup.setContent(position);
	}

	// drag and drop
	// https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
	private void registerDragAndDrop(Character name, SubScene pieceScene) {
		pieceScene.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (position.canAdd(name)) {
					/* drag was detected, start a drag-and-drop gesture */
					/* allow any transfer mode */
					Dragboard db = pieceScene.startDragAndDrop(TransferMode.ANY);
					/* Put a string on a dragboard */
					ClipboardContent content = new ClipboardContent();
					content.putString(name.toString());
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
						&& position.canAdd(event.getDragboard().getString().charAt(0))) {
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
						&& position.canAdd(event.getDragboard().getString().charAt(0))) {
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
					position.add(db.getString().charAt(0));
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
				if (event.getTransferMode() == TransferMode.MOVE) {
					if (name == '-' || name == '/') {
						setVisibility(subSceneMinus, false);
						setVisibility(subSceneSlash, false);
					} else
						setVisibility(pieceScene, false);
					meshGroup.setContent(position);
					positionLabel.setText(position.toString());
				}
				event.consume();
			}
		});
	}

	private void createPieceScene(SubScene subScene, Character pieceName, ColorBean colorBean) {
		// Smart Group
		SmartGroup group = new SmartGroup();
		subScene.setRoot(group);
		subScene.setFill(Color.SILVER);
		// Piece
		MeshGroup pieceMeshGroup = new MeshGroup(colorBean);
		pieceMeshGroup.setContent(Position.fromString(pieceName.toString()));
		group.getChildren().addAll(pieceMeshGroup, new AmbientLight(Color.WHITE));
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-5.5);
		subScene.setCamera(camera);
	}

	private void setVisibility(SubScene pieceScene, boolean visibility) {
		Group group = (Group) pieceScene.getRoot().getChildrenUnmodifiable().get(0);
		group.setVisible(visibility);
	}
}
