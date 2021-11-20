package net.treimers.square1.controller;

import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.treimers.square1.view.AbstractPiece;
import net.treimers.square1.view.Constants;
import net.treimers.square1.view.CornerPiece;
import net.treimers.square1.view.EdgePiece;
import net.treimers.square1.view.ImageLoader;
import net.treimers.square1.view.MiddlePiece;
import net.treimers.square1.view.SmartGroup;

/*

JavaFX 3D
https://stackoverflow.com/questions/26831871/coloring-individual-triangles-in-a-triangle-mesh-on-javafx

more sources

https://stackoverflow.com/questions/19459012/how-to-create-custom-3d-model-in-javafx-8
https://stackoverflow.com/questions/61231437/how-to-create-such-shape-using-javafx-trianglemesh
https://www.dummies.com/programming/java/javafx-add-a-mesh-object-to-a-3d-world/
https://www.genuinecoder.com/javafx-3d/

Sub Scenes

https://www.youtube.com/watch?v=-pzu5rbHS18

*/

/**
 * Instances of this class are used to control the flow of the Square-1 application.
 */
public class Square1Controller implements Initializable {
	/** The sub scene width. */
	private static final int WIDTH = 800;
	/** The sub scene height. */
	private static final int HEIGHT = 600;
	/** The label with the moves to solve a Square-1 position. */
	@FXML private Label solution;
	/** The sub scene showing the Square-1. */
	@FXML private SubScene subScene;
	/** The position to solve. */
	@FXML private TextField position;
	/** The radio menu item for piece A visibility. */
	@FXML private RadioMenuItem menuPieceA;
	/** The radio menu item for piece B visibility. */
	@FXML private RadioMenuItem menuPieceB;
	/** The radio menu item for piece C visibility. */
	@FXML private RadioMenuItem menuPieceC;
	/** The radio menu item for piece D visibility. */
	@FXML private RadioMenuItem menuPieceD;
	/** The radio menu item for piece E visibility. */
	@FXML private RadioMenuItem menuPieceE;
	/** The radio menu item for piece F visibility. */
	@FXML private RadioMenuItem menuPieceF;
	/** The radio menu item for piece G visibility. */
	@FXML private RadioMenuItem menuPieceG;
	/** The radio menu item for piece H visibility. */
	@FXML private RadioMenuItem menuPieceH;
	/** The radio menu item for piece 1 visibility. */
	@FXML private RadioMenuItem menuPiece1;
	/** The radio menu item for piece 2 visibility. */
	@FXML private RadioMenuItem menuPiece2;
	/** The radio menu item for piece 3 visibility. */
	@FXML private RadioMenuItem menuPiece3;
	/** The radio menu item for piece 4 visibility. */
	@FXML private RadioMenuItem menuPiece4;
	/** The radio menu item for piece 5 visibility. */
	@FXML private RadioMenuItem menuPiece5;
	/** The radio menu item for piece 6 visibility. */
	@FXML private RadioMenuItem menuPiece6;
	/** The radio menu item for piece 7 visibility. */
	@FXML private RadioMenuItem menuPiece7;
	/** The radio menu item for piece 8 visibility. */
	@FXML private RadioMenuItem menuPiece8;
	/** The radio menu item for piece M visibility. */
	@FXML private RadioMenuItem menuPieceM;
	/** The radio menu item for piece N visibility. */
	@FXML private RadioMenuItem menuPieceN;
	private double mouseOldX;
	private double mouseOldY;
	private double mousePosX;
	private double mousePosY;
	private RotateTransition rotateTransition;
	/** The primary stage. */
	private Stage primaryStage;
	private Rotate rotateX;
	private Rotate rotateY;
	private Rotate rotateZ;
	/** The group with x-, y- and z-axis. */
	private Group axis;
	/** The mesh group in the sub scene. */
	private Group meshGroup;
	/** The map with all pieces. */
	private Map<Character, AbstractPiece> pieceMap;
	/** The map with all radio menu items for piece visibility. */
	private Map<Character, RadioMenuItem> menuMap;

