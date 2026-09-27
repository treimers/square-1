package net.treimers.square1.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.jaapsch.square1.Solver;
import net.treimers.square1.exception.Square1Exception;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Move;
import net.treimers.square1.model.MoveSequence;
import net.treimers.square1.model.Position;
import net.treimers.square1.view.misc.MeshGroup;
import net.treimers.square1.view.misc.MoveAnimator;
import net.treimers.square1.view.misc.SmartGroup;

/**
 * Instance of this class are used as controller for Square-1 solve dialog.
 */
public class SolveController {
	/** Step for one click on a rotation button, in degrees. */
	private static final int ROTATION_STEP = 10;
	/** The sub scene showing the Square-1. */
	@FXML
	private SubScene subScene;
	/** The Square-1 position in standard notation. */
	@FXML
	private Label positionLabel;
	/** The move sequence in standard notation. */
	@FXML
	private TextFlow sequenceTextflow;
	/** The slider to move through the sequence. */
	@FXML
	private Slider slider;
	/** The original position. */
	private Position originalPosition;
	/** The current positon. */
	private Position position;
	/** The sequence of moves. */
	private MoveSequence sequence;
	/** The list of positions. */
	private List<Position> positionList;
	/** The meshgroup showing the Square-1. */
	private MeshGroup meshGroup;
	/** View rotation of the Square-1 in this dialog. */
	private SmartGroup smartGroup;
	/** Plays the move between two slider steps. */
	private final MoveAnimator animator = new MoveAnimator();
	/** Slider step currently shown. The move animation has finished for this step. */
	private int displayedStep;
	/** Ignores slider events while the code itself moves the slider. */
	private boolean suppressSlider;
	/** Step the running animation is heading for, or -1 when none is running. */
	private int animatingTarget = -1;
	Solver solver;

