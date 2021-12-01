package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import javafx.collections.ObservableList;
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
	    private ListView<?> AlbumPageListView;
	    
	    @FXML
	    private GridPane artistGrid;
	    
	    @FXML
	    private GridPane genreGrid;
	    
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
//		System.out.println("Albuminimi: " + album.getAlbumName());
//		System.out.println("artisti?? " +  artists);
		try {
			controller.readLocalAlbum(id);
			CollectionAdd.setDisable(true);
		} catch (Exception e) {
			e.getMessage();
		}
		
//		String genreString = "";
		Artist[] artistArray = artists.toArray(new Artist[artists.size()]);
		Genre[] genreArray = genres.toArray(new Genre[genres.size()]);
		
		
		artistGrid.getChildren().clear();
		artistGrid.setMaxWidth(250.0);
		genreGrid.getChildren().clear();
		genreGrid.setMaxWidth(250.0);
		
		for (int i = 0; i<artistArray.length; i++) {
			TextField artistField = new TextField();
			artistField.setText(artistArray[i].getArtistName());
			artistGrid.add(artistField, i, 0);
			artistGrid.setMargin(artistField, new Insets(3.0));
			artistField.setVisible(false);
		}
		
		for(int i = 0; i<artistArray.length; i++) {
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
		}
		
		for(int i = 0; i<genreArray.length; i++) {
			Label genreLabel = new Label();
			genreLabel.setText(genreArray[i].getGenreName());
			genreGrid.add(genreLabel, i, 0);
			genreLabel.setVisible(true);
		}
		
		
		AlbumName.setText(album.getAlbumName());
		AlbumYear.setText(String.valueOf(album.getAlbumYear()));		
	}
		@FXML
		void editContent(ActionEvent event) {
			if(!editing) {
				flipChildren(genreGrid.getChildren());
				flipChildren(artistGrid.getChildren());
				editButton.setText("Save");
				editing = true;	
				System.out.println("In edit mode!");
			}else {
				flipChildren(genreGrid.getChildren());
				flipChildren(artistGrid.getChildren());
				
			editButton.setText("Edit");
			System.out.println("Clicked save!");
			editing = false;
			
			List<String> genreList = new ArrayList<>();
			List<String> artistList = new ArrayList<>();

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
			
			String[] genreListTest = genreList.toArray(new String[genreList.size()]);
			String[] artistListTest = artistList.toArray(new String[artistList.size()]);
			String[] songListTest = {"First song", "Second song"};
			
			//These are for testing purposes
			for(String s : genreList) {
				System.out.println(s);
			}
			for(String s : artistList) {
				System.out.println(s);
			}
			
			int test = Integer.parseInt(this.AlbumYear.getText());
			controller.editAlbum(id, this.AlbumName.getText(), test , artistListTest, genreListTest, songListTest);
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
