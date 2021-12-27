package net.treimers.square1.view;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ColorDialog extends Dialog<ButtonType> {
	private ColorPicker[] colorPicker;

	public ColorDialog(Color[] colors) {
		setTitle("Colors");
		setHeaderText("Set the colors of the Square-1 sides");
		Color[] defaultColors = Arrays.copyOf(colors, colors.length);
		colorPicker = new ColorPicker[colors.length];
		for (int i = 0; i < colorPicker.length; i++)
			colorPicker[i] = new ColorPicker(colors[i]);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		Label top = new Label("Top:");
		GridPane.setConstraints(top, 0, 0);
		GridPane.setConstraints(colorPicker[0], 1, 0);
		Label bottom = new Label("Bottom:");
		GridPane.setConstraints(bottom, 2, 0);
		GridPane.setConstraints(colorPicker[5], 3, 0);
		Label left = new Label("Left:");
		GridPane.setConstraints(left, 0, 1);
		GridPane.setConstraints(colorPicker[1], 1, 1);
		Label right = new Label("Right:");
		GridPane.setConstraints(right, 2, 1);
		GridPane.setConstraints(colorPicker[3], 3, 1);
		Label front = new Label("Front:");
		GridPane.setConstraints(front, 0, 2);
		GridPane.setConstraints(colorPicker[2], 1, 2);
		Label back = new Label("Back:");
		GridPane.setConstraints(back, 2, 2);
		GridPane.setConstraints(colorPicker[4], 3, 2);
		Label hidden1 = new Label("Hidden 1:");
		GridPane.setConstraints(hidden1, 0, 3);
		GridPane.setConstraints(colorPicker[6], 1, 3);
		Label hidden2 = new Label("Hidden 2:");
		GridPane.setConstraints(hidden2, 2, 3);
		GridPane.setConstraints(colorPicker[7], 3, 3);
		Button reset = new Button("Reset Defaults");
		GridPane.setConstraints(reset, 0, 4, 4, 1, HPos.CENTER, VPos.CENTER);
		grid.getChildren().addAll(top, colorPicker[0], bottom, colorPicker[5], left, colorPicker[1], right,
				colorPicker[3], front, colorPicker[2], back, colorPicker[4], hidden1, colorPicker[6], hidden2,
				colorPicker[7], reset);
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < colorPicker.length; i++)
					colorPicker[i].setValue(defaultColors[i]);
			}
		});
		getDialogPane().setContent(grid);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		setResultConverter(new Callback<ButtonType, ButtonType>() {
			@Override
			public ButtonType call(ButtonType button) {
				if (button == ButtonType.OK) {
					for (int i = 0; i < colorPicker.length; i++) {
						colors[i] = colorPicker[i].getValue();
					}
				} else {
					for (int i = 0; i < colorPicker.length; i++)
						colorPicker[i].setValue(colors[i]);
				}
				return button;
			}
		});
	}
}
