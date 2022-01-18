package net.treimers.square1.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.prefs.Preferences;

import javafx.collections.ObservableList;
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
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.treimers.square1.Square1;
import net.treimers.square1.Version;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;
import net.treimers.square1.model.Side;
import net.treimers.square1.view.dialog.ColorDialog;
import net.treimers.square1.view.dialog.PositionDialog;
import net.treimers.square1.view.misc.ImageLoader;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.SmartGroup;
import net.treimers.square1.view.piece.AbstractPiece;

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
public class Square1Controller implements Initializable, ColorBean, PropertyChangeListener {
	/** The color node in the prefs tree. */
	private static final String COLORS_NODE = "colors";
	/** The red key for user prefs. */
	private static final String RED = ".red";
	/** The green key for user prefs. */
	private static final String GREEN = ".green";
	/** The blue key for user prefs. */
	private static final String BLUE = ".blue";
	/** The opacity key for user prefs. */
	private static final String OPACITY = ".opacity";
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
	/** The primary stage. */
	private Stage primaryStage;
	/** The help dialog. */
	private Alert helpDialog;
	/** The about dialog. */
	private Alert aboutDialog;
	/** The dialog for key short cuts. */
	private Alert shortcutDialog;
	/** The dialog used to set the colors of the Square-1 sides. */
	private ColorDialog colorDialog;
	/** The dialog used to enter a new position. */
	private PositionDialog positionDialog;
	/** The load file chooser. */
	private FileChooser loadFileChooser;
	/** The load file chooser. */
	private FileChooser saveFileChooser;
	/** The group with x-, y- and z-axis. */
	private Group axis;
	/** The mesh group in the sub scene. */
	private MeshGroup meshGroup;
	/** The map with all radio menu items for piece visibility. */
	/** A color change support object used to send out change events. */
	private PropertyChangeSupport colorChangeSupport;
	/** The colors of the Square-1 sides. */
	private Color[] colors;
	/** The position that shall be solved. */
	private Position position;
	/** Map with all radio menu items used to bind to visibility of pieces. */
	private Map<Character, RadioMenuItem> menuMap;
	/** The controller for the position dialog. */
	private PositionDialogController positionDialogController;
	/** Last directory of load and save dialogs. */
	private File lastDir;
	/** Last file of load and save dialogs. */
	private File lastFile;
	/** The current x rotation of Square-1 mesh group. */
	private Rotate rotateX;
	/** The current y rotation of Square-1 mesh group. */
	private Rotate rotateY;
	/** The current z rotation of Square-1 mesh group. */
	private Rotate rotateZ;
	/** Old mouse x position (used for rotations of Square-1 mesh group with mouse.) */
	private double mouseOldX;
	/** Old mouse y position (used for rotations of Square-1 mesh group with mouse.) */
	private double mouseOldY;
	/** Mouse x position (used for rotations of Square-1 mesh group with mouse.) */
	private double mousePosX;
	/** Mouse y position (used for rotations of Square-1 mesh group with mouse.) */
	private double mousePosY;

