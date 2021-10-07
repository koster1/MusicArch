package com.jcg.hibernate.maven;


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
public class Includes {
	
	
	@Column(name = "Järjestysnumero", updatable = false, nullable = false)
	private int songPosition;
	
	@JoinColumn(name = "AlbumiID", referencedColumnName = "AlbumiID", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Album album;
	
	@JoinColumn(name = "KappaleID", referencedColumnName = "KappaleID", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Song song;
	
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
	public Song getSong() {
		return song;
	}
}
