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

/**
 * Instance of this class are used as controller for Square-1 solve dialog.
 */
public class SolveController {
	/** The sub scene showing the Square-1. */
	@FXML private SubScene subScene;
	/** The Square-1 position in standard notation. */
	@FXML private Label positionLabel;
	/** The move sequence in standard notation. */
	@FXML private TextFlow sequenceTextflow;
	/** The slider to move through the sequence. */
	@FXML private Slider slider;
	/** The current position. */
	private Position position;
	/** The sequence of moves. */
	private MoveSequence moveSequence;
	/** The list of positions. */
	private List<Position> positionList;
	/** The meshgroup showing the Square-1. */
	private MeshGroup meshGroup;
	
	/**
	 * Initializes this instance.
	 * 
	 * @param colorBean the color bean used to get the Square-1 colors.
	 */
	public void init(ColorBean colorBean) {
		positionList = Collections.emptyList();
		sequenceTextflow.setMaxWidth(600);
		sequenceTextflow.setMaxHeight(200);
		sequenceTextflow.getChildren().clear();
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int intValue = newValue.intValue();
				selectSliderPosition(intValue);
			}
		});
		moveSequence = new MoveSequence("");
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
	
	/**
	 * Gets the position.
	 * 
	 * @return the position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position the position.
	 */
	public void setPosition(Position position) {
		this.position = position;
		sequenceTextflow.getChildren().clear();
		positionLabel.setText(position.toString());
		positionList = Arrays.asList(position);
		slider.setMax(0);
		meshGroup.setContent(position);
	}

	/**
	 * Handle user click on enter move button.
	 */
	@FXML
	void handleEnterMove() {
		TextInputDialog dialog = new TextInputDialog(moveSequence.toString());
		dialog.setTitle("Move");
		dialog.setHeaderText("Please enter a move sequence");
		dialog.setContentText("Move Sequence:");
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String moveString = result.get();
			moveSequence = new MoveSequence(moveString);
			sequenceTextflow.getChildren().setAll(new Text(moveSequence.toString()));
			try {
				positionList = position.move(moveSequence);
				slider.setMax(positionList.size() - 1.0);
			} catch (Square1Exception e) {
				positionList = Arrays.asList(position);
				slider.setMax(0);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handle user click on left button.
	 */
	@FXML
	void handleLeft() {
		Double doubleValue = slider.getValue();
		int sliderPos = doubleValue.intValue();
		if (sliderPos > 0)
			selectSliderPosition(sliderPos - 1);
	}

	/**
	 * Handle user click on right button.
	 */
	@FXML
	void handleRight(ActionEvent event) {
		Double doubleValue = slider.getValue();
		int sliderPos = doubleValue.intValue();
		if (sliderPos < positionList.size() - 1)
			selectSliderPosition(sliderPos + 1);
	}

	/**
	 * Handle user click on solve button.
	 */
	@FXML
	void handleSolve(ActionEvent event) {
		// future implementation
	}

	/**
	 * Selects the slider position.
	 * 
	 * 1. adjusts the slider
	 * 2. selects the new position from position list
	 * 3. displays the new position in the sub scene
	 * 4. high lights the current move
	 * 
	 * @param sliderPosition the new slider position.
	 */
	private void selectSliderPosition(int sliderPosition) {
		slider.setValue(sliderPosition);
		position = positionList.get(sliderPosition);
		meshGroup.setContent(position);
		List<Move> moves = moveSequence.getMoves();
		int i = 0;
		String beforeMove = "";
		String currentMove = "";
		String afterMove = "";
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
