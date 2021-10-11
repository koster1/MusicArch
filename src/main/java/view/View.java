package view;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;
import com.sun.glass.ui.Window;
import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;

import antlr.debug.Event;
import controller.Controller;
import controller.FrontPageController;
import controller.GUIController;
import controller.SearchController;
import controller.UserCollectionController;
import controller.RequestFormsController;
import controller.AlbumPageController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View extends Application {

	private Stage primaryStage;
	private static BorderPane rootLayout;
	private static BorderPane anotherRoot;
	private static AnchorPane userRoot;
	private static SplitPane splitPane;
	private static AnchorPane test;
	private static BorderPane g;
	private static GUIController guiController;
	private static Controller controller;
	private static ListView<Artist> artistListView;
	private static ListView<Genre> genreListView;
	private static ListView<Album> albumListView;
	private static ListView<Song> songListView;
	private Pane view;

	public void init() {
		controller = new Controller();
		guiController = new GUIController(this, controller);
	}

	/* public void init() {Controller controller = new Controller();} */

	@Override
	public void start(Stage primaryStage) throws IOException {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MusicArch");
		showHome();

		showFrontPage();
//		showAlbumPage();

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
		primaryStage.setMinWidth(1200);
		primaryStage.setOnCloseRequest(event -> {
			System.exit(-1);
		});
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// BorderPanen keskelle asetettu etusivunäkymä (sisältää tulevaisuudessa
	// listauksia genreistä tms)
	// BorderPanen keskelle asetettu etusivunäkymä (sisältää tulevaisuudessa
	// listauksia genreistä tms)
	public static void showFrontPage() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/FrontPage.fxml"));
		loader.setControllerFactory(FrontPageController -> new FrontPageController(controller));
		try {
			System.out.println("lataaja " + loader);
		} catch (Exception e) {
			System.out.println("päädyit tänne " + e.getMessage());
		}
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);

	}

	public static void showHelpPage() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/helpPage.fxml"));
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);

	}

	public static void showSearchPage(String searchText) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/SearchPage.fxml"));
		loader.setControllerFactory(SearchController -> new SearchController(searchText, controller));
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);
	}

	public static void showAlbumPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/AlbumPage.fxml"));
		loader.setControllerFactory(AlbumPageController -> new AlbumPageController(controller));
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);
	}

	//
	public static void showUserCollectionPage() throws IOException {
		System.out.println("User collection!!!");
		List<Window> windows = Window.getWindows();
		System.out.println(windows);
		boolean test = true;
		try {
			for (int i = 0; i < windows.size(); i++) {
				System.out.println(windows.get(i).getTitle());
				if (windows.get(i).getTitle().contains("User")) {
					System.out.println("Truee");
					test = false;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (test) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/OmaKokoelma2.fxml"));
			fxmlLoader.setControllerFactory(UserCollectionController -> new UserCollectionController(controller));
			userRoot = (AnchorPane) fxmlLoader.load();
			Scene scene = new Scene(userRoot);
			scene.getWindow();
			scene.getStylesheets().add("/view/style.css");
			Stage stage2 = new Stage();
			stage2.setMinWidth(1200);
			stage2.setMinHeight(500);
			stage2.setTitle("User");
			stage2.setScene(scene);
			stage2.show();
		}
	}

	// -------------------Lisayspohjan OMA stage---------------------------------
	// Lisäysnäkymän (sisältää lisäyspyynnöt ja lisäyspohjat) oma stage,
	// jossa vaihdetaan keskiosan lomake-fxml:ää
	public static void showRequestsWindow() throws IOException {
		System.out.print(" !!!    täällä ollaan    !!!");

		List<Window> windows = Window.getWindows();
		System.out.println(windows);
		boolean test = true;
		try {
			for (int i = 0; i < windows.size(); i++) {
				System.out.println(windows.get(i).getTitle());
				if (windows.get(i).getTitle().contains("Request")) {
					System.out.println("True");
					test = false;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (test) {
			FXMLLoader fxmlLoader = new FXMLLoader();

			fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/Requests.fxml"));
			fxmlLoader.setControllerFactory(RequestFormsController -> new RequestFormsController(controller));
			anotherRoot = (BorderPane) fxmlLoader.load();

			Scene scene = new Scene(anotherRoot);
			scene.getStylesheets().add("/view/style.css");
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setTitle("Request");
			stage.setScene(scene);
			stage.show();
		}

	}

	public static void showGenreForm() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/GenreForm.fxml"));
		loader.setControllerFactory(RequestFormsController -> new RequestFormsController(controller));
		AnchorPane genre = (AnchorPane) loader.load();
		anotherRoot.setCenter(genre);
	}

	public static void showAlbumForm() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/AlbumForm.fxml"));
		loader.setControllerFactory(RequestFormsController -> new RequestFormsController(controller));
		AnchorPane album = (AnchorPane) loader.load();
		anotherRoot.setCenter(album);
	}

	public static void showArtistForm() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/ArtistForm.fxml"));
		loader.setControllerFactory(RequestFormsController -> new RequestFormsController(controller));
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