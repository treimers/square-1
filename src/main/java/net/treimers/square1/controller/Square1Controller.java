package net.treimers.square1.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.treimers.square1.Version;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.dialog.ColorDialog;
import net.treimers.square1.view.dialog.PositionDialog;
import net.treimers.square1.view.misc.ImageLoader;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.SmartGroup;
import net.treimers.square1.view.piece.Layer;

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
		Color.DARKBLUE,
		Color.RED,
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
	/** The menu with the piece visibility radio menu items. */
	@FXML private Menu menuPieces;
	private double mouseOldX;
	private double mouseOldY;
	private double mousePosX;
	private double mousePosY;
	private Rotate rotateX;
	private Rotate rotateY;
	private Rotate rotateZ;
	/** The primary stage. */
	private Stage primaryStage;
	/** The group with x-, y- and z-axis. */
	private Group axis;
	/** The mesh group in the sub scene. */
	private MeshGroup meshGroup;
	/** The map with all radio menu items for piece visibility. */
	/** The alert for key short cuts. */
	private Alert shortcutAlert;
	/** The dialog used to set the colors of the Square-1 sides. */
	private ColorDialog colorDialog;
	/** A color change support object used to send out change events. */
	private PropertyChangeSupport colorChangeSupport;
	/** The colors of the Square-1 sides. */
	private Color[] colors;
	/** The position that shall be solved. */
	private Position position;
	/** The dialog used to enter a new position. */
	private PositionDialog positionDialog;
	private PositionDialogController positionDialogController;
	private Map<Character, RadioMenuItem> menuMap;
	private String lastFilename;

	public Square1Controller() {
		position = new Position();
		menuMap = new HashMap<>();
		colors = DEFAULT_COLORS;
		colorChangeSupport = new PropertyChangeSupport(this);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Colors
		colorDialog = new ColorDialog(this);
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		meshGroup = new MeshGroup(this);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		meshGroup.setContent(position);
		subScene.setRoot(smartGroup);
		subScene.setFill(Color.SILVER);
		// Key
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		subScene.setCamera(camera);
		// Mesh Group
		axis = buildAxes();
		//
		ObservableList<MenuItem> menuItems = menuPieces.getItems();
		for (MenuItem menuItem : menuItems) {
			if (menuItem instanceof Menu) {
				Menu menu = (Menu) menuItem;
				retrieveToggleMenues(menu);
			}
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
	}

	private void retrieveToggleMenues(Menu menu) {
		ObservableList<MenuItem> items = menu.getItems();
		for (MenuItem menuItem : items) {
			if (menuItem instanceof Menu)
				retrieveToggleMenues((Menu) menuItem);
			else if (menuItem instanceof RadioMenuItem)
				menuMap.put(menuItem.getId().charAt(0), (RadioMenuItem) menuItem);
		}
	}

	/**
	 * Sets the primary stage.
	 * @param primaryStage the primary stage.
	 * @throws IOException in case of load errors.
	 */
	public void setPrimaryStage(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		// Create Dialog
		shortcutAlert = createShortcutStage();
		positionDialog = createPositionDialog();
		// Key Event to enable Piece Menu
		menuPieces.setVisible(false);
		final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.isShortcutDown() && keyEvent.isAltDown() && keyEvent.isShiftDown()
						&& keyEvent.getCode() == KeyCode.A)
					menuPieces.setVisible(!menuPieces.isVisible());
				keyEvent.consume();
			}
		};
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
	}

	@Override
	public Color[] getColors() {
		return Arrays.copyOf(colors, colors.length);
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
	void doAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(ImageLoader.getLogoImageView());
		alert.setTitle("About");
		alert.setHeaderText(Version.getAppTitle() + "\nVersion " + Version.getAppVersion());
		alert.setContentText("Copyright " + Version.getAppVendor());
		alert.initOwner(primaryStage);
		alert.showAndWait();
	}

	@FXML
	public void doLoadPosition() throws IOException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Load Position");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		chooser.setInitialFileName(lastFilename);
		File file = chooser.showOpenDialog(primaryStage);
		if (file != null) {
			byte[] pos = Files.readAllBytes(file.toPath());
			String posString = new String(pos, StandardCharsets.UTF_8).replaceAll("\\s", "");
			position = Position.fromString(posString);
			meshGroup.setContent(position);
		}
	}

	@FXML
	public void doSavePosition() throws IOException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Position");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		chooser.setInitialFileName(lastFilename);
		File file = chooser.showSaveDialog(primaryStage);
		if (file != null) {
			String text = position.toString();
			Files.write(file.toPath(), text.getBytes(StandardCharsets.UTF_8));
		}
	}

	@FXML
	void doExit() {
		primaryStage.hide();
	}

	/**
	 * Opens color dialog and informs all listeners (pieces)
	 * about color changes.
	 * @param event the event fired by JavaFX.
	 */
	@FXML
	void doChangeColors() {
		Optional<Color[]> result = colorDialog.showAndWait();
		if (result.isPresent()) {
			Color[] oldColors = colors;
			colors = result.get();
			colorChangeSupport.firePropertyChange("colors", oldColors, colors);
		}
	}

	@FXML
	void doShowPosition() {
		Map<Layer, Character[]> pieces = position.getPieces();
		Layer[] layers = pieces.keySet().toArray(new Layer[0]);
		for (Layer layer : layers) {
			Character[] pieceNames = pieces.get(layer);
			for (Character character : pieceNames) {
				RadioMenuItem radioMenuItem = menuMap.get(character);
				if (radioMenuItem != null)
					radioMenuItem.setSelected(true);
			}
		}
		positionDialogController.setPosition(position);
		Optional<Position> result = positionDialog.showAndWait();
		if (result.isPresent()) {
			position = result.get();
			meshGroup.setContent(position);
		}
	}

	@FXML
	void doSolvePosition() {
		System.out.println("Solve");
	}

	@FXML
	void doXClock() {
		rotateX.setAngle(rotateX.getAngle() + 10.0f);
	}

	@FXML
	void doXAntiClock() {
		rotateX.setAngle(rotateX.getAngle() - 10.0f);
	}

	@FXML
	void doYClock() {
		rotateY.setAngle(rotateY.getAngle() + 10.0f);
	}

	@FXML
	void doYAntiClock() {
		rotateY.setAngle(rotateY.getAngle() - 10.0f);
	}

	@FXML
	void doZClock() {
		rotateZ.setAngle(rotateZ.getAngle() + 10.0f);
	}

	@FXML
	void doZAntiClock() {
		rotateZ.setAngle(rotateZ.getAngle() - 10.0f);
	}

	@FXML
	void doToggleAxis() {
		ObservableList<Node> children = meshGroup.getChildren();
		if (children.contains(axis))
			children.remove(axis);
		else
			children.add(axis);
	}

	/**
	 * Performs an animated 360° rotation of the Square-1.
	 */
	@FXML
	void doRotate() {
		meshGroup.animate();
	}

	@FXML
	void doReset() {
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
		meshGroup.setPieceVisibility(c, source.isSelected());
	}

	@FXML
	void doHideAll() {
		/*
		Set<Entry<Character, AbstractPiece>> entrySet = pieceMap.entrySet();
		for (Entry<Character, AbstractPiece> entry : entrySet)
			entry.getValue().setVisible(false);
			*/
	}

	@FXML
	void doShowAll() {
		/*
		Set<Entry<Character, AbstractPiece>> entrySet = pieceMap.entrySet();
		for (Entry<Character, AbstractPiece> entry : entrySet)
			entry.getValue().setVisible(true);
			*/
	}

	@FXML
	void doHotKeys() {
		shortcutAlert.showAndWait();
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
		final Box xAxis = new Box(3.5f, 0.02f, 0.02f);
		xAxis.setMaterial(redMaterial);
		final Box yAxis = new Box(0.02f, 3.5f, 0.02f);
		yAxis.setMaterial(greenMaterial);
		final Box zAxis = new Box(0.02f, 0.02f, 3.5f);
		zAxis.setMaterial(blueMaterial);
		Sphere xSphere = new Sphere(0.04f);
		xSphere.setTranslateX(1.75f);
		xSphere.setMaterial(redMaterial);
		Sphere ySphere = new Sphere(0.04f);
		ySphere.setTranslateY(1.75f);
		ySphere.setMaterial(greenMaterial);
		Sphere zSphere = new Sphere(0.04f);
		zSphere.setTranslateZ(1.75f);
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
		// dialog content
		URL resource = getClass().getResource("/net/treimers/square1/positiondialog.fxml");
		FXMLLoader loader = new FXMLLoader(resource);
		Parent root = loader.load();
		// controller
		positionDialogController = loader.getController();
		positionDialogController.init(this);
		// dialog
		PositionDialog dialog = new PositionDialog(root, positionDialogController);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(primaryStage.getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		return dialog;
	}
}
