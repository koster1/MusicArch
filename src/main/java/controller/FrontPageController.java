package controller;

import java.util.List;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.LocalAlbum;
import model.LocalArtist;
import model.LocalGenre;
import model.LocalSong;

public class FrontPageController {
	
	private Controller controller;
	@FXML
	private ListView<Genre> FrontGenreListView;
	
	@FXML
	private ListView<Artist> FrontArtistListView;
	
	@FXML
	private GridPane FrontPageGrid;
	
	@FXML
    private TabPane FrontPageTabPane;

    @FXML
    private Tab FrontPageGenreTab;
    
    @FXML
    private Tab FrontPageArtistTab;
    

    @FXML
    private ButtonBar BreadCrumbBar;
	
	public FrontPageController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * This method is for setting up the frontpage views list. 
	 * It also adds eventlisteners to all list items
	 * **/
	@FXML
	protected void initialize() {
		Genre[] genreList = controller.getGenres();
		Artist[] artistList = controller.getArtists();
		ObservableList<Artist> choices = FXCollections.observableArrayList(artistList);
		ObservableList<Genre> genreObservable = FXCollections.observableArrayList(genreList);
		
		FrontGenreListView.setCellFactory(lv -> new ListCell<Genre>() {
			@Override
			protected void updateItem(Genre genre, boolean empty) {
				super.updateItem(genre, empty);
				setText(empty || genre == null || genreList.length == 0 ? "" : genre.getGenreName()); 
			}
		});
		FrontGenreListView.setItems(genreObservable);
		
		FrontGenreListView.setOnMouseClicked(me -> {
			
			Genre listGenre = FrontGenreListView.getSelectionModel().getSelectedItem();
			List<Album> genreAlbums = controller.getGenreAlbums(listGenre.getGenreID());
			


			if(genreAlbums.size() > 0) {
				FrontPageGrid.getChildren().clear();
				FrontPageGrid.add(new Text(genreAlbums.get(0).getAlbumName()), 1, 0);
			} else {
				System.out.println("Nothing found ");
			}
			
  
		});
		
		FrontArtistListView.setCellFactory(lv -> new ListCell<Artist>() {
			@Override
			protected void updateItem(Artist artist, boolean empty) {
				super.updateItem(artist, empty);
				setText(empty || artist == null || artistList.length == 0 ? "" : artist.getArtistName());
			}
		});			
		FrontArtistListView.setItems(choices);
		
		FrontArtistListView.setOnMouseClicked(me -> {
			
			Artist listArtist = FrontArtistListView.getSelectionModel().getSelectedItem();
			List<Album> artistAlbums = controller.getArtistAlbums(listArtist.getArtistID());
			
			if(artistAlbums.size() > 0) {
				FrontPageGrid.getChildren().clear();
				Text text = new Text();
				text.setText(artistAlbums.get(0).getAlbumName());
				
				for(int i = 0; i < 9; i++) {
					System.out.println(FrontPageGrid.getChildren());
				FrontPageGrid.setOnMouseClicked(even -> {
					System.out.println("print me");
				});
				}
				FrontPageGrid.add(text, 1, 0);
			} else {
				System.out.println("Nothing found ");
			}
		});
		
		
		
	}
	
	

}
