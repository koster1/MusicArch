package controller;

import java.util.List;
import java.util.Set;


import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import view.View;


public class AlbumPageController {

	private Controller controller;
	private View view;
	
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
	    private ListView<?> AlbumPageListView;
	    
	    @FXML
	    private GridPane artistGrid;
	    
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
//		System.out.println("Frontpage id=" + this.id);
//		
//		System.out.println("Albuminimi: " + album.getAlbumName());
//		System.out.println("artisti?? " +  artists);
		
		try {
			controller.readLocalAlbum(id);
			CollectionAdd.setDisable(true);
		} catch (Exception e) {
			e.getMessage();
		}
		
		String artistString = "";
		String genreString = "";
		Artist[] artistTest = artists.toArray(new Artist[artists.size()]);
		
		
		artistGrid.getChildren().clear();
		artistGrid.setMaxWidth(200.0);

		
		for (int i = 0; i<artistTest.length; i++) {
			
			TextField artistField = new TextField();
			
			artistField.setText(artistTest[i].getArtistName());
			
			artistGrid.add(artistField, i, 0);
			artistGrid.setMargin(artistField, new Insets(3.0));
			artistField.setVisible(true);
		}
		
		for(int i = 0; i<artistTest.length; i++) {
			Label artistLabel = new Label();
			
			artistLabel.setText(artistTest[i].getArtistName());
			
			
			artistGrid.add(artistLabel, i, 0);
			artistLabel.setVisible(false);
		}
		
		for (Genre genre: genres) {
			genreString = genreString + genre.getGenreName() + " ";
		}
		
		AlbumName.setText(album.getAlbumName());
		AlbumYear.setText(String.valueOf(album.getAlbumYear()));
		
		AlbumGenre.setText(genreString); 
		
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