	/**
	 * Creates a new instance.
	 */
	public Square1Controller() {
		position = restorePosition();
		menuMap = new HashMap<>();
		colors = restoreColorScheme();
		colorChangeSupport = new PropertyChangeSupport(this);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Piece Menu
		ObservableList<MenuItem> menuItems = menuPieces.getItems();
		for (MenuItem menuItem : menuItems) {
			if (menuItem instanceof Menu) {
				Menu menu = (Menu) menuItem;
				retrieveToggleMenues(menu);
			}
		}
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		meshGroup = new MeshGroup(this);
		meshGroup.addPieceChangeListener(this);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		meshGroup.setContent(position);
		subScene.setRoot(smartGroup);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		subScene.setCamera(camera);
		// Mesh Group
		axis = buildAxes();
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

	/**
	 * Sets the primary stage.
	 * 
	 * @param primaryStage the primary stage.
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// Create Dialogs
		aboutDialog = createAboutDialog(primaryStage);
		colorDialog = createColorDialog(primaryStage);
		helpDialog = createHelpDialog(primaryStage);
		shortcutDialog = createShortcutDialog(primaryStage);
		positionDialog = createPositionDialog(primaryStage);
		loadFileChooser = createLoadFileChooser();
		saveFileChooser = createSaveFileChooser();
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

	// The ColorBean interface methods
	@Override
	public Color[] getColors() {
		return Arrays.copyOf(colors, colors.length);
	}

	@Override
	public Color[] getDefaultColors() {
		return Arrays.copyOf(DEFAULT_COLORS, DEFAULT_COLORS.length);
	}

	@Override
	public void addColorChangeListener(PropertyChangeListener listener) {
		colorChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removeColorChangeListener(PropertyChangeListener listener) {
		colorChangeSupport.removePropertyChangeListener(listener);
	}

	// the PropertyChangeListener interface method
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		@SuppressWarnings("unchecked")
		Map<Character, AbstractPiece> pieceMap = (Map<Character, AbstractPiece>) evt.getNewValue();
		Set<Character> menuSet = menuMap.keySet();
		for (Character c : menuSet) {
			RadioMenuItem radioMenuItem = menuMap.get(c);
			if (pieceMap.containsKey(c)) {
				radioMenuItem.setDisable(false);
				radioMenuItem.setSelected(true);
				AbstractPiece piece = pieceMap.get(c);
				piece.visibleProperty().bind(radioMenuItem.selectedProperty());
			} else {
				radioMenuItem.setSelected(false);
				radioMenuItem.setDisable(true);
			}
		}
	}

	/**
	 * Display an error dialog with optional exception stack trace.
	 * 
	 * @param ex the exception.
	 */
	public void alertException(Exception ex) {
		// based on http://code.makery.ch/blog/javafx-dialogs-official
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Unexpected error");
		alert.setContentText(ex.getClass().getSimpleName() + ": " + ex.getMessage());
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		Label label = new Label("Stacktrace:");
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

	/**
	 * Called when user requires about dialog.
	 */
	@FXML
	void doAbout() {
		aboutDialog.showAndWait();
	}

	/**
	 * Called when user requires load position file dialog.
	 */
	@FXML
	void doLoadPosition() {
		try {
			if (lastFile != null && lastDir != null) {
				loadFileChooser.setInitialDirectory(lastDir);
				loadFileChooser.setInitialFileName(lastFile.getName());
			}
			File file = loadFileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				lastDir = file.getParentFile();
				lastFile = file;
				byte[] pos = Files.readAllBytes(file.toPath());
				String posString = new String(pos, StandardCharsets.UTF_8).replaceAll("\\s", "");
				position = new Position(posString);
				meshGroup.setContent(position);
				persistPosition(position);
			}
		} catch (IOException e) {
			alertException(e);
		}
	}

	/**
	 * Called when user requires save position file dialog.
	 */
	@FXML
	void doSavePosition() {
		try {
			if (lastFile != null && lastDir != null) {
				saveFileChooser.setInitialDirectory(lastDir);
				saveFileChooser.setInitialFileName(lastFile.getName());
			}
			File file = saveFileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				lastDir = file.getParentFile();
				lastFile = file;
				String text = position.toString();
				Files.write(file.toPath(), text.getBytes(StandardCharsets.UTF_8));
			}
		} catch (IOException e) {
			alertException(e);
		}

	}

	/**
	 * Called when user requires application exit.
	 */
	@FXML
	void doExit() {
		primaryStage.hide();
	}

	/**
	 * Called when user requires change color dialog.
	 */
	@FXML
	void doChangeColors() {
		Optional<Color[]> result = colorDialog.showAndWait();
		if (result.isPresent()) {
			Color[] oldColors = colors;
			colors = result.get();
			colorChangeSupport.firePropertyChange(COLORS_NODE, oldColors, colors);
			persistColorScheme(colors);
		}
	}

	/**
	 * Called when user requires change position dialog.
	 */
	@FXML
	void doChangePosition() {
		positionDialogController.setPosition(position);
		Optional<Position> result = positionDialog.showAndWait();
		if (result.isPresent()) {
			position = result.get();
			meshGroup.setContent(position);
			persistPosition(position);
		}
	}

	/**
	 * Called when user requires solve position.
	 */
	@FXML
	void doSolvePosition() {
	}

	/**
	 * Called when user requires increase of x rotation.
	 */
	@FXML
	void doXClock() {
		rotateX.setAngle(rotateX.getAngle() + 10.0f);
	}

	/**
	 * Called when user requires decrease of x rotation.
	 */
	@FXML
	void doXAntiClock() {
		rotateX.setAngle(rotateX.getAngle() - 10.0f);
	}

