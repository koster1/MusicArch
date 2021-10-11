package main;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Song;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;

import controller.Controller;
import model.LocalGenre;

import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.RemoteDAO;


import view.*;


public class Main {
	public static void main(String args[]) {
//		RemoteDAO dao = new RemoteDAO();
//		LocalDAO lDAO = new LocalDAO();
//		View.main(args);
		RemoteDAO dao = new RemoteDAO();
		try {
			List<Album> testAlbum = dao.searchAlbum("testAlbum");
			for(int i = 0; i<testAlbum.size(); i++) {
				System.out.println("Found this -> "+testAlbum.get(i).getAlbumName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		List<String> testStrings = dao.getSearchable();
//		
//		for(int i = 0; i < testStrings.size(); i++) {
//			System.out.println("Searchable strings! Check it out ::: "+ testStrings.get(i));
//		}
/*
		View.main(args);		

		Genre testGenre = new Genre();
		testGenre.setGenreName("A testful genre!");
		
		Artist testArtist = new Artist();
		testArtist.setArtistName("A testing artist!");
		testArtist.setArtistBio("Don't mess with him >:c");
		
		Album testAlbum = new Album();
		testAlbum.setAlbumName("testAlbum!");
		Song song1 = new Song();
		song1.setSongName("The first song!");
		Song song2 = new Song();
		song2.setSongName("The second song! Ooooohhhh");
		
		testAlbum.addSong(song1);
		testAlbum.addSong(song2);
		testAlbum.addGenre(testGenre);
		testAlbum.addArtist(testArtist);
		
		try {
			dao.createAlbum(testAlbum);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
		}
		
//		LocalGenre testGenre = new LocalGenre();
//		testGenre.setGenreName("Wooowzers");
//		lDAO.createGenre(testGenre);
		
//		List<Album> testList = dao.genreAlbums(2);
//		for(int i = 0; i < testList.size(); i++) {
//			System.out.println("WOOOW" + testList.get(i).getAlbumYear());
//		}
//		List<Album> artistList = dao.artistAlbums(1);
//		for(int i = 0; i<artistList.size(); i++) {
//			System.out.println("Artistsss"+artistList.get(i).getAlbumName());
//		}

//		List<String> allSearchable = dao.getSearchable();
//		List<String> allGenres = dao.existingGenres();
//		List<String> allArtists = dao.existingArtists();
//		List<String> allAlbums = dao.existingAlbums();
//		
//		System.out.println("Now iterating every item in the database!");
//		for(int i = 0; i<allSearchable.size(); i++) {
//			System.out.println("Item: "+i+" : "+allSearchable.get(i));
//		}
//		System.out.println("All things done, now doing GENRES");
//		for(int i = 0; i<allGenres.size(); i++) {
//			System.out.println("Item: "+i+" : "+allGenres.get(i));
//		}
//		System.out.println("GENRES done, now doing ARTISTS");
//		for(int i = 0; i<allArtists.size(); i++) {
//			System.out.println("Item: "+i+" : "+allArtists.get(i));
//		}
//		System.out.println("ARTISTS done, now doing ALBUMS");
//		for(int i = 0; i<allAlbums.size(); i++) {
//			System.out.println("Item: "+i+" : "+allAlbums.get(i));
//		}
//		System.out.println("All done!");
		
//		System.out.println("Testing whether we can get all the searchable/existing items in each table");
//		System.out.println("All existing genres ->");
//		List<String> genreTest = dao.existingGenres();
//		for(int i = 0; i < genreTest.size(); i++) {
//			System.out.println(i+"st: "+genreTest.get(i));
//		}	
//		List<String> artistTest = dao.existingArtists();
//		for(int i = 0; i < artistTest.size(); i++) {
//			System.out.println(i+"st: "+artistTest.get(i));
//		}
//		List<String> albumTest = dao.existingAlbums();
//		for(int i = 0; i<albumTest.size(); i++) {
//			System.out.println(i+"st: "+albumTest.get(i));
//		}
		
//		Album wowAlbum = new Album();
//		wowAlbum.setAlbumName("BBB");
//		wowAlbum.setAlbumYear(333);
//		dao.createAlbum(wowAlbum);
//		
//		Album testAlbum = new Album();
//		testAlbum.setAlbumName("AAA");
//		testAlbum.setAlbumYear(555);
//		dao.createAlbum(testAlbum);
//		
//		
//		Album[] testAlbumList = dao.readAlbums();
//		System.out.println("Are these in alphabetical order now: ");
//		for(int i = 0; i<testAlbumList.length; i++) {
//			System.out.println(i+"st: "+testAlbumList[i].getAlbumName());
//		}
		
		
		
//		List<String> searchableStrings = dao.getSearchable();
//		System.out.println("Now creating a list of every name found in the database!");
//		for(int i = 0; i < searchableStrings.size(); i++) {
//			System.out.println("Iterating through all the names in the database : "+searchableStrings.get(i));
//		}
		/*	View.main(args);
		
		
		String[] genreList = new String[2];
		genreList[0] = "RockGenre";
		genreList[1] = "PopGenre";
		
		String[] artistList = new String[2];
		artistList[0] = "RockArtist";
		artistList[1] = "PopArtist";
		
		
		Controller controller = new Controller();
		controller.createGenre(genreList[0]);
		controller.createGenre(genreList[1]);		
	
<<<<<<< HEAD
	public static void main(String args[]) {
		View.main(args);
=======
		controller.createArtist(artistList[0], "A really cool rock artist!");
		controller.createArtist(artistList[1], "A moderately okay pop artist.");

		controller.createAlbum("A Rock Album", 666, genreList, artistList);
		
		*/

//		RemoteDAO dao = new RemoteDAO();
//		
//		LocalDAO localDAO = new LocalDAO();

//		LocalGenre genre = new LocalGenre();
//		genre.setGenreID(2);
//		genre.setGenreName("Rock");
//		localDAO.createGenre(genre);


//		dao.readArtist(2);
//		System.out.println("testi");

		
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//		Session session = factory.getCurrentSession();
//		
//		try {
//			session.beginTransaction();
//
//			Artist testArtist = new Artist();
//			Album testAlbum = new Album();
//			Genre testGenre = new Genre();
//			testArtist.setArtistName("Jefferson Airplane");
//			testArtist.setArtistBio("The one with the white rabbit song");
//			session.save(testArtist);
//			
//			testAlbum.setAlbumName("White Rabbit");
//			testAlbum.setAlbumYear(2007);
//			testAlbum.addArtist(testArtist);
//			session.save(testAlbum);
//			testAlbum.addGenre(testGenre);
//			testGenre.setGenreName("A chill song");
//			session.save(testGenre);
//			
//			session.getTransaction().commit();
//		}finally {
//			System.out.println("Woow!");
//		}
//		
//		
		

		
		

	
//		List<String> searchableStrings = localDAO.getSearchable();
//		for(int i = 0; i < searchableStrings.size(); i++) {
//			System.out.println("Iterating through all the names in the database : "+searchableStrings.get(i));
//		}

//		List<String> searchableStrings = dao.getSearchable();
//		System.out.println("Now creating a list of every name found in the database!");
//		for(int i = 0; i < searchableStrings.size(); i++) {
//			System.out.println("Iterating through all the names in the database : "+searchableStrings.get(i));
//		}


	
//		
		
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
//		
//		Session session = factory.getCurrentSession();
//		
//		try {
//			session.beginTransaction();
//			Album tempAlbum = new Album();
//			tempAlbum.setAlbumName("Album for the artists!");
//			tempAlbum.setAlbumYear(666);
//			
//			Genre tempGenre = new Genre();
//			tempGenre.
			
//			session.beginTransaction();
//			Genre tempGenre = new Genre();
//			tempGenre.setGenreName("ChadGenre");
//			
//			System.out.println("Saving the Genre!");
//			session.save(tempGenre);
//			System.out.println("Genre Saved!");
//			
//			Album tempAlbum = new Album();
//			tempAlbum.setAlbumName("Chad's album");
//			tempAlbum.setAlbumYear(2007);
//			
//			Album tempAlbum2 = new Album();
//			tempAlbum2.setAlbumName("Chad's return");
//			tempAlbum2.setAlbumYear(1998);
//
//			tempGenre.addAlbum(tempAlbum);
//			tempGenre.addAlbum(tempAlbum2);
//			
//			System.out.println("Saving albums!");
//			session.save(tempAlbum);
//			session.save(tempAlbum2);

//			System.out.println("Albums saved!");
//			
//			session.getTransaction().commit();			
//			
//			System.out.println("Done!");
//		}
//		finally {
//			System.out.println("Wooow");
//		}
		
		
//		Artist[] artistList = dao.readArtists();
//		System.out.println(artistList[0].getArtistName());
//		
//		
//		
//		System.out.println(localDAO.readArtists()[0].getArtistName());
//		Artist artist2 = new Artist();
//		
//		artist2.setArtistBio("test");
//		artist2.setArtistID(34);
//		artist2.setArtistName("Black Sabbathoni");
//		System.out.println(artist2.getArtistBio() + " " + artist2.getArtistID() + artist2.getArtistName());
//		localDAO.createArtist(artist2);
		
		
//		localDAO.createArtist(artist2);
//		Genre anotherTest;
//		
//		anotherTest = dao.readGenre("WowzersGenre");
//		System.out.println("A weird test"+anotherTest);



	}
}
