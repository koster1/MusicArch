package artistCreation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.RemoteDAO;

public class ArtistCreation {
	private RemoteDAO rDAO = new RemoteDAO();
	private String givenArtist = "Test Artist";
	
	@Test
	@DisplayName("Adding an artist into the database")
	public void createArtist() throws Exception {
		System.out.println("Adding artist");
		Artist testArtist = new Artist();
		testArtist.setArtistName(givenArtist);
		testArtist.setArtistBio("A test bio");
		rDAO.createArtist(testArtist);
		assertEquals(givenArtist, rDAO.searchArtist(givenArtist).get(0).getArtistName(), "Expected to find Test Artist");
		rDAO.removeArtist(rDAO.searchArtist(givenArtist).get(0).getArtistID());
	}
	
	@Test
	@DisplayName("Searching a specific artist from the database")
	public void searchArtist() throws Exception {
		System.out.println("Searching artist");
		Artist testArtist = new Artist();
		testArtist.setArtistName(givenArtist);
		testArtist.setArtistBio("A test bio");
		rDAO.createArtist(testArtist);
		assertEquals(givenArtist, rDAO.searchArtist(givenArtist).get(0).getArtistName());
		rDAO.removeArtist(rDAO.searchArtist(givenArtist).get(0).getArtistID());
	}
	
	@Test
	@DisplayName("Deleting artist from the database")
	public void deleteArtist() throws Exception {
		System.out.println("Deleting artist");
		Artist testArtist = new Artist();
		testArtist.setArtistName(givenArtist);
		testArtist.setArtistBio("A test bio");
		rDAO.createArtist(testArtist);
		rDAO.removeArtist(rDAO.searchArtist(givenArtist).get(0).getArtistID());
		assertEquals(null, rDAO.searchArtist(givenArtist).get(0).getArtistName());
		
	}

}
