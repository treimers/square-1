package net.treimers.square1.view.dialog;

import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
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
		ButtonType current = new ButtonType("Choose Current Position", ButtonData.OK_DONE);
		ButtonType last = new ButtonType("Choose Last Position", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, current, last);
		// result converter
		Callback<ButtonType, Position> callback = new Callback<ButtonType, Position>() {
			@Override
			public Position call(ButtonType buttonType) {
				if (buttonType == current)
					return solveDialogController.getPosition();
				else if (buttonType == last)
					return solveDialogController.getLastPosition();
				else
					return null;
			}
		};
		setResultConverter(callback);
	}
}
