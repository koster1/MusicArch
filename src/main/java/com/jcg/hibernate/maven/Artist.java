package com.jcg.hibernate.maven;

import java.util.ArrayList;
import java.util.List;

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
 * @author Alex
 *
 */

@Entity
@Table(name = "Artist")
public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ArtistID", updatable = false, nullable = false)
	private int artistID;

	@Column(name = "ArtistName")
	private String artistName;

	@Column(name = "Biography")
	private String artistBio;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
			@JoinTable(name = "AlbumArtists", joinColumns = { 
			@JoinColumn(name = "ArtistID") }, inverseJoinColumns = {
			@JoinColumn(name = "AlbumID") })

	private List<Album> artistAlbums;

	/**
	 * Returns a list of Albums related to this Artist
	 * @return artistAlbums
	 */
	public List<Album> getArtistAlbums() {
		return this.artistAlbums;
	}
	/**
	 * Sets a list of Albums for a given Artist
	 * @param artistAlbums
	 */
	public void setArtistAlbums(List<Album> artistAlbums) {
		this.artistAlbums = artistAlbums;
	}
	/**
	 * Adds an Album into this Artist
	 * @param album
	 */
	public void addAlbum(Album album) {
		if (artistAlbums == null) {
			artistAlbums = new ArrayList<>();
		}
		artistAlbums.add(album);
	}
	/**
	 * Returns an Artist's ID
	 * @return artistID
	 */
	public int getArtistID() {
		return artistID;
	}
	/**
	 * Sets an Artist's ID with a given parameter artistID
	 * @param artistID
	 */
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	/**
	 * Returns an Artist's name
	 * @return artistName
	 */
	public String getArtistName() {
		return artistName;
	}
	/**
	 * Sets a name for an Artist
	 * @param artistName
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	/**
	 * Returns an artist's biography
	 * @return artistBio
	 */
	public String getArtistBio() {
		return artistBio;
	}
	/**
	 * Sets a biography for an Artist
	 * @param artistBio
	 */
	public void setArtistBio(String artistBio) {
		this.artistBio = artistBio;
	}
}
