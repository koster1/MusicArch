package com.jcg.hibernate.maven;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import model.LocalArtist;
import model.LocalGenre;
import model.LocalAlbum;
import model.LocalSong;
import model.WishList;

/*
 * Data access object for the local database
 * @author Jani
 * */
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
	
	
	/**
	 * Method used to create a new genre in the database. Will first iterate through
	 * all found genreNames, to ensure that it will not allow the creation of a
	 * genre that already exists. 
	 * @param genre 
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
	public boolean createGenre(LocalGenre genre) throws Exception {
		LocalGenre[] genreSearch;
		genreSearch = readGenres();
		
		//First loop to check whether a given genre is already found within the database
		for(int i = 0; i < genreSearch.length; i++) {			
			if(genreSearch[i].getGenreName().equals(genre.getGenreName())) {
				throw new Exception("This Genre already exists!");
			}
		}
				
		Transaction transAct = null;
		
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			
			
//			genre.addAlbum(localAlbum);
			session.saveOrUpdate(genre);
			
//			session.saveOrUpdate(genre);
			
			transAct.commit();
			return true;
		}catch(Exception e) {
			System.out.println("exception rollback");
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	//These are missing a simple text search!
	/**
	 * Method used to search for a genre with a given id
	 * @param id 
	 * @return LocalGenre object
	 */
	public LocalGenre readGenre(int id) {	
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		LocalGenre genre = (LocalGenre)session.get(LocalGenre.class, id);
		System.out.println("Found this thing -> \""+genre.getGenreName()+"\"");
		session.getTransaction().commit();
		session.close();
		return genre;		
	}
	
	/**
	 * Method used to get a list of all the genres in the database
	 * @param  
	 * @return Returns a list of all LocalGenre objects
	 * @throws Exception
	 */
	public LocalGenre[] readGenres() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<LocalGenre> result = (List<LocalGenre>) session.createQuery("from LocalGenre").list();
			
			transAct.commit();
			LocalGenre[] array = new LocalGenre[result.size()];
			return (LocalGenre[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/**
	 * Method used to search for a genre
	 * @param genreSearch 
	 * @return Returns a LocalGenre
	 * @throws Exception
	 */
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
	
	/**
	 * Method used to edit a genre in the User's collection
	 * @param genreEdit
	 * @param id
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
	public boolean editGenre(LocalGenre genreEdit, int id) {
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
	/**
	 * Method used to remove a genre from the User's collection
	 * @param id
	 * @return true if removal succeeded, false if not
	 * @throws Exception
	 */
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
	/**
	 * Method used to create an artist in the User's collection.
	 * The method first checks if an artist already exists
	 * @param artist
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
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
	
	/**
	 * Method used to create an artist in the User's collection.
	 * The method first checks if an artist already exists
	 * @param artist
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
	public LocalArtist readArtist(int id) {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();			
		LocalArtist artist = (LocalArtist)session.get(LocalArtist.class, id);	
		System.out.println("Found this thing -> "+artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}
	
	/**
	 * Method used to get all the artists in the local database
	 * @param
	 * @return LocalArtist[] list of artists from the local database
	 * @throws Exception
	 */
	public LocalArtist[] readArtists() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			System.out.println("Local readArtists exception 0 ");
			@SuppressWarnings("unchecked")
			List<LocalArtist> result = (List<LocalArtist>) session.createQuery("from LocalArtist order by artistName").list();
			System.out.println("Local readArtists exception 1");
			transAct.commit();
			session.close();
			System.out.println("Local readArtists exception 2");
			LocalArtist[] array = new LocalArtist[result.size()];
			array = (LocalArtist[]) result.toArray(array);
			return array;
		} catch (Exception e) {
			System.out.println("Local readArtists exception 3");
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
//	public LocalArtist[] readArtists() {
//		Transaction transAct = null;
//		try (Session session = sessionFactory.openSession()) {
//			transAct = session.beginTransaction();
//			System.out.println("readArtists 1");
//			
//			
//			@SuppressWarnings("unchecked")
//			List<LocalArtist> result = (List<LocalArtist>) session.createQuery("from LocalArtist").list();
//			System.out.println("readArtists 2");
//			transAct.commit();
//			LocalArtist[] array = new LocalArtist[result.size()];
//			return (LocalArtist[]) result.toArray(array);
//		} catch (Exception e) {
//			if (transAct != null)
//				transAct.rollback();
//			throw e;
//		}
//	}
//	
	/**
	 * Method used for searching for an artist
	 * @param artistSearch a String given by the user
	 * @return List<LocalArtist>
	 * @throws Exception
	 */
	public List<LocalArtist> searchArtist(String artistSearch) throws Exception {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Artist where artistName =:name");
			List<LocalArtist> artistList = query.setParameter("name", artistSearch).list();
			
			if (artistList.size() == 0) {
				throw new Exception("Nothing found!");
			}
			transAct.commit();
			session.close();
			
			return artistList;
		}catch(Exception e){
			System.out.println("exception why??");
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
		
	}
	
	
//	public Artist searchArtist(String artistSearch) {
//		Transaction transAct = null;
//		try(Session session = sessionFactory.openSession()){
//			transAct = session.beginTransaction();	
//			Query query = session.createQuery("From Artisti where artistiNimi like:name");
//			List<Artist> artistList = query.setParameter("name", artistSearch).list();
//
//			transAct.commit();
//			session.close();
//			return artistList.get(0);			
//		}
//	}	
		
	
	/**
	 * Method used to remove an artist
	 * @param id 
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
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
	/**
	 * Method used for creating an album
	 * Checks if the album already exists
	 * @param LocalAlbum a LocalArtist given by the user
	 * @param LocalSong[] table of songs
	 * @param LocalArtist[] table of artists
	 * @param LocalGenre[] table of genres
	 * @return true if creation went through, false if not
	 * @throws Exception
	 */
	public boolean createAlbum(LocalAlbum localAlbum, LocalSong[] songs, LocalArtist[] artists, LocalGenre[] genres) throws Exception {
		LocalAlbum[] albumSearch = readAlbums();		
		//First loop to check whether a given genre is already found within the database
		for(int i = 0; i < albumSearch.length; i++) {			
			if(albumSearch[i].getAlbumName().equals(localAlbum.getAlbumName())) {
				throw new Exception("This Album already exists!");
			}
		}

		Transaction transAct = null;	
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			
			for(LocalSong localSong : songs) {
				localSong.addAlbum(localAlbum);
				
				session.saveOrUpdate(localSong);
			}
			for(LocalArtist localArtist : artists) {
				// try catch
				try {
					LocalArtist localArtis = (LocalArtist)session.load(LocalArtist.class, localArtist.getArtistID());
					localArtis.addAlbum(localAlbum);
					session.update(localArtis);
					
				} catch (Exception e){
					System.out.println(e.getMessage());
					localArtist.addAlbum(localAlbum);
					session.save(localArtist);					
				}
				
			}
			for(LocalGenre localGenre : genres) {
				try {
					LocalGenre localGenr = (LocalGenre)session.load(LocalGenre.class, localGenre.getGenreID());
					localGenr.addAlbum(localAlbum);
					session.update(localGenr);					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					localGenre.addAlbum(localAlbum);
					session.save(localGenre);					
				}
				
			}
			
//			session.saveOrUpdate(localAlbum);			
			session.save(localAlbum);
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	/**
	 * Method used for searching for an album
	 * @param id 
	 * @return LocalAlbum returns an album from the user's collection
	 * @throws Exception
	 */
	public LocalAlbum readAlbum(int id) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		LocalAlbum album = (LocalAlbum)session.get(LocalAlbum.class, id);
		System.out.println("Found this thing -> "+album.getAlbumName());
		session.getTransaction().commit();
		session.close();
		return album;	
	}
	
	/**
	 * Method used to get a list of albums
	 * @return LocalAlbum[] returns all the albums from an user's collection
	 * @throws Exception
	 */
	public LocalAlbum[] readAlbums() {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			System.out.println("album1");
			@SuppressWarnings("unchecked")
			List<LocalAlbum> result = (List<LocalAlbum>) session.createQuery("from LocalAlbum order by AlbumName").list();
			System.out.println("album2");
			LocalAlbum[] array = new LocalAlbum[result.size()];
			transAct.commit();
			return (LocalAlbum[]) result.toArray(array);
		}catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/**
	 * Method used for searching for an album with a String
	 * @param albumSearch 
	 * @return LocalAlbum returns an album from the user's collection
	 * @throws Exception
	 */
	public LocalAlbum searchAlbum(String albumSearch) throws Exception{
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
//			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Album where albumName like:name");
			List<LocalAlbum> albumList = query.setParameter("name", albumSearch).list();

			
//			transAct.commit();
			//session.close();
			
			if (albumList.size() == 0) {
				System.out.println("Nothing found in the albums with this search -> "+albumSearch); //This is the one that bugs out! Jumps over to the catch -> "Cannot rollback transaction in current status [COMMITTED]
				session.close();
				throw new Exception("Nothing found!");
			}
			return albumList.get(0);
		}catch(Exception e){
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	//To be tested! Still not sure how to handle the song list here :/
	/**
	 * Method used for editing an album
	 * @param albumEdit
	 * @param songEdit
	 * @param id
	 * @return true if success else false
	 * @throws Exception
	 */
	public boolean editAlbum(LocalAlbum albumEdit, LocalSong[] songEdit, int id) {
		Transaction transAct = null;		
		try(Session session = sessionFactory.openSession()){
		transAct = session.beginTransaction();		
		LocalAlbum editAlbum = (LocalAlbum)session.load(LocalAlbum.class, id);		
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
	/**
	 * Method used for removing an album
	 * @param id 
	 * @return true if success else false
	 * @throws Exception
	 */
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
	
	/**
	 * Method used for editing a song
	 * @param id 
	 * @return true if success else false
	 * @throws Exception
	 */
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
	
	/**
	 * Method gives name of everything in the local database
	 * @param id 
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getSearchable() {
		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			String sql = "select artistinimi from artisti union select albuminimi from albumi union select genrenimi from genre union select kappalenimi from kappale";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transaction.commit();
			return results;
		} catch(Exception e) {
			if(transaction != null)
				transaction.rollback();
			throw e;
		}
		
	}
	
//	 Method takes in an album's ID and then opens a session with it. During the session, a song-list can be created based on the instance
//	 After loading the list, the session is closed and the method returns a list of Songs based on the album.
	 
	/**
	 * Method used to get all songs from an album
	 * @param albumID 
	 * @return List<LocalSong>
	 * @throws Exception
	 */
	public List<LocalSong> localAlbumSongs(int albumID){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			LocalAlbum localAlbum = (LocalAlbum) session.load(LocalAlbum.class, albumID);
			List<LocalSong> array = localAlbum.getAlbumSongs();
			System.out.println(array);
			transAct.commit();
			session.close();
			return array;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/**
	 * Method used to get all genres from an album
	 * @param albumID 
	 * @return List<LocalGenre>
	 * @throws Exception
	 */
	public List<LocalGenre> getLocalAlbumGenres(int albumID) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			System.out.println("albumgenre1");
			LocalAlbum localAlbum = (LocalAlbum) session.load(LocalAlbum.class, albumID);
			System.out.println("albumgenre2");
			List<LocalGenre> array = localAlbum.getAlbumGenres();
			System.out.println("albumgenre3 " + array);
			transAct.commit();
			System.out.println("albumgenre4");
			session.close();
			System.out.println("albumgenre close");
			return array;
		}catch(Exception e) {
			System.out.println("albumgenre5");
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/**
	 * Method used to get all artists from an album
	 * @param albumID 
	 * @return List<LocalArtist>
	 * @throws Exception
	 */
	public List<LocalArtist> getLocalAlbumArtists(int albumID) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			System.out.println("albumgenre1");
			LocalAlbum localAlbum = (LocalAlbum) session.load(LocalAlbum.class, albumID);
			System.out.println("albumgenre2");
			List<LocalArtist> array = localAlbum.getAlbumArtists();
			System.out.println("albumgenre3 " + array);
			transAct.commit();
			System.out.println("albumgenre4");
			session.close();
			System.out.println("albumgenre close");
			return array;
		}catch(Exception e) {
			System.out.println("albumgenre5");
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	/**
	 * Method searches for an album in the wishlist
	 * @param albumID
	 * @return true if success else false
	 */
	@SuppressWarnings("deprecation")
	public boolean searchWishlist(int albumID) {

		Transaction transAct = null;
		
		try(Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
//			WishList wishList = (WishList) session.createQuery("FROM WishList where id = :albumID");
			Query query = session.createQuery("FROM WishList WHERE albumID = :albumID");
			query.setParameter("albumID", albumID);
			List results = query.list();
			if(results.size() == 0) {
				System.out.println("Could not find item");
				
				return false;
			}
			System.out.println("Found item: " + results);
			transAct.commit();
			
			return true;
		} catch (Exception e) {
			if(transAct != null) {
				System.out.println("searchWishlist exception");
				transAct.rollback();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Method adds an album to a wishlist
	 * Checks if it already exists in the wishlist
	 * @param albumID
	 * @param albumName
	 * @param albumYear
	 * @return true if success else false
	 * @throws Exception
	 */
	public boolean addToWishlist(int albumID, String albumName, int albumYear) {
		if(searchWishlist(albumID) == true) {
			return false;
		}
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			WishList wishList = new WishList();
			wishList.setAlbumID(albumID);
			wishList.setAlbumName(albumName);
			wishList.setAlbumYear(albumYear);
			session.save(wishList);
			transAct.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if(transAct != null) {
				transAct.rollback();
			}
			throw e;
		}
	}
	/**
	 * Method to remove an album from a wishlist
	 * Checks if it already exists in the wishlist
	 * @param id
	 * @return true if success else false
	 * @throws Exception
	 */
	public boolean removeFromWishlist(int id) {
		if(searchWishlist(id) == false) {
			return false;
		}
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Query query = session.createQuery("FROM WishList WHERE albumID = :albumID");
			query.setParameter("albumID", id);
			List results = query.list();
			WishList wishList = (WishList)query.list().get(0);
			System.out.println("name : " + wishList.getAlbumName() + " albumid = " + wishList.getAlbumID() + " wishlistID = " + wishList.getWishListID());
//			WishList removeWish = (WishList)session.load(WishList.class, wishList.getWishListID());
			
			session.delete(wishList);
			transAct.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if(transAct != null) {
				transAct.rollback();
			}
			throw e;
		}
	}
	
	/**
	 * Method to edit user's album description
	 * @param localAlbum
	 * @return true if success else false
	 * @throws Exception
	 */
	public boolean editLocalAlbumDescription(LocalAlbum localAlbum) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			System.out.println(localAlbum.getAlbumID() + " ");
			session.update(localAlbum);
			transAct.commit();
			session.close();
			return true;
		} catch (Exception e) {
			if(transAct != null) {
				transAct.rollback();
			}
			throw e;
		}
	}
	
	/**
	 * Method to get an album description of a specific album
	 * @param id
	 * @return String Returns the whole description of the found album
	 * @throws Exception
	 */
	public String getLocalAlbumDescription(int id) {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			LocalAlbum localAlbum = session.get(LocalAlbum.class, id);
			transAct.commit();
			session.close();
			return localAlbum.getAlbumDescription();
		} catch (Exception e) {
			if(transAct != null) {
				transAct.rollback();
			}
			throw e;
		}
	}
	/**
	 * Method to get all the wishlist items
	 * @return List<WishList> Returns all the wishlist items
	 * @throws Exception
	 */
	public List<WishList> readWishList() {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			System.out.println("wishlist1");
			@SuppressWarnings("unchecked")
			List<WishList> result = (List<WishList>) session.createQuery("from WishList order by AlbumName").list();
			System.out.println("wishlist2");
			transAct.commit();
			return result;
		}catch (Exception e) {
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
