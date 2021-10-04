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
@Table(name = "Artisti")
public class Artist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistiID", updatable = false, nullable = false)
	private int artistID;
	
	@Column(name = "ArtistiNimi")
	private String artistName;
	
	@Column(name = "Biografia")
	private String artistBio;
	
	@ManyToMany(fetch=FetchType.EAGER,
			cascade={CascadeType.ALL})
	@JoinTable(
			name="tekee",
			joinColumns={@JoinColumn(name="ArtistiID")},
			inverseJoinColumns={@JoinColumn(name="AlbumiID")}
			)
	private List<Album> artistAlbums;
	
	public List<Album> getArtistAlbums(){
		return this.artistAlbums;
	}
	public void setArtistAlbums(List<Album> artistAlbums) {
		this.artistAlbums = artistAlbums;
	}
	public void addAlbum(Album album) {
		if(artistAlbums == null) {
			artistAlbums = new ArrayList<>();
		}
		artistAlbums.add(album);
	}
	public int getArtistID() {
		return artistID;
	}
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistBio() {
		return artistBio;
	}
	public void setArtistBio(String artistBio) {
		this.artistBio = artistBio;
	}
}
