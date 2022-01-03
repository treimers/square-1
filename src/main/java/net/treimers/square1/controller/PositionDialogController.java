package net.treimers.square1.controller;

import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import net.treimers.square1.model.ColorBean;
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

public class PositionDialogController {
	@FXML private SubScene subScene;
	@FXML private SubScene subScene1;
	@FXML private SubScene subScene2;
	@FXML private SubScene subScene3;
	@FXML private SubScene subScene4;
	@FXML private SubScene subScene5;
	@FXML private SubScene subScene6;
	@FXML private SubScene subScene7;
	@FXML private SubScene subScene8;
	@FXML private SubScene subSceneA;
	@FXML private SubScene subSceneB;
	@FXML private SubScene subSceneC;
	@FXML private SubScene subSceneD;
	@FXML private SubScene subSceneE;
	@FXML private SubScene subSceneF;
	@FXML private SubScene subSceneG;
	@FXML private SubScene subSceneH;
	@FXML private SubScene subSceneM;
	@FXML private SubScene subSceneN;
	private double mouseOldX;
	private double mouseOldY;
	private double mousePosX;
	private double mousePosY;

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
        // piece sub scenes
        createPieceScene(subSceneA, new PieceA(0, 1, colorBean));
        createPieceScene(subSceneB, new PieceB(0, 1, colorBean));
        createPieceScene(subSceneC, new PieceC(0, 1, colorBean));
        createPieceScene(subSceneD, new PieceD(0, 1, colorBean));
        createPieceScene(subSceneE, new PieceE(0, 1, colorBean));
        createPieceScene(subSceneF, new PieceF(0, 1, colorBean));
        createPieceScene(subSceneG, new PieceG(0, 1, colorBean));
        createPieceScene(subSceneH, new PieceH(0, 1, colorBean));
        createPieceScene(subScene1, new Piece1(0, 1, colorBean));
        createPieceScene(subScene2, new Piece2(0, 1, colorBean));
        createPieceScene(subScene3, new Piece3(0, 1, colorBean));
        createPieceScene(subScene4, new Piece4(0, 1, colorBean));
        createPieceScene(subScene5, new Piece5(0, 1, colorBean));
        createPieceScene(subScene6, new Piece6(0, 1, colorBean));
        createPieceScene(subScene7, new Piece7(0, 1, colorBean));
        createPieceScene(subScene8, new Piece8(0, 1, colorBean));
        createMiddleScene(subSceneM, new PieceM(colorBean), new PieceN(0, colorBean));
        createMiddleScene(subSceneN, new PieceM(colorBean), new PieceN(1, colorBean));
	}

	private void createPieceScene(SubScene subScene, AbstractPiece piece) {
		SmartGroup group = new SmartGroup();
		subScene.setRoot(group);
		subScene.setFill(Color.SILVER);
        // Camera
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-5.5);
        subScene.setCamera(camera);
        // Piece
        Group meshGroup = new Group();
        meshGroup.getChildren().add(piece);
        group.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
        // Mouse Events
        Rotate rotateX = new Rotate(-55, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rotateZ = new Rotate(126, 0, 0, 0, Rotate.Z_AXIS);
        meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
        subScene.setOnMousePressed(me -> {
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
        });
        subScene.setOnMouseDragged(me -> {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
                rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
        });
	}

	private void createMiddleScene(SubScene subScene, AbstractPiece pieceM, AbstractPiece pieceN) {
		SmartGroup group = new SmartGroup();
		subScene.setRoot(group);
		subScene.setFill(Color.SILVER);
        // Camera
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-5.5);
        subScene.setCamera(camera);
        // Piece
        Group meshGroup = new Group();
        meshGroup.getChildren().addAll(pieceM, pieceN);
        group.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
        // Mouse Events
        Rotate rotateX = new Rotate(-55, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rotateZ = new Rotate(126, 0, 0, 0, Rotate.Z_AXIS);
        meshGroup.getTransforms().addAll(rotateX, rotateY, rotateZ);
        subScene.setOnMousePressed(me -> {
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
        });
        subScene.setOnMouseDragged(me -> {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
                rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
        });
	}
}
