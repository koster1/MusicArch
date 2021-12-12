package controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Language;
import model.LocalAlbum;
import model.LocalArtist;
import model.LocalGenre;
import model.LocalSong;
import model.WishList;
import view.View;


import com.jcg.hibernate.maven.Genre;

import java.io.IOException;
import java.util.List;


public class UserCollectionController {
	
	private Controller controller;
	private View view;

    @FXML
    private Label UserLabel;

    @FXML
    private AnchorPane UserCategories;
    
    @FXML
    private TitledPane UserAlbumDrop;
    
    @FXML
    private TabPane UserTabPane; 

    @FXML
    private ListView<LocalAlbum> GridListView;
    
    @FXML
    private GridPane UserGrid;
    
    @FXML
    private ListView<LocalSong> SongListView;
    

    @FXML
    private Label AlbumNameLabel;

    @FXML
    private Label AlbumArtistLabel;

    @FXML
    private Label AlbumGenreLabel;

    @FXML
    private Label UserCollectionTitle;
    
    @FXML
    private TextArea AlbumTextArea;
    
    @FXML
    private Text InputText;
    
    @FXML
    private Tab WishListTab;
    
    @FXML
    private ListView<WishList> GridWishListView;
    
    @FXML
    private Label InfoText;
    
    @FXML
    private Button AddDescriptionButton;
    
    @FXML
    private AnchorPane ParentAnchor;
    
    @FXML
    private SplitPane UserSplitPane;
    
    @FXML
    private GridPane CollectionGridPane;

    private boolean allowed;
    
    private String tempText = "";
    
    
    private DoubleProperty fontSize = new SimpleDoubleProperty(20);
    
    public UserCollectionController(Controller controller) {

    	this.controller = controller;
    	
    }
    
