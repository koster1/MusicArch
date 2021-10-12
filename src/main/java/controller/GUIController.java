package controller;

import controller.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import javafx.util.Duration;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Genre;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.List;
import com.jcg.hibernate.maven.Song;

import view.*;

public class GUIController {
	private static Controller controller;
	private View view;
	private BorderPane borderpane;
	private double pauseDuration = 0.2;
	private Genre genreResults;
	private Artist artistResults;
	private Album albumResults;
	private Song songResults;
	List<String> everythingFound;

	
	@FXML
    private ContextMenu searchContext;
	
	@FXML

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
	/*	SearchButton.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

		pause.setOnFinished(event1 -> {
			SearchButton.setStyle(null);
		});
		pause.play();*/
		
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

	
	public GUIController() {}
	
	public GUIController(View view, Controller controller) {
		this.view = view;
		this.controller = controller;
	}

	
	// ----------------SIVUJEN VAIHDOT JA PÄIVITYKSET-----------------------
	// Menunappulat
	
	
	public void goFrontPage() throws IOException {
		View.showFrontPage();

	}

	@FXML
	void ShowRequests(ActionEvent event) throws IOException {

/*
		Requests.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));
		
		pause.setOnFinished(event1 -> {
			Requests.setStyle(null);
		});
		pause.play();*/
		view.showRequestsWindow();
	}

	@FXML
	void GoHelpPage(ActionEvent event) throws IOException {
		/*Help.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));
		
		pause.setOnFinished(event1 -> {
			Help.setStyle(null);
		});
		pause.play();*/
		view.showHelpPage();

	}
	

	
	@FXML
	void goUserCollection(ActionEvent event) throws IOException {
	/*	UserCollection.setStyle("-fx-border-color: #ffff33");
		PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));
		
		pause.setOnFinished(event1 -> {
			UserCollection.setStyle(null);
		});
		pause.play();*/

		
		
		System.out.println("UserGenreDrop = "+UserGenreDrop);
		view.showUserCollectionPage();
	}
	
	@FXML
	void goSearchPage(ActionEvent event) throws IOException {
		String searchText = SearchBox.getText();
		view.showSearchPage(searchText);
	}
	
	@FXML
	void GoAlbumPage(ActionEvent event) throws IOException {
		view.showAlbumPage();
	}
	
	 @FXML
	 void getSearchable(MouseEvent event) {
		 if(everythingFound == null) {
			 everythingFound = new ArrayList<>();
		 }
		 everythingFound = controller.getSearchable();
		 System.out.println("Fetched all the searchable values");
    }
	
	 @FXML
	 void refreshSearchList(KeyEvent event) {
		 
		 
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		SearchBox.setContextMenu(searchContext);	
		searchContext.show(SearchBox, null, pauseDuration, 50);	
		
		for(int i = 0; i<everythingFound.size(); i++) {		
			if(everythingFound.get(i).toLowerCase().contains(SearchBox.getText().toLowerCase())) {
				strippedList.add(everythingFound.get(i));
				menuCounter++;
			}
		}
		searchContext.getItems().clear();
		if(menuCounter > 5) {
			menuCounter = 4;
		}
		for(int i = 0; i<menuCounter; i++) {
			if(SearchBox.getText().isEmpty()) {
				break;
			}
			String testString = strippedList.get(i);
			MenuItem searchItem = new MenuItem(testString);
			searchItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					SearchBox.setText(searchItem.getText());
					try {
						view.showSearchPage(SearchBox.getText());
						SearchBox.clear();
					}catch(IOException e1) {
						System.out.println("Failed to commit a search in GUIController's refreshSearchList method!");
						e1.printStackTrace();
					}	
				}
			});
			System.out.println("Added a new menu item -> "+searchItem.getText());
			searchContext.getItems().add(searchItem);
		}
	 }


	
	//-------------Search results from controller------
	
	public void setGenreResults(Genre genre) {
		this.genreResults = genre;
	}
	public Genre getGenreResults() {
		return genreResults;
	}
	public void setArtistResults(Artist artistResults) {
		this.artistResults = artistResults;
	}
	public Artist getArtistResults(){
		return artistResults;
	}
	public void setAlbumResults(Album album) {
		this.albumResults = album;
	}
	public Album getAlbumResults(){
		return albumResults;
	}
	public void setSongResults(Song song) {
		this.songResults = song;
	}
	public Song getSongResults(){
		return songResults;
	}

}

