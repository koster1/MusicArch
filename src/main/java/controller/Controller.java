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

    
    
    //These are for testing purposes!! Controller gets this info from View!
    private String givenGenre = "Black Metal";
    private String givenArtist = "Johnny Cash";
    private String givenAlbumName = "Black Sabbath";
    private String givenAlbumGenre = "Heavy Metal";
    
    private RemoteDAO remoteDAO = new RemoteDAO();
    
    private ArrayList<String> givenSongList = new ArrayList<String>();
    private ArrayList<Integer> givenSongDurations = new ArrayList<Integer>();    
    //These are for testing purposes!! Controller gets this info from View!  


    /**
     * Default constructor
     */
    public Controller() {
    	
    }
    public void createGenre(String genreName) {
    	Genre newGenre = new Genre();
    }
    public void createArtist(String artistName) {
    	Artist newArtisti = new Artist();
    }
    public void createAlbum(String albumName) {
    	Album newAlbum = new Album();
    	Song newSong = new Song();
    }
    
    

}