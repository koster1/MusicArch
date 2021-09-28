package com.jcg.hibernate.maven;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

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
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
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
			System.out.println("Got a creation request!");
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	//These are missing a simple text search!
	public Genre readGenre(int id) {	
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		Genre genre = (Genre)session.get(Genre.class, id);
		System.out.println("Found this thing -> \""+genre.getGenreName()+"\"");
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
			session.close();
			return (Genre[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	
	//TESTED! Works
	public Genre searchGenre(String genreSearch) {
		Genre returnable = new Genre();
		
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Genre where genreNimi like:name");
			List<Genre> genreList = query.setParameter("name", genreSearch).list();

			transAct.commit();
			session.close();
			
			if(!genreList.isEmpty()) {
				System.out.println("The genre list is NOT empty!");
				returnable = genreList.get(0);
				System.out.println("The returnable name is -> "+returnable.getGenreName());
				return returnable;
				}	
		}catch(Exception e){
			if(transAct != null)
				System.out.println("Did we get an EXCEPTION in the catch block?");
				transAct.rollback();
			throw e;
		}
		System.out.println("Nothing found, returning default value");
		return returnable;
	}
	
	//To be tested
	public boolean editGenre(Genre genreEdit, int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Genre editGenre = (Genre)session.load(Genre.class, id);		
		session.saveOrUpdate(editGenre);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//Working! Still needs extra logic for checking if a genre can be removed
	public boolean removeGenre(int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Genre removeGenre = (Genre)session.load(Genre.class, id);		
		session.delete(removeGenre);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public boolean createArtist(Artist artist) {
		Artist[] artistSearch = readArtists();		
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
			session.save(artist);
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	
	//TESTED! Works
	public Artist readArtist(int id) {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		Artist artist = (Artist)session.get(Artist.class, id);	
		System.out.println("Found this thing -> "+artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}
	//Tested! Works
	public Artist[] readArtists() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			System.out.println("readArtists 1");
			
			
			@SuppressWarnings("unchecked")
			List<Artist> result = (List<Artist>) session.createQuery("from Artist").list();
			System.out.println("readArtists 2");
			transAct.commit();
			Artist[] array = new Artist[result.size()];
			session.close();
			return (Artist[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public Artist searchArtist(String artistSearch) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Artisti where artistiNimi like:name");
			List<Artist> artistList = query.setParameter("name", artistSearch).list();

			transAct.commit();
			session.close();
			return artistList.get(0);
		}
	}	
	
	//To be tested!
	public boolean editArtist(Artist artistEdit, int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Artist editArtist = (Artist)session.load(Artist.class, id);		
		session.saveOrUpdate(editArtist);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}		
	
	//To be tested!
	public boolean removeArtist(int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Artist removeArtist = (Artist)session.load(Artist.class, id);		
		session.delete(removeArtist);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	//Still not sure how to handle the song list here :/ Implement searches by name to get a list of GenreIDs and ArtistIDs
	public boolean createAlbum(Album album, Song[] songs, Includes[] includes, String[] albumGenres, String[] albumArtists) {
		
		return true;
	}
	
	public Album readAlbum(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Album album = (Album)session.get(Album.class, id);
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
			List<Album> result = (List<Album>) session.createQuery("from Album").list();
			
			transAct.commit();
			Album[] array = new Album[result.size()];
			return (Album[]) result.toArray(array);
		}catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//To be tested! Still not sure how to handle the song list here :/
	public boolean editAlbum(Album albumEdit, Song[] songEdit, int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Album editAlbum = (Album)session.load(Album.class, id);		
		session.saveOrUpdate(editAlbum);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
		
	//To be tested!
	public boolean removeAlbum(int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Album removeAlbum = (Album)session.load(Album.class, id);		
		session.delete(removeAlbum);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public boolean editSong(int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		Song editSong = (Song)session.load(Song.class, id);		
		session.saveOrUpdate(editSong);
		transAct.commit();
		return true;
		
		}catch(Exception e) {
			if(transAct != null)
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
