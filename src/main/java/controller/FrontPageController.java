package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.View;

public class FrontPageController {
	
	private Controller controller;
	private View view;
	@FXML
	private ListView<Genre> FrontGenreListView;
	
	@FXML
	private ListView<Artist> FrontArtistListView;
	
	@FXML
	private GridPane FrontPageGrid;
	
	@FXML
    private TabPane FrontPageTabPane;

    @FXML
    private Tab FrontPageGenreTab;
    
    @FXML
    private Tab FrontPageArtistTab;
    

    @FXML
    private ButtonBar BreadCrumbBar;
    
    @FXML
    private Label ArtistOrGenreLabel;
	
	public FrontPageController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * This method is for setting up the frontpage list. 
	 * It also adds eventlisteners to all list items
	 * **/
	@FXML
	protected void initialize() {
		Platform.runLater(() -> {
		Genre[] genreList = controller.getGenres();
		Artist[] artistList = controller.getArtists();
		ObservableList<Artist> choices = FXCollections.observableArrayList(artistList);
		ObservableList<Genre> genreObservable = FXCollections.observableArrayList(genreList);
		
		FrontGenreListView.setCellFactory(lv -> new ListCell<Genre>() {
			@Override
			protected void updateItem(Genre genre, boolean empty) {
				super.updateItem(genre, empty);
				setText(empty || genre == null || genreList.length == 0 ? "" : genre.getGenreName()); 
			}
		});
		FrontGenreListView.setItems(genreObservable);
		FrontGenreListView.setOnKeyPressed(event -> {
			updateGenreList();
		});
		FrontGenreListView.setOnMouseClicked(me -> {
			updateGenreList();
		});
		
		FrontArtistListView.setCellFactory(lv -> new ListCell<Artist>() {
			@Override
			protected void updateItem(Artist artist, boolean empty) {
				super.updateItem(artist, empty);
				setText(empty || artist == null || artistList.length == 0 ? "" : artist.getArtistName());
			}
		});			
		FrontArtistListView.setItems(choices);
		FrontArtistListView.setOnKeyPressed(event -> {
			updateArtistList();
		});
		FrontArtistListView.setOnMouseClicked(me -> {
			updateArtistList();
		});
		});
		
		
	}
	
	public void updateGenreList() {
		Genre listGenre = FrontGenreListView.getSelectionModel().getSelectedItem();
		List<Album> genreAlbums = controller.getGenreAlbums(listGenre.getGenreID());
		ArtistOrGenreLabel.setText(listGenre.getGenreName());
		Collections.sort(genreAlbums, (x, y) -> {
            return Integer.compare(x.getAlbumYear(), y.getAlbumYear());
        });

		if(genreAlbums.size() > 0) {
			FrontPageGrid.getChildren().clear();
			
			int counter = 0;
			for(int i = 0; i < FrontPageGrid.getColumnCount(); i++) {
				for(int j = 0; j < FrontPageGrid.getRowCount(); j++) {	
					
					if(counter >= genreAlbums.size()) {
						break;
					}
					Text text2 = new Text("Release year: " + String.valueOf(genreAlbums.get(counter).getAlbumYear()));
					text2.setFont(new Font(15));
					Button button = new Button(genreAlbums.get(counter).getAlbumName());
					button.setId(String.valueOf(genreAlbums.get(counter).getAlbumID()));
					button.setMinWidth(150);
					GridPane grid = new GridPane();
					grid.add(text2, 0, 1);
					grid.add(button, 0, 2);
					button.addEventHandler(EventType.ROOT, (event) -> {
						if(event.getEventType() == ActionEvent.ACTION) {
							try {
								
								view.showAlbumPage(Integer.valueOf(button.getId()));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					FrontPageGrid.add(grid, i, j);
//					FrontPageGrid.add(button, i, j);
					counter++;
				}
			}
		} else {
			System.out.println("Nothing found ");
			FrontPageGrid.getChildren().clear();
		}
	}
	
	public void updateArtistList() {
		Artist listArtist = FrontArtistListView.getSelectionModel().getSelectedItem();
		List<Album> artistAlbums = controller.getArtistAlbums(listArtist.getArtistID());
		ArtistOrGenreLabel.setText(listArtist.getArtistName());
		Collections.sort(artistAlbums, (x, y) -> {
            return Integer.compare(x.getAlbumYear(), y.getAlbumYear());
        });
		if(artistAlbums.size() > 0) {
			FrontPageGrid.getChildren().clear();
			
			int counter = 0;
			for(int i = 0; i < FrontPageGrid.getColumnCount(); i++) {
				for(int j = 0; j < FrontPageGrid.getRowCount(); j++) {	
					
					if(counter >= artistAlbums.size()) {
						break;
					}
					GridPane grid = new GridPane();
					Text text2 = new Text("Release Year: " + String.valueOf(artistAlbums.get(counter).getAlbumYear()));
					text2.setFont(new Font(15));
					Button button = new Button(artistAlbums.get(counter).getAlbumName());
					button.setMinWidth(150);
					button.setId(String.valueOf(artistAlbums.get(counter).getAlbumID()));
					grid.add(text2, 0, 1);
					grid.add(button, 0, 2);
					
//					grid.setGridLinesVisible(true);
					button.addEventHandler(EventType.ROOT, (event) -> {
						if(event.getEventType() == ActionEvent.ACTION) {
							try {
								
								view.showAlbumPage(Integer.valueOf(button.getId()));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					FrontPageGrid.add(grid, i, j);
//					FrontPageGrid.add(button, i, j);
					counter++;
				}
			}
		} else {
			System.out.println("Nothing found ");
			FrontPageGrid.getChildren().clear();
		}
	}

}
