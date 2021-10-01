package com.jcg.hibernate.maven;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "Sisältyy")
public class Sisaltyy implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Järjestysnumero", updatable = false, nullable = false)
	private int songPosition;
	
	@JoinColumn(name = "AlbumiID", referencedColumnName = "AlbumiID", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Album album;
	

	@JoinColumn(name = "KappeleID", referencedColumnName = "KappaleID", insertable = false, updatable = false)
	@ManyToOne()
	private Song song;
	
}