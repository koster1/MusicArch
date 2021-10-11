package controller;

import controller.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ListView;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Genre;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcg.hibernate.maven.Song;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import view.*;

public class GUIController {
	private static Controller controller;
	private View view;
	private BorderPane borderpane;
	private double pauseDuration = 0.2;

	private List<Genre> genreResults;
	private List<Artist> artistResults;
	private List<Album> albumResults;
	private List<Song> songResults;
	
	@FXML

	private BorderPane mainPane;
	@FXML
	private ButtonBar Buttonbar;
	private AnchorPane UserCategories;
	
	@FXML
	public TitledPane UserGenreDrop;
	
	@FXML
	private TitledPane UserAlbumDrop;
	
	@FXML
	private TitledPane UserArtistDrop;
	
	@FXML
	private Button SearchButton;

	// Etusivun hakukenttä
	@FXML 
	private TextField SearchBox;

	@FXML
	void SearchTxt(ActionEvent event) {
		SearchButton.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

		pause.setOnFinished(event1 -> {
			SearchButton.setStyle(null);
		});
		pause.play();
	}

	//
	@FXML
	private GridPane gridView;
	@FXML
	private Button gridButton;
	// Rootlayoutin keskikohta
	@FXML
	private AnchorPane Keskikohta;

	// Pääsivun nappulat
	@FXML
	private Button Requests;
	@FXML
	private Button Help;
	@FXML
	private Button FrontPage;
	@FXML
	private Button UserCollection;

	// ----------------------Genren lisäyslomake---------------------
	@FXML
	private TextField GenreAddTxtField;
	@FXML
	private Label GenreAddLabel;
	@FXML
	private Button SendGenre;
	@FXML
	private Label GenreAddTitle;

	// ---------------------- Albumin lisäys--------------------

	// Selvitä mikä tää send on?
//	@FXML
//	private Button Send;
	@FXML
	private TextField ArtistsName;
	@FXML
	private TextArea Biografia;

	public GUIController() {
	}

	public GUIController(View view, Controller controller) {
		this.view = view;
		this.controller = controller;
	}


	// SendGenreButton lähettää Genre-lomakkeen tiedot controlleriin.
	// Ponnauttaa Virhe-ikkunan, jos tekstikenttä on tyhjä
	// Täytyy lisätä muitakin ehtoja
	@FXML
	void SendGenreButton(ActionEvent event) throws IOException {
		String genreName = GenreAddTxtField.getText();
		if (genreName.isEmpty()) {
			view.Error();
			/*
			 * Alert alert = new Alert(AlertType.ERROR); alert.setTitle("Virhe!");
			 * alert.setHeaderText("Tarkista genren nimi. Nimi ei voi olla tyhjä.");
			 * alert.setContentText("Ooops, i did it again ;)"); alert.showAndWait();
			 */
			GenreAddTxtField.clear();

		} else {

			controller.createGenre(genreName);

			Dialog<String> dialog = new Dialog<String>();
			dialog.setTitle("Pyyntö lähetetty");
			dialog.setHeaderText("Genrepyyntö lähetetty!");
			dialog.setContentText("Lähetit pyynnön lisätä genren: " + genreName);
			GenreAddTxtField.clear();

			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(type);
			dialog.showAndWait();

		}
	}

	// SendArtistButton lähettää Artist-lomakkeen tiedot controlleriin.
	// Virhe-ikkuna ponnautetaan jos kentät ovat tyhjiä
	// Täytyy lisätä muitakin ehtoja
	@FXML
	void SendArtistButton(ActionEvent event) throws IOException {
		System.out.print(" Artistin nimi: " + ArtistsName.getText() + " Artistin bio: " + Biografia.getText() + " ");
		String artistName = ArtistsName.getText();
		String artistBio = Biografia.getText();

		if (ArtistsName.getText().isEmpty() || Biografia.getText().isEmpty()) {
			view.Error();
			/*
			 * Alert alert = new Alert(AlertType.ERROR); alert.setTitle("Virhe!");
			 * alert.setHeaderText("Tarkista artistin nimi. Nimeä ei voi jättää tyhjäksi!");
			 * alert.setContentText("Ooops, there was an error!"); alert.showAndWait();
			 */
			ArtistsName.clear();
			Biografia.clear();
		} else {
			// Pitää hakea booleani että minkä perustella katsotaan onko artisti kannassa
			controller.createArtist(artistName, artistBio);
			Dialog<String> dialog = new Dialog<String>();
			dialog.setTitle("Pyyntö lähetetty");
			dialog.setHeaderText("Artistipyyntö lähetetty!");
			dialog.setContentText("Lähetit pyynnön lisätä artistin: " + artistName);

			ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(type);
			dialog.showAndWait();

			// Tässä on joku probleema!
			ArtistsName.clear();
			Biografia.clear();
		}

	};

