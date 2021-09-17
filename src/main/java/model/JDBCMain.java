package model;
import java.sql.*;
import java.util.ArrayList;

/*
 * Class used to create a connection between the program and the database.
 */

public class JDBCMain {
	
	private Connection conn;
	
	public JDBCMain() {
		
		final String URL = "jdbc:mariadb://localhost:3306/MusicArch";
		final String USERNAME = "olso";
		final String PWD = "olso";
		
		try {
			conn = DriverManager.getConnection(URL+"?user="+USERNAME+"&password="+PWD);
		}catch(SQLException e){System.out.println("Something went wrong in accessing: "+e.getMessage());}
		catch(Exception e1) {System.exit(0);}
		
	}
	
	/*
	 * Still testing. General idea is that this one gets the prepared statement from outside methods and simply sends it to the database.
	 * Tested, will now add a genre into the database. DOESNT disallow duplicates at this time, as we have an auto-incrementing value which makes each row unique!
	 * @return a boolean, whether the sending worked or not
	 */
	
	public boolean sendToDatabase(String sqlQuery) {		
		try(PreparedStatement sqlConn = conn
				.prepareStatement(sqlQuery)){
			sqlConn.executeUpdate();
			return true;
		}catch(SQLException e) {System.out.println("Something went wrong in creating an entry: "+e.getMessage());}
		
		return false;		
	}
	
	/*
	 * LOW PRIORITY
	 * Method is called when any given edit is made to the database, such as editing an artist's name, or an album's songs. Prepared statements are created elsewhere. Is it smart to prepare the statements elsewhere?
	 */
	public boolean editDatabase(String sqlQuery) {
		//To be coded
		return false;
	}
	
	/*
	 * HIGH PRIORITY
	 * Method is called when an item will be removed from the database, such as when a genre or an artist is retired. This one will require some extra logic to first check the database whether something CAN be removed 
	 * -> For example, if a genre is going to be removed and it is an album's ONLY genre, it shouldn't be possible to remove it. Will the database simply disallow it, since we've required it to have at least one genre?
	 * -> Will we end up with a lot of junk data in the case where multiple artists are removed? Are there any loose strings? Albums without artists? Shouldn't be allowed.
	 * -> Generally, it's unlikely that something will be outright removed from the database. The only reason this is necessary is strictly for testing purposes.
	 *		UNTESTED BIT! (Sidenote, how do we confirm in a test that the program deleted the ACTUAL record we wanted deleted? What if it deletes the wrong one? This one might need some more logic, at least for the testing phase)
	 */
	public boolean removeFromDatabase(String sqlQuery) {
		try(PreparedStatement sqlConn = conn
				.prepareStatement(sqlQuery)){
			sqlConn.executeUpdate();
			return true;
		}catch(SQLException e) {System.out.println("Something went wrong in creating an entry: "+e.getMessage());}
		return false;	
	}
	
	//We need a boolean-returning method here that checks whether we actually can delete a given record. It needs some extra logic. 
	//If a genre is being deleted, it needs to check if there are any albums with only that one genre. 
	//If an artist is being removed, it needs to check if there are any albums with only that artist. In this case, should the program then remove all the associated albums?
	//If an album is being removed, it needs to check if there are any songs with only that one album. This should be fine, actually. We can still keep the songs as kind of "junk data" in case a new album comes out with those songs included
	
	/*
	 * Method is called when a user needs to search something within the database. Come to think of it, this should be separated into multiple different types of searches... Vague text search, and specific search. 
	 * -> By genre/artist/album/songIDs would at least require their own logic? How can we search them intelligently?
	 * Problem here now is that we are allowing the addition of multiple things with the exact same values. 
	 * Contains lots of test code.
	 */
	public ArrayList<DeliveryObject> searchDatabase(String sqlQuery) {
		//String results = "The result of the query. Possibly formatted as a JSON?"; 
		ArrayList<DeliveryObject> test = new ArrayList<DeliveryObject>();
		
		try{
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next()) {
				Integer foundID = rs.getInt("genreID");
				System.out.println("Search results were as follows.  And then the actual ID of this genre was: "+foundID);
			}
		}catch(Exception e) {
			System.err.println("Something went wrong: "+e.getMessage());
		}
		return test;
	}
	
	public String searchName(String sqlQuery) {
		String result = null;
		
		try {
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next()) {
				result = rs.getString(0);
				System.out.println("Found this name with the ID search -> "+ result);
			}
		}catch(Exception e) {
			System.err.println("Something went wrong: "+e.getMessage());
		}
		return result;
	}
	
	public ArrayList<Integer> searchID(String sqlQuery) {
		ArrayList<Integer> resultIDs = new ArrayList<>();
		System.out.println("This is the sql Query in searchID -> "+sqlQuery);
		
		try {
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next()) {
				resultIDs.add(rs.getInt("genreID"));
				System.out.println("Found this ID -> " +rs.getInt("genreID"));		
			}
			return resultIDs;
		}catch(Exception e) {
			System.err.println("Something went wrong: "+e.getMessage());
		}
		return resultIDs;
	}
	
	
	/*
	 * Needs logic to check if the entry already exists in the database to prevent user from adding multiple with the same values. 
	 * Maybe convert initial query into lower case, pull info from database and convert those into lower case as well, then compare the pairs? 
	 * -> Would at least allow the database to have genres like "Black Metal" where some letters are capitalized, instead of just forcing everything in as either upper or lower case.
	 */
	public boolean existsInDatabase(String lookingFor) {
		try {
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(lookingFor);
			System.out.println("Checking rs was null thingy -> ");
			if(rs.next() == false) {return false;}
		}catch(Exception e) {
			System.err.println("Something went wrong: "+e.getMessage());
		}
		return true;	
	}
	
	/*
	 * Needs logic to check if we are allowed to delete a given entry. If album only has one genre, this should return false
	 */
	private boolean canDelete() {
		
		return false;
	}
	
	protected void finalize() {
		try {
			if(conn != null) conn.close();
		}catch (Exception e) {System.out.println("Something went wrong: "+e.getMessage());}
	}
	


}
