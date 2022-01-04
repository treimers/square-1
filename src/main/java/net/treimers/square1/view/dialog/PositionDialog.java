package net.treimers.square1.view.dialog;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import net.treimers.square1.controller.PositionDialogController;
import net.treimers.square1.model.ColorBean;
import net.treimers.square1.model.Position;

public class PositionDialog extends Dialog<Position> {
	private PositionDialogController positionDialogController;
	
	public PositionDialog(ColorBean colorBean) throws IOException {
		setTitle("Position");
		// dialog content
		URL resource = getClass().getResource("/net/treimers/square1/positiondialog.fxml");
		FXMLLoader loader = new FXMLLoader(resource);
		Parent root = loader.load();
		getDialogPane().setContent(root);
		// controller
		positionDialogController = loader.getController();
		positionDialogController.init(colorBean);
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
	
	public PositionDialogController getPositionDialogController() {
		return positionDialogController;
	}
}
