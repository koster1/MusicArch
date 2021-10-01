package com.jcg.hibernate.maven;

import java.util.List;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DAO {
	static Session session;
	static SessionFactory sessionFactory;
	
	public DAO(){	
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			}catch(Exception e){		
				System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
				System.exit(-1);
			}	
	}
	
	
	/*
	 * Method used to create a new genre in the database. Will first iterate through all found genreNames, to ensure that it will not allow the creation of a genre that already exists.
	 * TESTED - Works
	 */
	public boolean createGenre(Genre genre) {
		Genre[] genreSearch;
		genreSearch = readGenres();
		
		//First loop to check whether a given genre is already found within the database
		for(int i = 0; i < genreSearch.length; i++) {			
			if(genreSearch[i].getGenreName().equals(genre.getGenreName())) {
				System.out.println("This one already exists! Can't add it!");
				return false;
			}
		}
			
		Transaction transAct = null;
		
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			session.saveOrUpdate(genre);
			
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	
	public Genre readGenre(int tunnus) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Genre genre = session.get(Genre.class, tunnus);
			System.out.println(genre.getGenreName()+" Wow");
			transAct.commit();
			return genre;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public Genre[] readGenres() {
		
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Genre> result = (List<Genre>) session.createQuery("from Genre").list();
			
			transAct.commit();
			Genre[] array = new Genre[result.size()];
			return (Genre[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	
	public void finalize() {
		try {
			if(sessionFactory != null)
				sessionFactory.close();
		}catch(Exception e) {
			System.err.println("Session factory couldn't be closed: "+e.getMessage());
		}
	}
	
}
