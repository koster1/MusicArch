package model;
import java.util.*;

/**
 * 
 */
public class InputManagement {
    
	JDBCMain dao = new JDBCMain();
	
	char singleQuotesChar ='"';

    /**
     * Default constructor
     */
    public InputManagement() {}

    /**
     * HIGH PRIORITY
     * Each addition-method will get the deliveryObject, and then parse the information into a prepared statement before sending it to the database connector
     * @param inputType
     * @return
     */
    public void checkType(DeliveryObject deliveryObject) {    	
    	switch(deliveryObject.getInputType()) {
    	case "GENRE":
    		addGenre(deliveryObject);
    		break;
    	case "ARTIST":
    		addArtist(deliveryObject);
    		break;
    	case "ALBUM":
    		addAlbum(deliveryObject.getAlbumGenres(), deliveryObject.getAlbumName(), deliveryObject.getAlbumArtists(),deliveryObject.getSongArray(), deliveryObject.getSongDuration(), deliveryObject.getAlbumYear());
    		break;
    		default:
    	}
    }
    
    
    //Having separate addGenre/Artist/Album methods might be unnecessary? We could just have the String updateString created in the checkType method, and throw it to dao in each case?
    
    //-> Although this does partition it pretty nicely.
    /**
     * @param genre
     * @return
     */
    public void addGenre(DeliveryObject deliveryObject) {
        String updateString =
        		"INSERT INTO genre (genreNimi) VALUES ("+singleQuotesChar+deliveryObject.getGenreName()+singleQuotesChar+");";
        System.out.println("Got an addgenre request with this string -> "+updateString);
        dao.sendToDatabase(updateString);
    }

    /**
     * @param artistName
     * @param genre
     * @param bio
     * @return
     */
    public void addArtist(DeliveryObject deliveryObject) {
        // TODO Return message
    	 String updateString =
         		"INSERT INTO artist (artistName, artistBio) VALUES ("+singleQuotesChar+deliveryObject.getArtistName().get(0)+singleQuotesChar+", "+singleQuotesChar+deliveryObject.getArtistBio()+singleQuotesChar+");";
    	 dao.sendToDatabase(updateString);
    }

    /**
     * HIGH PRIORITY
     * This one is gonna hurt cos we need a loop to iterate through the possible artists involved, the songs and the song durations, and create the prepared statement from this. Unsure how to handle currently.
     * @param albumName
     * @param albumSongs
     * @param songDuration
     */
    public String addAlbum(ArrayList<String> albumGenres, String albumName, ArrayList<String> albumArtists, ArrayList<String> albumSongs, ArrayList<Integer> songDuration, int albumYear) {
        // TODO Return message
    	String yetAnotherCouldYouBelieveItRemoveThisPleaseAndThankYou = "Album genres are as follows: "+albumGenres+" And the album's name is: "+albumName+" And the artists are: "+albumArtists+" And the songs are: "+albumSongs+" And the durations are: "+songDuration+" And could you believe it, this album was made in: "+albumYear; 
    	return yetAnotherCouldYouBelieveItRemoveThisPleaseAndThankYou;
    }

    @Override
    public String toString() {
		return "InputManagement toString test";
        // TODO implement here
    }

}
