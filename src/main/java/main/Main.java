package main;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;

import controller.Controller;
import model.DeletionManagement;
import model.DeliveryObject;
import model.InputManagement;
import model.SearchManagement;

public class Main {
	
	public static void main(String args[]) {
		
		String[] genreList = new String[2];
		genreList[0] = "RockGenre";
		genreList[1] = "PopGenre";
		
		String[] artistList = new String[2];
		artistList[0] = "RockArtist";
		artistList[1] = "PopArtist";
		
		
		Controller controller = new Controller();
		controller.createGenre(genreList[0]);
		controller.createGenre(genreList[1]);		
	
		controller.createArtist(artistList[0], "A really cool rock artist!");
		controller.createArtist(artistList[1], "A moderately okay pop artist.");

		controller.createAlbum("A Rock Album", 666, genreList, artistList);
		
		
//		RemoteDAO dao = new RemoteDAO();
//		
//		LocalDAO localDAO = new LocalDAO();
		
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
		
		
		
	
//		List<String> searchableStrings = dao.getSearchable();
//		System.out.println("Now creating a list of every name found in the database!");
//		for(int i = 0; i < searchableStrings.size(); i++) {
//			System.out.println("Iterating through all the names in the database : "+searchableStrings.get(i));
//		}
		
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

//		
//		Genre searchTest;
//		searchTest = dao.searchGenre("TestGenre");
//		
//		System.out.println("Found this -> "+searchTest.getGenreName()+" with the ID of -> "+searchTest.getGenreID());
		
//		Genre[] testList;
//		
//		testList = dao.readGenres();
//		
//		for(int i = 0; i<testList.length; i++) {
//			System.out.println("Genren: "+testList[i].getGenreName()+" ID on: "+testList[i].getGenreID());
//		}
//		
//		Genre testSearch = new Genre();
//		testSearch = dao.searchGenre("SecondGenres");
//		System.out.println("Found this -> "+testSearch.getGenreName()+" with the ID of -> "+testSearch.getGenreID());
//		
//		Genre[] anotherTest;
//		anotherTest = dao.readGenres();
//		
//		for(int i = 0; i < anotherTest.length; i++) {
//			System.out.println("Wow");
//		}
		
//		Genre[] testList;
//		
//		testList = dao.readGenres();
//		
//		for(int i = 0; i<testList.length; i++) {
//			System.out.println("Genren: "+testList[i].getGenreName()+" ID on: "+testList[i].getGenreID());
//		}
		
		
//		Genre testSearch = new Genre();
//		testSearch = dao.searchGenre("SecondGenre");
//		System.out.println("Found this -> "+testSearch.getGenreName()+" with the ID of -> "+testSearch.getGenreID());
//		
//		
//		Genre genre1 = new Genre();
//		
//		genre1.setGenreName("UmpteenthGenre");
//		dao.createGenre(genre1);
//		
		
//		Genre genreTest = new Genre();
//		genreTest.setGenreName("TubularGenre2");
//		
//		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();   
//	    Session session = sessionFactory.openSession();
//	    session.beginTransaction();
//	    
//	    session.save(genreTest);
//	    
//	    session.getTransaction().commit();
//	    session.close();
//	    
//	    sessionFactory.close();
	    
	   

	}

}
