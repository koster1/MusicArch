package genreCreation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import model.DeletionManagement;
import model.DeliveryObject;
import model.InputManagement;
import model.SearchManagement;

public class GenreTest {
	
	private DeliveryObject deliveryObject = new DeliveryObject();
	private SearchManagement searchManager = new SearchManagement();
	private InputManagement inputManagement = new InputManagement();
	private DeletionManagement deletionManagement = new DeletionManagement();
	
	//Test parameters
	private String givenGenre = "Black Metal";
    //private String givenArtist = "Johnny Cash";
    //private String givenAlbumName = "Black Sabbath";
    //private String givenAlbumGenre = "Heavy Metal";
    //private ArrayList<String> givenSongList = new ArrayList<String>();
    //private ArrayList<Integer> givenSongDurations = new ArrayList<Integer>();
    private String genreInputType = "GENRE";
	
	@BeforeEach
	public void clearData() {
		deliveryObject.clearDelivery();
	}
	
	@Test
	@Order(1)
	@DisplayName("Input type addition")
	public void createType() {
		deliveryObject.setInputType("GENRE");
		assertEquals(genreInputType, deliveryObject.getInputType());
	}
	
	@Test
	@Order(2)
	@DisplayName("Input genre name test")
	public void createGenreName() {
		deliveryObject.setGenreName(givenGenre);
		assertEquals("Black Metal", deliveryObject.getGenreName());
	}
	
	@Test
	@Order(3)
	@DisplayName("Add Genre into database")
	public void createGenre() throws Exception {
		deliveryObject.setInputType(genreInputType);
		deliveryObject.setGenreName(givenGenre);
		
		inputManagement.checkType(deliveryObject);
		assertEquals(givenGenre, searchManager.searchGenre(searchManager.searchGenreID(givenGenre)), "Expected to find Black Metal");
	}
	
	@Test
	@DisplayName("Adding the same genre twice")
	public void createMultGenre() throws Exception {
		deliveryObject.setInputType(genreInputType);
		deliveryObject.setGenreName(givenGenre);
		
		inputManagement.checkType(deliveryObject);
		
		NullPointerException e = assertThrows(NullPointerException.class, new Executable() {
			public void execute() throws Throwable {
				inputManagement.checkType(deliveryObject);
			}
		});
        assertEquals("This one already exists! Can't add it!", e.getMessage());
	}
	
	@Test
	@Order(4)
	@DisplayName("Delete genre from database")
	public void deleteGenre() {
		System.out.println("DELETE TEST!!!!");
		int foundID = searchManager.searchGenreID(givenGenre);	
		deletionManagement.removeGenre(foundID);
		assertEquals(null, searchManager.searchGenre(foundID), "Expected to not find Black Metal!");
	}

}
