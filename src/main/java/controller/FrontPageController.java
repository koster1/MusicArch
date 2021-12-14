package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import model.Language;
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
			if(editing) editingError();
			else if(!editing) updateGenreList();
		});
		FrontGenreListView.setOnMouseClicked(me -> {
			if(editing) editingError();
			else if(!editing) updateGenreList();
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
			if(editing) editingError();
			else if(!editing) updateArtistList();
		});
		FrontArtistListView.setOnMouseClicked(me -> {
			if(editing) editingError();
			else if(!editing) updateArtistList();
		});
		});
		EditButton.setVisible(false);
		DeleteButton.setText(Language.getInstance().getBundle().getString("ArtistOrGenreDelete"));
		DeleteButton.setVisible(editing);
	}
	
	public void editingError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(Language.getInstance().getBundle().getString("EditingErrorTitle"));
		alert.setHeaderText(Language.getInstance().getBundle().getString("EditingErrorContent"));
		alert.showAndWait();
	}
	
	public void updateGenreList() {
		artistOrGenre = 2;
		EditButton.setVisible(true);
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
					Text text2 = new Text(Language.getInstance().getBundle().getString("AlbumReleaseYear") + String.valueOf(genreAlbums.get(counter).getAlbumYear()));
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
		EditButton.setVisible(true);
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
					Text text2 = new Text(Language.getInstance().getBundle().getString("AlbumReleaseYear") + String.valueOf(artistAlbums.get(counter).getAlbumYear()));
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
			EditButton.setText(Language.getInstance().getBundle().getString("ArtistOrGenreSave"));
			System.out.println("In edit mode!");
		}else {
			editing = false;
			flipChildren(ArtistOrGenreGrid.getChildren());
			EditButton.setText(Language.getInstance().getBundle().getString("ArtistOrGenreEdit"));
			
			String newArtistOrGenre = new String();
			for(Node n : ArtistOrGenreGrid.getChildren()) {
				if(n instanceof TextField) {
					String testName = ((TextField)n).getText();
					if(testName.isBlank() || testName.isEmpty()) {
						System.out.println("Empty string detected, using original");
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle(Language.getInstance().getBundle().getString("ArtistOrGenreError"));
						alert.setHeaderText(Language.getInstance().getBundle().getString("EmptyStringArtistOrGenreEdit"));
						alert.showAndWait();
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
					controller.editGenre(genreID, newArtistOrGenre);
					}catch(Exception e) {
						System.out.println("Something went wrong -> "+e.getMessage());
					}
				break;
			case 0: 
				try {
					view.showFrontPage();
				}catch(Exception e) {
					e.printStackTrace();
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(Language.getInstance().getBundle().getString("ArtistOrGenreError"));
		alert.setHeaderText(Language.getInstance().getBundle().getString("ArtistOrGenreDeleteHeader"));
		alert.setContentText(Language.getInstance().getBundle().getString("ArtistOrGenreDeleteContent"));
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
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
				case 0: 
					try {
						view.showFrontPage();
					}catch(Exception e) {
						e.printStackTrace();
					}
					break;
				
			}
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