	/**
	 * Called when user requires increase of y rotation.
	 */
	@FXML
	void doYClock() {
		rotateY.setAngle(rotateY.getAngle() + 10.0f);
	}

	/**
	 * Called when user requires decrease of y rotation.
	 */
	@FXML
	void doYAntiClock() {
		rotateY.setAngle(rotateY.getAngle() - 10.0f);
	}

	/**
	 * Called when user requires increase of z rotation.
	 */
	@FXML
	void doZClock() {
		rotateZ.setAngle(rotateZ.getAngle() + 10.0f);
	}

	/**
	 * Called when user requires decrease of z rotation.
	 */
	@FXML
	void doZAntiClock() {
		rotateZ.setAngle(rotateZ.getAngle() - 10.0f);
	}

	/**
	 * Called when user requires toggle of axis.
	 */
	@FXML
	void doToggleAxis() {
		ObservableList<Node> children = meshGroup.getChildren();
		if (children.contains(axis))
			children.remove(axis);
		else
			children.add(axis);
	}

	/**
	 * Called when user requires an animated 360° rotation of the Square-1.
	 */
	@FXML
	void doRotate() {
		meshGroup.animate();
	}

	/**
	 * Called when user requires reset of all Square-1 rotations.
	 */
	@FXML
	void doReset() {
		rotateX.setAngle(0.0f);
		rotateY.setAngle(0.0f);
		rotateZ.setAngle(0.0f);
	}

	/**
	 * Called when user requires hiding all Square-1 pieces.
	 */
	@FXML
	void doHideAll() {
		selectAllPieces(false);
	}

	/**
	 * Called when user requires showing all Square-1 pieces.
	 */
	@FXML
	void doShowAll() {
		selectAllPieces(true);
	}

	/**
	 * Called when user requires hot key dialog.
	 */
	@FXML
	void doHotKeys() {
		shortcutDialog.showAndWait();
	}

	/**
	 * Called when user requires help dialog.
	 */
	@FXML
	void doHelp() {
		helpDialog.showAndWait();
	}

	/**
	 * Retrieve all radio menus.
	 * 
	 * @param menu start menu for searching.
	 */
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
	 * Shows or hides all pieces via the radio menu items.
	 * 
	 * @param selected true for show, false for hide.
	 */
	private void selectAllPieces(boolean selected) {
		Set<Character> menuSet = menuMap.keySet();
		for (Character c : menuSet) {
			RadioMenuItem radioMenuItem = menuMap.get(c);
			radioMenuItem.setSelected(selected);
		}
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
	 * Creates a new about dialog.
	 * 
	 * @param primaryStage the primary stage.
	 * @return the new about dialog.
	 */
	private Alert createAboutDialog(Stage primaryStage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(ImageLoader.getLogoImageView());
		alert.setTitle("About");
		alert.setHeaderText(Version.getAppTitle() + "\nVersion " + Version.getAppVersion());
		alert.setContentText("Copyright © " + Version.getAppVendor());
		alert.initOwner(primaryStage);
		return alert;
	}

	/**
	 * Creates a new help dialog.
	 * 
	 * @param primaryStage the primary stage.
	 * @return the new help dialog.
	 */
	private Alert createHelpDialog(Stage primaryStage) {
		// create dialog
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Square-1 Help");
		alert.setHeaderText("Square-1 user manual");
		alert.getDialogPane().setMinWidth(1200);
		alert.getDialogPane().setMinHeight(800);
		alert.initOwner(primaryStage);
		try {
			// load and set the stage icon
			Image image = ImageLoader.getLogoImage();
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(image);
			// load the view
			URL resource = getClass().getResource("/net/treimers/square1/helpview.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// get view's controller and propagate stage to controller
			HelpController controller = loader.getController();
			controller.setMainController(this);
			// apply the scene to the dialog
			alert.getDialogPane().setContent(root);
		} catch (IOException e) {
			alertException(e);
		}
		return alert;
	}

	/**
	 * Creates a new color dialog.
	 * 
	 * @param primaryStage the primary stage.
	 * @return the new color dialog.
	 */
	private ColorDialog createColorDialog(Stage primaryStage) {
		ColorDialog dialog = new ColorDialog(this);
		dialog.initOwner(primaryStage);
		return dialog;
	}

	/**
	 * Creates a new key shortcuts dialog.
	 * 
	 * @param primaryStage the primary stage.
	 * @return the new key shortcuts dialog.
	 */
	private Alert createShortcutDialog(Stage primaryStage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		// set properties
		alert.setTitle("Keyboard Shortcuts");
		alert.setHeaderText("Keyboard shortcut overview");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(primaryStage.getIcons().get(0));
		alert.setResizable(false);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(primaryStage);
		// load content
		URL resource = Square1Controller.class.getResource("/net/treimers/square1/keyshortcuts.fxml");
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// set view's controller
			KeyboardShortcutController controller = loader.getController();
			controller.setMenuBar(menuBar);
			// register controller method called on showing event
			alert.setOnShowing(new EventHandler<DialogEvent>() {
				@Override
				public void handle(DialogEvent e) {
					controller.handleShowing();
				}
			});
			// add content to view
			alert.getDialogPane().setContent(root);
		} catch (IOException e) {
			alertException(e);
		}
		return alert;
	}

	/**
	 * Creates a new position dialog allowing the user to define a position to be solved.
	 * @return a new position dialog.
	 */
	private PositionDialog createPositionDialog(Stage primaryStage) {
		PositionDialog dialog = null;
		try {
			// dialog content
			URL resource = getClass().getResource("/net/treimers/square1/positiondialog.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// controller
			positionDialogController = loader.getController();
			positionDialogController.init(this);
			// dialog
			dialog = new PositionDialog(root, positionDialogController);
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(primaryStage.getIcons().get(0));
			stage.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
		} catch (IOException e) {
			alertException(e);
		}
		return dialog;
	}

	/**
	 * Creates a new file load dialog.
	 * 
	 * @return the file load dialog.
	 */
	private FileChooser createLoadFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Load Position");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		return chooser;
	}

	/**
	 * Creates a new file save dialog.
	 * 
	 * @return the file save dialog.
	 */
	private FileChooser createSaveFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Position");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		return chooser;
	}

