package controller;



import java.util.List;

import com.jcg.hibernate.maven.Artist;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchController{
	
	private String search;
	private Controller controller;
	
	@FXML
	private Label SearchLabel;
	
	@FXML
	private GridPane SearchGrid;
	
	public SearchController (String search, Controller controller) {
		this.search = search;
		this.controller = controller;
	}
	
	@FXML
	protected void initialize() {
		try {
			
			List<Artist> artistList = controller.searchAll(search);
			
			for(Artist artist : artistList) {
				System.out.println(artist.getArtistName());
				SearchGrid.add(new Text(artist.getArtistName()), 0, 0);
			}
			System.out.println("Search = " + search);
		}catch (Exception e) {
			System.out.println(e.getMessage() + " errorrrrr");
		}
		
		
	}


}
