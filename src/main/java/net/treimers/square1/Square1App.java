package net.treimers.square1;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import net.treimers.square1.view.Constants;
import net.treimers.square1.view.ImageLoader;
import net.treimers.square1.view.SmartGroup;

public class Square1App extends Application implements Constants {
	private static int WIDTH = 1400;
	private static int HEIGHT = 800;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// set the stage title
		primaryStage.setTitle("Square-1");
		// load and set the stage icon
		Image image = ImageLoader.getLogoImage();
		primaryStage.getIcons().add(image);
		// Camera
		Camera camera = new PerspectiveCamera();
		// Scene
		SmartGroup sceneRoot = new SmartGroup();
		Scene scene = new Scene(sceneRoot, WIDTH, HEIGHT);
		scene.setFill(Color.SILVER);
		scene.setCamera(camera);
		sceneRoot.translateXProperty().set(WIDTH / 2);
		sceneRoot.translateYProperty().set(HEIGHT / 2);
		// Move
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case W:
				sceneRoot.translateZProperty().set(sceneRoot.getTranslateZ() + 100);
				break;
			case S:
				sceneRoot.translateZProperty().set(sceneRoot.getTranslateZ() - 100);
				break;
			case Q:
				sceneRoot.rotateByX(10);
				break;
			case A:
				sceneRoot.rotateByX(-10);
				break;
			case E:
				sceneRoot.rotateByY(10);
				break;
			case D:
				sceneRoot.rotateByY(-10);
				break;
			default:
				break;
			}
		});
		sceneRoot.getChildren().addAll(_1, _2, _3, _4, _5, _6, _7, _8, A, B, C, D, E, F, G, H, buildAxes(), new AmbientLight(Color.WHITE));
		primaryStage.setTitle("JavaFX 3D - Icosahedron");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

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

	public static void run(String[] args) {
		launch(args);
	}
}
