package net.treimers.square1;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import net.treimers.square1.view.Constants;
import net.treimers.square1.view.EdgePiece;
import net.treimers.square1.view.ImageLoader;
import net.treimers.square1.view.SmartGroup;

public class Square1App extends Application implements Constants {
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
		Scene scene = new Scene(sceneRoot, WIDTH, HEIGHT);
		scene.setFill(Color.SILVER);
//		sceneRoot.translateXProperty().set(WIDTH / 2);
//		sceneRoot.translateYProperty().set(HEIGHT / 2);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-10);
		scene.setCamera(camera);
		// edges
		EdgePiece _1 = new EdgePiece(1.0f, 0, 1, Constants.GRAY, Constants.GRAY, Constants.YELLOW, Constants.GRAY,
				Constants.WHITE);
		EdgePiece _2 = new EdgePiece(1.0f, 1, 1, Constants.GRAY, Constants.GRAY, Constants.ORANGE, Constants.GRAY,
				Constants.WHITE);
		EdgePiece _3 = new EdgePiece(1.0f, 2, 1, Constants.GRAY, Constants.GRAY, Constants.BLUE, Constants.GRAY,
				Constants.WHITE);
		EdgePiece _4 = new EdgePiece(1.0f, 3, 1, Constants.GRAY, Constants.GRAY, Constants.RED, Constants.GRAY,
				Constants.WHITE);
		EdgePiece _5 = new EdgePiece(1.0f, 0, -1, Constants.GREEN, Constants.GRAY, Constants.YELLOW, Constants.GRAY,
				Constants.GRAY);
		EdgePiece _6 = new EdgePiece(1.0f, 1, -1, Constants.GREEN, Constants.GRAY, Constants.ORANGE, Constants.GRAY,
				Constants.GRAY);
		EdgePiece _7 = new EdgePiece(1.0f, 2, -1, Constants.GREEN, Constants.GRAY, Constants.BLUE, Constants.GRAY,
				Constants.GRAY);
		EdgePiece _8 = new EdgePiece(1.0f, 3, -1, Constants.GREEN, Constants.GRAY, Constants.RED, Constants.GRAY,
				Constants.GRAY);
		// mesh group
		Group meshGroup = new Group();
		Rotate rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		meshGroup.getTransforms().addAll(rotateX, rotateY);
		meshGroup.getChildren().addAll(_1, _2, _3, _4, _5, _6, _7, _8);
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

	/*
	// https://stackoverflow.com/questions/37075261/how-to-set-axis-triad-at-fixed-position-on-screen-in-javafx
	private Group buildAxes() {
		double len = 240.0;
		// x axis
		PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);
		Box xAxis = new Box(len, 1, 1);
		xAxis.setMaterial(redMaterial);
		Sphere xMark = new Sphere(3);
		xMark.setMaterial(redMaterial);
		xMark.setTranslateX(len / 2.0);
		// y axis
		PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);
		Box yAxis = new Box(1, len, 1);
		yAxis.setMaterial(greenMaterial);
		Sphere yMark = new Sphere(3);
		yMark.setMaterial(greenMaterial);
		yMark.setTranslateY(len / 2.0);
		// z axis
		PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);
		Box zAxis = new Box(1, 1, len);
		zAxis.setMaterial(blueMaterial);
		Sphere zMark = new Sphere(3);
		zMark.setMaterial(blueMaterial);
		zMark.setTranslateZ(len / 2.0);
		// all
		Group axisGroup = new Group();
		axisGroup.getChildren().addAll(xAxis, xMark, yAxis, yMark, zAxis, zMark);
		return axisGroup;
	}
	*/

	public static void run(String[] args) {
		launch(args);
	}
}
