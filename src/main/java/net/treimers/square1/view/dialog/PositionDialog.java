package net.treimers.square1.view.dialog;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import net.treimers.square1.controller.PositionDialogController;
import net.treimers.square1.model.Position;

public class PositionDialog extends Dialog<Position> {
	public PositionDialog(Parent root, PositionDialogController positionDialogController) throws IOException {
		setTitle("Position");
		// dialog content
		getDialogPane().setContent(root);
		// buttons
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		// result converter
		setResultConverter(new Callback<ButtonType, Position>() {
			@Override
			public Position call(ButtonType button) {
				if (button == ButtonType.OK) {
					return positionDialogController.getPosition();
				} else
					return null;
			}
		});
	}
}
