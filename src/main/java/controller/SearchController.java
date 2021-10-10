package controller;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchController{
	
	private String search;
	
	@FXML
	private Label SearchLabel;
	
	@FXML
	private GridPane SearchGrid;
	
	public SearchController (String search) {
		this.search = search;
	}
	
	@FXML
	protected void initialize() {
		System.out.println("Search = " + search);
		SearchGrid.add(new Text(search), 0, 0);
	}


}
