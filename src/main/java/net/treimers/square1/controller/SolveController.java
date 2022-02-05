package net.treimers.square1.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.MoveSequence;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.SmartGroup;

public class SolveController {
	@FXML private Slider slider;
	@FXML private Label positionLabel;
	@FXML private TextFlow sequenceTextflow;
	@FXML private SubScene subScene;
	private Position position;
	private List<Position> positionList;
	private MeshGroup meshGroup;
	private MoveSequence moveSequence;
	
	/**
	 * Initializes this instance.
	 * 
	 * @param colorBean the color bean used to get the Square-1 colors.
	 */
	public void init(ColorBean colorBean) {
		positionList = Collections.emptyList();
		sequenceTextflow.getChildren().clear();
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int intValue = newValue.intValue();
				selectSliderPosition(intValue);
			}
		});
		moveSequence = MoveSequence.fromString("");
		// Sub Scene
		SmartGroup smartGroup = new SmartGroup();
		meshGroup = new MeshGroup(colorBean);
		smartGroup.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));
		subScene.setRoot(smartGroup);
		subScene.setFill(Color.SILVER);
		// Camera
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-7);
		subScene.setCamera(camera);
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
		sequenceTextflow.getChildren().clear();
		positionLabel.setText(position.toString());
		positionList = Arrays.asList(position);
		slider.setMax(0);
		meshGroup.setContent(position);
	}

	@FXML
	void handleEnterMove(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog(moveSequence.toString());
		dialog.setTitle("Move");
		dialog.setHeaderText("Please enter a move sequence");
		dialog.setContentText("Move Sequence:");
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String moveString = result.get();
			moveSequence = MoveSequence.fromString(moveString);
			sequenceTextflow.getChildren().setAll(new Text(moveSequence.toString()));
			try {
				positionList = position.move(moveSequence);
				slider.setMax(positionList.size() - 1);
			} catch (Square1Exception e) {
				positionList = Arrays.asList(position);
				slider.setMax(0);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void handleLeft(ActionEvent event) {
		Double doubleValue = slider.getValue();
		int sliderPos = doubleValue.intValue();
		if (sliderPos > 0)
			selectSliderPosition(sliderPos - 1);
	}

	@FXML
	void handleRight(ActionEvent event) {
		Double doubleValue = slider.getValue();
		int sliderPos = doubleValue.intValue();
		if (sliderPos < positionList.size() - 1)
			selectSliderPosition(sliderPos + 1);
	}

	@FXML
	void handleSolve(ActionEvent event) {
	}

	private void selectSliderPosition(int sliderPosition) {
		slider.setValue(sliderPosition);
		position = positionList.get(sliderPosition);
		meshGroup.setContent(position);
		List<Move> moves = moveSequence.getMoves();
		int i = 0;
		String beforeMove = "", currentMove = "", afterMove = "";
		for (; i < sliderPosition - 1; i++)
			beforeMove += moves.get(i);
		if (sliderPosition > 0)
			currentMove = moves.get(sliderPosition - 1).toString();
		for (; i < moves.size(); i++)
			afterMove += moves.get(i);
		Text beforeText = new Text(beforeMove);
		beforeText.setFill(Color.BLACK);
		Text currentText = new Text(currentMove);
		currentText.setFill(Color.DARKGREEN);
		Text afterText = new Text(afterMove);
		afterText.setFill(Color.BLACK);
		sequenceTextflow.getChildren().clear();
		if (beforeMove.length() > 0)
			sequenceTextflow.getChildren().add(beforeText);
		if (currentMove.length() > 0)
			sequenceTextflow.getChildren().add(currentText);
		if (afterMove.length() > 0)
			sequenceTextflow.getChildren().add(afterText);
	}
}
