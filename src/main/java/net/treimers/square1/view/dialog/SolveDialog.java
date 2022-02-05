package net.treimers.square1.view.dialog;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import net.treimers.square1.controller.SolveController;
import net.treimers.square1.model.Position;

public class SolveDialog extends Dialog<Position> {
	public SolveDialog(Parent root, SolveController solveDialogController) {
		setTitle("Solve");
		// dialog content
		getDialogPane().setContent(root);
		// buttons
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		// result converter
		setResultConverter(new Callback<ButtonType, Position>() {
			@Override
			public Position call(ButtonType button) {
				if (button == ButtonType.OK) {
					return solveDialogController.getPosition();
				} else
					return null;
			}
		});
	}
}
