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
		RemoteDAO dao = new RemoteDAO();
		
		LocalDAO localDAO = new LocalDAO();
		
		dao.readArtist("2");
		
		
//		Artist artist = localDAO.readArist(2);
//		
//		System.out.println("Returned artist = " + artist.getArtistName());
		
		Artist artist2 = new Artist();
		
		artist2.setArtistBio("test");
		artist2.setArtistID(1);
		artist2.setArtistName("Black Sabbath");
		System.out.println(artist2.getArtistBio() + " " + artist2.getArtistID() + artist2.getArtistName());
		dao.createArtist(artist2);
//		localDAO.createArtist(artist2);
//		Genre anotherTest;
//		
//		anotherTest = dao.readGenre("WowzersGenre");
//		System.out.println("A weird test"+anotherTest);
//		
		
//		Genre[] testList;
//		
//		testList = dao.readGenres();
//		
//		for(int i = 0; i<testList.length; i++) {
//			System.out.println("Genren: "+testList[i].getGenreName()+" ID on: "+testList[i].getGenreID());
//		}
		
		
		
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
