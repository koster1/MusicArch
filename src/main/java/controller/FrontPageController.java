package controller;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private GridPane ArtistOrGenreGrid;
	
	@FXML
    private TabPane FrontPageTabPane;

    @FXML
    private Tab FrontPageGenreTab;
    
    @FXML
    private Tab FrontPageArtistTab;
    

    @FXML
    private ButtonBar BreadCrumbBar;
    
    @FXML
    private Button EditButton;

    @FXML
    private Button DeleteButton;
    
    @FXML
    private Label ArtistOrGenreLabel;
    
    @FXML
    private TextField ArtistFilterTextField;
    
    @FXML
    private TextField GenreFilterTextField;
    
    private Genre[] genreList;
    
    private Artist[] artistList;
    
    ObservableList<Artist> choices;
    
    ObservableList<Genre> genreObservable;
    
    private boolean editing = false;
    
    /**
     * This bit of code is so spaghetti it comes with meatballs. 
     * Basically, 0 means we're just in default. 1 is an artist, and 2 is genre.
     * It's to keep track of which one we're actually dealing with. If there's an elegant solution to this,
     * please fix it.
     */
    private int artistOrGenre = 0; 
	
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
		genreList = controller.getGenres();
		artistList = controller.getArtists();
		choices = FXCollections.observableArrayList(artistList);
		genreObservable = FXCollections.observableArrayList(genreList);
		
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
		artistOrGenre = 2;
		Genre listGenre = FrontGenreListView.getSelectionModel().getSelectedItem();
		List<Album> genreAlbums = controller.getGenreAlbums(listGenre.getGenreID());
		
		for(Node n : ArtistOrGenreGrid.getChildren()) {
			if(n instanceof TextField ) {
				((TextField) n).clear();
			}else if(n instanceof Label) {
				((Label)n).setText(null);
			}
		}
		
		for(int i = 0; i < 2; i++) {
			TextField artistOrGenreField = new TextField();
			artistOrGenreField.setText(listGenre.getGenreName());
			artistOrGenreField.setVisible(false);
			ArtistOrGenreGrid.add(artistOrGenreField, 0, 0);
			
			Label artistOrGenreLabel = new Label();
			artistOrGenreLabel.setText(listGenre.getGenreName());
			artistOrGenreLabel.setVisible(true);
			ArtistOrGenreGrid.add(artistOrGenreLabel, 0, 0);
		}
		
