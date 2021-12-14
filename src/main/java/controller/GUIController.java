package controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Language;

import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Genre;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jcg.hibernate.maven.Song;
import com.sun.glass.ui.Window;

import view.*;

public class GUIController {
	private static Controller controller;
	private View view;
	private Genre genreResults;
	private Artist artistResults;
	private Album albumResults;
	private Song songResults;
	List<String> everythingFound;
	List<String> albumsFound;

	@FXML
    private ContextMenu searchContext;
	@FXML
	private AnchorPane UserCategories;
	@FXML
	public TitledPane UserGenreDrop;
	@FXML
	private TitledPane UserAlbumDrop;
	@FXML
	private TitledPane UserArtistDrop;
	@FXML
	private Button SearchButton;
	@FXML 
	private TextField SearchBox;
	@FXML
	private GridPane gridView;
	@FXML 
	private Button gridButton;
	@FXML
	private AnchorPane Keskikohta;
	@FXML
	private Button Requests;
	@FXML
	private Button Help;
	@FXML
	private Button FrontPage;
	@FXML
	private Button UserCollection;
	@FXML
	private BorderPane mainPane;
	@FXML
	private ButtonBar Buttonbar;
	
    @FXML
    private AnchorPane RootAnchor;
	
    @FXML
    private MenuButton RootMenuButton;

    @FXML
    private MenuItem EnglishMenuItem;

    @FXML
    private MenuItem FinnishMenuItem;
    
    @FXML
    private GridPane ButtonGrid;

    private DoubleProperty fontSize = new SimpleDoubleProperty(20);
	public GUIController() {}
	
	public GUIController(View view, Controller controller) {
		this.view = view;
		this.controller = controller;
		
	}
	
	@FXML
	protected void initialize() {
		fontSize.bind(mainPane.widthProperty().add(mainPane.heightProperty()).divide(150));
		ButtonGrid.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
		ButtonGrid.prefWidthProperty().bind(mainPane.widthProperty().add(mainPane.heightProperty()).divide(4));
		SearchBox.focusedProperty().addListener(even -> {
			getSearchable2();
		});
		
	}

	
	//-----Page changes, window creation calls and page updates---
	//RootLayout2s menubuttons	
	public void goFrontPage() throws IOException {
		View.showFrontPage();
	}

	@FXML
	void ShowRequests(ActionEvent event) throws IOException {
		view.showRequestsWindow();
	}

	@FXML
	void GoHelpPage(ActionEvent event) throws IOException {
		view.showHelpPage();

	}
	
	@FXML
	void goUserCollection(ActionEvent event) throws IOException {
		
		
		System.out.println("UserGenreDrop = "+UserGenreDrop);
		view.showUserCollectionPage();
	}
	
	@FXML
	void goSearchPage(ActionEvent event) throws IOException {
		String searchText = SearchBox.getText();
		view.showSearchPage(searchText);
	}
	/*
	 * Upon clicking the search bar, this method is triggered, and will fetch
	 * all names found within the database.
	 */
	
