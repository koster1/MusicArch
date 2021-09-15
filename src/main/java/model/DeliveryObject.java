package model;
import java.util.*;

/**
 * 
 */
public class DeliveryObject {

    private ArrayList<String> genreName;
    private ArrayList<String> artistName;
    private String artistBio;
    private String albumName;
    private Integer albumYear;
    private ArrayList<String> songArray;
    private ArrayList<Integer> songDuration;
    private String inputType;


    public DeliveryObject() {}


    public ArrayList<String> getGenreName() {
        return genreName;
    }

    public void setGenreName(ArrayList<String> genreName) {
        this.genreName = genreName;
        System.out.println("Nyt lisättiin" + genreName);
    }
    
    public ArrayList<String> getArtistName() {
    	return artistName;
    }
    
    
    public void setArtistName(ArrayList<String> artistName) {
    	this.artistName = artistName;
    }

    public String getArtistBio() {
        return artistBio;
    }

    public void setArtistBio(String artistBio) {
        this.artistBio = artistBio;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(int albumYear) {
        this.albumYear = albumYear;
    }

    public ArrayList<String> getSongArray() {
        return songArray;
    }

    public void setSongArray(ArrayList<String> songArray) {
        this.songArray = songArray;
    }
    
    public ArrayList<Integer> getSongDuration() {
    	return songDuration;
    }
    
    public void setSongDuration(ArrayList<Integer> songDuration) {
    	this.songDuration = songDuration;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    /**
     * @return
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