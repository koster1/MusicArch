package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.RemoteDAO;

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
	    
	    int id;
	
	public AlbumPageController(Controller controller, int id) {
		this.controller = controller;
		this.id = id;
	}
	
	@FXML
	protected void initialize() {
		System.out.println("Frontpage id=" + this.id);

		AlbumName.setText("Wish You Were Here");
		AlbumYear.setText(String.valueOf(1975));
		AlbumArtist.setText("Pink Floyd");
		AlbumGenre.setText("Progressive Rock");
		
	}
	
	
}
