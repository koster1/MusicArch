package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.View;

public class RequestFormsController {
	
	

	private Controller controller;
	private View view;
	
    @FXML
    private AnchorPane RequestCategories;

    @FXML
    private TitledPane ArtistDrop;

    @FXML
    private TitledPane GenreDrop;

    @FXML
    private TitledPane AlbumDrop;


    @FXML
    private TextField GenreAddTxtField;

    @FXML
    private Label GenreAddLabel;

    @FXML
    private Button SendGenre;

    @FXML
    private Label GenreAddTitle;

    
    @FXML
    private TextField ArtistsName;


    @FXML
    private TextArea Biografia;

   



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

    @FXML
    private TextField GenreName;

    @FXML
    protected void initialize() {
    	
    }
    
    @FXML
    void Album(MouseEvent event) {

    }



    @FXML
    void Artist(MouseEvent event) {

    }



    @FXML
    void Genre(MouseEvent event) {

    }


    
    public RequestFormsController (Controller controller) {
    	this.controller = controller;
    }

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
	
	@FXML
	void SendAlbumButton(ActionEvent event) {
      String[] genreListGiven = { "Funk" };
      String[] artistName = { "Anna Puu" };
      String[] songListGiven = { "Testi2" };
//	      String albumName = AlbumName.getText();
      controller.createAlbum(AlbumName.getText(), 1970, genreListGiven, artistName, songListGiven);

	}

	

}
