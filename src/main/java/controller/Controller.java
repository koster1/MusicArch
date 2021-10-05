package controller;

import java.util.*;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

/**
 * 
 */
public class Controller {

	private RemoteDAO remoteDAO = new RemoteDAO();
	private LocalDAO localDAO = new LocalDAO();
	
	private ObservableList<String> list = FXCollections.observableArrayList();
	/**
	 * Default constructor
	 */

    /**
     * Default constructor
     */
    public Controller() {}
    
    //Luominen etätietokantaan.
    public void createGenre(String genreName) {
    	Genre newGenre = new Genre();
    	newGenre.setGenreName(genreName);
    	remoteDAO.createGenre(newGenre);
    }
    public void createArtist(String artistName, String artistBio) {
    	Artist newArtist = new Artist();
    	newArtist.setArtistName(artistName);
    	newArtist.setArtistBio(artistBio);
    	remoteDAO.createArtist(newArtist);
    }
    //SongListGiven removed for testing purposes!
    public void createAlbum(String albumName, int albumYear, String[] genreListGiven, String[] artistListGiven) {
    	
    	//So first we create an album with the given name and year
    	Album newAlbum = new Album();
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	
    	Genre[] albumGenres = new Genre[genreListGiven.length];
    	Artist[] albumArtists = new Artist[artistListGiven.length];
    	
    	if(genreListGiven.length != 0 || artistListGiven.length != 0) {
    		for(int i = 0; i<albumGenres.length; i++) {
    			Genre linkGenre = remoteDAO.searchGenre(genreListGiven[i]);
        		newAlbum.addGenre(linkGenre);
        	}
    		for(int i = 0; i<albumArtists.length; i++) {
    			Artist linkArtist = remoteDAO.searchArtist(artistListGiven[i]);
    			newAlbum.addArtist(linkArtist);
    		}
    		
    		remoteDAO.createAlbum(newAlbum);
    	}
    	else {
    		System.out.println("Can't create album without artists or genres!");
    	}
    	
    }
    //Paikalliseen tietokantaan luominen.
    public void createLocalGenre(String genreName) {
    	//LocalGenre localGenre = (LocalGenre) remoteDAO.searchGenre(genreName);
    	//LocalGenre localGenre = (LocalGenre) remoteDAO.searchGenre(genreName);
    	
    	
    	//localDAO.createGenre(localGenre);

    }
    public void createLocalArtist(String artistName, String artistBio) {
    	LocalArtist newArtist = new LocalArtist();
    	newArtist.setArtistName(artistName);
    	newArtist.setArtistBio(artistBio);
    	localDAO.createArtist(newArtist);
    }
    public void createLocalAlbum(String albumName, Song[] songListGiven, int albumYear, Genre[] genreListGiven, Artist[] artistListGiven ) {
    	LocalAlbum newAlbum = new LocalAlbum();
    	LocalSong[] songList = new LocalSong[songListGiven.length];
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	localDAO.createAlbum(newAlbum, songList);
    }
    
    //Tallennus etätietokantaan.
    
    public void saveGenre(int genreID) {
    	Genre saveGenre = new Genre();
    	saveGenre = remoteDAO.readGenre(genreID);
    }
    public void saveArtist(int artistID) {
    	Artist saveArtist = new Artist();
    	saveArtist = remoteDAO.readArtist(artistID);
    }
    public void saveAlbum(int albumID) {
    	Album saveAlbum = new Album();
    	saveAlbum = remoteDAO.readAlbum(albumID);
    }
    //Tallennus paikalliseen tietokantaan
    public void saveLocalGenre(int genreID) {
    	LocalGenre saveLocalGenre = new LocalGenre();
    	saveLocalGenre = localDAO.readGenre(genreID);
    }
    public void saveLocalArtist(int artistID) {
    	LocalArtist saveLocalArtist = new LocalArtist();
    	saveLocalArtist = localDAO.readArtist(artistID);
    }
    public void saveLocalAlbum(int albumID) {
    	LocalAlbum saveLocalAlbum = new LocalAlbum();
    	saveLocalAlbum = localDAO.readAlbum(albumID);
    }
    
