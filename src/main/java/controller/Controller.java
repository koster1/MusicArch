package controller;
import java.util.*;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;

import model.*;

/**
 * 
 */
public class Controller {

    private RemoteDAO remoteDAO = new RemoteDAO();
    


    /**
     * Default constructor
     */
    public Controller() {
    	
    }
    
    //Luominen tietokantaan
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
    public void createAlbum(String albumName, Song[] songListGiven, int albumYear, Genre[] genreListGiven, Artist[] artistListGiven ) {
    	Album newAlbum = new Album();
    	Song[] songList = new Song[songListGiven.length];
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
//    	remoteDAO.createAlbum(newAlbum, songList);
    }
    
    //Tallennus paikalliseen tietokantaan.
    
    //LocalDAO puuttuu vielä
   
    
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
    //Tietokannan muokkaus
    
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
    //Poistaminen Tietokannasta
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
    public void genreExist() {
    	boolean gN;
    
    	//remoteDAO.GenreExist();
    }
    

}
