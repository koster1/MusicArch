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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.mapping.Collection;


@Entity
@Table(name = "Album")
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AlbumID", updatable = false, nullable = false)
	private int albumID;
	
	@Column(name = "AlbumName")
	private String albumName;
	
	@Column(name = "AlbumYear")
	private int albumYear;

	@ManyToMany(fetch=FetchType.LAZY,
			cascade={CascadeType.ALL})
	@JoinTable(
			name="AlbumGenres",
			joinColumns={@JoinColumn(name="AlbumID")},
			inverseJoinColumns={@JoinColumn(name="GenreID")}
			)
	private List<Genre> albumGenres;
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade={CascadeType.ALL})
	@JoinTable(
			name="AlbumArtists",
			joinColumns={@JoinColumn(name="AlbumID")},
			inverseJoinColumns={@JoinColumn(name="ArtistID")})
	private List<Artist> albumArtists;
	
	@ManyToMany(fetch=FetchType.LAZY,
			cascade= {CascadeType.ALL})
	@JoinTable(
			name="AlbumSongs",
			joinColumns= {@JoinColumn(name="AlbumID")},
			inverseJoinColumns= {@JoinColumn(name="SongID")})
	private List<Song> albumSongs;
	
	public List<Artist> getAlbumArtists(){
		return albumArtists;
	}
	public void setAlbumArtists(List<Artist> albumArtists) {
		this.albumArtists = albumArtists;
	}
	public void addArtist(Artist artist) {
		if(albumArtists == null) {
			albumArtists = new ArrayList<>();
		}
		albumArtists.add(artist);
	}	
	public List<Genre> getAlbumGenres(){
		return albumGenres;
	}
	public void setAlbumGenres(List<Genre> albumGenres) {
		this.albumGenres = albumGenres;
	}
	public void addGenre(Genre genre) {
		System.out.println("genre = " + genre.getGenreName());
		if(albumGenres == null) {
			albumGenres = new ArrayList<>();
		}
		albumGenres.add(genre);
	}
	public void setAlbumSongs(List<Song> albumSongs) {
		this.albumSongs = albumSongs;
	}
	public void addSong(Song song) {
		if(albumSongs == null) {
			albumSongs = new ArrayList<>();
		}
		albumSongs.add(song);
	}
	public List<Song> getAlbumSongs() {
		return albumSongs;
	}
	public Album() {}
	
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
