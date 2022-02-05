package net.treimers.square1.view.dialog;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import net.treimers.square1.controller.PositionController;
import net.treimers.square1.model.Position;

/**
 * Instance of this class are used as position input dialog.
 */
public class PositionDialog extends Dialog<Position> {
	/**
	 * Creates a new instance.
	 * @param root the parent JavaFX node.
	 * @param positionDialogController the dialog controller.
	 */
	public PositionDialog(Parent root, PositionController positionDialogController) {
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
