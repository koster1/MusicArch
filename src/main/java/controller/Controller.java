package controller;

import java.util.*;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;
import model.*;

/**
 * 
 */
public class Controller {

	private RemoteDAO remoteDAO = new RemoteDAO();
	private LocalDAO localDAO = new LocalDAO();
	private GUIController GUIController = new GUIController();

    /**
     * Default constructor
     */
    public Controller() {}
    
    public void createGenre(String genreName) {
    	Genre newGenre = new Genre();
    	newGenre.setGenreName(genreName);
    	try {
			remoteDAO.createGenre(newGenre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to create a genre! ");
			e.printStackTrace();
		}
    }
    public void createArtist(String artistName, String artistBio) {
    	Artist newArtist = new Artist();
    	newArtist.setArtistName(artistName);
    	newArtist.setArtistBio(artistBio);
    	try {
			remoteDAO.createArtist(newArtist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to create an artist! ");
			e.printStackTrace();
		}
    }
    //SongListGiven removed for testing purposes!
    public void createAlbum(String albumName, int albumYear, String[] genreListGiven, String[] artistListGiven, String[] songListGiven) {
    	
    	Album newAlbum = new Album();
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	
    	Genre genre = new Genre();
    	Artist artist = new Artist();
    	List<Artist> linkArtist = new ArrayList<>();
    	List<Genre> linkGenre = new ArrayList<>();
    	
    	if(genreListGiven.length != 0 || artistListGiven.length != 0) {
    		for(int i = 0; i<genreListGiven.length; i++) {
				try {
					genre = (Genre) remoteDAO.searchGenre(genreListGiven[i]);
					if(genre != null) {
						linkGenre.add(genre);
					}
				} catch (Exception e) {
					System.out.println("Failed to add a Genre to Album!");
					System.out.println(e.getMessage());
				}
        	}
    		for(int i = 0; i<artistListGiven.length; i++) {
				try {
					artist = (Artist) remoteDAO.searchArtist(artistListGiven[i]);
					if(artist != null) {
						System.out.println("ArtistID " + artist.getArtistID());
						linkArtist.add(artist);
					}
				} catch (Exception e) {
					System.out.println("Failed to add an Artist to Album!");
					e.printStackTrace();
				}
    			
    		}
    		try {
    			remoteDAO.createAlbum(newAlbum, linkArtist, linkGenre);	
    			
			} catch (Exception e) {
				System.out.println("Failed to create an Album!");
				e.printStackTrace();
			}
    	}
    	else {
    		System.out.println("Can't create album without artists or genres!");
    	}
    	
    }
    
    public void createLocalArtist(int ArtistID, String artistName, String artistBio) throws Exception {
    	LocalArtist localArtist = new LocalArtist();
    	Artist remoteartist = remoteDAO.searchArtist(artistName);
    	
    	localArtist.setArtistID(ArtistID);
    	localArtist.setArtistName(artistName);
    	localArtist.setArtistBio(artistBio);

    	localDAO.createArtist(localArtist);
    }
    public void createLocalAlbum(String albumName, Song[] songListGiven, int albumYear, Genre[] genreListGiven, Artist[] artistListGiven ) throws Exception {
    	LocalAlbum newAlbum = new LocalAlbum();
    	LocalSong[] songList = new LocalSong[songListGiven.length];
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	localDAO.createAlbum(newAlbum, songList);
    }
    
    public Album getAlbum(int albumID) {
    	return remoteDAO.readAlbum(albumID);
    }
    public List<Artist> getAlbumArtistList(int albumID) {
    	return remoteDAO.albumArtistList(albumID);
    }
    public List<Genre> getAlbumGenreList(int albumID){
    	return remoteDAO.albumGenreList(albumID);
    }
    /*
     * saveLocalAlbum will fetch an album from the remoteDAO based on the album's ID, and then save it and it's related data into the local database.
     * This is currently still a heavy work in progress and has not been implemented in code.
     */
    public void saveLocalAlbum(int albumID) {
    	LocalAlbum saveLocalAlbum = new LocalAlbum();
    	Album readAlbum = remoteDAO.readAlbum(albumID);

    	saveLocalAlbum = localDAO.readAlbum(albumID);
    }
    
    
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
    
    public void removeGenre(int genreID) {
    	remoteDAO.removeGenre(genreID);
    }
    public void removeArtist(int artistID) {
    	remoteDAO.removeArtist(artistID);
    }
    public void removeAlbum(int albumID) {
    	remoteDAO.removeAlbum(albumID);
    }
    public void removeLocalGenre(int genreID) {
    	remoteDAO.removeGenre(genreID);
    }
    public void removeLocalArtist(int artistID) {
    	remoteDAO.removeArtist(artistID);
    }
    public void removeLocalAlbum(int albumID) {
    	remoteDAO.removeAlbum(albumID);
    }
    
    public Genre searchGenre(String genreName) throws Exception {
			 return remoteDAO.searchGenre(genreName);
	
    }
    public Artist searchArtist(String artistName) throws Exception { 	
    		return remoteDAO.searchArtist(artistName);
	
    }
    public Album searchAlbum(String albumName) throws Exception {
			return remoteDAO.searchAlbum(albumName);
    }
    private Song searchSongs(String songName) throws Exception {
			return remoteDAO.searchSong(songName);

    }
    public Genre[] getGenres() {
    	return remoteDAO.readGenres();
    }

    public Artist[] getArtists() {
    	return remoteDAO.readArtists();
    }
    public Album[] getAlbums() {
    	return remoteDAO.readAlbums();
    }
    public Song[] getSongs() {
    	return remoteDAO.readSongs();
    }
    
    public List<LocalArtist> getLocalArtist(String search) throws Exception {
    	return localDAO.searchArtist(search);
    }
    
    public List<String> existinLocalArtists() {
    	return localDAO.existingArtists();
    }
    
    public LocalGenre[] getLocalGenres() {
    	return localDAO.readGenres();
    }
    
    public LocalAlbum[] getLocalAlbums() {
    	return localDAO.readAlbums();
    }
    
    public LocalArtist[] readArtists() {
    	return localDAO.readArtists();
    }
    
    public LocalAlbum readLocalAlbum(int id) {
    	return localDAO.readAlbum(id);
    }
    
    public List<String> getSearchable() {
    	return remoteDAO.getSearchable();
    }
    
    public List<Album> getGenreAlbums(int genreID){
    	return remoteDAO.genreAlbums(genreID);
    }
    public List<Album> getArtistAlbums(int artistID){
    	return remoteDAO.artistAlbums(artistID);
    }
    public List<Song> getAlbumSong(int albumID){
    	return remoteDAO.albumSongs(albumID);
    }
    
    public List<LocalGenre> getLocalAlbumGenres(int albumID) {
    	List<LocalGenre> localGenreList = localDAO.getLocalAlbumGenres(albumID);
    	for(LocalGenre localGenre : localGenreList) {
    		System.out.println("albumgenres ");
    		System.out.println(localGenre);
    	}
    	System.out.println("albumgenre6");
    	return localGenreList;
    }
    public List<LocalArtist> getLocalAlbumArtists(int albumID) {
    	return localDAO.getLocalAlbumArtists(albumID);
    }
    
    public List<LocalSong> getLocalAlbumSongs(int albumID) {
    	return localDAO.localAlbumSongs(albumID);
    }
}
