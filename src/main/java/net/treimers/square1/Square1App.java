package net.treimers.square1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.treimers.square1.controller.Square1Controller;
import net.treimers.square1.view.misc.ImageLoader;

/**
 * Instances of this class are used as Square-1 JavaFX application.
 * 
 * <p>I found this very good description when starting at <a href="https://stackoverflow.com/questions/26831871/coloring-individual-triangles-in-a-triangle-mesh-on-javafx">Stackoverflow</a>
 */
public class Square1App extends Application {	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// set the stage title
		primaryStage.setTitle("Square-1");
		// load and set the stage icon
		Image image = ImageLoader.getLogoImage();
		primaryStage.getIcons().add(image);
		// load view
		FXMLLoader loader = new FXMLLoader(Square1App.class.getResource("/net/treimers/square1/square1.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Square1Controller controller = loader.getController();
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		// I got a NullPointerException, when calling this before primaryStage.setScene
		controller.setPrimaryStage(primaryStage);
		primaryStage.show();
	}

	/**
	 * Runs the application. Will be called by launcher from main.
	 * @param args the arguments from main method.
	 */
	public static void run(String[] args) {
		launch(args);
	}
}
