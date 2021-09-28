package com.jcg.hibernate.maven;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.mapping.Collection;

@Entity
@Table(name = "Genre")
public class Genre implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreID", updatable = false, nullable = false)
	private int genreID;
	
	@Column(name = "GenreNimi")
	private String genreName;
	
	public int getGenreID() {
		return genreID;
	}
	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	
	//Everything from down here is highly experimental
	@ManyToMany(
			targetEntity=com.jcg.hibernate.maven.Album.class,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE}
			)
	@JoinTable(
			name="koostuu",
			joinColumns={@JoinColumn(name="GenreID")},
			inverseJoinColumns= {@JoinColumn(name="AlbumiID")}
			)
	public Collection getAlbums() {
		return albums;
	}
	
	
}