	/**
	 * Constructs a new instance.
	 * @param primaryStage the primary stage.
	 */
	public Square1Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		pieceMap = new HashMap<>();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Position
		position.setText("A1B2C3D45E6F7G8H");
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		subScene.setRoot(smartGroup);
		subScene.setWidth(WIDTH);
		subScene.setHeight(HEIGHT);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		subScene.setCamera(camera);
		// Edges
		EdgePiece edge1 = new EdgePiece(0, 1, Constants.BLACK, Constants.GRAY, Constants.YELLOW, Constants.GRAY,
				Constants.WHITE);
		EdgePiece edge2 = new EdgePiece(1, 1, Constants.BLACK, Constants.GRAY, Constants.RED, Constants.GRAY,
				Constants.WHITE);
		EdgePiece edge3 = new EdgePiece(2, 1, Constants.BLACK, Constants.GRAY, Constants.BLUE, Constants.GRAY,
				Constants.WHITE);
		EdgePiece edge4 = new EdgePiece(3, 1, Constants.BLACK, Constants.GRAY, Constants.ORANGE, Constants.GRAY,
				Constants.WHITE);
		EdgePiece edge5 = new EdgePiece(3, -1, Constants.BLACK, Constants.GRAY, Constants.ORANGE, Constants.GRAY,
				Constants.GREEN);
		EdgePiece edge6 = new EdgePiece(2, -1, Constants.BLACK, Constants.GRAY, Constants.BLUE, Constants.GRAY,
				Constants.GREEN);
		EdgePiece edge7 = new EdgePiece(1, -1, Constants.BLACK, Constants.GRAY, Constants.RED, Constants.GRAY,
				Constants.GREEN);
		EdgePiece edge8 = new EdgePiece(0, -1, Constants.BLACK, Constants.GRAY, Constants.YELLOW, Constants.GRAY,
				Constants.GREEN);
		// Corners
		CornerPiece cornerA = new CornerPiece(0, 1, Constants.BLACK, Constants.GRAY, Constants.YELLOW, Constants.ORANGE,
				Constants.GRAY, Constants.WHITE);
		CornerPiece cornerB = new CornerPiece(1, 1, Constants.BLACK, Constants.GRAY, Constants.RED, Constants.YELLOW,
				Constants.GRAY, Constants.WHITE);
		CornerPiece cornerC = new CornerPiece(2, 1, Constants.BLACK, Constants.GRAY, Constants.BLUE, Constants.RED,
				Constants.GRAY, Constants.WHITE);
		CornerPiece cornerD = new CornerPiece(3, 1, Constants.BLACK, Constants.GRAY, Constants.ORANGE, Constants.BLUE,
				Constants.GRAY, Constants.WHITE);
		CornerPiece cornerE = new CornerPiece(3, -1, Constants.BLACK, Constants.GRAY, Constants.ORANGE, Constants.BLUE,
				Constants.GRAY, Constants.GREEN);
		CornerPiece cornerF = new CornerPiece(2, -1, Constants.BLACK, Constants.GRAY, Constants.BLUE, Constants.RED,
				Constants.GRAY, Constants.GREEN);
		CornerPiece cornerG = new CornerPiece(1, -1, Constants.BLACK, Constants.GRAY, Constants.RED, Constants.YELLOW,
				Constants.GRAY, Constants.GREEN);
		CornerPiece cornerH = new CornerPiece(0, -1, Constants.BLACK, Constants.GRAY, Constants.YELLOW,
				Constants.ORANGE, Constants.GRAY, Constants.GREEN);
		// Middle
		MiddlePiece middleM = new MiddlePiece(0, Constants.BLACK, Constants.YELLOW, Constants.ORANGE, Constants.GRAY,
				Constants.RED, Constants.BLACK);
		MiddlePiece middleN = new MiddlePiece(1, Constants.BLACK, Constants.BLUE, Constants.RED, Constants.GRAY,
				Constants.ORANGE, Constants.BLACK);
		// Piece Map
		pieceMap = Map.ofEntries(new AbstractMap.SimpleEntry<>('1', edge1), new AbstractMap.SimpleEntry<>('2', edge2),
				new AbstractMap.SimpleEntry<>('3', edge3), new AbstractMap.SimpleEntry<>('4', edge4),
				new AbstractMap.SimpleEntry<>('5', edge5), new AbstractMap.SimpleEntry<>('6', edge6),
				new AbstractMap.SimpleEntry<>('7', edge7), new AbstractMap.SimpleEntry<>('8', edge8),
				new AbstractMap.SimpleEntry<>('A', cornerA), new AbstractMap.SimpleEntry<>('B', cornerB),
				new AbstractMap.SimpleEntry<>('C', cornerC), new AbstractMap.SimpleEntry<>('D', cornerD),
				new AbstractMap.SimpleEntry<>('E', cornerE), new AbstractMap.SimpleEntry<>('F', cornerF),
				new AbstractMap.SimpleEntry<>('G', cornerG), new AbstractMap.SimpleEntry<>('H', cornerH),
				new AbstractMap.SimpleEntry<>('M', middleM), new AbstractMap.SimpleEntry<>('N', middleN));
		// Menu Map
		menuMap = Map.ofEntries(new AbstractMap.SimpleEntry<>('1', menuPiece1),
				new AbstractMap.SimpleEntry<>('2', menuPiece2),
				new AbstractMap.SimpleEntry<>('3', menuPiece3),
				new AbstractMap.SimpleEntry<>('4', menuPiece4),
				new AbstractMap.SimpleEntry<>('5', menuPiece5),
				new AbstractMap.SimpleEntry<>('6', menuPiece6),
				new AbstractMap.SimpleEntry<>('7', menuPiece7),
				new AbstractMap.SimpleEntry<>('8', menuPiece8),
				new AbstractMap.SimpleEntry<>('A', menuPieceA),
				new AbstractMap.SimpleEntry<>('B', menuPieceB),
				new AbstractMap.SimpleEntry<>('C', menuPieceC),
				new AbstractMap.SimpleEntry<>('D', menuPieceD),
				new AbstractMap.SimpleEntry<>('E', menuPieceE),
				new AbstractMap.SimpleEntry<>('F', menuPieceF),
				new AbstractMap.SimpleEntry<>('G', menuPieceG),
				new AbstractMap.SimpleEntry<>('H', menuPieceH),
				new AbstractMap.SimpleEntry<>('M', menuPieceM),
				new AbstractMap.SimpleEntry<>('N', menuPieceN)
		);
		// Register Radio Menu Items
		Set<Character> set = pieceMap.keySet();
		Character[] characters = set.toArray(new Character[set.size()]);
		for (Character c : characters) {
			AbstractPiece piece = pieceMap.get(c);
			RadioMenuItem radioMenuItem = menuMap.get(c);
			radioMenuItem.selectedProperty().bindBidirectional(piece.visibleProperty());
		}
		// All Nodes
		Node[] allNodes = new Node[] {
			edge1,
			edge2,
			edge3,
			edge4,
			edge8,
			edge7,
			edge6,
			edge5,
			cornerA,
			cornerB,
			cornerC,
			cornerD,
			cornerH,
			cornerG,
			cornerF,
			cornerE,
			middleM,
			middleN,
		};
		// Mesh Group
		meshGroup = new Group();
		axis = buildAxes();
		meshGroup.getChildren().addAll(allNodes);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		// Mouse Events
		subScene.setOnMousePressed(me -> {
			mouseOldX = me.getSceneX();
			mouseOldY = me.getSceneY();
		});
		rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		rotateZ = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
		subScene.setOnMouseDragged(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
			rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
		});
		// Animation
		rotateTransition = new RotateTransition(Duration.seconds(2.000), meshGroup);
		rotateTransition.setCycleCount(1);
		rotateTransition.setAxis(Rotate.Y_AXIS);
		rotateTransition.setByAngle(360);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
	}

	@FXML
	void doAbout(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(ImageLoader.getLogoImageView());
		alert.setTitle("About");
		alert.setHeaderText("Square-1\nVersion 0.0.1");
		alert.setContentText("Copyright © 2021, Thorsten Reimers");
		alert.initOwner(primaryStage);
		alert.showAndWait();
	}

	@FXML
	void doExit(ActionEvent event) {
		primaryStage.hide();
	}

	@FXML
	void doXClock(ActionEvent event) {
		rotateX.setAngle(rotateX.getAngle() + 10.0f);
	}

	@FXML
	void doXAntiClock(ActionEvent event) {
		rotateX.setAngle(rotateX.getAngle() - 10.0f);
	}

	@FXML
	void doYClock(ActionEvent event) {
		rotateY.setAngle(rotateY.getAngle() + 10.0f);
	}

	@FXML
	void doYAntiClock(ActionEvent event) {
		rotateY.setAngle(rotateY.getAngle() - 10.0f);
	}

	@FXML
	void doZClock(ActionEvent event) {
		rotateZ.setAngle(rotateZ.getAngle() + 10.0f);
	}

	@FXML
	void doZAntiClock(ActionEvent event) {
		rotateZ.setAngle(rotateZ.getAngle() - 10.0f);
	}

	@FXML
	void doToggleAxis(ActionEvent event) {
		ObservableList<Node> children = meshGroup.getChildren();
		if (children.contains(axis))
			children.remove(axis);
		else
			children.add(axis);
	}

	@FXML
	void doRotate(ActionEvent event) {
		animate();
	}

	@FXML
	void doReset(ActionEvent event) {
		rotateX.setAngle(0.0f);
		rotateY.setAngle(0.0f);
		rotateZ.setAngle(0.0f);
	}

	@FXML
	void doToggle(ActionEvent event) {
		RadioMenuItem source = (RadioMenuItem) event.getSource();
		String text = source.getText();
		int index = text.indexOf(' ');
		if (index < 0)
			return;
		char c = text.charAt(index + 1);
		AbstractPiece piece = pieceMap.get(c);
		piece.setVisible(source.isSelected());
	}

	@FXML
	void doHideAll(ActionEvent event) {
		Set<Character> set = pieceMap.keySet();
		for (Character c : set) {
			pieceMap.get(c).setVisible(false);
		}
	}

	@FXML
	void doShowAll(ActionEvent event) {
		Set<Character> set = pieceMap.keySet();
		for (Character c : set) {
			pieceMap.get(c).setVisible(true);
		}
	}

	@FXML
	void doSolve(ActionEvent event) {
		System.out.println("Solve");
	}

	// https://www.youtube.com/watch?v=oaL8n1bmD78
	/**
	 * Animates a 360° rotation of the Square-1.
	 */
	private void animate() {
		if (rotateTransition.getStatus() != Status.RUNNING)
			rotateTransition.playFromStart();
	}

	/**
	 * Creates a group with x-, y- and z-axis.
	 * @return the group with x-, y- and z-axis.
	 */
	private Group buildAxes() {
		Group axisGroup = new Group();
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);
		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);
		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);
		final Box xAxis = new Box(4.0f, 0.02f, 0.02f);
		xAxis.setMaterial(redMaterial);
		final Box yAxis = new Box(0.02f, 4.0f, 0.02f);
		yAxis.setMaterial(greenMaterial);
		final Box zAxis = new Box(0.02f, 0.02f, 4.0f);
		zAxis.setMaterial(blueMaterial);
		Sphere xSphere = new Sphere(0.04f);
		xSphere.setTranslateX(2.0f);
		xSphere.setMaterial(redMaterial);
		Sphere ySphere = new Sphere(0.04f);
		ySphere.setTranslateY(2.0f);
		ySphere.setMaterial(greenMaterial);
		Sphere zSphere = new Sphere(0.04f);
		zSphere.setTranslateZ(2.0f);
		zSphere.setMaterial(blueMaterial);
		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis, xSphere, ySphere, zSphere);
		return axisGroup;
	}
}
