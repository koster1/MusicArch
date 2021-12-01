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


@Entity
@Table(name = "Song")
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SongID", updatable = false, nullable = false)
	private int songID;
	
	@Column(name = "SongName")
	private String songName;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="AlbumSongs",
			joinColumns={@JoinColumn(name="SongID")},
			inverseJoinColumns={@JoinColumn(name="AlbumID")}
			)
	private List<Album> songAlbums;
	
	
	public int getSongID() {
		return songID;
	}
	public void setSongID(int songID) {
		this.songID = songID;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public void setSongAlbums(List<Album> songAlbums) {
		this.songAlbums = songAlbums;
	}
	public List<Album> getSongAlbums() {
		return songAlbums;
	}
	public void addAlbum(Album album) {
		if(songAlbums == null) {
			songAlbums = new ArrayList<>();
		}
		songAlbums.add(album);
	}
}