	public SolveController() throws Square1Exception {
		solver = new Solver();
	}

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
		ChangeListener<Number> changeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				onSliderSettled();
			}
		};
		slider.valueProperty().addListener(changeListener);
		slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue)
					onSliderSettled();
			}
		});
		sequence = new MoveSequence();
		// Sub Scene
		smartGroup = new SmartGroup();
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
	 * Sets the position.
	 * 
	 * @param position the position.
	 */
	public void setPosition(Position position) {
		this.originalPosition = position;
		this.position = position;
		smartGroup.resetRotation();
		positionList = Arrays.asList(position);
		sequence = new MoveSequence();
		showStep(0);
	}

	/**
	 * Handle user click on enter move button.
	 */
	@FXML
	void handleEnterMove() {
		TextInputDialog dialog = new TextInputDialog(sequence.toString());
		dialog.setTitle("Move");
		dialog.setHeaderText("Please enter a move sequence");
		dialog.setContentText("Move Sequence:");
		boolean success = false;
		do {
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				String moveString = result.get();
				MoveSequence seq = new MoveSequence(moveString);
				try {
					List<Position> list = originalPosition.move(seq);
					this.sequence = seq;
					positionList = list;
					prepareSlider();
					showStep(0);
					success = true;
				} catch (Square1Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Illegal Move");
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			} else
				success = true;
		} while (!success);
	}

	/**
	 * Turns the view 10° clockwise around the x-axis.
	 */
	@FXML
	void doXClock() {
		smartGroup.rotateByX(ROTATION_STEP);
	}

	/**
	 * Turns the view 10° anticlockwise around the x-axis.
	 */
	@FXML
	void doXAntiClock() {
		smartGroup.rotateByX(-ROTATION_STEP);
	}

	/**
	 * Turns the view 10° clockwise around the y-axis.
	 */
	@FXML
	void doYClock() {
		smartGroup.rotateByY(ROTATION_STEP);
	}

	/**
	 * Turns the view 10° anticlockwise around the y-axis.
	 */
	@FXML
	void doYAntiClock() {
		smartGroup.rotateByY(-ROTATION_STEP);
	}

	/**
	 * Turns the view 10° clockwise around the z-axis.
	 */
	@FXML
	void doZClock() {
		smartGroup.rotateByZ(ROTATION_STEP);
	}

	/**
	 * Turns the view 10° anticlockwise around the z-axis.
	 */
	@FXML
	void doZAntiClock() {
		smartGroup.rotateByZ(-ROTATION_STEP);
	}

	/**
	 * Plays a 360° turn of the Square-1 around the y-axis.
	 */
	@FXML
	void doRotate() {
		if (animator.isRunning()) {
			animator.cancel();
			meshGroup.setContent(positionList.get(displayedStep));
		}
		meshGroup.animate();
	}

	/**
	 * Restores the view orientation used when the dialog was opened.
	 */
	@FXML
	void doReset() {
		smartGroup.resetRotation();
	}

	/**
	 * Handle user click on left button.
	 */
	@FXML
	void handleLeft() {
		step(-1);
	}

	/**
	 * Handle user click on right button.
	 */
	@FXML
	void handleRight() {
		step(1);
	}

	/**
	 * Plays one move from the position on screen.
	 * The buttons do not follow the slider thumb: a thumb that already sits on the last step, or a
	 * drag that never finished, must not swallow the next click.
	 *
	 * @param direction -1 for the previous step, +1 for the next step.
	 */
	private void step(int direction) {
		if (positionList.size() < 2)
			return;
		suppressSlider = true;
		if (slider.isValueChanging())
			slider.setValueChanging(false);
		if (animator.isRunning()) {
			animator.cancel();
			meshGroup.setContent(positionList.get(displayedStep));
		}
		animatingTarget = -1;
		int target = displayedStep + direction;
		if (target < 0 || target >= positionList.size()) {
			suppressSlider = false;
			return;
		}
		if (positionList.get(displayedStep).toString().length() != 17) {
			suppressSlider = false;
			showStep(target);
			return;
		}
		slider.setValue(target);
		suppressSlider = false;
		animatingTarget = target;
		playToward(target);
	}

	/**
	 * Handle user click on solve button.
	 */
	@FXML
	void handleSolve() {
		String solution = null;
		try {
			if (solver != null) {
				solution = solver.solve(originalPosition.toString());
				MoveSequence seq = new MoveSequence(solution);
				List<Position> list = originalPosition.move(seq);
				this.sequence = seq;
				positionList = list;
				prepareSlider();
				showStep(0);
			}
		} catch (Square1Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			if (solution == null)
				alert.setHeaderText("Error");
			else
				alert.setHeaderText("Error with solution: " + solution);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Shows ticks for one step per move and snaps the thumb to those steps.
	 */
	private void prepareSlider() {
		slider.setMax(positionList.size() - 1.0);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
	}

	/**
	 * Shows a step at once, without playing the moves in between.
	 * 
	 * @param step the step to show.
	 */
	private void showStep(int step) {
		animator.cancel();
		animatingTarget = -1;
		displayedStep = step;
		position = positionList.get(step);
		positionLabel.setText(position.toString());
		meshGroup.setContent(position);
		showSequence(step - 1);
		suppressSlider = true;
		slider.setValue(step);
		suppressSlider = false;
	}

	/**
	 * Starts the move animation when the user lets the slider go, or when the arrows move it.
	 */
	private void onSliderSettled() {
		if (suppressSlider || slider.isValueChanging() || positionList.size() < 2)
			return;
		int target = (int) Math.round(slider.getValue());
		if (target < 0)
			target = 0;
		if (target > positionList.size() - 1)
			target = positionList.size() - 1;
		if (animator.isRunning() && target == animatingTarget)
			return;
		if (target == displayedStep && !animator.isRunning())
			return;
		if (animator.isRunning()) {
			animator.cancel();
			position = positionList.get(displayedStep);
			meshGroup.setContent(position);
		}
		if (positionList.get(displayedStep).toString().length() != 17) {
			showStep(target);
			return;
		}
		animatingTarget = target;
		playToward(target);
	}

	/**
	 * Plays the moves from the step on screen to {@code target}, one move at a time.
	 * 
	 * @param target the step to reach.
	 */
	private void playToward(int target) {
		if (target == displayedStep) {
			animatingTarget = -1;
			return;
		}
		int next = displayedStep + Integer.signum(target - displayedStep);
		boolean forward = next > displayedStep;
		int moveIndex = Math.min(displayedStep, next);
		Move move = sequence.getMoves().get(moveIndex);
		Position before = positionList.get(moveIndex);
		Position after = positionList.get(moveIndex + 1);
		showSequence(moveIndex);
		animator.play(meshGroup, move, forward, before, after, () -> {
			displayedStep = next;
			position = positionList.get(next);
			positionLabel.setText(position.toString());
			showSequence(next - 1);
			playToward(target);
		});
	}

	/**
	 * Shows the move sequence and marks the move that is playing, or the move that led to this step.
	 * 
	 * @param activeMove the move to mark, or -1 if none.
	 */
	private void showSequence(int activeMove) {
		sequenceTextflow.getChildren().clear();
		List<Move> moves = sequence.getMoves();
		for (int i = 0; i < moves.size(); i++) {
			Text text = new Text(moves.get(i).toString());
			if (i == activeMove) {
				text.setFill(Color.DARKGREEN);
				text.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 4));
			} else
				text.setFill(Color.BLACK);
			sequenceTextflow.getChildren().add(text);
		}
	}

	/**
	 * Get the current position under the slider.
	 * 
	 * @return the current position under the slider.
	 */
	public Position getPosition() {
		if (animator.isRunning()) {
			animator.cancel();
			animatingTarget = -1;
			position = positionList.get(displayedStep);
			meshGroup.setContent(position);
			showSequence(displayedStep - 1);
			suppressSlider = true;
			slider.setValue(displayedStep);
			suppressSlider = false;
		}
		return position;
	}

	/**
	 * Get the last position under the slider.
	 * 
	 * @return the last position under the slider.
	 */
	public Position getLastPosition() {
		return positionList.get(positionList.size() - 1);
	}
}
