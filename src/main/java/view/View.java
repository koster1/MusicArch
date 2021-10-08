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
import controller.GUIController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
//import javafx.stage.Window;

public class View extends Application {

	private Stage primaryStage;
	private static BorderPane rootLayout;
	private static BorderPane anotherRoot;
	private static AnchorPane userRoot;
	private static SplitPane splitPane;
	private static AnchorPane test;
	private static BorderPane g;
	private Pane view;
	private static GUIController guiController;
	private static Controller controller;
	private static ListView<Artist> artistListView;
	private static ListView<Genre> genreListView;
	private static ListView<Album> albumListView;
	private static ListView<Song> songListView;

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
		guiController.goFrontPage();
//		showFrontPage();

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
	//BorderPanen keskelle asetettu etusivunäkymä (sisältää tulevaisuudessa listauksia genreistä tms)
	public static void showFrontPage(Artist[] artistList, Genre[] genreList) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/FrontPage.fxml"));
		try {
			System.out.println("lataaja " + loader);
		} catch (Exception e) {
			System.out.println("päädyit tänne " + e.getMessage());
		}
		
		ObservableList<Artist> choices = FXCollections.observableArrayList(artistList);
		ObservableList<Genre> genreObservable = FXCollections.observableArrayList(genreList);
		
		AnchorPane Frontpage = (AnchorPane) loader.load();
		rootLayout.setCenter(Frontpage);

		TabPane tabPane = (TabPane) Frontpage.getChildren().get(0);
		GridPane gridPane = (GridPane) Frontpage.getChildren().get(1);
//		gridPane.setOnMouseClicked(event -> {System.out.println("GridPane event");});
		Button button = new Button();
		button.setOnMouseClicked(event -> {System.out.println("Childrennnn");});
		button.setAccessibleText("this is a button, wow");
		gridPane.add(button, 0, 0);
		
		int counter1 = 0;
		for (Artist artist : artistList) {
			System.out.println(artist.getArtistName());
		}
			Tab tab = tabPane.getTabs().get(0);
			Tab tab2 = tabPane.getTabs().get(1);
			Tab tab3 = tabPane.getTabs().get(2);
			Tab tab4 = tabPane.getTabs().get(3);
			
			AnchorPane anchorpane = (AnchorPane)tab.getContent();
			AnchorPane anchorpane2 = (AnchorPane)tab2.getContent();
			AnchorPane anchorpane3 = (AnchorPane)tab3.getContent();
			AnchorPane anchorpane4 = (AnchorPane)tab4.getContent();
//				@SuppressWarnings("unchecked")
			genreListView = (ListView<Genre>)anchorpane.getChildren().get(0);
			artistListView = (ListView<Artist>)anchorpane2.getChildren().get(0);
			albumListView = (ListView<Album>)anchorpane3.getChildren().get(0);
			songListView = (ListView<Song>)anchorpane4.getChildren().get(0);
			
			tab.setOnSelectionChanged(event -> {
				System.out.println("test");
				});
			genreListView.setCellFactory(lv -> new ListCell<Genre>() {
				@Override
				protected void updateItem(Genre genre, boolean empty) {
					super.updateItem(genre, empty);
					setText(empty || genre == null ? "" : genre.getGenreName()); 
				}
			});
			genreListView.setItems(genreObservable);
			
			artistListView.setCellFactory(lv -> new ListCell<Artist>() {
				@Override
				protected void updateItem(Artist artist, boolean empty) {
					super.updateItem(artist, empty);
					setText(empty || artist == null ? "" : artist.getArtistName());
				}
			});			
			artistListView.setItems(choices);
				
		System.out.println(artistListView.getItems().get(0).getArtistID());
//		tab.setContent(text);
//		GridPane gridPane = (GridPane)tabPane.getTabs().get(0).getContent();
//		Text text = new Text(genreList[0].getGenreName());
//		text.setId("1");
//		System.out.println("Tabpane näy ");
//		gridPane.add(text, 0, 0);
//		GridPane gridPane = (GridPane)Frontpage.getChildren().get();
//		gridPane.setAlignment(Pos.CENTER);
		
		int counter = 0;
//		for(int i = 0; i < gridPane.getColumnCount(); i++) {
//			for(int j = 0; j < gridPane.getRowCount(); j++) {
//				if(counter < stringList.size()) {
//					Text text = new Text();
//					text.setText(stringList.get(counter));
//					gridPane.add(text, i, j);
//					
//					counter++;
//				}
//			}
//		}

	}
	
	public static void test() throws IOException {
		Artist selectedItem = artistListView.getSelectionModel().getSelectedItem();
		if(selectedItem != null) {
			System.out.println("id = " + selectedItem.getArtistID());
		} else {
			System.out.println("no item selected");
		}
	}

	public static void showHelpPage() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(View.class.getResource("/view/fxmlFiles/helpPage.fxml"));
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
			for(int i = 0; i < windows.size(); i++) {
				System.out.println(windows.get(i).getTitle());
				if(windows.get(i).getTitle().contains("User")) {
					System.out.println("Truee");
					test = false;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(test) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/OmaKokoelma2.fxml"));
			userRoot = (AnchorPane) fxmlLoader.load();
			Scene scene = new Scene(userRoot);
			scene.getWindow();
			scene.getStylesheets().add("/view/style.css");
			Stage stage2 = new Stage();
			stage2.setMinWidth(1200);
			stage2.setTitle("User");
			stage2.setScene(scene);
			stage2.show();
			userRoot.requestFocus();
		}
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
		

		
			List<Window> windows = Window.getWindows();
			System.out.println(windows);
			boolean test = true;
			try {
				for(int i = 0; i < windows.size(); i++) {
					System.out.println(windows.get(i).getTitle());
					if(windows.get(i).getTitle().contains("Request")) {
						System.out.println("Truee");
						test = false;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if(test) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(View.class.getResource("/view/fxmlFiles/LisaysPyynnot2.fxml"));
				anotherRoot = (BorderPane) fxmlLoader.load();

				Scene scene = new Scene(anotherRoot);
				scene.getStylesheets().add("/view/style.css");
				Stage stage = new Stage();
				stage.setResizable(false);
//				stage.setMinWidth(1000);
//				stage.setMaxWidth(1000);
				stage.setTitle("Request");
				stage.setScene(scene);
				stage.show();
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