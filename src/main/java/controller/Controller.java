package controller;

import java.util.*;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;

import model.*;

/**
 * 
 */
public class Controller {

	private RemoteDAO remoteDAO = new RemoteDAO();
	//private LocalDAO localDAO = new LocalDAO();

	/**
	 * Default constructor
	 */
	public Controller() {
	}

	// Luominen etätietokantaan
	public void createGenre(String genreName) {
		Genre newGenre = new Genre();
		newGenre.setGenreName(genreName);
		remoteDAO.createGenre(newGenre);
	}

	public void createArtist(String artistName, String artistBio) {
		Artist newArtist = new Artist();
		newArtist.setArtistName(artistName);
		newArtist.setArtistBio(artistBio);
		remoteDAO.createArtist(newArtist);
	}

	public void createAlbum(String albumName, Song[] songListGiven, int albumYear, Genre[] genreListGiven,
			Artist[] artistListGiven) {
		Album newAlbum = new Album();
		Song[] songList = new Song[songListGiven.length];
		newAlbum.setAlbumName(albumName);
		newAlbum.setAlbumYear(albumYear);
		remoteDAO.createAlbum(newAlbum, songList, null, null, null);
	}

	// Paikalliseen tietokantaan luominen
	public void createLocalGenre(String genreName) {
		Genre newGenre = new Genre();
		newGenre.setGenreName(genreName);
		localDAO.createGenre(newGenre);
	}

	public void createLocalArtist(String artistName, String artistBio) {
		Artist newArtist = new Artist();
		newArtist.setArtistName(artistName);
		newArtist.setArtistBio(artistBio);
		localDAO.createArtist(newArtist);
	}

	public void createLocalAlbum(String albumName, Song[] songListGiven, int albumYear, Genre[] genreListGiven,
			Artist[] artistListGiven) {
		Album newAlbum = new Album();
		Song[] songList = new Song[songListGiven.length];
		newAlbum.setAlbumName(albumName);
		newAlbum.setAlbumYear(albumYear);
		localDAO.createAlbum(newAlbum, songList);
	}

	// Tallennus etätietokantaan.

	public void saveGenre(int genreID) {
		Genre saveGenre = new Genre();
		saveGenre = remoteDAO.readGenre(genreID);
	}

	public void saveArtist(int artistID) {
		Artist saveArtist = new Artist();
		saveArtist = remoteDAO.readArtist(artistID);
	}

	public void saveAlbum(int albumID) {
		Album saveAlbum = new Album();
		saveAlbum = remoteDAO.readAlbum(albumID);
	}

	// Tallennus paikalliseen tietokantaan
	public void saveLocalGenre(int genreID) {
		Genre saveLocalGenre = new Genre();
		saveLocalGenre = localDAO.readGenre(genreID);
	}

	public void saveLocalArtist(int artistID) {
		Artist saveLocalArtist = new Artist();
		saveLocalArtist = localDAO.readArtist(artistID);
	}

	public void saveLocalAlbum(int albumID) {
		Album saveLocalAlbum = new Album();
		saveLocalAlbum = localDAO.readAlbum(albumID);
	}

	// Etätietokannan muokkaus

	public void editGenre(String genreID, String genreName) {
		Genre editGenre = new Genre();
		int editID = Integer.parseInt(genreID);
		editGenre.setGenreName(genreName);
		remoteDAO.editGenre(editGenre, editID);
	}

	public void editArtist(String artistID, String artistName, String artistBio) {
		Artist editArtist = new Artist();
		int editID = Integer.parseInt(artistID);
		editArtist.setArtistName(artistName);
		editArtist.setArtistBio(artistBio);
		remoteDAO.editArtist(editArtist, editID);
	}

	public void editAlbum(String albumID, String albumName, Song[] songListGiven, int albumYear) {
		Album editAlbum = new Album();
		int editID = Integer.parseInt(albumID);
		editAlbum.setAlbumName(albumName);
		editAlbum.setAlbumYear(albumYear);
		remoteDAO.editAlbum(editAlbum, null, editID);
	}

	// Paikallisen tietokannan muokkaus
	public void editLocalGenre(String genreID, String genreName) {
		Genre editLocalGenre = new Genre();
		int editID = Integer.parseInt(genreID);
		editLocalGenre.setGenreName(genreName);
		localDAO.editGenre(editLocalGenre, editID);
	}

	public void editLocalArtist(String artistID, String artistName, String artistBio) {
		Artist editLocalArtist = new Artist();
		int editID = Integer.parseInt(artistID);
		editLocalArtist.setArtistName(artistName);
		editLocalArtist.setArtistBio(artistBio);
		localDAO.editArtist(editLocalArtist, editID);
	}

	public void editLocalAlbum(String albumID, String albumName, Song[] songListGiven, int albumYear) {
		Album editLocalAlbum = new Album();
		int editID = Integer.parseInt(albumID);
		editLocalAlbum.setAlbumName(albumName);
		editLocalAlbum.setAlbumYear(albumYear);
		localDAO.editAlbum(editLocalAlbum, null, editID);
	}

	// Poistaminen etätietokannasta
	public void removeGenre(String genreID) {
		int removeID = Integer.parseInt(genreID);
		remoteDAO.removeGenre(removeID);
	}

	public void removeArtist(String artistID) {
		int removeID = Integer.parseInt(artistID);
		remoteDAO.removeArtist(removeID);
	}

	public void removeAlbum(String albumID) {
		int removeID = Integer.parseInt(albumID);
		remoteDAO.removeAlbum(removeID);
	}

	// Poistaminen paikallisesta tietokannasta
	public void removeLocalGenre(String genreID) {
		int removeID = Integer.parseInt(genreID);
		remoteDAO.removeGenre(removeID);
	}

	public void removeLocalArtist(String artistID) {
		int removeID = Integer.parseInt(artistID);
		remoteDAO.removeArtist(removeID);
	}

	public void removeLocalAlbum(String albumID) {
		int removeID = Integer.parseInt(albumID);
		remoteDAO.removeAlbum(removeID);
	}

	// Hakutoiminnot

	/*
	 * public void searchAll(int genreID, int artistID, int albumID) {
	 * 
	 * }
	 */

	public void searchGenre(String genreName) {
		remoteDAO.searchGenre(genreName);
	}

	public void searchArtist(String artistName) {
		remoteDAO.searchArtist(artistName);
	}
	/*
	 * public void searchAlbum (String albumName) { remoteDAO.searchAlbum() }
	 */

	/*
	 * public boolean genreExist() {
	 * 
	 * }
	 */

}
