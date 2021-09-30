package com.jcg.hibernate.maven;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.LocalArtist;
import model.LocalGenre;
import model.LocalAlbum;
import model.LocalSong;

public class LocalDAO {
	
//	static Session session;
	static SessionFactory sessionFactory;
	
	public LocalDAO() {
		try {
			sessionFactory = new Configuration().configure("localDAOhibernate.cfg.xml").buildSessionFactory();
			
		} catch (Exception e) {
			System.out.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
//		session = sessionFactory.openSession();
	}
	
	
	
	public boolean createGenre(LocalGenre genre) {
		LocalGenre[] genreSearch;
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
	//These are missing a simple text search!
	public LocalGenre readGenre(int id) {	
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		LocalGenre genre = (LocalGenre)session.get(LocalGenre.class, id);
		System.out.println("Found this thing -> \""+genre.getGenreName()+"\"");
		session.getTransaction().commit();
		session.close();
		return genre;		
	}
	
	public LocalGenre[] readGenres() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Genre> result = (List<Genre>) session.createQuery("from Genre").list();
			
			transAct.commit();
			LocalGenre[] array = new LocalGenre[result.size()];
			return (LocalGenre[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//TESTED! Works
	public LocalGenre searchGenre(String genreSearch) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Genre where genreNimi like:name");
			List<LocalGenre> genreList = query.setParameter("name", genreSearch).list();

			transAct.commit();
			session.close();
			return genreList.get(0);			
		}
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
	
	public boolean createArtist(LocalArtist artist) {
		LocalArtist[] artistSearch = readArtists();
		
		//First loop to check whether a given artist is already found within the database
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
	
	public LocalArtist readArtist(int id) {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		LocalArtist artist = (LocalArtist)session.get(LocalArtist.class, id);	
		System.out.println("Found this thing -> "+artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}
	
	public LocalArtist[] readArtists() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			System.out.println("readArtists 1");
			
			
			@SuppressWarnings("unchecked")
			List<LocalArtist> result = (List<LocalArtist>) session.createQuery("from LocalArtist").list();
			System.out.println("readArtists 2");
			transAct.commit();
			LocalArtist[] array = new LocalArtist[result.size()];
			return (LocalArtist[]) result.toArray(array);
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
	//Still not sure how to handle the song list here :/
	public boolean createAlbum(Album album, LocalSong[] songs) {
		return true;
	}
	
	public LocalAlbum readAlbum(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		LocalAlbum album = (LocalAlbum)session.get(LocalAlbum.class, id);
		System.out.println("Found this thing -> "+album.getAlbumName());
		session.getTransaction().commit();
		session.close();
		return album;	
	}
	
	public LocalAlbum[] readAlbums() {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<LocalAlbum> result = (List<LocalAlbum>) session.createQuery("from LocalAlbumi").list();
			
			transAct.commit();
			LocalAlbum[] array = new LocalAlbum[result.size()];
			return (LocalAlbum[]) result.toArray(array);
		}catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//To be tested! Still not sure how to handle the song list here :/
	public boolean editAlbum(LocalAlbum albumEdit, LocalSong[] songEdit, int id) {
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
		LocalAlbum removeAlbum = (LocalAlbum)session.load(LocalAlbum.class, id);		
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
		LocalSong editSong = (LocalSong)session.load(LocalSong.class, id);		
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
