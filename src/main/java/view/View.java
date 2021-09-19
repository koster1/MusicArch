package view;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		// Create the FXMLLoader 
        FXMLLoader loader = new FXMLLoader();
        // Path to the FXML File
        String fxmlDocPath = "./TestiLomake.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
 
        // Create the Pane and all Details
        VBox root = (VBox) loader.load(fxmlStream);
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("A FXML Example with a Controller");
        // Display the Stage
        stage.show();
		
	}
	
}