package net.treimers.square1.view.dialog;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.treimers.square1.controller.MenuHandler;

/**
 * Instances of this class are used to create alerts in Square-1 application.
 * 
 * <p>
 * System menus will be disabled while this alert is displayed.
 */
public class Square1Alert extends Alert {

	/**
	 * Creates a new instance.
	 * 
	 * @param type the alert type.
	 * @param menuHandler the handler used to enable and disable menus.
	 */
	public Square1Alert(AlertType type, MenuHandler menuHandler) {
		super(type);
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
