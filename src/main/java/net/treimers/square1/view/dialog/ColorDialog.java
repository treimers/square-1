package net.treimers.square1.view.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import net.treimers.square1.controller.MenuHandler;
import net.treimers.square1.model.ColorBean;

/**
 * Instances of this class are used to display a color choosing dialog
 * allowing the user to changes the colored sides of the Square-1.
 */
public class ColorDialog extends Square1Dialog<Color[]> {
	/** Array of color pickers to change the colored sides of the Square-1. */
	private ColorPicker[] colorPicker;
	/** Array with colored sides of the Square-1. */
	private Color[] colors;

	/**
	 * Creates a new instance.
	 * @param menuHandler
	 * @param colorBean the color bean to inform listeners.
	 */
	public ColorDialog(MenuHandler menuHandler, ColorBean colorBean) {
		super(menuHandler);
		setTitle("Colors");
		setHeaderText("Set the colors of the Square-1 sides");
		colors = colorBean.getColors();
		int numColors = colors.length;
		colorPicker = new ColorPicker[numColors];
		for (int i = 0; i < numColors; i++)
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
		Label hidden1 = new Label("Inside Vertical:");
		GridPane.setConstraints(hidden1, 0, 3);
		GridPane.setConstraints(colorPicker[6], 1, 3);
		Label hidden2 = new Label("Inside Horizontal:");
		GridPane.setConstraints(hidden2, 2, 3);
		GridPane.setConstraints(colorPicker[7], 3, 3);
		Button reset = new Button("Reset to Defaults");
		GridPane.setConstraints(reset, 0, 4, 4, 1, HPos.CENTER, VPos.CENTER);
		grid.getChildren().addAll(top, colorPicker[0], bottom, colorPicker[5], left, colorPicker[1], right,
				colorPicker[3], front, colorPicker[2], back, colorPicker[4], hidden1, colorPicker[6], hidden2,
				colorPicker[7], reset);
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// set all colors there defaults
				Color[] defaultColors = colorBean.getDefaultColors();
				for (int i = 0; i < colorPicker.length; i++)
					colorPicker[i].setValue(defaultColors[i]);
			}
		});
		getDialogPane().setContent(grid);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Callback<ButtonType, Color[]> callback = new Callback<ButtonType, Color[]>() {
			@Override
			public Color[] call(ButtonType buttonType) {
				if (buttonType == ButtonType.OK) {
					// return the color picker values on ok
					colors = new Color[colorPicker.length];
					for (int i = 0; i < colors.length; i++) {
						colors[i] = colorPicker[i].getValue();
					}
				} else {
					// reset the color pickers to the old values on cancel
					for (int i = 0; i < colorPicker.length; i++)
						colorPicker[i].setValue(colors[i]);
				}
				return colors;
			}
		};
		setResultConverter(callback);
	}

	/**
	 * Sets the initial colors for this color dialog.
	 * 
	 * @param colors the initial colors.
	 */
	public void setColors(Color[] colors) {
		this.colors = colors;
		for (int i = 0; i < colors.length; i++)
			colorPicker[i].setValue(colors[i]);
	}
}
