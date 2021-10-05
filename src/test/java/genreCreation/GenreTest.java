package genreCreation;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.RemoteDAO;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;


public class GenreTest {
	
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

	}
	
	@Test
	@Order(1)
	@DisplayName("Input type addition")
	public void createType() {
	}
	
	@Test
	@Order(2)
	@DisplayName("Input genre name test")
	public void createGenreName() {
	}
	
	@Test
	@Order(3)
	@DisplayName("Add Genre into database")


	public void createGenre() throws Exception {

	}
	
	@Test
	@Order(5)
	@DisplayName("Adding the same genre twice")
	public void createMultGenre() throws Exception {

		

	}
	
	@Test
	@Order(4)
	@DisplayName("Delete genre from database")
	public void deleteGenre() {

	}

}
