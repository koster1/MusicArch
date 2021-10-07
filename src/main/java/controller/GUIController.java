package controller;

import controller.*;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Genre;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import view.View;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.jcg.hibernate.maven.Song;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.*;

public class GUIController {
	private static Controller controller;
	private View view;
	private BorderPane borderpane;
	private double pauseDuration = 0.2;
	
	@FXML
	private BorderPane mainPane;
	
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
	
	public GUIController() {}
	
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
			dialog.setContentText("Lähetit pyynnön lisätä genren: " + artistName);

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
	/*
	 * @FXML private TextField Songs1;
	 * 
	 * @FXML private TextField Songs2;
	 */

	@FXML
	private TextField GenreName;

	@FXML
	void SendAlbumButton(ActionEvent event) {

		// controller.createAlbum(albumName, null, julkaisuvuosi, null, null);

	}

	// ------------AlbumiFormin toiminnallisuus--------------------
	// Pitää lisätä uusia tekstikenttiä moniarvoisille tiedoille
	@FXML
	private ScrollPane artistScroll;

	@FXML
	private GridPane root;

	@FXML
	private Pane pane;
	int i = 1;

	@FXML
	void NewArtist(ActionEvent event) {

		TextField textField[] = new TextField[15];
		textField[i] = new TextField();
		root.add(textField[i], 0, i);
		i = i + 1;

		
		
		
		
		// kesken
		/*
		 * TextField txtfield = new TextField(); artistScroll.setContent(txtfield);
		 */
		/*
		 * final HBox parent = new HBox(5.0); // 5.0 is the distance between the field
		 * and the button; hbox is the parent // of both
		 * 
		 * TextField field = new TextField(); Button button = new Button("-"); // the
		 * button to "close" the textfield button.setOnAction((e) ->
		 * pane.getChildren().remove(parent)); // button click removes the hbox
		 * button.setPrefSize(100, 27); // only if you're using a custom font / styling
		 * HBox.setHgrow(field, Priority.ALWAYS); // field should always grow
		 * HBox.setHgrow(button, Priority.NEVER); // button should never grow
		 * parent.getChildren().setAll(field, button); // add the textfield and the
		 * button to the hbox pane.getChildren().add(parent); // add the hbox to your
		 * main grid
		 */

		// Lisää textfieldin mutta pitää tehdä scrollpane
		// root.setHgap(10);
		// root.setVgap(10);
		/*
		 * TextField textField[] = new TextField[15]; Button btn = new
		 * Button("Add TextField"); root.add(btn, 0, 0); btn.setOnAction(e -> {
		 * textField[i] = new TextField(); root.add(textField[i], 5, i); i = i + 1;
		 * 
		 * });
		 */

	}

	@FXML
	void NewGenre(ActionEvent event) {

	}

	@FXML
	void NewSong(ActionEvent event) {
	}

	// Lisäyspyynnöt- sivu (Splitpane- näkymä)

	@FXML
	private SplitPane SplittedRequestPage;
	@FXML
	private AnchorPane RequestCategories;

	// ----------------SIVUJEN VAIHDOT JA PÄIVITYKSET-----------------------
	// Menunappulat
	@FXML
	public void goFrontPage(ActionEvent event) throws IOException {
		System.out.println("Test11");
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
			ArrayList<String> stringList = new ArrayList<>();
			for (Artist artist : artistList) {
				System.out.println(artist.getArtistName());
				stringList.add(artist.getArtistName());
			}
			System.out.println(stringList);
			View.showFrontPage(stringList);
			
		} catch (Exception e) {
			System.out.println(e.getMessage()+" EnTItY MaNAgEr iS CloSEd");
		}

	}
	
	public void goFrontPage() throws IOException {
		Artist[] artistList = controller.getArtists();
		ArrayList<String> stringList = new ArrayList<>();
		for (Artist artist : artistList) {
			System.out.println(artist.getArtistName());
			stringList.add(artist.getArtistName());
		}
		
			View.showFrontPage(stringList);
		
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
		view.showUserCollectionPage();
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
		for(int i = 0; i < gridView.getColumnCount(); i++) {
			for(int j = 0; j < gridView.getRowCount(); j++) {
				if(counter < stringList.size()) {
					Text text = new Text();
					text.setText(artistList[counter].getArtistName());
					gridView.add(text, i, j);
					counter++;
				}
			}
		}
		
	}
	

}
