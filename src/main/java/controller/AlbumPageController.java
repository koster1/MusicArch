package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import view.View;


public class AlbumPageController {

	private Controller controller;
	private View view;
	private boolean editing = false;
	
	   @FXML
	    private GridPane AlbumInfo;

	    @FXML
	    private Label AlbumName;

	    @FXML
	    private Label AlbumYear;

	    @FXML
	    private Label AlbumArtist;
	    
	    @FXML
	    private Label AlbumGenre;

	    @FXML
	    private Button WishlistAdd;

	    @FXML
	    private Button CollectionAdd;
	    
	    @FXML
	    private Button BackButton;
	    
	    @FXML
	    private Button editButton;

	    @FXML
	    private ListView<Song> SongListView;
	    
	    @FXML
	    private GridPane artistGrid;
	    
	    @FXML
	    private GridPane genreGrid;
	    
	    @FXML
	    private GridPane albumNameGrid;
	    
	    @FXML
	    private GridPane albumYearGrid;
	    
	    @FXML
	    private TextField albumNameField;

	    @FXML
	    private Label albumNameLabel;

	    @FXML
	    private TextField albumYearField;

	    @FXML
	    private Label albumYearLabel;
	    
	    private int id;
	    private Album album;
	    private Set<Artist> artists;
	    private Set<Genre> genres;
	    private Set<Song> songs;
	
	public AlbumPageController(Controller controller, int id) {
		this.controller = controller;
		this.id = id;
		this.album = this.controller.getAlbum(this.id);
		this.artists = this.controller.getAlbumArtistList(id);
		this.genres = this.controller.getAlbumGenreList(id);
		this.songs = this.controller.getAlbumSong(id);
	}
	
	
	@FXML
	protected void initialize() {
		
		try {
			controller.readLocalAlbum(id);
			CollectionAdd.setDisable(true);
		} catch (Exception e) {
			e.getMessage();
		}
		
		Artist[] artistArray = artists.toArray(new Artist[artists.size()]);
		Genre[] genreArray = genres.toArray(new Genre[genres.size()]);
		
		
		
		artistGrid.getChildren().clear();
		artistGrid.setMaxWidth(250.0);
		genreGrid.getChildren().clear();
		genreGrid.setMaxWidth(250.0);
		albumNameGrid.getChildren().clear();
		albumNameGrid.setMaxWidth(250.0);
		albumYearGrid.getChildren().clear();
		albumYearGrid.setMaxWidth(250.0);
				
		TextField albumNameField = new TextField();
		albumNameField.setText(album.getAlbumName());
		albumNameGrid.add(albumNameField, 0, 0);
		albumNameField.setVisible(false);
		
		Label albumNameLabel = new Label();
		albumNameLabel.setText(album.getAlbumName());
		albumNameGrid.add(albumNameLabel, 0, 0);
		albumNameLabel.setVisible(true);
		
		TextField albumYearField = new TextField();
		albumYearField.setText(String.valueOf(album.getAlbumYear()));
		
		albumYearGrid.add(albumYearField, 0, 0);
		albumYearField.setVisible(false);
		
		Label albumYearLabel = new Label();
		albumYearLabel.setText(String.valueOf(album.getAlbumYear()));
		
		albumYearGrid.add(albumYearLabel, 0, 0);
		albumYearLabel.setVisible(true);
		for (int i = 0; i<artistArray.length; i++) {
			TextField artistField = new TextField();
			artistField.setText(artistArray[i].getArtistName());
			artistGrid.add(artistField, i, 0);
			artistField.setVisible(false);
			
			Label artistLabel = new Label();
			artistLabel.setText(artistArray[i].getArtistName());
			artistGrid.add(artistLabel, i, 0); 
			artistLabel.setVisible(true);
		}
		
		for(int i = 0; i<genreArray.length; i++) {
			TextField genreField = new TextField();
			genreField.setText(genreArray[i].getGenreName());
			genreGrid.add(genreField, i, 0);
			genreField.setVisible(false);
			
			Label genreLabel = new Label();
			genreLabel.setText(genreArray[i].getGenreName());
			genreGrid.add(genreLabel, i, 0);
			genreLabel.setVisible(true);
		}		
		
//		AlbumName.setText(album.getAlbumName());
//		AlbumYear.setText(String.valueOf(album.getAlbumYear()));
//		AlbumArtist.setText(artistString);
//		AlbumGenre.setText(genreString); 
		
		ObservableList<Song> observableSongs = FXCollections.observableArrayList(songs);
		
		
		SongListView.setCellFactory(lv -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song song, boolean empty) {
				super.updateItem(song, empty);
				setText(empty || song == null || songs.size() == 0 ? "" : song.getSongName());
			}
		});	
		
		SongListView.setItems(observableSongs);
		
