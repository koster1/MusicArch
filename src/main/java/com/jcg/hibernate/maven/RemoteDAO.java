package com.jcg.hibernate.maven;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
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


//	private static SessionFactory buildSessionFactory() {
//		Configuration config = new Configuration();
//		config.configure("hibernate.cfg.xml");
//		
//		ServiceRegistry serviceReg = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
//		
//		sessionFactory = config.buildSessionFactory(serviceReg);
//		return sessionFactory;
//	}

	public RemoteDAO() {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
//		session = buildSessionFactory().openSession();
//		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	
	
	// -------------JEMILAN TESTIT-----------------------
	public boolean GenreExist(String genreName) {
		Genre[] genreSearch = readGenres();

		// First loop to check whether a given genre is already found within the
		// database
		for (int i = 0; i < genreSearch.length; i++) {
			if (genreSearch[i].getGenreName().equals(genreName)) {
				System.out.println("This one already exists! Can't add it!");

				return true;
			}
		}
		return false;
	}

	// --------------------------------------------------------------
	/*
	 * Method used to create a new genre in the database. Will first iterate through
	 * all found genreNames, to ensure that it will not allow the creation of a
	 * genre that already exists. TESTED - Works
	 */
	public boolean createGenre(Genre genre) {
		Genre[] genreSearch;
		genreSearch = readGenres();

		// First loop to check whether a given genre is already found within the
		// database
		for (int i = 0; i < genreSearch.length; i++) {
			if (genreSearch[i].getGenreName().equals(genre.getGenreName())) {
				System.out.println("This one already exists! Can't add it!");

				return false;
			}
		}

		Transaction transAct = null;

		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			session.saveOrUpdate(genre);
			System.out.println("Got a creation request!");
			transAct.commit();
			return true;
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	// These are missing a simple text search!
	public Genre readGenre(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Genre genre = (Genre) session.get(Genre.class, id);
		System.out.println("Found this thing -> \"" + genre.getGenreName() + "\"");
		session.getTransaction().commit();
		session.close();
		return genre;
	}

	public Genre[] readGenres() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Genre> result = (List<Genre>) session.createQuery("from Genre order by genreName").list();

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

	public Genre searchGenre(String genreSearch) {
		Genre returnable = new Genre();

		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Query query = session.createQuery("From Genre where genreNimi like:name");
			List<Genre> genreList = query.setParameter("name", genreSearch).list();

			transAct.commit();
			session.close();

			if (!genreList.isEmpty()) {
				System.out.println("The genre list is NOT empty!");
				returnable = genreList.get(0);
				System.out.println("The returnable name is -> " + returnable.getGenreName());
				return returnable;
			}
		} catch (Exception e) {
			if (transAct != null)
				System.out.println("Did we get an EXCEPTION in the catch block?");
			transAct.rollback();
			throw e;
		}
		System.out.println("Nothing found, returning default value");
		return returnable;
	}

	// To be tested
	public boolean editGenre(Genre genreEdit, int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Genre editGenre = (Genre) session.load(Genre.class, id);
			session.saveOrUpdate(editGenre);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	// Working! Still needs extra logic for checking if a genre can be removed
	public boolean removeGenre(int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Genre removeGenre = (Genre) session.load(Genre.class, id);
			session.delete(removeGenre);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	public boolean createArtist(Artist artist) {
		Artist[] artistSearch = readArtists();
		// First loop to check whether a given genre is already found within the
		// database
		for (int i = 0; i < artistSearch.length; i++) {
			if (artistSearch[i].getArtistName().equals(artist.getArtistName())) {
				System.out.println("This one already exists! Can't add it!");
				return false;
			}
		}

		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			session.saveOrUpdate(artist);
			session.save(artist);
			transAct.commit();
			return true;
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	// TESTED! Works
	public Artist readArtist(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Artist artist = (Artist) session.get(Artist.class, id);
		System.out.println("Found this thing -> " + artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}

	// Tested! Works
	public Artist[] readArtists() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Artist> result = (List<Artist>) session.createQuery("from Artist order by artistName").list();
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
		Artist returnable = new Artist();
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Artist where artistName like:name");
			List<Artist> artistList = query.setParameter("name", artistSearch).list();

			if(!artistList.isEmpty()) {
				System.out.println("The genre list is NOT empty!");
				returnable = artistList.get(0);
				System.out.println("The returnable name is -> "+returnable.getArtistName());
				return returnable;
				}	
		}catch(Exception e){
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
		System.out.println("Nothing found, returning default value");
		return returnable;
	}
		
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

	public boolean createAlbum(Album album) {
		Album[] albumSearch = readAlbums();		
		//First loop to check whether a given genre is already found within the database
		for(int i = 0; i < albumSearch.length; i++) {			
			if(albumSearch[i].getAlbumName().equals(album.getAlbumName())) {
				System.out.println("This one already exists! Can't add it!");
				return false;
			}
		}

		Transaction transAct = null;	
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			session.saveOrUpdate(album);			
			session.save(album);
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
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
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Album> result = (List<Album>) session.createQuery("from Album order by albumName").list();

			transAct.commit();
			Album[] array = new Album[result.size()];
			return (Album[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	// To be tested! Still not sure how to handle the song list here :/
	public boolean editAlbum(Album albumEdit, Song[] songEdit, int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Album editAlbum = (Album) session.load(Album.class, id);
			session.saveOrUpdate(editAlbum);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

	// To be tested!
	public boolean removeAlbum(int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Album removeAlbum = (Album) session.load(Album.class, id);
			session.delete(removeAlbum);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}

//	public getRelated(albumID) {
//		
//	}

	public boolean editSong(int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Song editSong = (Song) session.load(Song.class, id);
			session.saveOrUpdate(editSong);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public List<String> getSearchable(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select artistinimi from artisti union select albuminimi from albumi union select genrenimi from genre union select kappalenimi from kappale";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}		
	}	
	public List<String> existingGenres(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select genrenimi from genre";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	public List<String> existingArtists(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select artistinimi from artisti";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	public List<String> existingAlbums(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select albuminimi from albumi";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	public List<String> existingSongs(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select kappalenimi from kappale";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	
	/*
	public Artist[] genreArtists(int genreID) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public List<Album> artistAlbums(int artistID){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Album> testing = session.createCriteria(Album.class).setFetchMode("Genre", FetchMode.JOIN).add(Restrictions.eqOrIsNull("genreID", 1)).list();
			System.out.println(testing.get(0));
			transAct.commit();
			return testing;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	*/
	//STILL INCOMPLETE, not working properly. "Lazy loading not initialized"?
	public Album genreAlbums(int genreID){
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Genre> result = (List<Genre>) session.createQuery("from Genre order by genreName").list();
			List<Album> array = result.get(0).getGenreAlbums();
			transAct.commit();
			return array.get(0);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//public List<Song> albumSongs(int albumID){}
	public void finalize() {
		try {
			if (sessionFactory != null)
				sessionFactory.close();
		} catch (Exception e) {
			System.err.println("Session factory couldn't be closed: " + e.getMessage());
		}
	}

}
