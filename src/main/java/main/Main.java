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
import view.*;

public class Main {
	
	public static void main(String args[]) {

		View.main(args);


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
