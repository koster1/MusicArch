package controller;

import java.io.IOException;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import view.View;

public class SearchController {
	private View view;

	private String search;
	private Controller controller;
	
	@FXML
	private Label SearchLabel;
	
	@FXML
	private GridPane SearchGrid;
	
    @FXML
    private Label ArtistLabel;

    @FXML
    private Label GenreLabel;

    @FXML
    private Label AlbumLabel;
    
    @FXML
    private Button requestFormButton;

    @FXML
    private AnchorPane requestFormAnchor;
	
	public SearchController (String search,Controller controller) {
		this.search = search;
		this.controller = controller;
	}
	
	
	public SearchController(Controller controller2) {
		// TODO Auto-generated constructor stub
		this.controller = controller2;
	}


	/**
	 * This initialize is for setting up the SearchPage with the search results
	 * **/
	@FXML
	protected void initialize() {
		try {
			Artist artistResults = controller.searchArtist(search);
			SearchGrid.add(new Text(artistResults.getArtistName() + " löytyi"), 0, 0);
			
		}catch (Exception e) {
			SearchGrid.add(new Text("Artistia " + this.search + " ei löytynyt"), 0, 0);
			System.out.println(e.getMessage());
		}
		
		try {
			Genre genreResults = controller.searchGenre(search);
			SearchGrid.add(new Text(genreResults.getGenreName() + " löytyi"), 0, 1);
		 
		}catch (Exception e) {
			SearchGrid.add(new Text("Genreä " + this.search + " ei löytynyt"), 0, 1);
			System.out.println(e.getMessage());
		}
		try {
			
			Album albumResults = controller.searchAlbum(search);
			SearchGrid.add(new Text(albumResults.getAlbumName() + " löytyi"), 0, 2);
			
			System.out.println("Search = " + search);
		}catch (Exception e) {
			SearchGrid.add(new Text("Albumia " + this.search + " ei löytynyt"), 0, 2);
			System.out.println(e.getMessage());
		}
		
		
	}

    @FXML
    void openRequestForm(ActionEvent event) throws IOException {
    	view.showRequestForm();
    }
	
	


}