	// Lähetetään album-lomakkeen tiedot controllerille
	// ALbum jutut

	@FXML
	private TextField Released;

	@FXML
	private Label Year;

	@FXML
	private Label AlbumTitle;

	@FXML
	private TextField AlbumName;

	@FXML
	private TextField ArtistName;

	@FXML
	private TextField Songs;
	// @FXML
//	private TextField GenreName;
	@FXML
	private TextField GenreName1;
	@FXML
	private TextField GenreName2;
	@FXML
	private TextField GenreName3;
	@FXML
	private TextField textfield;
	@FXML
	private Button NewArtist;
	int i = 0;

	@FXML
	void SendAlbumButton(ActionEvent event) {
		// System.out.print(Arrays.toString(ArtistList.toArray()));
		//tee textfieldistä näkyvä kaikille
	//	ArtistList.add(field.getText().toString());

		for (i = 0; i < ArtistList.size(); i++) {
			System.out.println(i);
		}
		/*for (i = 0; i < ArtistList.size(); i++) {
			System.out.print(Arrays.toString(ArtistList.toArray()));
		}*/
		System.out.print(Arrays.toString(ArtistList.toArray()));
		
		String albumName = AlbumName.getText();
		String a = Songs.getText();
		// String Gn = GenreName.getText();
		String Gn1 = GenreName1.getText();
		String Gn2 = GenreName2.getText();
		String Gn3 = GenreName3.getText();
		String aN = ArtistName.getText();
		int albumYear = Integer.parseInt(Released.getText());
		ArtistList.add(ArtistName.getText());
//		String[] GenreListGiven = { "Rock", "Pop" };
//		String[] artistName = { "Abba", "Red Hot Chili Peppers" };
//		String[] songListGiven = { "Testi" };
		//(ArtistList.toArray(new String[0]))
		
		//controller.createAlbum(albumName, albumYear, GenreListGiven, artistName, songListGiven);
	}

	// ------------AlbumiFormin toiminnallisuus--------------------
	// Pitää lisätä uusia tekstikenttiä moniarvoisille tiedoille
	@FXML
	private ScrollPane artistScroll;
	@FXML
	private Button OkButton;
	@FXML
	private VBox root;
	@FXML
	private VBox root1;
	@FXML
	private Pane root2;
	@FXML
	private Button save;
	List<String> ArtistList = new ArrayList<String>();
	int counter = 0;
	@FXML
	void NewArtist(ActionEvent event) {
		counter++;
		final HBox parent = new HBox(5.0);
		TextField field = new TextField();
		field.setId("" + counter);
		//tee tähän counteri
		Button button = new Button("-");
		field.setAlignment(Pos.CENTER_LEFT);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setOnAction((e) -> parent.getChildren().clear());
		HBox.setHgrow(field, Priority.ALWAYS);
		HBox.setHgrow(button, Priority.NEVER);
		parent.getChildren().setAll(field, button);
		root.getChildren().add(parent);

		// String items = ;
		save.setOnAction((e2) -> {
			ArtistList.add(field.getText().toString());
			
		});
		
	}

	@FXML
	void NewGenre(ActionEvent event) {
		// Ei tiedä tarviiko
	}

	@FXML
	void NewSong(ActionEvent event) {
		final HBox parent = new HBox(5.0);
		TextField field = new TextField();
		Button button = new Button("-");
		field.setAlignment(Pos.CENTER_LEFT);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setOnAction((e) -> root1.getChildren().remove(parent));
		HBox.setHgrow(field, Priority.ALWAYS);
		HBox.setHgrow(button, Priority.NEVER);
		parent.getChildren().setAll(field, button);
		root1.getChildren().add(parent);
	}

	// Lisäyspyynnöt- sivu (Splitpane- näkymä)

	@FXML
	private SplitPane SplittedRequestPage;
	@FXML
	private AnchorPane RequestCategories;
	
	// ----------------SIVUJEN VAIHDOT JA PÄIVITYKSET-----------------------
	// Menunappulat
	
    @FXML
    private GridPane FrontGrid;
	
	@FXML
	private AnchorPane FrontAnchor;
	
