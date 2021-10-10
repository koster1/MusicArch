package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import view.View;


import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Album;

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
    private ListView<Artist> GridListView;
    
    @FXML
    private GridPane UserGrid;
    
    public UserCollectionController() {

    	this.controller = new Controller();
    }
    
    @FXML
    protected void initialize() {
    	

    	Artist[] artistList = controller.getArtists();
    	ObservableList<Artist> choices = FXCollections.observableArrayList(artistList);
    	for(Artist artist : artistList) {
    		System.out.println(artist);
    	}
    	
    	GridListView.setCellFactory(lv -> new ListCell<Artist>() {
			@Override
			protected void updateItem(Artist artist, boolean empty) {
				super.updateItem(artist, empty);
				setText(empty || artist == null || artistList.length == 0 ? "" : artist.getArtistName());
			}
		});			
		GridListView.setItems(choices);
    	try {
    		
    		
    	}catch (Exception e) {
    		System.out.println("Miksiiii" + e.getMessage());
    	}
    }

    @FXML
    void Album(MouseEvent event) {
	UserLabel.setText("Muutu");
    	
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


}
