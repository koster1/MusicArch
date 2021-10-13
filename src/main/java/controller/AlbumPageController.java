package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.RemoteDAO;

public class AlbumPageController {

	private Controller controller;
	private View view;
	private RemoteDAO rDAO;
	
	   @FXML
	    private GridPane AlbumInfo;

	    @FXML
	    private Label AlbumName;

	    @FXML
	    private Label AlbumYear;

	    @FXML
	    private Label AlbumArtist;
	    
	    @FXML
	    private Label AlbumGenre;

	    @FXML
	    private Button WishlistAdd;

	    @FXML
	    private Button CollectionAdd;
	    
	    @FXML
	    private Button BackButton;

	    @FXML
	    private ListView<?> AlbumPageListView;
	
	public AlbumPageController(Controller controller) {
		this.controller = controller;
	}
	
	@FXML
	protected void initialize() {
		// Build the listview
		System.out.println("You are in Album page");
		
		Album album = controller.getAlbum(2);
//		List<Artist> albumArtistlist = controller.getAlbumArtistList(2);
//		List<Genre> albumGenreList = controller.getAlbumGenreList(2);
 		
		
//		//tekee artistilistasta stringin
//	
		StringBuilder strbul=new StringBuilder();
//        for(Artist artist : albumArtistlist)
//        {
//            strbul.append(artist.getArtistName());
//            //for adding comma between elements
//            strbul.append(",");
//        }
//        String artists=strbul.toString();
//       
//		
//		//genreistä string
//		
//		StringBuilder strbul2=new StringBuilder();
//        for(Genre genre : albumGenreList)
//        {
//            strbul2.append(genre.getGenreName());
//            //for adding comma between elements
//            strbul2.append(",");
//        }
//        String genres=strbul2.toString();
//       
	
		
		AlbumName.setText(album.getAlbumName());
		AlbumYear.setText(String.valueOf(album.getAlbumYear()));
//		AlbumArtist.setText(artists);
//		AlbumGenre.setText(genres);
		
		//AlbumPageListView.setAccessibleText(album.getAlbumSongs());
		
	}
	
	
}
