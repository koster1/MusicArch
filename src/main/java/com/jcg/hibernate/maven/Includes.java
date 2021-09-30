package com.jcg.hibernate.maven;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Table(name = "Sisältyy")
public class Includes implements Serializable {
	
	int songPosition;
	
	@ManyToOne
	@MapsId("AlbumiID")
	@JoinColumn(name="AlbumiID")
	Album album;
	
	@ManyToOne
	@MapsId("KappaleID")
	@JoinColumn(name="songID")
	Song song;
	
	public void setSongPosition(int songPosition) {
		this.songPosition = songPosition;
	}
	public int getSongPosition() {
		return songPosition;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public Album getAlbum() {
		return album;
	}
	public void setSong(Song song) {
		this.song = song;
	}
}
