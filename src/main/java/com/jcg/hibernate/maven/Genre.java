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
 * Genre class defines the parameters used by Genres in Hibernate's ORM.
 * @author Alex
 *
 */

@Entity
@Table(name = "Genre")
public class Genre  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GenreID", updatable = false, nullable = false)
	private int genreID;

	@Column(name = "GenreName")
	private String genreName;
	
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name = "AlbumGenres", joinColumns = { 
			@JoinColumn(name = "GenreID") }, inverseJoinColumns = {
			@JoinColumn(name = "AlbumID") })
	private List<Album> genreAlbums;
	/**
	 * Returns a Genre's ID
	 * @return genreID
	 */
	public int getGenreID() {
		return genreID;
	}
	/**
	 * Sets an ID for a given Genre
	 * @param genreID
	 */
	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}
	/**
	 * Returns a given Genre's name
	 * @return genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * Sets a name for a Genre
	 * @param genreName
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	/**
	 * Returns a list of Albums related to a given Genre
	 * @return genreAlbums
	 */
	public List<Album> getGenreAlbums() {
		return genreAlbums;
	}
	/**
	 * Sets a list of Albums as this Genre's Albums
	 * @param genreAlbums
	 */
	public void setGenreAlbums(List<Album> genreAlbums) {
		this.genreAlbums = genreAlbums;
	}
	/**
	 * Adds an Album to this Genre
	 * @param album
	 */
	public void addAlbum(Album album) {
		if (genreAlbums == null) {
			genreAlbums = new ArrayList<>();
		}
		genreAlbums.add(album);
	}
}
