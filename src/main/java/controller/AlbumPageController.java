package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AlbumPageController {

	private Controller controller;
	
	@FXML
	private ListView AlbumPageListView;
	
	public AlbumPageController(Controller controller) {
		this.controller = controller;
	}
	
	@FXML
	protected void initialize() {
		// Build the listview
		System.out.println("You are in Album page");
	}
	
	
}
