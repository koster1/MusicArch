package model;

import java.util.ArrayList;

//HIGH PRIORITY!! -> CHANGE TO HIBERNATE -> Much better for controlling many-to-many; many-to-one, etc! This is placeholder code to get the functionality tested

//Purpose of this class is to manage search inputs. Unsure what exactly each method should return. Considering the fact that if they return a list of our delivery objects, it should technically work? There must be a better way. JSON?
//-> At least if they all come out as deliveryObjects, assigning them and referring to them should be easy, but this feels wrong, somehow. There must be a better way to get the results.

public class SearchManagement {
	
	JDBCMain dao = new JDBCMain();
	
	//This is a test list. Do not take seriously. Hopefully a better way for this can be found.
	ArrayList<DeliveryObject> resultObjects = new ArrayList<DeliveryObject>();
	String genreSearchTest = null;
	char singleQuotesChar ='"';
	
	public SearchManagement() {}
	
	public Integer searchAll(String searchQuery){
		//searchGenres(searchQuery);
		//searchArtists(searchQuery);
		//searchAlbums(searchQuery);
		//searchSongs(searchQuery);
		int resultINT = searchGenreID(searchQuery);
		
		return resultINT;
	}
	
	//The idea with these ones is that it's the "search everything" kind of method. So the user searches all genres for things that are "like" whatever they put in. 
	//We should likely put the user's searches into lower-caps in case SQL is case-sensitive, maybe... How about special characters, will they be a problem?
	private String searchGenres(String searchQuery){
		System.out.println("Did we get into the genre search?");
		String updateString =
        		"SELECT * FROM genre WHERE genreNimi LIKE "+singleQuotesChar+"%"+searchQuery+"%"+singleQuotesChar+";";
		System.out.println("Sent this query -> "+updateString);
		
		genreSearchTest = dao.searchName(searchQuery);
		
		System.out.println("This is what we got from the query -> "+genreSearchTest);
		return genreSearchTest;	
	}
	
	private ArrayList<DeliveryObject> searchArtists(String searchQuery){
		String updateString =
        		"SELECT * FROM artisti WHERE artistiNimi LIKE "+singleQuotesChar+"%"+searchQuery+"%"+singleQuotesChar+";";
		
		System.out.println("Sent this query -> "+updateString);
		
		return resultObjects;
	}
	
	private ArrayList<DeliveryObject> searchAlbums(String searchQuery){
		String updateString =
        		"SELECT * FROM albumi WHERE albumiNimi LIKE "+singleQuotesChar+"%"+searchQuery+"%"+singleQuotesChar+";";
		
		System.out.println("Sent this query -> "+updateString);
		
		return resultObjects;
	}
	
	private ArrayList<DeliveryObject> searchSongs(String searchQuery){
		String updateString =
        		"SELECT * FROM kappale WHERE kappaleNimi LIKE "+singleQuotesChar+"%"+searchQuery+"%"+singleQuotesChar+";";
		
		System.out.println("Sent this query -> "+updateString);
		
		return resultObjects;
	}
	
	//These may be unnecessary, but I have them here just in case we want to search specific IDs per table. Should likely also add in finding the genre/artist/album/songIDs with a name search!
	//Should also all be lists, in case we have someone searching like... "Metal" -> Can return "Black Metal," "Heavy Metal," "Death Metal," etc...
	public Integer searchGenreID(String genreName) {
		String updateString =
				"SELECT genreID FROM genre WHERE genreNimi like "+singleQuotesChar+"%"+genreName+"%"+singleQuotesChar+";";
		System.out.println("This is now in searchGenreID and got this result -> "+dao.searchID(updateString));
		
		return dao.searchID(updateString).get(0);
	}
	public Integer searchArtistID(String artistName) {
		String updateString =
				"SELECT artistiID FROM artist WHERE artistiNimi LIKE "+singleQuotesChar+"%"+artistName+"%"+singleQuotesChar+";";
		return dao.searchID(updateString).get(0);
	}
	public Integer searchAlbumID(String albumName) {
		String updateString =
				"SELECT albumiID FROM albumi WHERE albumiNimi LIKE "+singleQuotesChar+"%"+albumName+"%"+singleQuotesChar+";";
		return dao.searchID(updateString).get(0);
	}
	public Integer searchSongID(String songName) {
		String updateString =
				"SELECT kappaleID FROM kappale WHERE kappaleNimi LIKE "+singleQuotesChar+"%"+songName+"%"+singleQuotesChar+";";
		return dao.searchID(updateString).get(0);
	}
	
	//These ones collectively search the name of any given ID
	public String searchGenre(Integer genreID) {
		String updateString = 
				"Select genreNimi FROM genre WHERE genreID = "+genreID;
		return dao.searchName(updateString);
	}
	public String searchArtist(Integer artistID) {
		String updateString = 
				"Select artistiNimi FROM artisti WHERE genreID = "+artistID;
		return dao.searchName(updateString);
	}
	public String searchAlbum(Integer albumID) {
		String updateString = 
				"Select albumiNimi FROM genre WHERE albumiID = "+albumID;
		return dao.searchName(updateString);
	}
	public String searchSong(Integer songID) {
		String updateString = 
				"Select kappaleNimi FROM kappale WHERE kappaleID = "+songID;
		return dao.searchName(updateString);
	}
}
