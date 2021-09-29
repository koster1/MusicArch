package com.jcg.hibernate.maven;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.mapping.Collection;

@Entity
@Table(name = "Albumi")
public class Album {
	// @OneToMany()
	// @MappedBy("Song") or @MappedBy("Album") if in Song class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AlbumiID", updatable = false, nullable = false)
	private int albumID;
	
	@Column(name = "AlbumiNimi")
	private String albumName;
	
	@Column(name = "Julkaisuvuosi")
	private int albumYear;
	
	public int getAlbumID() {
		return albumID;
	}
	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public int getAlbumYear() {
		return albumYear;
	}
	public void setAlbumYear(int albumYear) {
		this.albumYear = albumYear;
	}
	
	@ManyToMany(
			cascade= {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy="albums",
			targetEntity=Genre.class
			)
	public Collection getGenres() {
		return genres;
	}
	
}
