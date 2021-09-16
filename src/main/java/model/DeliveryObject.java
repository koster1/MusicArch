package model;
import java.util.*;

/**
 * Still a bit messy. Still needs the logic to add only single artists, genres, etc.
 */
public class DeliveryObject {

    private String genreName = null;
    private ArrayList<String> artistName = new ArrayList<>();
    private String artistBio = null;
    private String albumName = null;
    private ArrayList<String> albumArtists = new ArrayList<>();
    private ArrayList<String> albumGenres = new ArrayList<>();
    private Integer albumYear = null;
    private ArrayList<String> songArray = new ArrayList<>();
    private ArrayList<Integer> songDuration = new ArrayList<>();
    private String inputType = null;   
    
    public DeliveryObject() {}


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
        System.out.println("Nyt lisättiin: " + this.genreName);
    }
    
    public ArrayList<String> getArtistName() {
    	return artistName;
    }
    
    
    public void setArtistName(ArrayList<String> artistName) {
    	this.artistName = artistName;
    	System.out.println("Nyt lisättiin: " + this.artistName);
    }

    public String getArtistBio() {
        return artistBio;
    }

    public void setArtistBio(String artistBio) {
        this.artistBio = artistBio;
        System.out.println("Nyt lisättiin: " + this.artistBio);
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
        System.out.println("Nyt lisättiin: " + this.albumName);
    }

    public int getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(int albumYear) {
        this.albumYear = albumYear;
        System.out.println("Nyt lisättiin: " + this.albumYear);
    }
    
    public ArrayList<String> getAlbumArtists() {
		return albumArtists;
	}


	public void setAlbumArtists(ArrayList<String> albumArtists) {
		this.albumArtists = albumArtists;
	}


	public ArrayList<String> getAlbumGenres() {
		return albumGenres;
	}


	public void setAlbumGenres(ArrayList<String> albumGenres) {
		this.albumGenres = albumGenres;
		System.out.println("Nyt lisättiin: " + this.albumGenres);
	}


    public ArrayList<String> getSongArray() {
        return songArray;
    }

    public void setSongArray(ArrayList<String> songArray) {
        this.songArray = songArray;
        System.out.println("Nyt lisättiin: " + this.songArray);
    }
    
    public ArrayList<Integer> getSongDuration() {
    	return songDuration;
    }
    
    public void setSongDuration(ArrayList<Integer> songDuration) {
    	this.songDuration = songDuration;
    	System.out.println("Nyt lisättiin: " + this.songDuration);
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
        System.out.println("Nyt lisättiin: " + this.inputType);
    }

    /**
     * Method used to clear deliveryObject. Might be obsolete if a .clear() exists higher in the hierarchy
     */
    public void clearDelivery() {
        this.genreName = null;
        this.artistName = null;
        this.artistBio = null;
        this.albumName = null;
        this.albumYear = null;
        this.songArray = null;
        this.inputType = null;
    }


	
}