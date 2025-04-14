package net.treimers.square1.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import net.treimers.square1.Version;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.MoveSequence;
import net.treimers.square1.model.Position;
import net.treimers.square1.model.Square1Data;
import net.treimers.square1.model.persistence.FileStore;
import net.treimers.square1.model.persistence.PreferencesStore;
import net.treimers.square1.solver.Scrambler;
import net.treimers.square1.view.dialog.ColorDialog;
import net.treimers.square1.view.dialog.PositionDialog;
import net.treimers.square1.view.dialog.SolveDialog;
import net.treimers.square1.view.dialog.Square1Alert;
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
 * Instances of this class are used to control the flow of the Square-1
 * application.
 */
public class Square1Controller implements Initializable, ColorBean, PropertyChangeListener, MenuHandler {
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
	private static final String COLOR = null;
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
	/** The dialog used to enter a new solution. */
	private SolveDialog solveDialog;
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
	/** The solution String. */
	private MoveSequence solution;
	/** Map with all radio menu items used to bind to visibility of pieces. */
	private Map<Character, CheckMenuItem> menuMap;
	/** The controller for the position dialog. */
	private PositionController positionDialogController;
	/** The controller for the solution dialog. */
	private SolveController solveDialogController;
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
	/** The file store. */
	private FileStore fileStore;
	/** The prefs store. */
	private PreferencesStore preferencesStore;

