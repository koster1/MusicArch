package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import view.View;

import java.util.List;

import com.jcg.hibernate.maven.Album;

import com.jcg.hibernate.maven.RemoteDAO;

public class AlbumPageController {

	private Controller controller;
	private View view;
	private RemoteDAO rDAO;
	
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
		// Build the listview
		System.out.println("You are in Album page");
		
		System.out.println("Frontpage id=" + this.id);
		
		
		AlbumName.setText("");
		AlbumYear.setText("1975");
		AlbumArtist.setText("Pink Floyd");
		AlbumGenre.setText("Progressive Rock");
		
		AlbumPageListView.setAccessibleText("test");
		
	}
	
	
}
