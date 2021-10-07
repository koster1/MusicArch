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
@Table(name = "Kappale")
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KappaleID", updatable = false, nullable = false)
	private int songID;
	
	@Column(name = "KappaleNimi")
	private String songName;
	
	@Column(name = "KappaleenKesto")
	private int songDuration;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade={CascadeType.ALL})
	@JoinTable(
			name="koostuu",
			joinColumns={@JoinColumn(name="KappelID")},
			inverseJoinColumns={@JoinColumn(name="AlbumiID")}
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
	public int getSongDuration() {
		return songDuration;
	}
	public void setSongDuration(int songDuration) {
		this.songDuration = songDuration;
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
