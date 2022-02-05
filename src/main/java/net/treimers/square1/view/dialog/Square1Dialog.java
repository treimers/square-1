package net.treimers.square1.view.dialog;

import javafx.event.EventHandler;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.treimers.square1.controller.MenuHandler;

/**
 * Instances of this abstract class are used as dialogs in Square-1 application.
 * 
 * @param <R> The return type of the dialog, via the result property.
 */
public abstract class Square1Dialog<R> extends Dialog<R> {
	/**
	 * Crates a new instance.
	 * 
	 * @param menuHandler the handler used to enable and disable menus.
	 */
	protected Square1Dialog(MenuHandler menuHandler) {
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				menuHandler.setMenusEnabled(false);
			}
		});
		stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				menuHandler.setMenusEnabled(true);
			}
		});
	}
}