	@FXML
	void getSearchable(MouseEvent event) {
		if(albumsFound == null) {
			 albumsFound = new ArrayList<>();
		 }
		 albumsFound = controller.getSearchableAlbums();
		 System.out.println("Fetched all the searchable values");
	}

	
	 @FXML
	 void getSearchable2() {
		 if(everythingFound == null) {
			 everythingFound = new ArrayList<>();
		 }
		 everythingFound = controller.getSearchable();
		 System.out.println("Fetched all the searchable values");
    }
	/**
	 * refreshSearchList-method is triggered by keypresses in the search-bar.
	 * Each time a new key is pressed, the method will compare the user's input
	 * against a list of all names in the database. 
	 * Upon finding a match, it will create a list of MenuItems inside the ContextMenu,
	 * which will autofill the search bar upon being clicked, and automatically
	 * trigger a search inside the program
	 */
	 @FXML
	 void refreshSearchList(KeyEvent event) {
		 
		int menuCounter = 0;
		List<String> strippedList = new ArrayList<String>();
		SearchBox.setContextMenu(searchContext);
		searchContext.show(SearchBox, Side.BOTTOM, 0, 0);	

		
		searchContext.getItems().clear();
		for(int i = 0; i<albumsFound.size(); i++) {		
			if(albumsFound.get(i).toLowerCase().contains(SearchBox.getText().toLowerCase())) {
				strippedList.add(albumsFound.get(i));
				menuCounter++;
			}
		}
		if(menuCounter > 5) {
			menuCounter = 4;
		}
		System.out.println(menuCounter + " menucounter");
		for(int i = 0; i<menuCounter; i++) {
			String testString = strippedList.get(i);
			MenuItem searchItem = new MenuItem(testString);
			searchItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					SearchBox.setText(searchItem.getText());
					try {
						try {
							Album albumFound = controller.searchAlbum(SearchBox.getText());
							view.showAlbumPage(albumFound.getAlbumID());
						} catch (Exception e) {
							view.showSearchPage(SearchBox.getText());
							e.printStackTrace();
						}
					}catch(IOException e1) {
						System.out.println("Failed to commit a search in GUIController's refreshSearchList method!");
						e1.printStackTrace();
					}
					SearchBox.clear();
				}
			});
			System.out.println("Added a new menu item -> "+searchItem.getText());
			searchContext.getItems().add(searchItem);
		}
	 }
	
	//----Search results from controller------
	
	public void setGenreResults(Genre genre) {
		this.genreResults = genre;
	}
	public Genre getGenreResults() {
		return genreResults;
	}
	public void setArtistResults(Artist artistResults) {
		this.artistResults = artistResults;
	}
	public Artist getArtistResults(){
		return artistResults;
	}
	public void setAlbumResults(Album album) {
		this.albumResults = album;
	}
	public Album getAlbumResults(){
		return albumResults;
	}
	public void setSongResults(Song song) {
		this.songResults = song;
	}
	public Song getSongResults(){
		return songResults;
	}
    @FXML
    void changeToEnglish(ActionEvent event) {
    	System.out.println("Changing to English");
//    	Language.getInstance().setDefault(null);
    	Language.getInstance().setLocale("en", "FI");
    	Platform.runLater(() -> {
    		windowHelper();
    		Language.getInstance().saveLocale("en", "FI");
    	});

    	try {
			view.showFrontPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	FrontPage.setText(Language.getInstance().getBundle().getString("FrontPageButton"));
    	UserCollection.setText(Language.getInstance().getBundle().getString("UserCollectionButton"));
    	Help.setText(Language.getInstance().getBundle().getString("HelpButton"));
    	Requests.setText(Language.getInstance().getBundle().getString("RequestsButton"));
    	SearchBox.setPromptText(Language.getInstance().getBundle().getString("SearchBoxTextField"));
    	SearchButton.setText(Language.getInstance().getBundle().getString("SearchButton"));
    	RootMenuButton.setText(Language.getInstance().getBundle().getString("LanguageButton"));
    }

    @FXML
    void changeToFinnish(ActionEvent event) {
    	System.out.println("Changing to Finnish");
    	Language.getInstance().setLocale("fi", "FI");
    	Platform.runLater(() -> {
    		windowHelper();
    		Language.getInstance().saveLocale("fi", "FI");
    	});
    	try {
			view.showFrontPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	FrontPage.setText(Language.getInstance().getBundle().getString("FrontPageButton"));
    	UserCollection.setText(Language.getInstance().getBundle().getString("UserCollectionButton"));
    	Help.setText(Language.getInstance().getBundle().getString("HelpButton"));
    	Requests.setText(Language.getInstance().getBundle().getString("RequestsButton"));
    	SearchBox.setPromptText(Language.getInstance().getBundle().getString("SearchBoxTextField"));
    	SearchButton.setText(Language.getInstance().getBundle().getString("SearchButton"));
    	RootMenuButton.setText(Language.getInstance().getBundle().getString("LanguageButton"));
    	
    }
    void windowHelper() {
    	List<Window> windows = Window.getWindows();
    	for(Window window : windows) {
    		System.out.println("Ikkuna " + window.getTitle());
    		if(window.getTitle().contains("User")) {
    			try {
    				window.close();
					view.refreshUserCollection();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
 
}

