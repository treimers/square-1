package net.treimers.square1.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.GridEntry;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.SmartGroup;

/**
 * Instances of this class are used as controller for Square-1 position dialog.
 */
public class PositionDialogController {
	/** The main sub scene with Square-1 cube in the position dialog. */
	@FXML private SubScene square1Scene;
	/** The grid pane with all sub scenes for single pieces used as drag sources. */
	@FXML private GridPane pieceGridPane;
	/** The label reflecting the current position. */
	@FXML private Label positionLabel;
	/** The current position. */
	private Position position;
	/** The last position used for reset. */
	private Position lastPosition;
	/** The mesh group containing the Square-1 cube. */
	private MeshGroup meshGroup;
	/** The map with all piece mesh group saved by the piece name. */
	private Map<Character, MeshGroup> pieceGroupMap;
	/** Identifies the cells in piece grid pane by piece name, row index and column index. */
	private GridEntry[] gridEntries = {
		// grid line 1
		new GridEntry('A', 0, 0),
		new GridEntry('B', 1, 0),
		new GridEntry('1', 2, 0),
		new GridEntry('2', 3, 0),
		// grid line 2
		new GridEntry('C', 0, 2),
		new GridEntry('D', 1, 2),
		new GridEntry('3', 2, 2),
		new GridEntry('4', 3, 2),
		new GridEntry('-', 4, 2),
		// grid line 3
		new GridEntry('E', 0, 4),
		new GridEntry('F', 1, 4),
		new GridEntry('5', 2, 4),
		new GridEntry('6', 3, 4),
		new GridEntry('/', 4, 4),
		// grid line 4
		new GridEntry('G', 0, 6),
		new GridEntry('H', 1, 6),
		new GridEntry('7', 2, 6),
		new GridEntry('8', 3, 6),
	};

	/**
	 * Initializes this instance.
	 * 
	 * @param colorBean the color bean used to get the Square-1 colors.
	 */
	public void init(ColorBean colorBean) {
		pieceGroupMap = new HashMap<>();
		// Create an array with piece subscenes from the piece grid
		ObservableList<Node> children = pieceGridPane.getChildren();
		int columnCount = pieceGridPane.getColumnCount();
		int rowCount = pieceGridPane.getRowCount();
		SubScene[][] subscenes = new SubScene[columnCount][rowCount];
		for (Node node : children) {
			if (node instanceof SubScene) {
				SubScene subScene = (SubScene) node;
				Integer xValue = GridPane.getColumnIndex(node);
				Integer yValue = GridPane.getRowIndex(node);
				int x = xValue == null ? 0 : xValue.intValue();
				int y = yValue == null ? 0 : yValue.intValue();
				subscenes[x][y] = subScene;
			}
		}
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		meshGroup = new MeshGroup(colorBean);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		square1Scene.setRoot(smartGroup);
		square1Scene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		square1Scene.setCamera(camera);
		// piece sub scenes
		for (GridEntry gridEntry : gridEntries) {
			SubScene pieceScene = subscenes[gridEntry.getPosx()][gridEntry.getPosy()];
			createPieceScene(pieceScene, gridEntry, colorBean);
		}
	}

	/**
	 * Performs an animated 360° rotation of the Square-1.
	 */
		public void doRotate() {
		meshGroup.animate();
		Set<Character> pieceNames = pieceGroupMap.keySet();
		for (Character c : pieceNames) {
			pieceGroupMap.get(c).animate();
		}
	}
	
	/**
	 * Sets the position to empty position.
	 */
	public void doSetEmptyPosition() {
		this.position = Position.fromString("");
		displayPosition(this.position);
	}

	/**
	 * Sets the position to solved position.
	 */
	public void doSetSolvedPosition() {
		this.position = new Position();
		displayPosition(this.position);
	}

	/**
	 * Resets the position to last position.
	 */
	public void doResetPosition() {
		this.position = new Position(this.lastPosition);
		displayPosition(this.position);
	}