	/**
	 * Creates a new instance.
	 * 
	 * @throws Square1Exception in case of any errors.
	 */
	public Square1Controller() throws Square1Exception {
		menuMap = new HashMap<>();
		fileStore = new FileStore();
		preferencesStore = new PreferencesStore(this);
		Square1Data data = preferencesStore.restore();
		position = data.buildPosition();
		colors = data.buildColors();
		colorChangeSupport = new PropertyChangeSupport(this);
		solution = new MoveSequence();
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
	 * @throws Square1Exception in case of any errors.
	 */
	public void setPrimaryStage(Stage primaryStage) throws Square1Exception {
		this.primaryStage = primaryStage;
		// Create Dialogs
		aboutDialog = createAboutDialog(primaryStage);
		colorDialog = createColorDialog(primaryStage);
		helpDialog = createHelpDialog(primaryStage);
		shortcutDialog = createShortcutDialog(primaryStage);
		positionDialog = createPositionDialog(primaryStage);
		solveDialog = createSolveDialog(primaryStage);
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
			CheckMenuItem checkMenuItem = menuMap.get(c);
			if (pieceMap.containsKey(c)) {
				checkMenuItem.setDisable(false);
				checkMenuItem.setSelected(true);
				AbstractPiece piece = pieceMap.get(c);
				piece.visibleProperty().bind(checkMenuItem.selectedProperty());
			} else {
				checkMenuItem.setSelected(false);
				checkMenuItem.setDisable(true);
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
	void doLoad() {
		try {
			if (lastFile != null && lastDir != null) {
				loadFileChooser.setInitialDirectory(lastDir);
				loadFileChooser.setInitialFileName(lastFile.getName());
			}
			File file = loadFileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				lastDir = file.getParentFile();
				lastFile = file;
				fileStore.setFile(file);
				Square1Data data = fileStore.restore();
				// get position
				position = new Position(data.getPositionString());
				meshGroup.setContent(position);
				// get colors
				Color[] oldColors = colors;
				colors = data.buildColors();
				colorChangeSupport.firePropertyChange(COLOR, oldColors, colors);
				solution = data.getSolution();
			}
			preferencesStore.store(new Square1Data(colors, position, solution));
		} catch (Square1Exception e) {
			alertException(e);
		}
	}

	/**
	 * Called when user requires save position file dialog.
	 */
	@FXML
	void doSave() {
		try {
			if (lastFile != null && lastDir != null) {
				saveFileChooser.setInitialDirectory(lastDir);
				saveFileChooser.setInitialFileName(lastFile.getName());
			}
			File file = saveFileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				lastDir = file.getParentFile();
				lastFile = file;
				Square1Data data = new Square1Data(colors, position, solution);
				fileStore.setFile(file);
				fileStore.store(data);
			}
		} catch (Square1Exception e) {
			alertException(e);
		}

	}

	/**
	 * Called when user requires application exit.
	 */
	@FXML
	void doExit() {
		try {
			preferencesStore.store(new Square1Data(colors, position, solution));
		} catch (Square1Exception e) {
			alertException(e);
		}
		primaryStage.hide();
	}

	/**
	 * Called when user requires change color dialog.
	 */
	@FXML
	void doChangeColors() {
		colorDialog.setColors(colors);
		Optional<Color[]> result = colorDialog.showAndWait();
		if (result.isPresent()) {
			Color[] oldColors = colors;
			colors = result.get();
			colorChangeSupport.firePropertyChange(COLOR, oldColors, colors);
		}
		try {
			preferencesStore.store(new Square1Data(colors, position, solution));
		} catch (Square1Exception e) {
			alertException(e);
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
			solution = new MoveSequence();
			meshGroup.setContent(position);
			try {
				preferencesStore.store(new Square1Data(colors, position, solution));
			} catch (Square1Exception e) {
				alertException(e);
			}
		}
	}

	/**
	 * Called when user requires scramble current position.
	 * 
	 * @throws Square1Exception
	 * @throws InterruptedException
	 */
	@FXML
	void doScramble() throws Square1Exception, InterruptedException {
		Scrambler scrambler = new Scrambler();
		List<Position> positions = scrambler.generateScramble(position);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		for (int i = 0; i < positions.size(); i++) {
			Position movePosition = positions.get(i);
			KeyFrame keyFrame = new KeyFrame(
					Duration.millis(500 * (i + 1)),
					event -> {
						meshGroup.setContent(movePosition);
					});
			timeline.getKeyFrames().add(keyFrame);
			position = movePosition;
		}
		timeline.play();
	}

	/**
	 * Called when user requires solve position.
	 */
	@FXML
	void doSolvePosition() {
		// center solve dialog on screen
		Platform.runLater(() -> {
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Window window = solveDialog.getDialogPane().getScene().getWindow();
			window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
			window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
		});
		solveDialogController.setPosition(position);
		Optional<Position> result = solveDialog.showAndWait();
		if (result.isPresent()) {
			position = result.get();
			meshGroup.setContent(position);
		}
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
	void doToggleAxis(ActionEvent event) {
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
		// center help dialog on screen
		Platform.runLater(() -> {
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Window window = helpDialog.getDialogPane().getScene().getWindow();
			window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
			window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
		});
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
			else if (menuItem instanceof CheckMenuItem)
				menuMap.put(menuItem.getId().charAt(0), (CheckMenuItem) menuItem);
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
			CheckMenuItem checkMenuItem = menuMap.get(c);
			checkMenuItem.setSelected(selected);
		}
	}

	/**
	 * Creates a group with x-, y- and z-axis.
	 * 
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
		Alert alert = new Square1Alert(AlertType.INFORMATION, this);
		alert.setGraphic(ImageLoader.getLogoImageView());
		alert.setTitle("About");
		alert.setHeaderText(Version.getAppTitle() + "\nVersion " + Version.getAppVersion());
		alert.setContentText("Copyright © " + Version.getAppVendor());
		alert.initOwner(primaryStage);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.centerOnScreen();
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
		Alert alert = new Square1Alert(AlertType.INFORMATION, this);
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
			stage.centerOnScreen();
			// load the view
			URL resource = getClass().getResource("/net/treimers/square1/helppanel.fxml");
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
		ColorDialog dialog = new ColorDialog(this, this);
		dialog.initOwner(primaryStage);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.centerOnScreen();
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
		stage.centerOnScreen();
		alert.setResizable(false);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initStyle(StageStyle.UTILITY);
		alert.initOwner(primaryStage);
		// load content
		URL resource = Square1Controller.class.getResource("/net/treimers/square1/keyboardshortcutpanel.fxml");
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// set view's controller
			KeyboardShortcutController controller = loader.getController();
			controller.setMenuBar(menuBar);
			// register controller method called on showing event
			EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
				@Override
				public void handle(DialogEvent e) {
					controller.handleShowing();
				}
			};
			alert.setOnShowing(eventHandler);
			// add content to view
			alert.getDialogPane().setContent(root);
		} catch (IOException e) {
			alertException(e);
		}
		return alert;
	}

	/**
	 * Creates a new position dialog allowing the user to define a position to be
	 * solved.
	 * 
	 * @param primaryStage the primary stage of the app.
	 * @return a new position dialog.
	 * @throws Square1Exception in case of any errors.
	 */
	private PositionDialog createPositionDialog(Stage primaryStage) throws Square1Exception {
		try {
			// dialog content
			URL resource = getClass().getResource("/net/treimers/square1/positionpanel.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// controller
			positionDialogController = loader.getController();
			positionDialogController.init(this);
			// dialog
			PositionDialog dialog = null;
			dialog = new PositionDialog(this, root, positionDialogController);
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(primaryStage.getIcons().get(0));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.centerOnScreen();
			dialog.initOwner(primaryStage);
			return dialog;
		} catch (IOException e) {
			throw new Square1Exception(e);
		}
	}

	/**
	 * Creates a new solve dialog allowing the user to solve a scramble Square-1.
	 * 
	 * @param primaryStage the primary stage of the app.
	 * @return a new solve dialog.
	 * @throws Square1Exception in case of any errors.
	 */
	private SolveDialog createSolveDialog(Stage primaryStage) throws Square1Exception {
		try {
			// dialog content
			URL resource = getClass().getResource("/net/treimers/square1/solvepanel.fxml");
			FXMLLoader loader = new FXMLLoader(resource);
			Parent root = loader.load();
			// controller
			solveDialogController = loader.getController();
			solveDialogController.init(this);
			// dialog
			SolveDialog dialog = new SolveDialog(this, root, solveDialogController);
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(primaryStage.getIcons().get(0));
			stage.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			stage.centerOnScreen();
			return dialog;
		} catch (IOException e) {
			throw new Square1Exception(e);
		}
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

	@Override
	public void setMenusEnabled(boolean enable) {
		ObservableList<Menu> menus = menuBar.getMenus();
		for (Menu menu : menus)
			menu.setVisible(enable);
	}
}
