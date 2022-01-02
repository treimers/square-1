package net.treimers.square1.controller;

import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import net.treimers.square1.view.misc.SmartGroup;
import net.treimers.square1.view.piece.AbstractCornerPiece;
import net.treimers.square1.view.piece.corner.PieceA;

public class PositionDialogController {
	@FXML private SubScene subScene;
	@FXML private SubScene subSceneA;
	@FXML private SubScene subSceneB;
	@FXML private SubScene subScene1;
	@FXML private SubScene subScene2;
	@FXML private SubScene subSceneM;
	@FXML private SubScene subSceneC;
	@FXML private SubScene subSceneD;
	@FXML private SubScene subScene3;
	@FXML private SubScene subScene4;
	@FXML private SubScene subSceneE;
	@FXML private SubScene subSceneF;
	@FXML private SubScene subScene5;
	@FXML private SubScene subScene6;
	@FXML private SubScene subSceneN;
	@FXML private SubScene subSceneG;
	@FXML private SubScene subSceneH;
	@FXML private SubScene subScene7;
	@FXML private SubScene subScene8;
	private double mouseOldAX;
	private double mouseOldAY;
	private double mousePosAX;
	private double mousePosAY;

	public void init(ColorBean colorBean) {
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
        // piece A sub scenes
        SmartGroup groupA = new SmartGroup();
        subSceneA.setRoot(groupA);
        subSceneA.setFill(Color.SILVER);
        // Camera A
        Camera cameraA = new PerspectiveCamera(true);
        cameraA.setNearClip(0.1);
        cameraA.setFarClip(10000.0);
        cameraA.setTranslateZ(-4.5);
        subSceneA.setCamera(cameraA);
        // Piece A
        AbstractCornerPiece cornerA1 = new PieceA(0, 1, colorBean);
        Group meshGroupA = new Group();
        meshGroupA.getChildren().add(cornerA1);
        groupA.getChildren().addAll(meshGroupA, new AmbientLight(Color.WHITE));
        // Mouse Events A
        Rotate rotateAX = new Rotate(-55, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateAY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rotateAZ = new Rotate(126, 0, 0, 0, Rotate.Z_AXIS);
        meshGroupA.getTransforms().addAll(rotateAX, rotateAY, rotateAZ);
        subSceneA.setOnMousePressed(me -> {
                mouseOldAX = me.getSceneX();
                mouseOldAY = me.getSceneY();
        });
        subSceneA.setOnMouseDragged(me -> {
                mousePosAX = me.getSceneX();
                mousePosAY = me.getSceneY();
                rotateAX.setAngle(rotateAX.getAngle() - (mousePosAY - mouseOldAY));
                rotateAY.setAngle(rotateAY.getAngle() + (mousePosAX - mouseOldAX));
                mouseOldAX = mousePosAX;
                mouseOldAY = mousePosAY;
        });

	}
}
