package controller;

import controller.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.*;

public class GUIController {
	Controller controller = new Controller();
	private View view;
	private BorderPane borderpane;

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
	@FXML
	private TextField Released;
	@FXML
	private Label Year;
	@FXML
	private Button SendAlbum;
	@FXML
	private Label AlbumTitle;
	@FXML
	private TextField AlbumName;
	@FXML
	private TextField ArtistName;
	@FXML
	private TextField GenreName;
	@FXML
	private TextField Songs;
	@FXML
	private Button AddSong;
	@FXML
	private Button AddArtist;
	@FXML
	private Button Send;
	@FXML
	private TextField ArtistsName;
	@FXML
	private TextArea Biografia;

//LISÄÄ MYÖS EHTO ETTEI VOI TALLENTAA SPACEA
	// SendGenreButton lähettää Genre-lomakkeen tiedot controlleriin.
	// Ponnauttaa Virhe-ikkunan, jos tekstikenttä on tyhjä
	// Täytyy lisätä muitakin ehtoja
	@FXML
	void SendGenreButton(ActionEvent event) {
		String genreName = GenreAddTxtField.getText();
		if (genreName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Virhe!");
			alert.setHeaderText("Tarkista genren nimi. Nimi ei voi olla tyhjä.");
			alert.setContentText("Ooops, i did it again ;)");
			alert.showAndWait();
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

//TÄSSÄ KOHTAA JOKU VIRHE
	// SendArtistButton lähettää Artist-lomakkeen tiedot controlleriin.
	// Virhe-ikkuna ponnautetaan jos kentät ovat tyhjiä
	// Täytyy lisätä muitakin ehtoja
	@FXML
	void SendArtistButton(ActionEvent event) {
		String artistName = ArtistsName.getText();
		String artistBio = Biografia.getText();
	/*	if (ArtistsName.getText().isEmpty() || Biografia.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Virhe!");
			alert.setHeaderText("Tarkista artistin nimi. Nimeä ei voi jättää tyhjäksi!");
			alert.setContentText("Ooops, there was an error!");
			alert.showAndWait();
		} else {*/
			controller.createArtist(artistName, artistBio);
		
	};

	// ------------AlbumiFormin toiminnallisuus--------------------
	@FXML
	void NewArtist(ActionEvent event) {
	}

	@FXML
	void NewGenre(ActionEvent event) {
	}

	@FXML
	void NewSong(ActionEvent event) {
	}

	@FXML
	void SendAlbumButton(ActionEvent event) {
		/*
		 * String genreName = GenreAddTxtField.getText(); GenreAddTxtField.clear();
		 */
	}

	// Lisäyspyynnöt- sivu (Splitpane- näkymä)

	@FXML
	private SplitPane SplittedRequestPage;
	@FXML
	private AnchorPane RequestCategories;

	// ----------------SIVUJEN VAIHDOT JA PÄIVITYKSET-----------------------
	// Menunappulat
	@FXML
	void goFrontPage(ActionEvent event) throws IOException {
		view.showFrontPage();
	}

	@FXML
	void ShowRequests(ActionEvent event) throws IOException {
		view.showRequestsWindow();
	}

	@FXML
	void GoHelpPage(ActionEvent event) throws IOException {
		view.showHelpPage();

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

}