package main;

import controller.*;
import java.util.*;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MusicArch");

        initRootLayout();

        ShowForm();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the form inside the root layout.
     */
    public void ShowForm() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/TestiLomake.fxml"));
            AnchorPane form = (AnchorPane) loader.load();
            
            // Set form into the center of root layout.
            rootLayout.setCenter(form);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public static void main(String args[]) {
		launch(args);

		//These are for testing purposes! The given values should be given to the Controller by the UI, not main! Main only exists to start the program and set the GUI!		
		Controller controller = new Controller();
		controller.setInputManagement();
	
		System.out.println("End of program!");
		

		
		//System.out.println(controller + " aaa");
		//controller.setInputManagement("Heavy Metal");
		
	}

}
