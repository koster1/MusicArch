package model;

/*
 * Class is meant to manage the different deletion requests from admin-user. This is pretty boilerplate, and shouldn't come into effect all too much. All methods currently handle removal requests by their respective IDs, NOT names. 
 * Might be worth adding some extra logic to this later on.
 */
public class DeletionManagement {
	
	JDBCMain dao = new JDBCMain();
	String deleteString;
	
	public DeletionManagement() {}
	
	public void removeGenre(Integer genreID) {
		deleteString = 
				"DELETE FROM genre WHERE genreID="+genreID;
		dao.removeFromDatabase(deleteString);
	}
	public void removeArtist(Integer artistID) {
		deleteString = 
				"DELETE FROM artisti WHERE artistiID="+artistID;
		dao.removeFromDatabase(deleteString);
	}
	public void removeAlbum(Integer albumID) {
		deleteString =
				"DELETE FROM albumi WHERE albumiID="+albumID;
		dao.removeFromDatabase(deleteString);
	}
	public void removeSong(Integer songID) {
		deleteString =
				"DELETE FROM kappale WHERE kappaleID="+songID;
		dao.removeFromDatabase(deleteString);
	}

}
