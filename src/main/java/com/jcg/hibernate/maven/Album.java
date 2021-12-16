package com.jcg.hibernate.maven;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * Artist class defines the parameters used by Artists in Hibernate's ORM.
 * @author Alex, Jani
 *
 */

@Entity
@Table(name = "Album")
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AlbumID", updatable = false, nullable = false)
	private int albumID;
	
	@Column(name = "AlbumName")
	private String albumName;
	
	@Column(name = "AlbumYear")
	private int albumYear;

	@ManyToMany(fetch=FetchType.LAZY,
			cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="AlbumGenres",
			joinColumns={@JoinColumn(name="AlbumID")},
			inverseJoinColumns={@JoinColumn(name="GenreID")}
			)
	private Set<Genre> albumGenres = new HashSet<Genre>();
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="AlbumArtists",
			joinColumns={@JoinColumn(name="AlbumID")},
			inverseJoinColumns={@JoinColumn(name="ArtistID")})
	private Set<Artist> albumArtists = new HashSet<Artist>();
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE, 
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="AlbumSongs",
			joinColumns= {@JoinColumn(name="AlbumID")},
			inverseJoinColumns= {@JoinColumn(name="SongID")})
	private Set<Song> albumSongs = new HashSet<Song>();
	
		
	/**
	 * getAlbumArtists() method will return a set of each Artist related to this Album
	 * @return Set<Artist> 
	 */
	public Set<Artist> getAlbumArtists(){
		System.out.println("album artists " + this.albumArtists.toString());
		
		return this.albumArtists;
	}
	/**
	 * Gives set of Artists to a given Album
	 * @param albumArtists 
	 */
	public void setAlbumArtists(Set<Artist> albumArtists) {
		this.albumArtists = albumArtists;
	}
	/**
	 * Adds an individual Artist to an Album
	 * @param artist
	 */
	public void addArtist(Artist artist) {
		if(albumArtists == null) {
			albumArtists = new HashSet<Artist>();
		}
		albumArtists.add(artist);
	}	
	/**
	 * Removes an Artist from an Album
	 * @param artist
	 */
	public void removeArtist(Artist artist) {
		albumArtists.remove(artist);
	}
	/**
	 * Removes all Artists
	 */
	public void removeArtists() {
		albumArtists.clear();
	}
	/**
	 * Returns every Genre in a given Album
	 * @return Set<Genre>
	 */
	public Set<Genre> getAlbumGenres(){
		// Do not remove the print below or you will get:
		//"failed to lazily initialize a collection of role: com.jcg.hibernate.maven.Album.albumGenres, could not initialize proxy - no Session" in the AlbumPage
		System.out.println("album genres " +this.albumGenres.toString());
		return this.albumGenres;
	}
	/**
	 * Gives a set of Genres to a given Album
	 * @param albumGenres
	 */
	public void setAlbumGenres(Set<Genre> albumGenres) {
		this.albumGenres = albumGenres;
	}
	/**
	 * Adds a Genre to a given Album
	 * @param genre
	 */
	public void addGenre(Genre genre) {
		System.out.println("genre = " + genre.getGenreName());
		if(albumGenres == null) {
			albumGenres = new HashSet<Genre>();
		}
		albumGenres.add(genre);
	}
	/**
	 * Removes a given Genre from an Album
	 * @param genre
	 */
	public void removeGenre(Genre genre) {
		albumGenres.remove(genre);
	}
	/**
	 * Removes all Genres from an Album
	 */
	public void removeGenres() {
		albumGenres.clear();
	}
	/**
	 * Gives a set of Songs to an Album
	 * @param albumSongs
	 */
	public void setAlbumSongs(Set<Song> albumSongs) {
		this.albumSongs = albumSongs;
	}
	/**
	 * Adds a song to a given Album
	 * @param song
	 */
	public void addSong(Song song) {
		if(albumSongs == null) {
			albumSongs = new HashSet<>();
		}
		albumSongs.add(song);
	}
	/**
	 * Removes a Song from an Album
	 * @param song
	 */
	public void removeSong(Song song) {
		albumSongs.remove(song);
	}
	/**
	 * Removes all Songs from an Album
	 */
	public void removeSongs() {
		albumSongs.clear();
	}
	/**
	 * Returns a set of all Songs related to an Album
	 * @return albumSong
	 */
	public Set<Song> getAlbumSongs() {
		System.out.println("album songs " + this.albumSongs);
		return albumSongs;
	}
	public Album() {}
	
	/**
	 * Returns a given Album's ID from the database
	 * @return albumID
	 */
	public int getAlbumID() {
		return albumID;
	}
	/**
	 * Sets an AlbumID with a given integer value
	 * @param albumID
	 */
	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}
	/**
	 * Returns a given Album's name
	 * @return albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * Sets a name for a given Album
	 * @param albumName
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * Returns an Album's release year
	 * @return albumYear
	 */
	public int getAlbumYear() {
		return albumYear;
	}
	/**
	 * Sets a release year for an Album
	 * @param albumYear
	 */
	public void setAlbumYear(int albumYear) {
		this.albumYear = albumYear;
	}
	/**
	 * Clears the Album's contents
	 */
	public void clearAlbum() {
		albumGenres.clear();
		albumArtists.clear();
//		albumSongs.clear();
	}
	
	
}
