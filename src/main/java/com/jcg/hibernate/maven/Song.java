package com.jcg.hibernate.maven;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
//	@OneToMany(mappedBy="Kappale")
//	private List<Includes> includes;
//	
//	public List<Includes> getSongAlbums(){
//		return includes;
//	}
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

}
