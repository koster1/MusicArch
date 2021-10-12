package controller;

import controller.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Genre;
import java.io.IOException;
import java.util.List;
import com.jcg.hibernate.maven.Song;
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