//		AlbumYear.setText(String.valueOf(album.getAlbumYear()));	
	}
		@FXML
		void editContent(ActionEvent event) {
			if(!editing) {
				editing = true;	
				flipChildren(genreGrid.getChildren());
				flipChildren(artistGrid.getChildren());
				flipChildren(albumYearGrid.getChildren());
				flipChildren(albumNameGrid.getChildren());
				
				editButton.setText("Save");
				System.out.println("In edit mode!");
			}else {
				editing = false;
				flipChildren(genreGrid.getChildren());
				flipChildren(artistGrid.getChildren());
				flipChildren(albumYearGrid.getChildren());
				flipChildren(albumNameGrid.getChildren());
	
				editButton.setText("Edit");
				System.out.println("Clicked save!");
				
				List<String> genreList = new ArrayList<>();
				List<String> artistList = new ArrayList<>();
				String newName = new String();
				int newYear = this.album.getAlbumYear();
	
				for(Node n : artistGrid.getChildren()) {
					if(n instanceof TextField) {
						artistList.add(((TextField)n).getText());
					}
				}
				for(Node n : genreGrid.getChildren()) {
					if(n instanceof TextField) {
						genreList.add(((TextField)n).getText());
					}
				}
				for(Node n : albumNameGrid.getChildren()) {
					if(n instanceof TextField) {
						newName = ((TextField) n).getText();
					}
				}
				for(Node n : albumYearGrid.getChildren()) {
					if(n instanceof TextField) {
						try {
						newYear = Integer.parseInt(((TextField)n).getText());
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
				
				String[] genreListTest = genreList.toArray(new String[genreList.size()]);
				String[] artistListTest = artistList.toArray(new String[artistList.size()]);
				
				//These are for testing purposes
				for(String s : genreList) {
					System.out.println(s);
				}
				for(String s : artistList) {
					System.out.println(s);
				}
				int test = 0;
				try {
					test = Integer.parseInt(this.AlbumYear.getText());
				}catch(Exception e) {
					System.out.println("User tried to input a number! Using the original album year -> "+this.album.getAlbumYear());
					test = this.album.getAlbumYear();
				}
				
				controller.editAlbum(id, newName, newYear, artistListTest, genreListTest);
				try {
					view.showAlbumPage(this.id);
				} catch (IOException e) {
					System.out.println("Failed to refresh album page");
					e.printStackTrace();
					}
				}
		}
		
		private void flipChildren(ObservableList<Node> list) {
			for(Node n : list) {
				if(n.isVisible()) {
					n.setVisible(false);
				}else {
					n.setVisible(true);
				}
			}
		}
	
	   @FXML
	    void addToCollection(ActionEvent event) throws Exception {
		   
		   this.controller.createLocalAlbum(this.id, album.getAlbumName(), this.songs, album.getAlbumYear(), this.genres, this.artists );
	    }
	   
	   @FXML
       void addToWishList(ActionEvent event) {
           controller.addToWishlist(this.id);
       }
	
}