    /**
	 * This initialize method is for setting up the UserCollection with data from then local database.
	 * It also gives all the list items clickEvenetListeners for album info.
	 * **/
    @FXML
    protected void initialize() {
    	try {
    		fontSize.bind(ParentAnchor.widthProperty().add(ParentAnchor.heightProperty()).divide(135).add(2));
    		GridListView.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
    		SongListView.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
    		UserSplitPane.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
    		AddDescriptionButton.prefWidthProperty().bind(CollectionGridPane.widthProperty().divide(2));
    		
    		Platform.runLater(() -> {
    			LocalAlbum[] albumList;
    			
    			albumList = controller.getLocalAlbums();
    			ObservableList<LocalAlbum> choices = FXCollections.observableArrayList(albumList);
    			for(LocalAlbum album : albumList) {
    				System.out.println(album);
    			}
    			
    			GridListView.setCellFactory(lv -> new ListCell<LocalAlbum>() {
    				@Override
    				protected void updateItem(LocalAlbum localAlbum, boolean empty) {
    					super.updateItem(localAlbum, empty);
    					setText(empty || localAlbum == null || albumList.length == 0 ? "" : localAlbum.getAlbumName());
    				}
    			});			
    			
    			GridListView.setItems(choices);
    			
    			GridListView.setOnMouseClicked(me -> {
    				AddDescriptionButton.setDisable(true);
    				this.allowed = false;
    				LocalAlbum listLocalAlbum = GridListView.getSelectionModel().getSelectedItem();
    				List<LocalGenre> localGenre = controller.getLocalAlbumGenres(listLocalAlbum.getAlbumID());
    				List<LocalArtist> localArtist = controller.getLocalAlbumArtists(listLocalAlbum.getAlbumID());
    				if(listLocalAlbum != null) {
    					AlbumNameLabel.setText(listLocalAlbum.getAlbumName()); 				
    				}
    				if(localGenre.size() > 0) {
    					AlbumGenreLabel.setText(localGenre.get(0).getGenreName());
    					System.out.println(listLocalAlbum.getAlbumName());
    				} else {
    					AlbumGenreLabel.setText(Language.getInstance().getBundle().getString("AlbumGenreLabelFail"));
    				}
    				if(localArtist.size() > 0) {
    					AlbumArtistLabel.setText(localArtist.get(0).getArtistName());
    				} else {
    					AlbumArtistLabel.setText(Language.getInstance().getBundle().getString("AlbumArtistLabelFail"));
    				}
    				this.tempText = controller.getLocalAlbumDescription(listLocalAlbum.getAlbumID());
    				AlbumTextArea.setText(this.tempText);
    				InputText.setText("" + AlbumTextArea.getText().length() + "/" + "1000");
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
    		});
    		
    		
    	} catch(Exception e) {
    		System.out.println(e.getMessage() + " usercollection init");
    	}
    	Platform.runLater(() -> {
    	List<WishList> wishList;
		try {
   		
    		wishList = controller.readWishList();
    		ObservableList<WishList> choices = FXCollections.observableArrayList(wishList);
    		for(WishList wish : wishList) {
    			System.out.println(wish);
    		}
    		
    		GridWishListView.setCellFactory(lv -> new ListCell<WishList>() {
    			@Override
    			protected void updateItem(WishList localAlbum, boolean empty) {
    				super.updateItem(localAlbum, empty);
    				setText(empty || localAlbum == null || wishList.size() == 0 ? "" : localAlbum.getAlbumName());
    			}
    		});			
    		
    		GridWishListView.setItems(choices);
		
    		// Still needs implementation on how to present the wishlist data in the middle part:
    		
//    		GridWishListView.setOnMouseClicked(me -> {
//    			AddDescriptionButton.setDisable(true);
//    			this.allowed = false;
//    			LocalAlbum listLocalAlbum = GridWishListView.getSelectionModel().getSelectedItem();
//    			List<LocalGenre> localGenre = controller.getLocalAlbumGenres(listLocalAlbum.getAlbumID());
//    			List<LocalArtist> localArtist = controller.getLocalAlbumArtists(listLocalAlbum.getAlbumID());
//    			if(listLocalAlbum != null) {
//    				AlbumNameLabel.setText(listLocalAlbum.getAlbumName()); 				
//    			}
//    			if(localGenre.size() > 0) {
//    				AlbumGenreLabel.setText(localGenre.get(0).getGenreName());
//    				System.out.println(listLocalAlbum.getAlbumName());
//    			} else {
//    				AlbumGenreLabel.setText("Ei löytynyt genrejä");
//    			}
//    			if(localArtist.size() > 0) {
//    				AlbumArtistLabel.setText(localArtist.get(0).getArtistName());
//    			} else {
//    				AlbumArtistLabel.setText("Ei löytynyt artisteja");
//    			}
//    			this.tempText = controller.getLocalAlbumDescription(listLocalAlbum.getAlbumID());
//    			AlbumTextArea.setText(this.tempText);
//    			InputText.setText("" + AlbumTextArea.getText().length() + "/" + "1000");
//    			List<LocalSong> localSongs = controller.getLocalAlbumSongs(listLocalAlbum.getAlbumID());
//    			ObservableList<LocalSong> observableSongs = FXCollections.observableArrayList(localSongs);
//    			
//        		SongListView.setCellFactory(lv -> new ListCell<LocalSong>() {
//        			@Override
//        			protected void updateItem(LocalSong localSong, boolean empty) {
//        				super.updateItem(localSong, empty);
//        				setText(empty || localSong == null || localSongs.size() == 0 ? "" : localSong.getSongName());
//        			}
//        		});		
//    			SongListView.setItems(observableSongs);
//    		});
    		
    		
    	} catch(Exception e) {
    		System.out.println(e.getMessage() + " usercollection init");
    	}
    	});
		

    }
    @FXML
    void getDetails() {
    	LocalAlbum localAlbum = GridListView.getSelectionModel().getSelectedItem();
    	System.out.println("Album id from listItem" + localAlbum.getAlbumID());
    }

    @FXML
    void Album(MouseEvent event) {
    	
    	try {
    		UserGrid.getChildren().clear();
    		Genre[] genreList = controller.getGenres();
    		int counter = 0;
    		System.out.println(UserGrid + " UserGrid????");
    		for(Genre genre : genreList) {
    			System.out.println(genre.getGenreName());
    			Text text = new Text(genre.getGenreName());
    			UserGrid.add(text, 0, counter);
    			counter++;
    		}
    	}catch (Exception e) {
    		System.out.println("Miksiiii" + e.getMessage());
    	}
    }
    /**
	 * Action event for button that saves the changes in the description TextArea
	 * 
	 * **/
    @FXML
    void saveDescription(ActionEvent event) {
    	if(allowed) {
    		this.tempText = AlbumTextArea.getText();
    		LocalAlbum localAlbum = GridListView.getSelectionModel().getSelectedItem();
    		if(localAlbum != null) {
    			localAlbum.setAlbumDescription(this.tempText);
    			controller.editLocalAlbumDescription(localAlbum);
    			InfoText.setText(Language.getInstance().getBundle().getString("SavedText"));
    			AddDescriptionButton.setDisable(true);
    		}
    	}
    }
    /**
	 * Checks if the TextArea char count is within allowed amount
	 * **/
    @FXML
    void charLimit(KeyEvent event) {
    	InputText.setText("" + AlbumTextArea.getText().length() + "/" + "1000");
    	if(this.tempText.equals(AlbumTextArea.getText())) {
    		InfoText.setText(Language.getInstance().getBundle().getString("SaveText"));
    		AddDescriptionButton.setDisable(true);
    	} else {
    		InfoText.setText(Language.getInstance().getBundle().getString("SaveText"));
    		AddDescriptionButton.setDisable(false);
    	}
    	allowed = true;
    	if(AlbumTextArea.getText().length() > 1000) {
    		InputText.setText((AlbumTextArea.getText().length() - 1000) + Language.getInstance().getBundle().getString("CharOver"));
    		this.allowed = false;
    		AddDescriptionButton.setDisable(!this.allowed);
    	}
    } 
    /**
	 * Button event that deletes an album from user collection
	 * **/
    @FXML
    void deleteAlbum(ActionEvent event) {
    	try {
    		int albumChoice = GridListView.getSelectionModel().getSelectedItem().getAlbumID();
    		controller.removeLocalAlbum(albumChoice);
    		
    	} catch (Exception ex) {
    		System.out.println(ex.getMessage() + " tried to remove null album");
    		return;
    	}
    		
		try {
			View.showUserCollectionPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
    

}
