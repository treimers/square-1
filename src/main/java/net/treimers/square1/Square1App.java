package net.treimers.square1;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import net.treimers.square1.view.Constants;
import net.treimers.square1.view.CornerPiece;
import net.treimers.square1.view.EdgePiece;
import net.treimers.square1.view.ImageLoader;
import net.treimers.square1.view.MiddlePiece;
import net.treimers.square1.view.SmartGroup;

public class Square1App extends Application {
	private static int WIDTH = 1400;
	private static int HEIGHT = 800;
	private double mouseOldX;
	private double mouseOldY;
	private double mousePosX;
	private double mousePosY;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// set the stage title
		primaryStage.setTitle("Square-1");
		// load and set the stage icon
		Image image = ImageLoader.getLogoImage();
		primaryStage.getIcons().add(image);
		// Scene
		SmartGroup sceneRoot = new SmartGroup();
		Scene scene = new Scene(sceneRoot, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-10);
		scene.setCamera(camera);
		// edges
		EdgePiece edge1 = new EdgePiece(1.0f, 0, 1, Constants.BLACK, Constants.BLACK, Constants.YELLOW, Constants.BLACK,
				Constants.WHITE);
		EdgePiece edge2 = new EdgePiece(1.0f, 1, 1, Constants.BLACK, Constants.BLACK, Constants.ORANGE, Constants.BLACK,
				Constants.WHITE);
		EdgePiece edge3 = new EdgePiece(1.0f, 2, 1, Constants.BLACK, Constants.BLACK, Constants.BLUE, Constants.BLACK,
				Constants.WHITE);
		EdgePiece edge4 = new EdgePiece(1.0f, 3, 1, Constants.BLACK, Constants.BLACK, Constants.RED, Constants.BLACK,
				Constants.WHITE);
		EdgePiece edge5 = new EdgePiece(1.0f, 0, -1, Constants.BLACK, Constants.BLACK, Constants.YELLOW,
				Constants.BLACK, Constants.GREEN);
		EdgePiece edge6 = new EdgePiece(1.0f, 1, -1, Constants.BLACK, Constants.BLACK, Constants.ORANGE,
				Constants.BLACK, Constants.GREEN);
		EdgePiece edge7 = new EdgePiece(1.0f, 2, -1, Constants.BLACK, Constants.BLACK, Constants.BLUE, Constants.BLACK,
				Constants.GREEN);
		EdgePiece edge8 = new EdgePiece(1.0f, 3, -1, Constants.BLACK, Constants.BLACK, Constants.RED, Constants.BLACK,
				Constants.GREEN);
		// corners
		CornerPiece corner1 = new CornerPiece(1.0f, 0, 1, Constants.BLACK, Constants.BLACK, Constants.YELLOW,
				Constants.ORANGE, Constants.BLACK, Constants.WHITE);
		CornerPiece corner2 = new CornerPiece(1.0f, 1, 1, Constants.BLACK, Constants.BLACK, Constants.ORANGE,
				Constants.BLUE, Constants.BLACK, Constants.WHITE);
		CornerPiece corner3 = new CornerPiece(1.0f, 2, 1, Constants.BLACK, Constants.BLACK, Constants.BLUE,
				Constants.RED, Constants.BLACK, Constants.WHITE);
		CornerPiece corner4 = new CornerPiece(1.0f, 3, 1, Constants.BLACK, Constants.BLACK, Constants.RED,
				Constants.YELLOW, Constants.BLACK, Constants.WHITE);
		CornerPiece corner5 = new CornerPiece(1.0f, 0, -1, Constants.BLACK, Constants.BLACK, Constants.YELLOW,
				Constants.ORANGE, Constants.BLACK, Constants.GREEN);
		CornerPiece corner6 = new CornerPiece(1.0f, 1, -1, Constants.BLACK, Constants.BLACK, Constants.ORANGE,
				Constants.BLUE, Constants.BLACK, Constants.GREEN);
		CornerPiece corner7 = new CornerPiece(1.0f, 2, -1, Constants.BLACK, Constants.BLACK, Constants.BLUE,
				Constants.RED, Constants.BLACK, Constants.GREEN);
		CornerPiece corner8 = new CornerPiece(1.0f, 3, -1, Constants.BLACK, Constants.BLACK, Constants.RED,
				Constants.YELLOW, Constants.BLACK, Constants.GREEN);
		// middle
		MiddlePiece middle1 = new MiddlePiece(1.0f, 0, Constants.BLACK, Constants.YELLOW, Constants.ORANGE,
				Constants.BLACK, Constants.RED, Constants.BLACK);
		MiddlePiece middle2 = new MiddlePiece(1.0f, 1, Constants.BLACK, Constants.BLUE, Constants.RED, Constants.BLACK,
				Constants.ORANGE, Constants.BLACK);
		// all nodes
		Node[] allNodes = new Node[] {
			edge1,
			edge2,
			edge3,
			edge4,
			edge5,
			edge6,
			edge7,
			edge8,
			corner1,
			corner2,
			corner3,
			corner4,
			corner5,
			corner6,
			corner7,
			corner8,
			middle1,
			middle2,
		};
		// mesh group
		Group meshGroup = new Group();
		Group axes = buildAxes();
		meshGroup.getChildren().addAll(axes);
		meshGroup.getChildren().addAll(allNodes);
		sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		scene.setOnMousePressed(me -> {
			mouseOldX = me.getSceneX();
			mouseOldY = me.getSceneY();
		});
		Rotate rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Rotate rotateZ = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
		scene.setOnMouseDragged(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
			rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
		});
		// key events
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			ObservableList<Node> children = meshGroup.getChildren();
			switch (event.getCode()) {
			case A:
				toggle(corner1, children);
				break;
			case B:
				toggle(corner2, children);
				break;
			case C:
				toggle(corner3, children);
				break;
			case D:
				toggle(corner4, children);
				break;
			case E:
				toggle(corner5, children);
				break;
			case F:
				toggle(corner6, children);
				break;
			case G:
				toggle(corner7, children);
				break;
			case H:
				toggle(corner8, children);
				break;
			case DIGIT1:
				toggle(edge1, children);
				break;
			case DIGIT2:
				toggle(edge2, children);
				break;
			case DIGIT3:
				toggle(edge3, children);
				break;
			case DIGIT4:
				toggle(edge4, children);
				break;
			case DIGIT5:
				toggle(edge5, children);
				break;
			case DIGIT6:
				toggle(edge6, children);
				break;
			case DIGIT7:
				toggle(edge7, children);
				break;
			case DIGIT8:
				toggle(edge8, children);
				break;
			case M:
				toggle(middle1, children);
				break;
			case N:
				toggle(middle2, children);
				break;
			case X:
				toggle(axes, children);
				break;
			case DIGIT0:
				children.removeAll(allNodes);
				break;
			case DIGIT9:
				children.removeAll(allNodes);
				children.addAll(allNodes);
				break;
			case Y:
				rotateZ.setAngle(rotateZ.getAngle() - 10);
				break;
			case Z:
				rotateZ.setAngle(rotateZ.getAngle() + 10);
				break;
			default:
				break;
			}
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void toggle(Node node, ObservableList<Node> children) {
		if (children.contains(node))
			children.remove(node);
		else
			children.add(node);
	}

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

	public static void run(String[] args) {
		launch(args);
	}
}
