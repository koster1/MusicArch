package com.jcg.hibernate.maven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Kappale")
public class Kappale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KappaleID", updatable = false, nullable = false)
	private int songID;
	
	@Column(name = "KappaleNimi")
	private String songName;
	
	@Column(name = "KappaleenKesto")
	private int songDuration;
	
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
