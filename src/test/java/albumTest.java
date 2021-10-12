

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.RemoteDAO;

public class albumTest {
	private RemoteDAO rDAO = new RemoteDAO();
	private String givenAlbumTitle = "Test Album";
	
	@Test
	@DisplayName("Adding an album to the database")
	public void createAlbum() throws Exception {
		System.out.println("Adding Album");
		Album testAlbum = new Album();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		rDAO.createAlbum(testAlbum, null, null);
		assertEquals(givenAlbumTitle, rDAO.searchAlbum(givenAlbumTitle).getAlbumName(), "Expected to find Test Album");	
		rDAO.removeAlbum(rDAO.searchAlbum(givenAlbumTitle).getAlbumID());
	}
	
	@Test
	@DisplayName("Searching for a specific album from the database")
	public void searchAlbum() throws Exception{
		System.out.println("Searching Album");
		Album testAlbum = new Album();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		rDAO.createAlbum(testAlbum, null, null);
		assertEquals(givenAlbumTitle, rDAO.searchAlbum(givenAlbumTitle).getAlbumName());
		rDAO.removeAlbum(rDAO.searchAlbum(givenAlbumTitle).getAlbumID());
	}
	
	@Test
	@DisplayName("Deleting a specific album from the database")
	public void deleteArtist() throws Exception {
		System.out.println("Deleting Album");
		Album testAlbum = new Album();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		rDAO.createAlbum(testAlbum, "test", "test");
		System.out.println("This is the Album we are deleting -> "+rDAO.searchAlbum(givenAlbumTitle).getAlbumName());
		rDAO.removeAlbum(rDAO.searchAlbum(givenAlbumTitle).getAlbumID());
		assertThrows(Exception.class, () -> {
			rDAO.searchAlbum(givenAlbumTitle).getAlbumName();
		});
	}

}
