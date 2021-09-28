package genreCreation;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.RemoteDAO;


public class GenreTest {
	
	private RemoteDAO rDAO = new RemoteDAO();
	
	//Test parameters
	private String givenGenre = "TestGenre";
    //private String givenArtist = "Johnny Cash";
    //private String givenAlbumName = "Black Sabbath";
    //private String givenAlbumGenre = "Heavy Metal";
    //private ArrayList<String> givenSongList = new ArrayList<String>();
    //private ArrayList<Integer> givenSongDurations = new ArrayList<Integer>();

	
	
	
//	@Test
//	@Order(5)
//	@Disabled
//	@DisplayName("Adding the same genre twice")
//	public void createMultGenre() throws Exception {
//		deliveryObject.setInputType(genreInputType);
//		deliveryObject.setGenreName(givenGenre);
//		
//		Exception e = assertThrows(Exception.class, new Executable() {
//			public void execute() throws Throwable {				
//				inputManagement.checkType(deliveryObject);
//			}
//		});
//        assertEquals("This one already exists! Can't add it!", e.getMessage());
//	}
	
	@Test
	@Order(3)
	@DisplayName("Searching a specific Genre from database")
	public void searchGenre() {
		System.out.println("NOW DOING THE SEARCH TEST");
		Genre testGenre = new Genre();
		testGenre.setGenreName("TestGenre");
		rDAO.createGenre(testGenre);

		Genre searchTest;
		searchTest = rDAO.searchGenre("TestGenre");
		
		System.out.println("Found this -> "+searchTest.getGenreName()+" with the ID of -> "+searchTest.getGenreID());
		assertEquals("TestGenre", searchTest.getGenreName());
		rDAO.removeGenre(searchTest.getGenreID());
	}
	
	@Test
	@Order(2)
	@DisplayName("Delete genre from database")
	public void deleteGenre() {
		System.out.println("NOW DOING THE DELETION TEST");
		Genre testGenre = new Genre();
		testGenre.setGenreName("TestGenre");
		rDAO.createGenre(testGenre);
		Genre testing = rDAO.searchGenre(givenGenre);
		System.out.println("The ID for: "+testing.getGenreName() + " : is -> "+testing.getGenreID());
		rDAO.removeGenre(testing.getGenreID());
		assertEquals(null, rDAO.searchGenre(givenGenre).getGenreName(), "Expected ID to be zero");
	}
	
	@Test
	@Order(1)
	@DisplayName("Add Genre into database")
	public void createGenre() {
		System.out.println("NOW DOING THE CREATION TEST");
		Genre testGenre = new Genre();
		testGenre.setGenreName("TestGenre");
		rDAO.createGenre(testGenre);
		
		assertEquals(givenGenre, rDAO.searchGenre(givenGenre).getGenreName(), "Expected to find TestGenre!");
		rDAO.removeGenre(rDAO.searchGenre(givenGenre).getGenreID());
	}

}
