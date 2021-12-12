package controller;

import java.util.*;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;
import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;
import com.jcg.hibernate.maven.UserRequests;

import model.*;

/**
 * @author Alex, Jani
 */
public class Controller {

	private RemoteDAO remoteDAO = RemoteDAO.getInstance();
	private LocalDAO localDAO = new LocalDAO();
	private GUIController GUIController = new GUIController();

    public Controller() {}
    
    public void createGenre(String genreName) throws Exception {
    	Genre newGenre = new Genre();
    	newGenre.setGenreName(genreName);
    	try {
			remoteDAO.createGenre(newGenre);
		} catch (Exception e) {
			System.out.println("Failed to create a genre! ");
			e.printStackTrace();
			throw e;

		}
    }
    public void createArtist(String artistName, String artistBio) throws Exception {
    	Artist newArtist = new Artist();
    	newArtist.setArtistName(artistName);
    	newArtist.setArtistBio(artistBio);
    	try {
			remoteDAO.createArtist(newArtist);
		} catch (Exception e) {
			System.out.println("Failed to create an artist! ");
			e.printStackTrace();
			throw e;
		}
    }
    //SongListGiven removed for testing purposes!
    public void createAlbum(String albumName, int albumYear, String[] genreListGiven, String[] artistListGiven, String[] songListGiven) throws Exception {
    	
    	Album newAlbum = new Album();
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	
    	Genre genre = new Genre();
    	Artist artist = new Artist();
    	Set<Artist> linkArtist = new HashSet<>();
    	Set<Genre> linkGenre = new HashSet<>();
    	Set<Song> linkSong = new HashSet<>();

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
    		
    		for(int i = 0; i<songListGiven.length; i++) {
					Song song = new Song();
					song.setSongName(songListGiven[i]);
					linkSong.add(song);    		
					System.out.println("Added song IN ORDER > "+song.getSongName());
    		}
    		try {
    			remoteDAO.createAlbum(newAlbum, linkArtist, linkGenre, linkSong);	
			} catch (Exception e) {
				System.out.println("Failed to create an Album!");
				e.printStackTrace();
				throw e;
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
    public void createLocalGenre(int genreID, String genreName) throws Exception {
    	LocalGenre localGenre = new LocalGenre();
    	LocalGenre remoteGenre = localDAO.searchGenre(genreName);
    	
    	localGenre.setGenreID(genreID);
    	localGenre.setGenreName(genreName);

    	
    	localDAO.createGenre(localGenre);
    }
    public void createLocalAlbum(int albumID, String albumName, Set<Song> songListGiven, int albumYear, Set<Genre> genreListGiven, Set<Artist> artistListGiven ) throws Exception {
    	LocalAlbum newAlbum = new LocalAlbum();
    	LocalSong[] songList = new LocalSong[songListGiven.size()];
    	LocalArtist[] artistList = new LocalArtist[artistListGiven.size()];
    	LocalGenre[] genreList = new LocalGenre[genreListGiven.size()];
    	System.out.println("Before songListGiven");
    	int counter = 0;
    	for(Song song : songListGiven) {
    		LocalSong localSong = new LocalSong();
    		localSong.setSongName(song.getSongName());
    		localSong.setSongID(song.getSongID());
    		songList[counter] = localSong;
    		counter++;
    	}
    	
    	counter = 0;
    	System.out.println("before ArtistListGiven");
    	for(Artist artist : artistListGiven) {
    		System.out.println("items: " + artist.getArtistName());
    		LocalArtist localArtist = new LocalArtist();
    		localArtist.setArtistName(artist.getArtistName());
    		localArtist.setArtistID(artist.getArtistID());
    		localArtist.setArtistBio(artist.getArtistBio());
    		artistList[counter] = localArtist;
    		counter++;
    	}
    	
    	counter = 0;
    	for(Genre genre : genreListGiven) {
    		LocalGenre localGenre = new LocalGenre();
    		localGenre.setGenreName(genre.getGenreName());
    		localGenre.setGenreID(genre.getGenreID());
    		genreList[counter] = localGenre;
    		counter++;
    	}
    	for(LocalArtist localArtist : artistList) {
    		System.out.println(localArtist.getArtistName());
    	}
    	newAlbum.setAlbumName(albumName);
    	newAlbum.setAlbumYear(albumYear);
    	newAlbum.setAlbumID(albumID);
    	newAlbum.setAlbumDescription("");
    	System.out.println("before localDAO.createAlbum ");
    	localDAO.createAlbum(newAlbum, songList, artistList, genreList);
    }

    public Album getAlbum(int albumID) {
    	return remoteDAO.readAlbum(albumID);
    }
    
    public Set<Artist> getAlbumArtistList(int albumID) {
    	Set<Artist> list = remoteDAO.albumArtistList(albumID);
    	if(list != null) {
    		
    		System.out.println("Lista artisteista" + list.size());
    	} else {
    		System.out.println("Ei ole null???");
    	}
    	return list;
//    	return remoteDAO.albumArtistList(albumID);
    }
    public Set<Genre> getAlbumGenreList(int albumID){
    	return remoteDAO.albumGenreList(albumID);
    }
    
    //This works with the assumption that the calling methods check that the corresponding fields aren't empty!
    public void editGenre(int genreID, String genreName) {
    	Genre editGenre = new Genre();
    	editGenre.setGenreName(genreName);
    	remoteDAO.editGenre(editGenre, genreID);
    }
    //This works with the assumption that the calling methods check that the corresponding fields aren't empty!
    public void editArtist(int artistID, String artistName) {
    	Artist editArtist = new Artist();
    	editArtist.setArtistName(artistName);
    	remoteDAO.editArtist(editArtist, artistID);
    }
    //Still WIP
    public void editAlbum(int albumID, String albumName, int albumYear, String[] artistListEdit, String[] genreListEdit) {
    	Album editAlbum = new Album();
    	
    	editAlbum.setAlbumName(albumName);
    	editAlbum.setAlbumYear(albumYear);
    	
    	if(genreListEdit.length != 0 || artistListEdit.length != 0) {
    		for(int i = 0; i < genreListEdit.length; i++) {
    			try {
    				Genre genre = remoteDAO.searchGenre(genreListEdit[i]);
    				if(genre != null) {
    					editAlbum.addGenre(genre);
    				}
    			}catch(Exception e) {
    				System.out.println("Failed to add a genre to the editable list! (in Controller's editAlbum() method! Error message -> "+e.getMessage());
    			}
    		}
    		for(int i = 0; i < artistListEdit.length; i++) {
    			try {
    				Artist artist = remoteDAO.searchArtist(artistListEdit[i]);
    				if(artist != null) {
    					editAlbum.addArtist(artist);
    				}
    			}catch(Exception e) {
    				System.out.println("Failed to add an artist to the editable list! (In Controller's editAlbum() method! Error message -> "+e.getMessage());
    			}
    		}
    		
    		
    	}
    	try {
			remoteDAO.editAlbum(albumID, editAlbum);
		} catch (Exception e) {
			System.out.println("Whoops");
			e.printStackTrace();
		}
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
    
    public void editLocalAlbumDescription(LocalAlbum localAlbum) {
    	localDAO.editLocalAlbumDescription(localAlbum);
    }
    
    public String getLocalAlbumDescription(int id) {
    	return localDAO.getLocalAlbumDescription(id);
    	
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
    
    public LocalAlbum searchLocalAlbum(String albumName) throws Exception {
		return localDAO.searchAlbum(albumName);
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
    
    public LocalAlbum readLocalAlbum(int id) throws Exception {
    	return localDAO.readAlbum(id);
    }
    
    public List<String> getSearchable() {
    	return remoteDAO.getSearchable();
    }
    
    public List<String> getSearchableAlbums(){
    	return remoteDAO.existingAlbums();
    }
    public List<String> getSearchableGenres(){
    	return remoteDAO.existingGenres();
    }
    public List<String> getSearchableArtists(){
    	return remoteDAO.existingArtists();
    }
    public List<String> getSearchableSongs(){
    	return remoteDAO.existingSongs();
    }
    public List<Album> getGenreAlbums(int genreID){
    	return remoteDAO.genreAlbums(genreID);
    }
    public List<Album> getArtistAlbums(int artistID){
    	return remoteDAO.artistAlbums(artistID);
    }
    public Set<Song> getAlbumSong(int albumID){
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
    
    public boolean addToWishlist(int albumID, String albumName, int albumYear) {
    	
    	localDAO.addToWishlist(albumID, albumName, albumYear);
    	return true;
    }
    
    public List<WishList> readWishList() {
    	return localDAO.readWishList();
    }
    
    public void createRequest(String rTitle, String rContents) {
    	UserRequests newRequest = new UserRequests();
    	newRequest.setRequestTitle(rTitle);
    	newRequest.setRequestContents(rContents);
    	try {
    		remoteDAO.createRequest(newRequest);
    	}catch(Exception e) {
    		System.out.println("Failed to create a user request!");
    		e.printStackTrace();
    	}
    }
    public UserRequests getRequest(int id) {
    	return remoteDAO.readRequest(id);
    }
    public UserRequests[] getRequests() {
    	return remoteDAO.readRequests();
    }
    public UserRequests searchRequestTitle(String rTitle) throws Exception {
    	return remoteDAO.searchRequestTitle(rTitle);
    }
    public boolean removeRequest(int id) {
    	return remoteDAO.removeRequest(id);
    }
    
}
