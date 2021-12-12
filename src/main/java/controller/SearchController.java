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

    @FXML
    private GridPane artistSearchGrid;

    @FXML
    private GridPane genreSearchGrid;

    @FXML
    private GridPane albumSearchGrid;
    @FXML
    private Label foundGenre;
    @FXML
    private Label notFoundGenre;
    @FXML
    private Label foundArtist;

    @FXML
    private Label notFoundArtist;
    @FXML
    private Label foundAlbum;

    @FXML
    private Label notFoundAlbum;

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
			foundGenre.setVisible(true);
			notFoundGenre.setVisible(false);
			Genre genreResults = controller.searchGenre(search);
			SearchGrid.add(new Text(genreResults.getGenreName()), 0, 0);

		} catch (Exception e) {
			foundGenre.setVisible(false); 
			notFoundGenre.setVisible(true);
			SearchGrid.add(new Text(this.search), 0, 0);
			System.out.println(e.getMessage());
		}
		try {
			Artist artistResults = controller.searchArtist(search);
			foundArtist.setVisible(true);
			notFoundArtist.setVisible(false);
			SearchGrid.add(new Text(artistResults.getArtistName()), 0, 1);
			//SearchGrid.add(new Text(artistResults.getArtistName() + " löytyi"), 0, 0);

		} catch (Exception e) {
			foundArtist.setVisible(false);
			notFoundArtist.setVisible(true);
			SearchGrid.add(new Text(this.search), 0, 1);
			System.out.println(e.getMessage());
		}
		
		try {
			foundAlbum.setVisible(true);
			notFoundAlbum.setVisible(false);
			Album albumResults = controller.searchAlbum(search);
			SearchGrid.add(new Text(albumResults.getAlbumName()), 0, 2);
			System.out.println("Search = " + search);
			
		} catch (Exception e) {
			foundAlbum.setVisible(false);
			notFoundAlbum.setVisible(true);
			SearchGrid.add(new Text(this.search), 0, 2);
			System.out.println(e.getMessage());
		}

	}

	@FXML
	void openRequestForm(ActionEvent event) throws IOException {
		requestFormButton.setVisible(false);
	//	requestLabel.setVisible(false);
		

		GridPane form = new GridPane();
		TextArea text = new TextArea();
		Label title = new Label("Otsikko");
		Label textareatitle = new Label("Otsikko");

		TextField requestTitle = new TextField();
		Button sendR = new Button("Lähetä");
		form.addRow(1, title, requestTitle);
		form.addRow(2,textareatitle, text);
		form.add(sendR, 1, 3);
		title.setPrefWidth(200);
		sendR.setPrefWidth(200);
		GridPane.setMargin(sendR, new Insets(5, 10, 10, 5)); 
		GridPane.setMargin(text, new Insets(5, 10, 10, 5));
		GridPane.setMargin(requestTitle, new Insets(5, 10, 10, 5));



		
		SearchGrid.add(form, 0 ,4);
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
