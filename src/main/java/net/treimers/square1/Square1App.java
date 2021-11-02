package net.treimers.square1;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
		EdgePiece edge5 = new EdgePiece(1.0f, 0, -1, Constants.GREEN, Constants.BLACK, Constants.YELLOW,
				Constants.BLACK, Constants.BLACK);
		EdgePiece edge6 = new EdgePiece(1.0f, 1, -1, Constants.GREEN, Constants.BLACK, Constants.ORANGE,
				Constants.BLACK, Constants.BLACK);
		EdgePiece edge7 = new EdgePiece(1.0f, 2, -1, Constants.GREEN, Constants.BLACK, Constants.BLUE, Constants.BLACK,
				Constants.BLACK);
		EdgePiece edge8 = new EdgePiece(1.0f, 3, -1, Constants.GREEN, Constants.BLACK, Constants.RED, Constants.BLACK,
				Constants.BLACK);
		// corners
		CornerPiece corner1 = new CornerPiece(1.0f, 0, 1, Constants.BLACK, Constants.YELLOW, Constants.ORANGE,
				Constants.BLACK, Constants.BLACK, Constants.WHITE);
		CornerPiece corner2 = new CornerPiece(1.0f, 1, 1, Constants.BLACK, Constants.ORANGE, Constants.BLUE,
				Constants.BLACK, Constants.BLACK, Constants.WHITE);
		CornerPiece corner3 = new CornerPiece(1.0f, 2, 1, Constants.BLACK, Constants.BLUE, Constants.RED,
				Constants.BLACK, Constants.BLACK, Constants.WHITE);
		CornerPiece corner4 = new CornerPiece(1.0f, 3, 1, Constants.BLACK, Constants.RED, Constants.YELLOW,
				Constants.BLACK, Constants.BLACK, Constants.WHITE);
		CornerPiece corner5 = new CornerPiece(1.0f, 0, -1, Constants.GREEN, Constants.YELLOW, Constants.ORANGE,
				Constants.BLACK, Constants.BLACK, Constants.BLACK);
		CornerPiece corner6 = new CornerPiece(1.0f, 1, -1, Constants.GREEN, Constants.ORANGE, Constants.BLUE,
				Constants.BLACK, Constants.BLACK, Constants.BLACK);
		CornerPiece corner7 = new CornerPiece(1.0f, 2, -1, Constants.GREEN, Constants.BLUE, Constants.RED,
				Constants.BLACK, Constants.BLACK, Constants.BLACK);
		CornerPiece corner8 = new CornerPiece(1.0f, 3, -1, Constants.GREEN, Constants.RED, Constants.YELLOW,
				Constants.BLACK, Constants.BLACK, Constants.BLACK);
		// middle
		MiddlePiece middlePiece1 = new MiddlePiece(1.0f, 0, Constants.BLACK, Constants.YELLOW, Constants.ORANGE,
				Constants.BLACK, Constants.RED, Constants.BLACK);
		MiddlePiece middlePiece2 = new MiddlePiece(1.0f, 1, Constants.BLACK, Constants.BLUE, Constants.RED,
				Constants.BLACK, Constants.ORANGE, Constants.BLACK);
		// mesh group
		Group meshGroup = new Group();
		Rotate rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY);
		meshGroup.getChildren().addAll(edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8, corner1, corner2,
				corner3, corner4, corner5, corner6, corner7, corner8, middlePiece1, middlePiece2);
		sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		scene.setOnMousePressed(me -> {
			mouseOldX = me.getSceneX();
			mouseOldY = me.getSceneY();
		});
		scene.setOnMouseDragged(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
			rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void run(String[] args) {
		launch(args);
	}
}
