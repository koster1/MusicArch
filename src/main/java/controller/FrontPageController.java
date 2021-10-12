package controller;

import java.util.List;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
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
	
	public FrontPageController(Controller controller) {
		this.controller = controller;
	}
	
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
			List<LocalGenre> localGenre = controller(listGenre.getGenreID());
			List<LocalArtist> localArtist = controller.getLocalAlbumArtists(listLocalAlbum.getAlbumID());
			if(listLocalAlbum != null) {
				AlbumNameLabel.setText(listLocalAlbum.getAlbumName()); 				
			}
			if(localGenre.size() > 0) {
				AlbumGenreLabel.setText(localGenre.get(0).getGenreName());
				System.out.println(listLocalAlbum.getAlbumName());
			} else {
				AlbumGenreLabel.setText("Not found");
			}
			if(localArtist.size() > 0) {
				AlbumArtistLabel.setText(localArtist.get(0).getArtistName());
			} else {
				AlbumArtistLabel.setText("Not found");
			}
			List<LocalSong> localSongs = controller.getLocalAlbumSongs(listLocalAlbum.getAlbumID());
			ObservableList<LocalSong> observableSongs = FXCollections.observableArrayList(localSongs);
			
    		SongListView.setCellFactory(lv -> new ListCell<LocalSong>() {
    			@Override
    			protected void updateItem(LocalSong localSong, boolean empty) {
    				super.updateItem(localSong, empty);
    				setText(empty || localSong == null || localSongs.size() == 0 ? "" : localSong.getSongName());
    			}
    		});		
			SongListView.setItems(observableSongs);
		});
		
		FrontArtistListView.setCellFactory(lv -> new ListCell<Artist>() {
			@Override
			protected void updateItem(Artist artist, boolean empty) {
				super.updateItem(artist, empty);
				setText(empty || artist == null || artistList.length == 0 ? "" : artist.getArtistName());
			}
		});			
		FrontArtistListView.setItems(choices);
		
		
		
	}
	
	

}
