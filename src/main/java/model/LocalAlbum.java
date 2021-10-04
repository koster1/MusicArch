package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import model.LocalGenre;

@Entity
@Table(name = ("Albumi"))
public class LocalAlbum {

	@Id
	@Column(name = "AlbumiID", updatable = false, nullable = false)
	private int albumID;
	
	@Column(name = "AlbumiNimi")
	private String albumName;
	
	@Column(name = "Julkaisuvuosi")
	private int albumYear;
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade={CascadeType.ALL})
	@JoinTable(
			name="koostuu",
			joinColumns={@JoinColumn(name="AlbumiID")},
			inverseJoinColumns={@JoinColumn(name="GenreID")}
			)
	private List<LocalGenre> albumGenres;
	
	public List<LocalGenre> getAlbumGenres(){
		return albumGenres;
	}
	
	public void setAlbumGenres(List<LocalGenre> albumGenres) {
		this.albumGenres = albumGenres;
	}
	public void addGenre(LocalGenre genre) {
		if(albumGenres == null) {
			albumGenres = new ArrayList<>();
		}
		albumGenres.add(genre);
	}
	
	public LocalAlbum() {}
	
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