    //Etätietokannan muokkaus
    
    public void editGenre(String genreID, String genreName) {
    	Genre editGenre = new Genre();
    	int editID = Integer.parseInt(genreID);
    	editGenre.setGenreName(genreName);
    	remoteDAO.editGenre(editGenre, editID);
    }
    public void editArtist(String artistID, String artistName, String artistBio) {
    	Artist editArtist = new Artist();
    	int editID = Integer.parseInt(artistID);
    	editArtist.setArtistName(artistName);
    	editArtist.setArtistBio(artistBio);
    	remoteDAO.editArtist(editArtist, editID);
    }
    public void editAlbum(String albumID, String albumName, Song[] songListGiven, int albumYear) {
    	Album editAlbum = new Album();
    	int editID = Integer.parseInt(albumID);
    	editAlbum.setAlbumName(albumName);
    	editAlbum.setAlbumYear(albumYear);
    	remoteDAO.editAlbum(editAlbum, null, editID);
    }
    //Paikallisen tietokannan muokkaus
    public void editLocalGenre(String genreID, String genreName) {
    	LocalGenre editLocalGenre = new LocalGenre();
    	int editID = Integer.parseInt(genreID);
    	editLocalGenre.setGenreName(genreName);
    	localDAO.editGenre(editLocalGenre, editID);
    }
    public void editLocalArtist(String artistID, String artistName, String artistBio) {
    	LocalArtist editLocalArtist = new LocalArtist();
    	int editID = Integer.parseInt(artistID);
    	editLocalArtist.setArtistName(artistName);
    	editLocalArtist.setArtistBio(artistBio);
    	localDAO.editArtist(editLocalArtist, editID);
    }
    public void editLocalAlbum(String albumID, String albumName, Song[] songListGiven, int albumYear) {
    	LocalAlbum editLocalAlbum = new LocalAlbum();
    	int editID = Integer.parseInt(albumID);
    	editLocalAlbum.setAlbumName(albumName);
    	editLocalAlbum.setAlbumYear(albumYear);
    	localDAO.editAlbum(editLocalAlbum, null, editID);
    }
    
    //Poistaminen etätietokannasta
    public void removeGenre(String genreID) {
    	int removeID = Integer.parseInt(genreID);
    	remoteDAO.removeGenre(removeID);
    }
    public void removeArtist(String artistID) {
    	int removeID = Integer.parseInt(artistID);
    	remoteDAO.removeArtist(removeID);
    }
    public void removeAlbum(String albumID) {
    	int removeID = Integer.parseInt(albumID);
    	remoteDAO.removeAlbum(removeID);
    }
    //Poistaminen paikallisesta tietokannasta
    public void removeLocalGenre(String genreID) {
    	int removeID = Integer.parseInt(genreID);
    	remoteDAO.removeGenre(removeID);
    }
    public void removeLocalArtist(String artistID) {
    	int removeID = Integer.parseInt(artistID);
    	remoteDAO.removeArtist(removeID);
    }
    public void removeLocalAlbum(String albumID) {
    	int removeID = Integer.parseInt(albumID);
    	remoteDAO.removeAlbum(removeID);
    }
    
    //Hakutoiminnot
    
    /*public void searchAll(int genreID, int artistID, int albumID) {
    	
    }
     */
    
    public void searchGenre(String genreName) {
    	remoteDAO.searchGenre(genreName);
    }
    public void searchArtist(String artistName) {
    	remoteDAO.searchArtist(artistName);
    }
    /* public void searchAlbum (String albumName) {
    	remoteDAO.searchAlbum()
    } */
    
    public Artist[] getArtists() {
    	return remoteDAO.readArtists();
    }
    

}
