package main;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Genre;

import controller.Controller;
import model.DeletionManagement;
import model.DeliveryObject;
import model.InputManagement;
import model.SearchManagement;

public class Main {
	
	public static void main(String args[]) {
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
		
		Genre testSearch = new Genre();
		testSearch = dao.searchGenre("SecondGenre");
		System.out.println("Found this -> "+testSearch.getGenreName()+" with the ID of -> "+testSearch.getGenreID());
		
		
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
