package com.jcg.hibernate.maven;

import java.util.List;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import model.LocalSong;

public class RemoteDAO {
	static Session session;
	static SessionFactory sessionFactory;

	public RemoteDAO() {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
			System.exit(-1);
		}
	}
	/*
	 * Method used to create a new genre in the database. Will first iterate through
	 * all found genreNames, to ensure that it will not allow the creation of a
	 * genre that already exists. 
	 */
	public boolean createGenre(Genre genre) throws Exception {
		Genre[] genreSearch = readGenres();
		for (int i = 0; i < genreSearch.length; i++) {
			if (genreSearch[i].getGenreName().equals(genre.getGenreName())) {
				throw new Exception("This Genre already exists!");
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
	/*
	 * readGenre() will return a singular Genre-object from the remote database, based on the given genreID
	 */
	public Genre readGenre(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Genre genre = (Genre) session.get(Genre.class, id);
		System.out.println("Found this thing -> \"" + genre.getGenreName() + "\"");
		session.getTransaction().commit();
		session.close();
		return genre;
	}
	/*
	 * readGenres() will return a list of all Genres found within the database
	 */
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
	/*
	 * searchGenre() will return a single Genre-object based on a simple String input, used to search the database for the Genre
	 */
	public Genre searchGenre(String genreSearch) throws Exception {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Query query = session.createQuery("From Genre where genreName =:name");
			List<Genre> genreList = query.setParameter("name", genreSearch).list();
			System.out.println("The genre search result was -> "+genreList.get(0).getGenreName());
			if (genreList.size() == 0) {
				throw new Exception("Nothing found!");
			}
			
			transAct.commit();
			session.close();
			return genreList.get(0);

		} catch (Exception e) {
			if (transAct != null)
			transAct.rollback();
			throw e;
		}
	}
	/*
	 * editGenre() will update a given Genre based on its ID. This is used to find the Genre from the database, which will then be 
	 * updated based on a given Genre-object
	 */
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
	/*
	 * removeGenre() will remove a single Genre based on the given genreID
	 */
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
	/*
	 * Method used to create a new artist in the database. Will first iterate through
	 * all found artistNames, to ensure that it will not allow the creation of a
	 * artist that already exists. 
	 */
	public boolean createArtist(Artist artist) throws Exception {
		Artist[] artistSearch = readArtists();
		for (int i = 0; i < artistSearch.length; i++) {
			if (artistSearch[i].getArtistName().equals(artist.getArtistName())) {
				throw new Exception("This Artist already exists!");
			}
		}

		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			session.saveOrUpdate(artist);
			transAct.commit();
			return true;
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * readArtist() will return a singular Artist-object from the remote database, based on the given artistID
	 */
	public Artist readArtist(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Artist artist = (Artist) session.get(Artist.class, id);
		System.out.println("Found this thing -> " + artist.getArtistName());
		session.getTransaction().commit();
		session.close();
		return artist;
	}

	/*
	 * readArtists() will return a list of all artists found within the database
	 */
	public Artist[] readArtists() {
		System.out.println("Got a readartist request");
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
	/*
	 * searchArtist() will return a single Artist-object based on a simple String input, used to search the database for the Artist
	 */
	public Artist searchArtist(String artistSearch) throws Exception {
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Artist where ArtistName =:name");
			List<Artist> artistList = query.setParameter("name", artistSearch).list();
			
			if (artistList.size() == 0) {
				System.out.println("Nothing found!");
				throw new Exception("Nothing found!");
			}
			transAct.commit();
			session.close();
			
			return artistList.get(0);
		}catch(Exception e){
			System.out.println("exception why??");
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
		
	}
	/*
	 * editArtist() will update a given Artist based on its ID. This is used to find the Artist from the database, which will then be 
	 * updated based on a given Artist-object
	 */
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
	/*
	 * removeArtist() will remove a single Artist based on the given artistID
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
	/*
	 * Method used to create a new album in the database. Will first iterate through
	 * all found albumNames, to ensure that it will not allow the creation of a
	 * album that already exists. 
	 * Additionally, it will also add the genres and artists to a the album.
	 */
	public boolean createAlbum(Album album, List<Artist> artistList, List<Genre> genreList) throws Exception {
		Album[] albumSearch = readAlbums();	
		
		for(int i = 0; i < albumSearch.length; i++) {
			if(albumSearch[i].getAlbumName().equals(album.getAlbumName())) {
				throw new Exception("This Album already exists!");
			}
		}
		Transaction transAct = null;	
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			for(Artist artist : artistList) {
				
			Artist artist2 = (Artist)session.load(Artist.class, artist.getArtistID());
			artist2.addAlbum(album);
			session.update(artist2);
		}
			for(Genre genre : genreList) {
				Genre genre2 = (Genre)session.load(Genre.class, genre.getGenreID());
				genre2.addAlbum(album);
				session.update(genre2);	
			}
			transAct.commit();
			session.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	
	/*
	 * Method used to add an album to a single genre.
	 */
	public boolean addAlbumGenre(Album album, Artist artist, Genre genre) {
		Transaction transAct = null;	
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			genre.addAlbum(album);
			session.saveOrUpdate(genre);
			transAct.commit();
			System.out.println("After Commit");
			session.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	/*
	 * readAlbum() will return a singular Album-object from the remote database, based on the given albumID
	 */
	public Album readAlbum(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Album album = (Album)session.get(Album.class, id);
		System.out.println("Found this thing -> "+album.getAlbumName());
		session.getTransaction().commit();
		session.close();
		return album;	
	}
	/*
	 * readAlbums() will return a list of all albums found within the database
	 */
	public Album[] readAlbums() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Album> result = (List<Album>) session.createQuery("from Album").list();
			System.out.println("After album query ");
			transAct.commit();
			Album[] array = new Album[result.size()];
			
			 return (Album[])result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			return new Album[0];
		}
	}
	/*
	 * searchAlbum() will return a single Album-object based on a simple String input, used to search the database for the Album
	 */
	public Album searchAlbum(String albumSearch) throws Exception{
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Album where albumName like:name");
			List<Album> albumList = query.setParameter("name", albumSearch).list();
			
			transAct.commit();
			session.close();
			
			if (albumList.size() == 0) {
				throw new Exception("Nothing found!");
			}
			return albumList.get(0);
		}catch(Exception e){
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * editAlbum() will update a given Album based on its ID. This is used to find the Album from the database, which will then be 
	 * updated based on a given Album-object
	 */
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
	/*
	 * removeAlbum() will remove a single Album based on the given albumID
	 */
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
	/*
	 * Method used to create a new song in the database. Will first iterate through
	 * all found songNames, to ensure that it will not allow the creation of a
	 * song that already exists. 
	 */
	public boolean createSong(Song song) throws Exception {
		Song[] songSearch = readSongs();		
		for(int i = 0; i < songSearch.length; i++) {			
			if(songSearch[i].getSongName().equals(song.getSongName())) {
				throw new Exception("This Song already exists!");
			}
		}
		
		Transaction transAct = null;	
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			session.saveOrUpdate(song);			
			session.save(song);
			transAct.commit();
			return true;
		}catch(Exception e) {
			if(transAct != null) 
				transAct.rollback();
			throw e;			
		}
	}
	/*
	 * readSong() will return a singular Song-object from the remote database, based on the given songID
	 */
	public Song readSong(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Song song = (Song)session.get(Song.class, id);
		System.out.println("Found this thing -> "+song.getSongName());
		session.getTransaction().commit();
		session.close();
		return song;	
	}
	
	/*
	 * readSongs() will return a list of all albums found within the database
	 */
	public Song[] readSongs() {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Song> result = (List<Song>) session.createQuery("from Song order by songName").list();

			transAct.commit();
			Song[] array = new Song[result.size()];
			return (Song[]) result.toArray(array);
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * searchSong() will return a single Song-object based on a simple String input, used to search the database for the Song
	 */
	public Song searchSong(String songSearch) throws Exception{
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();	
			Query query = session.createQuery("From Song where SongName like:name");
			List<Song> songList = query.setParameter("name", songSearch).list();
			
			transAct.commit();
			session.close();
			
			if (songList.size() == 0) {
				throw new Exception("Nothing found!");
			}
			return songList.get(0);
		}catch(Exception e){
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * editSong() will update a given Song based on its ID. This is used to find the Song from the database, which will then be 
	 * updated based on a given Song-object
	 */
	public boolean editSong(Song songEdit, int id) {
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
	/*
	 * removeSong() will remove a single Song based on the given songID
	 */
	public boolean removeSong(int id) {
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			Song removeSong= (Song) session.load(Song.class, id);
			session.delete(removeSong);
			transAct.commit();
			return true;

		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * getSearchable() will return a list of every single name within the database.
	 */
	public List<String> getSearchable(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select ArtistName from Artist union select AlbumName from Album union select GenreName from Genre union select SongName from Song";
			SQLQuery query = session.createSQLQuery(sql);
			List<String> results = query.list();
			transAct.commit();
			session.close();
			return results;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}		
	}	
	/*
	 * existingGenres() will return a list of every genreName found within the database
	 */
	public List<String> existingGenres(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select GenreName from Genre";
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
	 * existingArtists() will return a list of every artistName found within the database
	 */
	public List<String> existingArtists(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select ArtistName from Artist";
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
	 * existingAlbums() will return a list of every albumName found within the database
	 */
	public List<String> existingAlbums(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select AlbumName from Album";
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
	 * existingSongs() will return a list of every songName found within the database
	 */
	public List<String> existingSongs(){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			String sql = "select SongName from Song";
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
	 * Method takes in a given genre's ID, and then opens a session with it. During the session, an album-list can be created based on the instance
	 * After loading the list, the session is closed and the method returns a list of Albums based on the genre
	 */
	public List<Album> genreAlbums(int genreID){
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();
			
			Genre genre = (Genre) session.load(Genre.class, genreID);
			List<Album> array = genre.getGenreAlbums();
			transAct.commit();
			session.close();
			return array;
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * Method takes in a given artist's ID, and then opens a session with it. During the session, an album-list can be created based on the instance
	 * After loading the list, the session is closed and the method returns a list of Albums based on the artist
	 */
	public List<Album> artistAlbums(int artistID){
		Transaction transAct = null;
		try (Session session = sessionFactory.openSession()) {
			transAct = session.beginTransaction();

			Artist artist = (Artist) session.load(Artist.class, artistID);
			List<Album> array = artist.getArtistAlbums();
			transAct.commit();
			session.close();
			return array;
		} catch (Exception e) {
			if (transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * Method takes in an album's ID and then opens a session with it. During the session, a song-list can be created based on the instance
	 * After loading the list, the session is closed and the method returns a list of Songs based on the album.
	 */
	public List<Song> albumSongs(int albumID){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			Album album = (Album) session.load(Album.class, albumID);
			List<Song> array = album.getAlbumSongs();
			transAct.commit();
			session.close();
			return array;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * Method takes in an album's ID and then opens a session using it. A list of artists is created based on the Album, 
	 * and the method thus returns a list of Artists related to the given album
	 */
	public List<Artist> albumArtistList(int albumID){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			Album album = (Album) session.load(Album.class, albumID);
			List<Artist> array = album.getAlbumArtists();
			transAct.commit();
			session.close();
			return array;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	/*
	 * Method takes in an album's ID and then opens a session using it. A list of genres is created based on the Album,
	 * and the method thus returns a list of Genres related to the given album
	 */
	public List<Genre> albumGenreList(int albumID){
		Transaction transAct = null;
		try(Session session = sessionFactory.openSession()){
			transAct = session.beginTransaction();
			Album album = (Album) session.load(Album.class, albumID);
			List<Genre> array = album.getAlbumGenres();
			transAct.commit();
			session.close();
			return array;
		}catch(Exception e) {
			if(transAct != null)
				transAct.rollback();
			throw e;
		}
	}
	
	public void finalize() {
		try {
			if (sessionFactory != null)
				System.out.println("Sessiotehdas suljettu");
				sessionFactory.close();
		} catch (Exception e) {
			System.err.println("Session factory couldn't be closed: " + e.getMessage());
		}
	}

}