	/**
 	 * Persists a position to user preferences.
 	 * 
	 * @param position the position.
	 */
	private void persistPosition(Position position) {
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		userNode.put("position", position.toString());
	}

	/**
 	 * Restores a position from user preferences.
 	 * 
	 * @return the position.
	 */
	private Position restorePosition() {
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		String positonString = userNode.get("position", Position.SOLVED_POSITION_STRING);
		return new Position(positonString);
	}

	/**
	 * Persists all colors to user preferences.
	 * 
	 * @param sides all Square-1 sides as enum array.
	 * @param colors the color array.
	 */
	private void persistColorScheme(Color[] colors) {
		Side[] allSides = Side.values();
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		Preferences colorNode = userNode.node(COLORS_NODE);
		for (Side side : allSides) {
			Color color = colors[side.ordinal()];
			colorNode.putDouble(side.name().toLowerCase() + RED, color.getRed());
			colorNode.putDouble(side.name().toLowerCase() + GREEN, color.getGreen());
			colorNode.putDouble(side.name().toLowerCase() + BLUE, color.getBlue());
			colorNode.putDouble(side.name().toLowerCase() + OPACITY, color.getOpacity());
		}
	}
	
	/**
	 * Restores all colors from user preferences.
	 * 
	 * @param sides all Square-1 sides as enum array.
	 * @return the color array loaded.
	 */
	private Color[] restoreColorScheme() {
		Side[] allSides = Side.values();
		Color[] retval = new Color[allSides.length];
		Preferences userNode = Preferences.userNodeForPackage(Square1.class);
		Preferences colorNode = userNode.node(COLORS_NODE);
		for (Side side : allSides) {
			Color defaultColor = DEFAULT_COLORS[side.ordinal()];
			double red = colorNode.getDouble(side.name().toLowerCase() + RED, defaultColor.getRed());
			double green = colorNode.getDouble(side.name().toLowerCase() + GREEN, defaultColor.getGreen());
			double blue = colorNode.getDouble(side.name().toLowerCase() + BLUE, defaultColor.getBlue());
			double opacity = colorNode.getDouble(side.name().toLowerCase() + OPACITY, defaultColor.getOpacity());
			retval[side.ordinal()] = new Color(red, green, blue, opacity);
		}
		return retval;
	}
}
