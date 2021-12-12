package controller;

import java.io.IOException;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
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
    private Label requestLabel;
	@FXML
	private AnchorPane requestFormAnchor;

	public SearchController(String search, Controller controller) {
		this.search = search;
		this.controller = controller;
	}

	/**
	 * This initialize is for setting up the SearchPage with the search results
	 **/
	@FXML
	protected void initialize() {
		try {
			Artist artistResults = controller.searchArtist(search);
			SearchGrid.add(new Text(artistResults.getArtistName() + " löytyi"), 0, 0);

		} catch (Exception e) {
			SearchGrid.add(new Text("Artistia " + this.search + " ei löytynyt"), 0, 0);
			System.out.println(e.getMessage());
		}

		try {
			Genre genreResults = controller.searchGenre(search);
			SearchGrid.add(new Text(genreResults.getGenreName() + " löytyi"), 0, 1);

		} catch (Exception e) {
			SearchGrid.add(new Text("Genreä " + this.search + " ei löytynyt"), 0, 1);
			System.out.println(e.getMessage());
		}
		try {

			Album albumResults = controller.searchAlbum(search);
			SearchGrid.add(new Text(albumResults.getAlbumName() + " löytyi"), 0, 2);
			System.out.println("Search = " + search);
			
		} catch (Exception e) {
			SearchGrid.add(new Text("Albumia " + this.search + " ei löytynyt"), 0, 2);
			System.out.println(e.getMessage());
		}

	}

	@FXML
	void openRequestForm(ActionEvent event) throws IOException {
		requestFormButton.setVisible(false);
		requestLabel.setVisible(false);
	//	view.showRequestForm(search);
		
	//	requestFormAnchor.visibleProperty().setValue(false);

		GridPane form = new GridPane();
		TextArea text = new TextArea();
		Label label = new Label("Otsikko");
		TextField requestTitle = new TextField();
		Button sendR = new Button("Send");
		// SearchGrid.add
		form.addColumn(1, label, requestTitle);
		form.addColumn(2, text);
		form.addColumn(3, sendR);
		label.setPrefWidth(150);
		sendR.setPrefWidth(150);
//		sendR.setPadding(new Insets(5));
		GridPane.setMargin(sendR, new Insets(5, 10, 10, 10));
		GridPane.setMargin(text, new Insets(5, 10, 10, 10));
		GridPane.setMargin(label, new Insets(5, 10, 10, 10));



		
		SearchGrid.add(form, 0 ,4);
				// button.setOnAction((e) -> ankkuri.getChildren().remove(button.getId()));
				sendR.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
					controller.createRequest(requestTitle.getText(), text.getText());
					text.clear();
					requestTitle.clear();
			
			}
		}); 
	}

}