	@FXML
	public void goFrontPage(ActionEvent event) throws IOException {
		System.out.println("FrontGrid = " + FrontGrid);
		FrontPage.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));
		System.out.println("Test12");
		pause.setOnFinished(event1 -> {
			FrontPage.setStyle(null);
		});
		pause.play();
		System.out.println("Test13");
		try {
			Artist[] artistList = controller.getArtists();
			Genre[] genreList = controller.getGenres();
			Album[] albumList = controller.getAlbums();
			
			System.out.println("albumList version 2 = "+albumList.length);
			for (Artist artist : artistList) {
				System.out.println("test" + artist.getArtistName());
//				stringList.add(artist.getArtistName());
			}



//			System.out.println(stringList);
			View.showFrontPage(artistList, genreList);

		} catch (Exception e) {
			System.out.println(e.getMessage()+" EnTItY MaNAgEr iS CloSEd");
		}

	}

	public void goFrontPage() throws IOException {
		Artist[] artistList = controller.getArtists();
		Genre[] genreList = controller.getGenres();
		ArrayList<String> stringList = new ArrayList<>();
		for (Artist artist : artistList) {
			System.out.println(artist.getArtistName());
			stringList.add(artist.getArtistName());
		}


	

		
			View.showFrontPage(artistList, genreList);
		
	}

	@FXML
	void ShowRequests(ActionEvent event) throws IOException {

		Requests.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

		pause.setOnFinished(event1 -> {
			Requests.setStyle(null);
		});
		pause.play();
		view.showRequestsWindow();
	}

	@FXML
	void GoHelpPage(ActionEvent event) throws IOException {
		Help.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

		pause.setOnFinished(event1 -> {
			Help.setStyle(null);
		});
		pause.play();
		view.showHelpPage();

	}

	@FXML
	void goUserCollection(ActionEvent event) throws IOException {
		UserCollection.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

		pause.setOnFinished(event1 -> {
			UserCollection.setStyle(null);
		});
		pause.play();

		
		
		System.out.println("UserGenreDrop = "+UserGenreDrop);
		view.showUserCollectionPage();
	}

	@FXML
	void goSearchPage(ActionEvent event) throws IOException {
		String searchText = SearchBox.getText();
		view.showSearchPage(searchText);
	}

	// --------------- Lisäyspyynnöt- sivun dropit-------------------

	@FXML
	private TitledPane GenreDrop;
	@FXML
	private TitledPane AlbumDrop;
	@FXML
	private TitledPane ArtistDrop;
	@FXML
	private AnchorPane RequestForms;

//-------------Dropit-----------------------
	@FXML
	void Album(MouseEvent event) throws IOException {
	}

	@FXML
	void Artist(MouseEvent event) {
	}

	@FXML
	void Genre(MouseEvent event) throws IOException {
	}

// -----------------LomakeButtonit------------
	@FXML
	void GenreFormButton(ActionEvent event) throws IOException {
		view.showGenreForm();
	}

	@FXML
	void AlbumFormButton(ActionEvent event) throws IOException {
		view.showAlbumForm();
	}

	@FXML
	void ArtistFormButton(ActionEvent event) throws IOException {
		view.showArtistForm();
	}

// -------------------Receive content from remote database-------------

	//
	@FXML
	void getArtists(ActionEvent event) throws IOException {
		Artist[] artistList = controller.getArtists();
		System.out.println(artistList[0].getArtistName() + " and " + artistList[0].getArtistID());
		ArrayList<String> stringList = new ArrayList<>();
		for (Artist artist : artistList) {
			System.out.println(artist.getArtistName());
			stringList.add(artist.getArtistName());
		}
		int counter = 0;
		for (int i = 0; i < gridView.getColumnCount(); i++) {
			for (int j = 0; j < gridView.getRowCount(); j++) {
				if (counter < stringList.size()) {
					Text text = new Text();
					text.setText(artistList[counter].getArtistName());
					gridView.add(text, i, j);
					counter++;
				}
			}
		}

	}

	
	//-------------Search results from controller------
	
	public void setGenreResults(List<Genre> genreResults) {
		this.genreResults = genreResults;
	}
	public List<Genre> getGenreResults() {
		return genreResults;
	}
	public void setArtistResults(List<Artist> artistResults) {
		this.artistResults = artistResults;
	}
	public List<Artist> getArtistResults(){
		return artistResults;
	}
	public void setAlbumResults(List<Album> albumResults) {
		this.albumResults = albumResults;
	}
	public List<Album> getAlbumResults(){
		return albumResults;
	}
	public void setSongResults(List<Song> songResults) {
		this.songResults = songResults;
	}
	public List<Song> getSongResults(){
		return songResults;
	}
}
