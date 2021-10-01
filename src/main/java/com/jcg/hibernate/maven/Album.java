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

import org.hibernate.mapping.Collection;


@Entity
@Table(name = "Albumi")
public class Album {
	
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
	
}
