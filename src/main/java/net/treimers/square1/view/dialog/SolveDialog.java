package net.treimers.square1.view.dialog;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import net.treimers.square1.controller.MenuHandler;
import net.treimers.square1.controller.SolveController;
import net.treimers.square1.model.Position;

/**
 * Instances of this class are used as solve dialog.
 * 
 */
public class SolveDialog extends Square1Dialog<Position> {
	/**
	 * Creates a new instance.
	 * 
	 * @param menuHandler the handler used to enable and disable menus.
	 * @param root the parent JavaFX node.
	 * @param solveDialogController the dialog controller.
	 */
	public SolveDialog(MenuHandler menuHandler, Parent root, SolveController solveDialogController) {
		super(menuHandler);
		setTitle("Solve");
		// dialog content
		getDialogPane().setContent(root);
		// buttons
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		// result converter
		Callback<ButtonType, Position> callback = new Callback<ButtonType, Position>() {
			@Override
			public Position call(ButtonType button) {
				if (button == ButtonType.OK) {
					return solveDialogController.getPosition();
				} else
					return null;
			}
		};
		setResultConverter(callback);
	}
}
