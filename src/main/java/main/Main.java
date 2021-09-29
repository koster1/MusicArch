package main;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;

import controller.Controller;
import model.DeletionManagement;
import model.DeliveryObject;
import model.InputManagement;
import model.SearchManagement;
import view.*;

public class Main {
	
	public static void main(String args[]) {
		View.main(args);

		RemoteDAO dao = new RemoteDAO();
		

//		Genre anotherTest;
//		
//		anotherTest = dao.readGenre("WowzersGenre");
//		System.out.println("A weird test"+anotherTest);
//		
		
		Genre[] testList;
		
		testList = dao.readGenres();
		
		for(int i = 0; i<testList.length; i++) {
			System.out.println("Genren: "+testList[i].getGenreName()+" ID on: "+testList[i].getGenreID());
		}

	
		
		Artist[] testList2;
		
		testList2 = dao.readArtists();
		
		for(int i = 0; i<testList2.length; i++) {
			System.out.println("Genren: "+testList2[i].getArtistName()+" ID on: "+testList2[i].getArtistID());
		}
		
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
