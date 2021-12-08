import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.Song;

import controller.Controller;
import model.LocalAlbum;

public class localAlbumTest {
	private String givenAlbumTitle = "Test Album";
	private String artistName = "Jenkins Artist";
	private String artistBio = "Jenkins' personal little artist!";
	private String genreName = "A really cool Jenkins Genre!";
	private Controller controller = new Controller();
	
	@BeforeEach
	public void beforeEach() {
		try {
			controller.removeLocalAlbum(controller.searchLocalAlbum(givenAlbumTitle).getAlbumID());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("Adding an album to the database")
	public void createAlbum() throws Exception {
		Genre genre = new Genre();
		genre.setGenreID(1000);
		genre.setGenreName(genreName);
		
		Artist artist = new Artist();
		artist.setArtistID(1000);
		artist.setArtistName(artistName);
		
		Song song = new Song();
		song.setSongID(1000);
		song.setSongName("kappaleTesti");
		
		controller.createLocalArtist(artist.getArtistID(), artist.getArtistName(), artistBio);
		controller.createLocalGenre(1000, genreName);
		
		Set<Genre> testGenreList = new HashSet<Genre>();
		Set<Artist> testArtistList = new HashSet<Artist>();
		Set<Song> testSongSet = new HashSet<Song>();
		testGenreList.add(genre);
		testArtistList.add(artist);
		testSongSet.add(song);
		
		LocalAlbum testAlbum = new LocalAlbum();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		
		controller.createLocalAlbum(1000, "localTestAlbum", testSongSet, 1995, testGenreList, testArtistList);
		assertEquals(givenAlbumTitle, controller.searchAlbum(givenAlbumTitle).getAlbumName(), "Expected to find Test Album");	
		controller.removeAlbum(controller.searchAlbum(givenAlbumTitle).getAlbumID());
	}
	
	@Test
	@DisplayName("Searching for a specific album from the database")
	public void searchAlbum() throws Exception{
		controller.createArtist(artistName, artistBio);
		controller.createGenre(genreName);
		
		String[] testGenreList = {genreName};
		String[] testArtistList = {artistName};
		
		Album testAlbum = new Album();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		
		controller.createAlbum(givenAlbumTitle, 1234, testGenreList, testArtistList, testArtistList);
		assertEquals(givenAlbumTitle, controller.searchAlbum(givenAlbumTitle).getAlbumName(), "Expected to find Test Album");
		controller.removeAlbum(controller.searchAlbum(givenAlbumTitle).getAlbumID());
	}
	
	@Test
	@DisplayName("Deleting a specific album from the database")
	public void deleteArtist() throws Exception {
		controller.createArtist(artistName, artistBio);
		controller.createGenre(genreName);
		
		String[] testGenreList = {genreName};
		String[] testArtistList = {artistName};
		
		Album testAlbum = new Album();
		testAlbum.setAlbumName(givenAlbumTitle);
		testAlbum.setAlbumYear(1234);
		
		controller.createAlbum(givenAlbumTitle, 1234, testGenreList, testArtistList, testArtistList);
		controller.removeAlbum(controller.searchAlbum(givenAlbumTitle).getAlbumID());
		assertThrows(Exception.class, () -> {
			controller.searchAlbum(givenAlbumTitle).getAlbumName();
		});
	}
}