//		ArtistOrGenreLabel.setText(listGenre.getGenreName()); //UPDATE THIS
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
		artistOrGenre = 1;
		Artist listArtist = FrontArtistListView.getSelectionModel().getSelectedItem();
		List<Album> artistAlbums = controller.getArtistAlbums(listArtist.getArtistID());


		for(Node n : ArtistOrGenreGrid.getChildren()) {
			if(n instanceof TextField ) {
				((TextField) n).clear();
			}else if(n instanceof Label) {
				((Label)n).setText(null);
			}
		}
		
		for(int i = 0; i < 2; i++) {
			TextField artistOrGenreField = new TextField();
			artistOrGenreField.setText(listArtist.getArtistName());
			artistOrGenreField.setVisible(false);
			ArtistOrGenreGrid.add(artistOrGenreField, 0, 0);
			
			Label artistOrGenreLabel = new Label();
			artistOrGenreLabel.setText(listArtist.getArtistName());
			artistOrGenreLabel.setVisible(true);
			ArtistOrGenreGrid.add(artistOrGenreLabel, 0, 0);
		}
		
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
	
	@FXML
    void editContent(ActionEvent event) {
		if(!editing) {
			editing = true;
			flipChildren(ArtistOrGenreGrid.getChildren());
			EditButton.setText("Save");
			System.out.println("In edit mode!");
		}else {
			editing = false;
			flipChildren(ArtistOrGenreGrid.getChildren());
			EditButton.setText("Edit");
			
			String newArtistOrGenre = new String();
			for(Node n : ArtistOrGenreGrid.getChildren()) {
				if(n instanceof TextField) {
					String testName = ((TextField)n).getText();
					if(testName.isBlank() || testName.isEmpty()) {
						System.out.println("Empty string detected, using original");
						for(Node m : ArtistOrGenreGrid.getChildren()) {
							if(m instanceof Label) {
								newArtistOrGenre = ((Label) m).getText();
							}
						}
					}else {
						newArtistOrGenre = ((TextField)n).getText();
					}
				}
			}
			
			switch(artistOrGenre) {
			case 1:
				try {
					String originalArtist = new String();
					for(Node n : ArtistOrGenreGrid.getChildren()) {
						if(n instanceof Label) {
							originalArtist = ((Label) n).getText();
						}
					}
					int artistID = controller.searchArtist(originalArtist).getArtistID();
					controller.editArtist(artistID, newArtistOrGenre);
					}catch(Exception e) {
						System.out.println("Something went wrong -> "+e.getMessage());
					}
				break;
			case 2:
				try {
					String originalGenre = new String();
					for(Node n : ArtistOrGenreGrid.getChildren()) {
						if(n instanceof Label) {
							originalGenre = ((Label)n).getText();
						}
					}
					int genreID = controller.searchGenre(originalGenre).getGenreID();
					System.out.println("The genre's id was -> "+genreID);
					controller.editGenre(genreID, newArtistOrGenre);
					}catch(Exception e) {
						System.out.println("Something went wrong -> "+e.getMessage());
					}
				break;
				
			}
			try {
				view.showFrontPage();
			}catch(Exception e) {
				System.out.println("Failed to refresh front page");
				e.printStackTrace();
			}
		}
		DeleteButton.setVisible(editing);
		
    }
	
	@FXML
    void deleteButton(ActionEvent event) {
		switch(artistOrGenre) {
			case 1:
				try {
					String originalArtist = new String();
					for(Node n : ArtistOrGenreGrid.getChildren()) {
						if(n instanceof Label) {
							originalArtist = ((Label) n).getText();
						}
					}
					int artistID = controller.searchArtist(originalArtist).getArtistID();
					controller.removeArtist(artistID);
					}catch(Exception e) {
						System.out.println("Something went wrong -> "+e.getMessage());
					}
				break;
			case 2:
				try {
					String originalGenre = new String();
					for(Node n : ArtistOrGenreGrid.getChildren()) {
						if(n instanceof Label) {
							originalGenre = ((Label)n).getText();
						}
					}
					int genreID = controller.searchGenre(originalGenre).getGenreID();
					System.out.println("The genre's id was -> "+genreID);
					controller.removeGenre(genreID);
					}catch(Exception e) {
						System.out.println("Something went wrong -> "+e.getMessage());
					}
				break;
			
		}
		try {
			view.showFrontPage();
		}catch(Exception e) {
			System.out.println("Failed to refresh front page");
			e.printStackTrace();
		}
    }
	
	private void flipChildren(ObservableList<Node> list) {
		for(Node n : list) {
			if(n.isVisible() && !(n instanceof Button)) {
				n.setVisible(false);
			}else {
				n.setVisible(true);
			}
			
		}
	}
	
	  @FXML
	    void filterArtist(KeyEvent event) {
		  	//artistList
		  choices.clear();
		  ArtistFilterTextField.getText();
		  List<Artist> test = new ArrayList<>();
		  for(Artist artist : artistList) {
			  test.add(artist);
		  }
		 test.stream().filter(x -> x.getArtistName().toLowerCase().contains(ArtistFilterTextField.getText().toLowerCase())).forEach(y -> choices.add(y));
	    }

	    @FXML
	    void filterGenre(KeyEvent event) {
	    	//genreList
			genreObservable.clear();
			GenreFilterTextField.getText();
			List<Genre> test = new ArrayList<>();
			for(Genre genre : genreList) {
				test.add(genre);
			}
			test.stream().filter(x -> x.getGenreName().toLowerCase().contains(GenreFilterTextField.getText().toLowerCase())).forEach(y -> genreObservable.add(y));
	    }

    

}