	/**
	 * Gets the position.
	 * @return the position.
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 * @param position the position.
	 */
	public void setPosition(Position position) {
		this.position = new Position(position);
		this.lastPosition = position;
		displayPosition(this.position);
	}

	/**
	 * Displays the current position in Square-1 subscene of the position dialog.
	 * 
	 * @param position the current position.
	 */
	private void displayPosition(Position position) {
		Set<Entry<Character, MeshGroup>> entrySet = this.pieceGroupMap.entrySet();
		for (Entry<Character, MeshGroup> entry : entrySet)
			entry.getValue().setVisible(position.isAvailable(entry.getKey()));
		this.positionLabel.setText(position.toString());
		meshGroup.setContent(position);
	}

	// drag and drop
	// https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
	/**
	 * Registers drag and drop behaviour on a piece sub scene.
	 * 
	 * @param pieceName the piece name.
	 * @param pieceScene the piece sub scene.
	 */
	private void registerDragAndDrop(Character pieceName, SubScene pieceScene) {
		pieceScene.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (position.canAdd(pieceName)) {
					/* drag was detected, start a drag-and-drop gesture */
					/* allow any transfer mode */
					Dragboard db = pieceScene.startDragAndDrop(TransferMode.ANY);
					/* Put a string on a dragboard */
					ClipboardContent content = new ClipboardContent();
					content.putString(pieceName.toString());
					db.setContent(content);
				}
				event.consume();
			}
		});
		square1Scene.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data is dragged over the target */
				/* accept it only if it is not dragged from the same node 
				 * and if it has a string data */
				if (event.getGestureSource() != square1Scene && event.getDragboard().hasString()
						&& position.canAdd(event.getDragboard().getString().charAt(0))) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();
			}
		});
		square1Scene.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture entered the target */
				/* show to the user that it is an actual gesture target */
				if (event.getGestureSource() != square1Scene && event.getDragboard().hasString()
						&& position.canAdd(event.getDragboard().getString().charAt(0))) {
					square1Scene.setFill(Color.GRAY);
				}
				event.consume();
			}
		});
		square1Scene.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* mouse moved away, remove the graphical cues */
				square1Scene.setFill(Color.SILVER);
				event.consume();
			}
		});
		square1Scene.setOnDragDropped(new EventHandler<DragEvent>() {
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
					if (pieceName == '-' || pieceName == '/') {
						MeshGroup minus = pieceGroupMap.get('-');
						minus.setVisible(false);
						MeshGroup slash = pieceGroupMap.get('/');
						slash.setVisible(false);
					} else {
						MeshGroup pieceGroup = pieceGroupMap.get(pieceName);
						pieceGroup.setVisible(false);
					}
					meshGroup.setContent(position);
					positionLabel.setText(position.toString());
				}
				event.consume();
			}
		});
	}

	/**
	 * Fills a piece sub scene with content.
	 * 
	 * @param pieceScene the piece sub scene.
	 * @param gridEntry the piece data (name, grid x position, grid y position).
	 * @param colorBean the color bean used to colorize the piece.
	 */
	private void createPieceScene(SubScene pieceScene, GridEntry gridEntry, ColorBean colorBean) {
		// Smart Group
		SmartGroup group = new SmartGroup();
		pieceScene.setRoot(group);
		pieceScene.setFill(Color.SILVER);
		// Piece
		MeshGroup pieceMeshGroup = new MeshGroup(colorBean);
		pieceMeshGroup.setContent(Position.fromString(gridEntry.toString()));
		group.getChildren().addAll(pieceMeshGroup, new AmbientLight(Color.WHITE));
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-5.5);
		pieceScene.setCamera(camera);
		// Map
		pieceGroupMap.put(gridEntry.getName(), pieceMeshGroup);
		// Drag And Drop
		registerDragAndDrop(gridEntry.getName(), pieceScene);
	}
}
