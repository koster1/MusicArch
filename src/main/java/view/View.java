package view;

import java.io.FileInputStream;

import controller.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
	
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * public void init() { GUIController guiController = new GUIController(this); }
	 */

	@Override
	public void start(Stage stage) throws Exception {

		System.out.println("Näkymää ladataan..");
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/fxmlFiles/genrelisays.fxml"));
		stage.setTitle("MusicArch");
		stage.setScene(new Scene(root));
		stage.show();

	}

}