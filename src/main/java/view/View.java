package view;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.stage.Window;

public class View extends Application {

	private Stage primaryStage;
	private static BorderPane rootLayout;
	private static BorderPane anotherRoot;
	private static SplitPane splitPane;
	private static AnchorPane test;
	private static BorderPane g;
	private Pane view;

	/*
	 * public void init() { GUIController guiController = new GUIController(this); }
	 */
	/* public void init() {Controller controller = new Controller();} */

	@Override
	public void start(Stage primaryStage) throws IOException {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MusicArch");
		showHome();
		showFrontPage();
	}

	// Näyttää etusivun Pohjan (BorderPane), johon on asetettu menuvalikko
	public void showHome() throws IOException {
		// Load root layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/RootLayout2.fxml"));

		rootLayout = (BorderPane) loader.load();

		Scene scene = new Scene(rootLayout);
		String css = this.getClass().getResource("/view/style.css").toExternalForm();
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// BorderPanen keskelle asetettu etusivunäkymä (sisältää tulevaisuudessa
	// listauksia genreistä tms)
	public static void showFrontPage() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/FrontPage.fxml"));
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);

	}

	public static void showHelpPage() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/helpPage.fxml"));
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);

	}

	// -------------------Lisayspohjan OMA stage---------------------------------
	// Lisäysnäkymän (sisältää lisäyspyynnöt ja lisäyspohjat) oma stage,
	// jossa vaihdetaan keskiosan lomake-fxml:ää
	//
	public static void showRequestsWindow() throws IOException {
		System.out.print(" !!!    täällä ollaan    !!!");
		/*
		 * try { System.out.print("   " + anotherRoot); }catch (Exception e){
		 * System.out.print(e.getMessage()); }
		 */
		anotherRoot = null;

		if (anotherRoot == null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/LisaysPyynnot2.fxml"));
			anotherRoot = (BorderPane) fxmlLoader.load();
			Scene scene = new Scene(anotherRoot);
			scene.getStylesheets().add("/view/style.css");

			Stage stage = new Stage();
			stage.setTitle("New Window");
			stage.setScene(scene);
			stage.show();

		} else if (anotherRoot != null) {
			((Stage) anotherRoot.getScene().getWindow()).close();

//			anotherRoot.toFront();

		}
	}

	public static void showGenreForm() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/genrelisays.fxml"));
		AnchorPane genre = (AnchorPane) loader.load();
		anotherRoot.setCenter(genre);
	}

	public static void showAlbumForm() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/albumilisays.fxml"));
		AnchorPane album = (AnchorPane) loader.load();
		anotherRoot.setCenter(album);
	}

	public static void showArtistForm() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/artistilisays.fxml"));
		AnchorPane artist = (AnchorPane) loader.load();
		anotherRoot.setCenter(artist);
	}

	public static void Error() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/Error.fxml"));
		test = (AnchorPane) fxmlLoader.load();
		Scene error = new Scene(test);
		error.getStylesheets().add("/view/style.css");
		Stage stage = new Stage();
		stage.setTitle("Error Window");
		stage.setScene(error);
		stage.show();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);

	}
}