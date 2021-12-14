package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.jcg.hibernate.maven.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.MenuItem;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Language;
import model.LocalGenre;
import view.View;

public class RequestFormsController {
	private Controller controller;
	private View view;

	@FXML
	private AnchorPane RequestCategories;
	@FXML
	private TitledPane GenreDrop;
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
	private VBox root2;

	@FXML
	private ScrollPane scrollPane;

	// --------------testejä-----------------------------------------------

	@FXML
	private ContextMenu searchContext;
	@FXML
	private ContextMenu searchContextArtist;
	@FXML
	private Button SearchButton;
	// @FXML
	// private TextField SearchBox;

	private Genre genreResults;
	private Artist artistResults;
	private Album albumResults;
	private Song songResults;
	// List<String> everythingFound;
	List<String> genresFound;
	List<String> artistsFound;
	List<String> albumsFound;

	@FXML
	private GridPane requestGrid;

	@FXML
	private ListView<UserRequests> requestList;

	@FXML
	private Label requestTitle;

	@FXML
	private Label requestText;

	@FXML
	private Text moi;

	public RequestFormsController() {
	}

	public RequestFormsController(Controller controller) {
		this.controller = controller;
	}

	int requestId;

	// Dropdownlists mouseevents
	@FXML
	void Genre(MouseEvent event) {

		// UserRequests[] titles = controller.getRequest(int id);
		UserRequests[] requests = controller.getRequests();
		ObservableList<UserRequests> requestObs = FXCollections.observableArrayList(requests);
		requestList.setCellFactory(lv -> new ListCell<UserRequests>() {
			@Override
			protected void updateItem(UserRequests r, boolean empty) {
				super.updateItem(r, empty);
				setText(empty || r == null || requests.length == 0 ? "" : r.getRequestTitle());
			}
		});
		requestList.setItems(requestObs);
		GenreDrop.autosize();

		requestList.setOnMouseClicked(me -> {
			Button delete = new Button();
			delete.setText(Language.getInstance().getBundle().getString("DeletePageButton"));
			UserRequests ur = requestList.getSelectionModel().getSelectedItem();
			requestTitle.setText(ur.getRequestTitle());
			moi.setText(ur.getRequestContents());
			requestGrid.add(delete, 0, 3);
			delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					controller.removeRequest(ur.getRequestID());
					requestGrid.getChildren().clear();
					requestList.refresh();
				}

			});

		});

	}


	// GenreFormButton, AlbumFromButton and ArtistFromButton sends request to view
	// to change forms in requests.fxml
	@FXML
	void GenreFormButton(ActionEvent event) throws IOException {
		view.showGenreForm();
	}

	// ----------------------Search existing genres------------------------

	@FXML
	void getSearchable(MouseEvent event) {
		if (genresFound == null) {
			genresFound = new ArrayList<>();
		}
		genresFound = controller.getSearchableGenres();
		System.out.println("Fetched all the searchable values");
	}

	@FXML
	void refreshSearchList(KeyEvent event) {
		GenreAddTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 50)
				GenreAddTxtField.setText(oldValue);
		});
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		GenreAddTxtField.setContextMenu(searchContext);
		searchContext.show(GenreAddTxtField, Side.BOTTOM, 0, 0);
		searchContext.getItems().clear();
		for (int i = 0; i < genresFound.size(); i++) {
			if (genresFound.get(i).toLowerCase().contains(GenreAddTxtField.getText().toLowerCase())) {
				strippedList.add(genresFound.get(i));
				menuCounter++;
			}
		}
		if (menuCounter > 5) {
			menuCounter = 4;
		}

		for (int i = 0; i < menuCounter; i++) {
			String testString = strippedList.get(i);

			MenuItem searchItem = new MenuItem(testString);

			System.out.println("Added a new menu item -> " + searchItem.getText());
			searchContext.getItems().add(searchItem);

		}
	}

//-----------------------Search existing genres ends--------

	@FXML
	void ArtistFormButton(ActionEvent event) throws IOException {
		view.showArtistForm();
	}

