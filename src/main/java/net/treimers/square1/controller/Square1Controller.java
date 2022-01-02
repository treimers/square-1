package net.treimers.square1.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.treimers.square1.Version;
import net.treimers.square1.view.dialog.ColorDialog;
import net.treimers.square1.view.dialog.PositionDialog;
import net.treimers.square1.view.misc.ImageLoader;
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

/*
 * Rotate camera
 *   https://www.py4u.net/discuss/2130399
 *   https://stackoverflow.com/questions/48948092/javafx-rotating-camera-around-a-pivot
 */

/**
 * Instances of this class are used to control the flow of the Square-1 application.
 */
public class Square1Controller implements Initializable, ColorBean {
	/** The default colors of the Square-1 sides. */
	private static final Color[] DEFAULT_COLORS = new Color[] {
		Color.WHITE,
		Color.YELLOW,
		Color.ORANGE,
		Color.RED,
		Color.DARKBLUE,
		Color.GREEN,
		Color.GRAY,
		Color.BLACK,
	};
	/** The label with the moves to solve a Square-1 position. */
	@FXML private Label solution;
	/** The sub scene showing the Square-1. */
	@FXML private SubScene subScene;
	/** The menu bar. */
	@FXML private MenuBar menuBar;
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
	private Rotate rotateX;
	private Rotate rotateY;
	private Rotate rotateZ;
	private RotateTransition rotateTransition;
	/** The primary stage. */
	private Stage primaryStage;
	/** The group with x-, y- and z-axis. */
	private Group axis;
	/** The mesh group in the sub scene. */
	private Group meshGroup;
	/** The map with all pieces. */
	private Map<Character, AbstractPiece> pieceMap;
	/** The map with all radio menu items for piece visibility. */
	private Map<Character, RadioMenuItem> menuMap;
	/** The alert for key short cuts. */
	private Alert shortcutAlert;
	/** The dialog used to set the colors of the Square-1 sides. */
	private ColorDialog colorDialog;
	/** A color change support object used to send out change events. */
	private PropertyChangeSupport colorChangeSupport;
	/** The colors of the Square-1 sides. */
	private Color[] colors;
	/** The dialog used to enter a new position. */
	private PositionDialog positionDialog;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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
		// Colors
		colorChangeSupport = new PropertyChangeSupport(this);
		colorDialog = new ColorDialog(this);
		colors = DEFAULT_COLORS;
		// Piece Map
		pieceMap = Map.ofEntries(
				// Piece 1
				new AbstractMap.SimpleEntry<>('1', new Piece1(0, 1, this)),
				// Piece 2
				new AbstractMap.SimpleEntry<>('2', new Piece2(1, 1, this)),
				// Piece 3
				new AbstractMap.SimpleEntry<>('3', new Piece3(2, 1, this)),
				// Piece 4
				new AbstractMap.SimpleEntry<>('4', new Piece4(3, 1, this)),
				// Piece 5
				new AbstractMap.SimpleEntry<>('5', new Piece5(3, -1, this)),
				// Piece 6
				new AbstractMap.SimpleEntry<>('6', new Piece6(2, -1, this)),
				// Piece 7
				new AbstractMap.SimpleEntry<>('7', new Piece7(1, -1, this)),
				// Piece 8
				new AbstractMap.SimpleEntry<>('8', new Piece8(0, -1, this)),
				// Piece A
				new AbstractMap.SimpleEntry<>('A', new PieceA(0, 1, this)),
				// Piece B
				new AbstractMap.SimpleEntry<>('B', new PieceB(1, 1, this)),
				// Piece C
				new AbstractMap.SimpleEntry<>('C', new PieceC(2, 1, this)),
				// Piece D
				new AbstractMap.SimpleEntry<>('D', new PieceD(3, 1, this)),
				// Piece E
				new AbstractMap.SimpleEntry<>('E', new PieceE(3, -1, this)),
				// Piece F
				new AbstractMap.SimpleEntry<>('F', new PieceF(2, -1, this)),
				// Piece G
				new AbstractMap.SimpleEntry<>('G', new PieceG(1, -1, this)),
				// Piece H
				new AbstractMap.SimpleEntry<>('H', new PieceH(0, -1, this)),
				// Piece M
				new AbstractMap.SimpleEntry<>('M', new PieceM(0, this)),
				// Piece N
				new AbstractMap.SimpleEntry<>('N', new PieceN(1, this)));
		// Menu Map
		menuMap = Map.ofEntries(
				// Menu Toggle Piece 1
				new AbstractMap.SimpleEntry<>('1', menuPiece1),
				// Menu Toggle Piece 2
				new AbstractMap.SimpleEntry<>('2', menuPiece2), 
				// Menu Toggle Piece 3
				new AbstractMap.SimpleEntry<>('3', menuPiece3),
				// Menu Toggle Piece 4
				new AbstractMap.SimpleEntry<>('4', menuPiece4), 
				// Menu Toggle Piece 5
				new AbstractMap.SimpleEntry<>('5', menuPiece5),
				// Menu Toggle Piece 6
				new AbstractMap.SimpleEntry<>('6', menuPiece6), 
				// Menu Toggle Piece 7
				new AbstractMap.SimpleEntry<>('7', menuPiece7),
				// Menu Toggle Piece 8
				new AbstractMap.SimpleEntry<>('8', menuPiece8), 
				// Menu Toggle Piece A
				new AbstractMap.SimpleEntry<>('A', menuPieceA),
				// Menu Toggle Piece B
				new AbstractMap.SimpleEntry<>('B', menuPieceB), 
				// Menu Toggle Piece C
				new AbstractMap.SimpleEntry<>('C', menuPieceC),
				// Menu Toggle Piece D
				new AbstractMap.SimpleEntry<>('D', menuPieceD), 
				// Menu Toggle Piece E
				new AbstractMap.SimpleEntry<>('E', menuPieceE),
				// Menu Toggle Piece F
				new AbstractMap.SimpleEntry<>('F', menuPieceF), 
				// Menu Toggle Piece G
				new AbstractMap.SimpleEntry<>('G', menuPieceG),
				// Menu Toggle Piece H
				new AbstractMap.SimpleEntry<>('H', menuPieceH), 
				// Menu Toggle Piece M
				new AbstractMap.SimpleEntry<>('M', menuPieceM),
				// Menu Toggle Piece N
				new AbstractMap.SimpleEntry<>('N', menuPieceN));
		// Mesh Group
		meshGroup = new Group();
		axis = buildAxes();
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		// Register Radio Menu Items and
		// add all Nodes to View
		Set<Character> set = pieceMap.keySet();
		Character[] characters = set.toArray(new Character[set.size()]);
		for (Character c : characters) {
			AbstractPiece piece = pieceMap.get(c);
			RadioMenuItem radioMenuItem = menuMap.get(c);
			radioMenuItem.selectedProperty().bindBidirectional(piece.visibleProperty());
			meshGroup.getChildren().add(piece);
		}
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
		// Setup Animation
		rotateTransition = new RotateTransition(Duration.seconds(2.000), meshGroup);
		rotateTransition.setCycleCount(1);
		rotateTransition.setAxis(Rotate.Y_AXIS);
		rotateTransition.setByAngle(360);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
	}

	/**
	 * Sets the primary stage.
	 * @param primaryStage the primary stage.
	 * @throws IOException in case of load errors.
	 */
	public void setPrimaryStage(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		// Create ShortCuts Dialog
		shortcutAlert = createShortcutStage();
		positionDialog = createPositionDialog();
	}

	@Override
	public Color[] getDefaultColors() {
		return DEFAULT_COLORS;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		colorChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		colorChangeSupport.removePropertyChangeListener(listener);
	}

	@FXML
	void doAbout(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(ImageLoader.getLogoImageView());
		alert.setTitle("About");
		alert.setHeaderText(Version.getAppTitle() + "\nVersion " + Version.getAppVersion());
		alert.setContentText("Copyright " + Version.getAppVendor());
		alert.initOwner(primaryStage);
		alert.showAndWait();
	}

	@FXML
	void doExit(ActionEvent event) {
		primaryStage.hide();
	}

	@FXML
	void doShowPosition(ActionEvent event) {
		positionDialog.showAndWait();
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
	void doHotKeys(ActionEvent event) {
		shortcutAlert.showAndWait();
	}

	@FXML
	void doSolve(ActionEvent event) {
		System.out.println("Solve");
	}

	/**
	 * Opens color dialog and informs all listeners (pieces)
	 * about color changes.
	 * @param event the event fired by JavaFX.
	 */
	@FXML
	void doChangeColors(ActionEvent event) {
		Optional<Color[]> result = colorDialog.showAndWait();
		if (result.isPresent()) {
			Color[] oldColors = colors;
			colors = result.get();
			colorChangeSupport.firePropertyChange("colors", oldColors, colors);
		}
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

	/**
	 * Creates the stage with the key short cuts.
	 * @throws IOException in case of load errors.
	 */
	private Alert createShortcutStage() throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		// set properties
		alert.setTitle("Keyboard Shortcuts");
		alert.setHeaderText("Keyboard shortcut overview");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(primaryStage.getIcons().get(0));
		alert.setResizable(false);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initStyle(StageStyle.UTILITY);
		// load content
		URL resource = Square1Controller.class.getResource("/net/treimers/square1/keyshortcuts.fxml");
		FXMLLoader loader = new FXMLLoader(resource);
		Parent root = loader.load();
		// set view's controller
		KeyboardShortcutController controller = loader.getController();
		controller.setMenuBar(menuBar);
		// add content to view
		alert.getDialogPane().setContent(root);
		alert.initOwner(primaryStage);
		return alert;
	}

	/**
	 * Creates a new position dialog allowing the user to define a position to be solved.
	 * @return a new position dialog.
	 * @throws IOException in case of load errors.
	 */
	private PositionDialog createPositionDialog() throws IOException {
		PositionDialog dialog = new PositionDialog(this);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(primaryStage.getIcons().get(0));
		dialog.initOwner(primaryStage);
		return dialog;
	}
}
