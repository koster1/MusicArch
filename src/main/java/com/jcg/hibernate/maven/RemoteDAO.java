package com.jcg.hibernate.maven;

import java.util.List;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class RemoteDAO {
	static Session session;
	static SessionFactory sessionFactory;
	
//	private static SessionFactory buildSessionFactory() {
//		Configuration config = new Configuration();
//		config.configure("hibernate.cfg.xml");
//		
//		ServiceRegistry serviceReg = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
//		
//		sessionFactory = config.buildSessionFactory(serviceReg);
//		return sessionFactory;
//	}
	
	public RemoteDAO(){	
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			}catch(Exception e){		
				System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
				System.exit(-1);
			}	
		
//		session = buildSessionFactory().openSession();
//		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
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
	
	public Genre readGenre(String id) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		Genre genre = (Genre)session.get(Genre.class, id);
		
		System.out.println("Found this thing -> "+genre.getGenreName());
		session.getTransaction().commit();
		session.close();

		return genre;		

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
	
	//To be coded
	public boolean editGenre(Genre genreEdit, int id) {
		return true;
	}
	
	//To be coded
	public boolean removeGenre(int id) {
		return true;
	}
	
	public boolean createArtist(Artist artist) {
		Artist[] artistSearch;
		artistSearch = readArtists();
		
		//First loop to check whether a given genre is already found within the database
		for(int i = 0; i < artistSearch.length; i++) {			
			if(artistSearch[i].getArtistName().equals(artist.getArtistName())) {
				System.out.println("This one already exists! Can't add it!");
				return false;
			}
		}
			
		Transaction transAct = null;
		
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			session.saveOrUpdate(artist);
			
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	
	public Artist readArtist(String id) {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		Artist artist = (Artist)session.get(Artist.class, id);
		
		System.out.println("Found this thing -> "+artist.getArtistName());
		session.getTransaction().commit();
		session.close();

		return artist;		

	}
	
	public Artist[] readArtists() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Artist> result = (List<Artist>) session.createQuery("from Artisti").list();
			
			transAct.commit();
			Artist[] array = new Artist[result.size()];
			return (Artist[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//To be coded
	public boolean editArtist(Artist artistEdit, int id) {
		return true;
		}		
	
	//To be coded
	public boolean removeArtist(int id) {
		return true;
	}
	
	public boolean createAlbum(Album album, Song[] songs) {
		return true;
	}
	
	public Album readAlbum(String tunnus) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Album album = (Album)session.get(Album.class, tunnus);
		System.out.println("Found this thing -> "+album.getAlbumName());
		session.getTransaction().commit();
		session.close();
		return album;	
	}
	
	public Album[] readAlbums() {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Album> result = (List<Album>) session.createQuery("from Albumi").list();
			
			transAct.commit();
			Album[] array = new Album[result.size()];
			return (Album[]) result.toArray(array);
		}catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//To be coded
	public boolean editAlbum(Album albumEdit, Song[] songEdit, int id) {
			return true;
	}
		
	//To be coded
	public boolean removeAlbum(int id) {
		return true;
	}
	
	public boolean editSong(int id) {
		return true;
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
