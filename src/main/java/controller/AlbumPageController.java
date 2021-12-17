package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Language;
import view.View;

/**
 * This controller displays the Album's information to the user. Additionally, it is used to control both editing and deleting an Album from the database by a system administrator.
 * @author Alex, Kalle
 *
 */

public class AlbumPageController {

	private Controller controller;
	private View view;
	private boolean editing = false;
	
	@FXML
	private AnchorPane ParentAnchor;
	
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
	    private Button deleteButton;

	    @FXML
	    private ListView<String> SongListView;
	    
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
	    
	    @FXML
	    private GridPane AlbumPageButtonGrid;
	    
	    @FXML
	    private GridPane ParentGrid;
	    
	    private int id;
	    private Album album;
	    private Set<Artist> artists;
	    private Set<Genre> genres;
	    private Set<Song> songs;
	    
	    private IntegerProperty fontSize = new SimpleIntegerProperty(20);
	
	public AlbumPageController(Controller controller, int id) {
		this.controller = controller;
		this.id = id;
		this.album = this.controller.getAlbum(this.id);
		this.artists = this.controller.getAlbumArtistList(id);
		this.genres = this.controller.getAlbumGenreList(id);
		this.songs = this.controller.getAlbumSong(id);
	}
	
	/**
	 * Method initializes the Album Page View with appropriate text fields and labels to show the user a given album's information.
	 * @author Alex
	 */
	@FXML
	protected void initialize() {
		fontSize.bind(ParentAnchor.widthProperty().add(ParentAnchor.heightProperty()).divide(140).add(1));
		SongListView.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
//		ParentGrid.prefHeightProperty().bind(ParentAnchor.heightProperty().add(ParentAnchor.widthProperty()));
		AlbumInfo.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
//		AlbumInfo.prefHeightProperty().bind(ParentAnchor.heightProperty());
		System.out.println(fontSize.doubleValue()+ " fontSize");
		AlbumPageButtonGrid.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.divide(1.2).asString(), ";"));
		Platform.runLater(new Runnable() {
			public void run() {
				
				try {
					controller.readLocalAlbum(id);
					CollectionAdd.setText(Language.getInstance().getBundle().getString("RemoveFromCollection"));
					WishlistAdd.setDisable(true);
				} catch (Exception e) {
					if(controller.searchWishlist(id)) {
						WishlistAdd.setText(Language.getInstance().getBundle().getString("RemoveFromWishList"));
					}
					e.getMessage();
				}
			}
		});
		
		
		Artist[] artistArray = artists.toArray(new Artist[artists.size()]);
		Genre[] genreArray = genres.toArray(new Genre[genres.size()]);

				
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
//			genreField.prefWidthProperty().bind(genreGrid.widthProperty().divide(2));
			genreField.setVisible(false);
			
			Label genreLabel = new Label();
			genreLabel.setText(genreArray[i].getGenreName());
			genreGrid.add(genreLabel, i, 0);
			genreLabel.setVisible(true);
		}		
		
		List<String> songList = new ArrayList<>();
		
		for(Song s : songs) {
			songList.add(s.getSongName());
		}
		
		Collections.sort(songList);
		
		ObservableList<String> observableSongs = FXCollections.observableArrayList(songList);
		
		SongListView.setItems(observableSongs);
		deleteButton.setVisible(false);
		
	}
	/**
	 * When the Edit Button is pressed, this method flips the visibility each child of the Album Information's grid to engage in edit-mode.
	 * When in edit mode, Edit Button will now function as a Save Button, which when pressed will check if each TextField has received valid inputs (IE, not blank or empty Strings). 
	 * If tests are passed, the new data will be sent over to the Controller, to save the information into the Remote Database.
	 * @param event
	 * @author Alex
	 */
		@FXML
		void editContent(ActionEvent event) {
			if(!editing) {
				editing = true;	
				flipChildren(genreGrid.getChildren());
				flipChildren(artistGrid.getChildren());
				flipChildren(albumYearGrid.getChildren());
				flipChildren(albumNameGrid.getChildren());
				
				editButton.setText(Language.getInstance().getBundle().getString("EditButtonSave"));
				
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
						String testName = ((TextField)n).getText();
						if(testName.isBlank() || testName.isEmpty()) {
							System.out.println("Empty string detected. Using original name : "+album.getAlbumName());
							testName = album.getAlbumName();
						}
						newName = testName;
					}
				}
				for(Node n : albumYearGrid.getChildren()) {
					if(n instanceof TextField) {
						String testYear = ((TextField)n).getText();
						if(testYear.isBlank() || testYear.isEmpty()) {
							System.out.println("Empty string detected. Using original year : "+album.getAlbumYear());
							newYear = album.getAlbumYear();
						}
						try {
							newYear = Integer.parseInt(testYear);
						}catch(Exception e) {
							System.out.println("Could not parse integer. Using original year : "+album.getAlbumYear());
							newYear = album.getAlbumYear();
						}
					}
				}
				
				String[] genreListTest = genreList.toArray(new String[genreList.size()]);
				String[] artistListTest = artistList.toArray(new String[artistList.size()]);
				
				if(!listEmpty(genreListTest) && !listEmpty(artistListTest)) {
					controller.editAlbum(id, newName, newYear, artistListTest, genreListTest);
				}
				try {
					view.showAlbumPage(this.id);
				} catch (IOException e) {
					System.out.println("Failed to refresh album page");
					e.printStackTrace();
					}
				}
			deleteButton.setVisible(editing);
		}
		/**
		 * flipChildren()'s purpose is to receive an ObservableList, and it will flip the visibility of each node given.
		 * @param list Given list that has its children flipped.
		 * @author Alex
		 */
		private void flipChildren(ObservableList<Node> list) {
			for(Node n : list) {
				if(n.isVisible()) {
					n.setVisible(false);
				}else {
					n.setVisible(true);
				}
			}
		}
		
		/**
		 * A Button event that attempts to remove an album from the Remote Database, based on an Album's ID. (Probably not the best, given that this does it without an alert?)
		 * @param event A Button click event.
		 * @author Alex
		 */
	  @FXML
	  void deleteAlbum(ActionEvent event) {
		  controller.removeAlbum(this.id); 
		try {
			view.showFrontPage();
		}catch(IOException e) {
			System.out.println("Failed to refresh album page");
			e.printStackTrace();
		}
	   }
	  /**
	   * An assistive method used to check if a given list is completely empty. Will check for both empty and blank strings. 
	   * @param list A list to check.
	   * @return listEmpty A variable that is used to determine if a list is empty.
	   * @author Alex
	   */
	  private boolean listEmpty(String[] list) {
		  boolean listEmpty = true;
		  for(String s : list) {
			  if(!s.isEmpty() || !s.isBlank()) {
				  listEmpty = false;
			  }
		  }
		  return listEmpty;
	  }
	  /**
	   * A method used to add this given Album's information into the user's local collection.
	   * @param event A Button click event.
	   * @throws Exception An exception will be thrown if controller fails to add it to the local collection.
	   * @author Kalle
	   */
	   @FXML
	    void addToCollection(ActionEvent event) {

		   try {
			   this.controller.createLocalAlbum(this.id, album.getAlbumName(), this.songs, album.getAlbumYear(), this.genres, this.artists );
			   CollectionAdd.setText(Language.getInstance().getBundle().getString("AddToCollectionButton"));
//			   WishlistAdd.setText(Language.getInstance().getBundle().getString("RemoveFromWishList"));
			   if(this.controller.searchWishlist(id))  {
				   this.controller.removeFromWishlist(id);				   				   
			   }
			   WishlistAdd.setDisable(true);
			   CollectionAdd.setText(Language.getInstance().getBundle().getString("RemoveFromCollection"));

		   }catch(Exception e) {
			   System.out.println("Failed to add Album to local collection : "+e.getMessage());
			   controller.removeLocalAlbum(this.id);
			   CollectionAdd.setText(Language.getInstance().getBundle().getString("AddToCollectionButton"));
			   WishlistAdd.setDisable(false);
		   }
		   
	    }
	   /**
	    * A method used to add this given Album into the user's own wish list.
	    * @param event A Button click event.
	    * @author Kalle
	    */
	   @FXML
       void addToWishList(ActionEvent event) {
           if(controller.addToWishlist(this.id, album.getAlbumName(), album.getAlbumYear())) {
        	   WishlistAdd.setText(Language.getInstance().getBundle().getString("RemoveFromWishList"));        	   
           } else {
        	   controller.removeFromWishlist(id);
        	   WishlistAdd.setText(Language.getInstance().getBundle().getString("AddToWishlistButton"));
           }
       }
	
}
