package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
	private VBox root;
	@FXML
	private VBox root1;

	@FXML
	protected void initialize() {

	}

	// Dropdownlists
	@FXML
	void Album(MouseEvent event) {

	}

	@FXML
	void Artist(MouseEvent event) {

	}

	@FXML
	void Genre(MouseEvent event) {

	}

	public RequestFormsController(Controller controller) {
		this.controller = controller;
	}

	// Switch forms in requests.fxml
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

	private Button save;
	List<Object> ArtistList = new ArrayList<Object>();
	int counter = 0;

	@FXML
	void NewArtist(ActionEvent event) {
			TextField field = new TextField();

		//counter++;
		final HBox parent = new HBox(5.0);
		field.setId("" + counter++);
		System.out.print(field.getId());
		ArtistList.add(field.getId());
		Button button = new Button("-");
		field.setAlignment(Pos.CENTER_LEFT);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setOnAction((e) -> parent.getChildren().clear());
		HBox.setHgrow(field, Priority.ALWAYS);
		HBox.setHgrow(button, Priority.NEVER);
		parent.getChildren().setAll(field, button);
		root.getChildren().add(parent);

	}

	@FXML
	void NewGenre(ActionEvent event) {

	}

	@FXML
	void NewSong(ActionEvent event) {
		/*
		 * final HBox parent = new HBox(5.0); TextField field = new TextField(); Button
		 * button = new Button("-"); field.setAlignment(Pos.CENTER_LEFT);
		 * button.setAlignment(Pos.CENTER_RIGHT); button.setOnAction((e) ->
		 * root1.getChildren().remove(parent)); HBox.setHgrow(field, Priority.ALWAYS);
		 * HBox.setHgrow(button, Priority.NEVER); parent.getChildren().setAll(field,
		 * button); root1.getChildren().add(parent);
		 */
	}

	// SendGenreButton, SendArtistButton and SendAlbumButton sends data to
	// controller.
	// Popups error window if textfields are empty or
	@FXML
	void SendGenreButton(ActionEvent event) throws IOException {
		String genreName = GenreAddTxtField.getText();
		if (genreName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Virhe!");
			alert.setHeaderText("Tarkista genren nimi. Nimi ei voi olla tyhjä.");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Vahistuspyyntö");
			alert.setHeaderText("Vahvista genrepyyntö:");
			alert.setContentText("Haluatko lähettää pyynnön lisätä genren " + genreName + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				controller.createGenre(genreName);
				GenreAddTxtField.clear();

			}
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Virhe!");
			alert.setHeaderText("Tarkista artistin nimi. Nimeä ei voi jättää tyhjäksi!");
			alert.showAndWait();

		} else {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Vahvistuspyyntö");
			alert.setHeaderText("Vahvista artistipyyntö:");
			alert.setContentText("Haluatko lähettää pyynnön lisätä artistin " + artistName
					+ " ja artistille biografian: " + artistBio + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				controller.createArtist(artistName, artistBio);
				ArtistsName.clear();
				Biografia.clear();
			}
		}

	};

	@FXML
	void SendAlbumButton(ActionEvent event) {
		//ArtistList.add(field.getText().toString());
		int i = 0;
		System.out.print(Arrays.toString(ArtistList.toArray()));
		for (i = 0; i < ArtistList.size(); i++) {
			 System.out.print(i); 
			 }
			 
		String[] genreListGiven = { "Funk" };
		String[] artistName = { "Anna Puu" };
		String[] songListGiven = { "Testi2" };
//	  String albumName = AlbumName.getText();
		controller.createAlbum(AlbumName.getText(), 1970, genreListGiven, artistName, songListGiven);

	}

}
