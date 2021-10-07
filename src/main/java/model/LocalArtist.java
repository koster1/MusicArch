package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Artisti")
public class LocalArtist {
	
	@Id
    @Column(name = "ArtistiID", updatable = false, nullable = false)
	private int artistID;
	
	@Column(name = "ArtistiNimi")
	private String artistName;
	
	@Column(name = "Biografia")
	private String artistBio;
	
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
