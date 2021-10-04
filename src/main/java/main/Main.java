package main;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.Album;
import com.jcg.hibernate.maven.Artist;
import com.jcg.hibernate.maven.Genre;
import com.jcg.hibernate.maven.LocalDAO;

import controller.Controller;
import model.DeletionManagement;
import model.DeliveryObject;
import model.InputManagement;
import model.LocalArtist;
import model.LocalGenre;
import model.SearchManagement;
import view.*;


public class Main {
	public static void main(String args[]) {
		System.out.println("HELLO :)");
		View.main(args);

	/*	
		String[] genreList = new String[2];
		genreList[0] = "RockGenre";
		genreList[1] = "PopGenre";
		
		String[] artistList = new String[2];
		artistList[0] = "RockArtist";
		artistList[1] = "PopArtist";
		
		
		Controller controller = new Controller();
		controller.createGenre(genreList[0]);
		controller.createGenre(genreList[1]);		
	
		controller.createArtist(artistList[0], "A really cool rock artist!");
		controller.createArtist(artistList[1], "A moderately okay pop artist.");

		controller.createAlbum("A Rock Album", 666, genreList, artistList);
		
		*/


}
}
