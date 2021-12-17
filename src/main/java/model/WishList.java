package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hibernate class for local database
 * @author Jani
 * */
@Entity
@Table(name = "WishList")
public class WishList {

	@Id
	@Column(name = "wishListID", updatable = false, nullable = false)
	private int wishListID;
	
	@Column(name = "albumID")
	private int albumID;
	
	@Column(name = "AlbumName")
	private String albumName;
	
	
	@Column(name = "AlbumYear")
	private int albumYear;
	
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName2) {
		this.albumName = albumName2;
	}

	public int getAlbumYear() {
		return albumYear;
	}

	public void setAlbumYear(int albumYear) {
		this.albumYear = albumYear;
	}
	
	public int getWishListID() {
		return this.wishListID;
	}
	
	public void setWishListID(int id) {
		this.wishListID = id;
	}
	
	public int getAlbumID() {
		return this.albumID;
	}
	
	public void setAlbumID(int id) {
		this.albumID = id;
	}
	
	
	
}
