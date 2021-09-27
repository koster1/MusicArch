package controller;
import controller.*;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import view.View;

public class GUIController {
	/*DAO dao = new DAO();
	Genre genre = new Genre();
	Album album = new Album();	*/		
	Controller controller = new Controller();
	// TestiLomake
	@FXML private TextField GenreAddTxtField;
	@FXML private Label GenreAddLabel;
	@FXML private Button SendGenre;
	@FXML private Label GenreAddTitle;

	//Albumin lisäys
    @FXML private TextField Released;
    @FXML private Label Year;
    @FXML private Button SendAlbum;
    @FXML private Label AlbumTitle;
    @FXML private TextField AlbumName;
    @FXML private TextField ArtistName;
    @FXML private TextField Song;
    @FXML private Button AddSong;
    @FXML private Button AddArtist;

    @FXML void SendAlbumClicked(ActionEvent event) {}

	@FXML void SendGenreClicked(ActionEvent event) {
		String genreName = GenreAddTxtField.getText();		
		GenreAddTxtField.clear();
		System.out.print(genreName);
		controller.createGenre(genreName);
		}

	// Lisäyspyynnöt
	@FXML private TitledPane GenreDrop;
	@FXML void GenreDropClicked(MouseEvent event) {
	}
}