//------------------Search existing artists --------------------

	@FXML
	void getSearchableArtists(MouseEvent event) {
		if (artistsFound == null) {
			artistsFound = new ArrayList<>();
		}
		artistsFound = controller.getSearchableArtists();
		System.out.println("Fetched all the searchable values");
	}

	@FXML
	void refreshArtistSearchList(KeyEvent event) {
		ArtistsName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 50)
				ArtistsName.setText(oldValue);
		});
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		ArtistsName.setContextMenu(searchContextArtist);

		searchContextArtist.show(ArtistsName, Side.BOTTOM, 0, 0);

		searchContextArtist.getItems().clear();
		for (int i = 0; i < artistsFound.size(); i++) {
			if (artistsFound.get(i).toLowerCase().contains(ArtistsName.getText().toLowerCase())) {
				strippedList.add(artistsFound.get(i));
				menuCounter++;

			}
		}
		if (menuCounter > 5) {
			menuCounter = 4;
		}
		System.out.println(menuCounter + " menucounter");
		for (int i = 0; i < menuCounter; i++) {
			String testString = strippedList.get(i);
			MenuItem searchItem = new MenuItem(testString);
			System.out.println("Added a new menu item -> " + searchItem.getText());
			searchContextArtist.getItems().add(searchItem);
		}

	}

	@FXML
	void AlbumFormButton(ActionEvent event) throws IOException {
		view.showAlbumForm();
	}

	// -------------------Search existing genres to album-------------------

	@FXML
	private TextField addGenres2Album;
	@FXML
	private GridPane ankkuri;

	@FXML
	void getAlbumGenresSearchable(MouseEvent event) {
		if (genresFound == null) {
			genresFound = new ArrayList<>();
		}
		genresFound = controller.getSearchableGenres();
		System.out.println("Fetched all the searchable values");
	}

	int genreCounter = 0;
	ArrayList<Button> genreButtonList = new ArrayList<Button>();

	@FXML
	void refreshGenreList(KeyEvent event) {
		addGenres2Album.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 50)
				addGenres2Album.setText(oldValue);
		});
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		addGenres2Album.setContextMenu(searchContext);
		searchContext.show(addGenres2Album, Side.BOTTOM, 0, 0);
		searchContext.getItems().clear();
		for (int i = 0; i < genresFound.size(); i++) {
			if (genresFound.get(i).toLowerCase().contains(addGenres2Album.getText().toLowerCase())) {
				strippedList.add(genresFound.get(i));
				menuCounter++;
			}
		}
		if (menuCounter > 5) {
			menuCounter = 4;
		}
		System.out.println(menuCounter + " menucounter");
		for (int i = 0; i < menuCounter; i++) {
			String testString = strippedList.get(i);
			MenuItem searchItem = new MenuItem(testString);
			// Add selected genres to list
			searchItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					addGenres2Album.setText(searchItem.getText());
					Button button = new Button(addGenres2Album.getText());
					button.setId(String.valueOf(genreCounter++));
					System.out.println(button.getId());
					if (genreButtonList.size() < 3) {
					genreButtonList.add(button);
					// button.setAlignment(Pos.CENTER_RIGHT);
					ankkuri.add(button, genreCounter, 0);
					addGenres2Album.clear();
					}
					System.out.println("Poistamista ennen: " + genreButtonList);

					// button.setOnAction((e) -> ankkuri.getChildren().remove(button.getId()));
					button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							ankkuri.getChildren().remove(button);
							genreButtonList.remove(button);
							System.out.println("Poistamisen jälkeen: " + genreButtonList);

						}

					});
				}
			});
			System.out.println("Added a new menu item -> " + searchItem.getText());
			searchContext.getItems().add(searchItem);
		}
		System.out.println(genreButtonList);
	}

	// -------------------Search existing artists to album-------------------

	@FXML
	private TextField addArtists2Album;
	@FXML
	private GridPane artistTags;

	@FXML
	void getAlbumArtistsSearchable(MouseEvent event) {
		if (artistsFound == null) {
			artistsFound = new ArrayList<>();
		}
		artistsFound = controller.getSearchableArtists();
		System.out.println("Fetched all the searchable values");
	}

	int artistCounter = 0;
	ArrayList<Button> artistButtonList = new ArrayList<Button>();

	@FXML
	void refreshArtistList(KeyEvent event) {
		addArtists2Album.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > 50)
				addArtists2Album.setText(oldValue);
		});
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		addArtists2Album.setContextMenu(searchContextArtist);

		searchContextArtist.show(addArtists2Album, Side.BOTTOM, 0, 0);

		searchContextArtist.getItems().clear();
		for (int i = 0; i < artistsFound.size(); i++) {
			if (artistsFound.get(i).toLowerCase().contains(addArtists2Album.getText().toLowerCase())) {
				strippedList.add(artistsFound.get(i));
				menuCounter++;
			}
		}
		if (menuCounter > 5) {
			menuCounter = 4;
		}
		System.out.println(menuCounter + " menucounter");
		for (int i = 0; i < menuCounter; i++) {
			String testString = strippedList.get(i);
			MenuItem searchItem = new MenuItem(testString);
			// Add selected genres to list
			searchItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					addArtists2Album.setText(searchItem.getText());
					Button button = new Button(addArtists2Album.getText());
					button.setId(String.valueOf(artistCounter++));
					System.out.println(button.getId());
					if (artistButtonList.size() < 3) {
					artistButtonList.add(button);
					// button.setAlignment(Pos.CENTER_RIGHT);
					artistTags.add(button, artistCounter, 0);
					addArtists2Album.clear();
					}
					System.out.println("Poistamista ennen: " + artistButtonList);

					// button.setOnAction((e) -> ankkuri.getChildren().remove(button.getId()));
					button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							artistTags.getChildren().remove(button);
							artistButtonList.remove(button);
							System.out.println("Poistamisen jälkeen: " + artistButtonList);

						}

					});
				}
			});
			System.out.println("Added a new menu item -> " + searchItem.getText());
			searchContextArtist.getItems().add(searchItem);
		}
		System.out.println(artistButtonList);
	}

	int counter3 = 0;
	ArrayList<TextField> songList = new ArrayList<TextField>();

	@FXML
	void NewSong(ActionEvent event) {

		TextField field = new TextField();
		final HBox parent = new HBox(5.0);
		field.setId(String.valueOf(counter3++));
		field.setFocusTraversable(true);
		System.out.print(field.getId());
		songList.add(field);
		System.out.println(field.getId());
		// ArtistList.add(field.getId());
		Button button = new Button("-");
		field.setAlignment(Pos.CENTER_LEFT);
		button.setAlignment(Pos.CENTER_RIGHT);
		// button.setOnAction((e) -> parent.getChildren().clear() &&
		// songList.remove(field));
		button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				parent.getChildren().clear();
				songList.remove(field);
				System.out.println("Poistamisen jälkeen: " + songList);
			}
		});
		HBox.setHgrow(field, Priority.ALWAYS);
		HBox.setHgrow(button, Priority.NEVER);
		parent.getChildren().setAll(field, button);
		root1.getChildren().add(parent);
		field.requestFocus();
		root1.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

	}

	// SendGenreButton sends textfield data to controller
	// The Error window pops up if GenreAddTxtField textfield is empty.
	String olemassa;

	@FXML
	void SendGenreButton(ActionEvent event) throws IOException {
		String genreName = GenreAddTxtField.getText();
		if (genreName.isEmpty() || genreName.isBlank()) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(Language.getInstance().getBundle().getString("AlertTitleGenreEmpty"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextGenreEmpty"));
			alert.showAndWait();

		} else {
			// view.showConf();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(Language.getInstance().getBundle().getString("ConfirmGenre"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextGenre"));
			alert.setContentText(Language.getInstance().getBundle().getString("AlertContentTextGenre"));
			//alert.setContentText(genreName);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				try {
					controller.createGenre(genreName);
					GenreAddTxtField.clear();

				} catch (Exception e) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle(Language.getInstance().getBundle().getString("AlertTitleGenreExist"));
					alert2.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextGenreExist"));
					alert2.showAndWait();
					e.printStackTrace();

				}
			}
		}
	}

	// SendArtistButton sends textfield data to controller
	// The Error window pops up if ArtistsName textfield is empty.
	@FXML
	void SendArtistButton(ActionEvent event) throws IOException {
		String artistName = ArtistsName.getText();
		String artistBio = Biografia.getText();

		if (ArtistsName.getText().isEmpty() || ArtistsName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(Language.getInstance().getBundle().getString("AlertTitleArtistEmpty"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextArtistEmpty"));
			alert.showAndWait();
			

		} else {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(Language.getInstance().getBundle().getString("ConfirmArtist"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextArtist"));
			alert.setContentText(Language.getInstance().getBundle().getString("AlertContentTextArtist"));

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				try {
					controller.createArtist(artistName, artistBio);
					ArtistsName.clear();
					Biografia.clear();

				} catch (Exception e) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle(Language.getInstance().getBundle().getString("AlertTitleArtistExist"));
					alert2.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextArtistExist"));
					alert2.showAndWait();
					e.printStackTrace();

				}
			}
		}

	};

	@FXML
	void SendAlbumButton(ActionEvent event) throws IOException {

		if (AlbumName.getText().isEmpty() || AlbumName.getText().isBlank()) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(Language.getInstance().getBundle().getString("AlertTitleAlbumEmpty"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextAlbumEmpty"));
			alert.showAndWait();


		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(Language.getInstance().getBundle().getString("ConfirmAlbum"));
			alert.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextAlbum"));
			alert.setContentText(Language.getInstance().getBundle().getString("AlertContentTextAlbum"));
		
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				try {
					String[] genreListGiven = new String[genreButtonList.size()];
					int k = 0;
					for (Button button : genreButtonList) {
						System.out.println("Testataan listaa " + button.getId());
						System.out.println(button.getText());
						genreListGiven[k] = button.getText();
						k++;
					}

					String[] artistName = new String[artistButtonList.size()];
					int j = 0;

					for (Button artist : artistButtonList) {
						System.out.println("Testataan listaa " + artist.getId());
						System.out.println(artist.getText());
						artistName[j] = artist.getText();
						j++;
					}

					String[] songListGiven = new String[songList.size()];

					int h = 0;

					for (int i = 0; i < songList.size(); i++) {
						System.out.println("Testing list ->" + songList.get(i).getId());
						System.out.println(songList.get(i).getText());
						songListGiven[i] = songList.get(i).getText();
					}

					for (String s : songListGiven) {
						System.out.println(s);
					}

					controller.createAlbum(AlbumName.getText(), Integer.parseInt(Released.getText()), genreListGiven,
							artistName, songListGiven);

				} catch (Exception e) {
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.setTitle(Language.getInstance().getBundle().getString("AlertTitleAlbumExist"));
					alert2.setHeaderText(Language.getInstance().getBundle().getString("AlertHeaderTextAlbumExist"));
					alert2.showAndWait();
					e.printStackTrace();

				}
			}
		}

	}
	// ----Search results from controller------

	public void setGenreResults(Genre genre) {
		this.genreResults = genre;
	}

	public Genre getGenreResults() {
		return genreResults;
	}

	public void setArtistResults(Artist artistResults) {
		this.artistResults = artistResults;
	}

	public Artist getArtistResults() {
		return artistResults;
	}

	public void setAlbumResults(Album album) {
		this.albumResults = album;
	}

	public Album getAlbumResults() {
		return albumResults;
	}

	public void setSongResults(Song song) {
		this.songResults = song;
	}

	public Song getSongResults() {
		return songResults;
	}
}
