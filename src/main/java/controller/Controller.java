package controller;
import java.util.*;
import model.*;

/**
 * 
 */
public class Controller {

    private InputManagement inputManagement;
    private SearchManagement searchManagement;
    private DeletionManagement deleteManagement;
    private DeliveryObject deliveryObject;
    
    
    //These are for testing purposes!! Controller gets this info from View!
    private String givenGenre = "Black Metal";
    private String givenArtist = "Johnny Cash";
    private String givenAlbumName = "Black Sabbath";
    private String givenAlbumGenre = "Heavy Metal";
    private ArrayList<String> givenSongList = new ArrayList<String>();
    private ArrayList<Integer> givenSongDurations = new ArrayList<Integer>();    
    //These are for testing purposes!! Controller gets this info from View!  


    /**
     * Default constructor
     */
    public Controller() {
    	this.deliveryObject = new DeliveryObject();
    	this.inputManagement = new InputManagement();
    	this.searchManagement = new SearchManagement();
    	this.deleteManagement = new DeletionManagement();
    }
    
    //Does this method get EVERYTHING that we can throw in? Type, genre, artist, album, songs? That doesn't make sense, it becomes way too bloated.
    //Here we create the delivery object, and we essentially fill it with everything we can from the UI.
    //Is it then essentially... like, 
    //deliveryObject.setInputType(GUI.getInputType)?
    //deliveryObject.setGenreName(GUI.getGenreName)?
    //deliveryObject.setArtistName(GUI.getArtistName)?
    //Which means, at least for simulating it here, we can just assume the information comes from the GUI, where the controller fetches it, essentially?
    //Note, this is going to get messy and likely become a very bloated method. Maybe do a switch case? Checks which type we are adding, then make an "inputGenre/Artist/Album" type methods?
    public void setInputManagement() throws Exception {
    	
    	System.out.println("Testing?");
    	deliveryObject.setInputType("GENRE");
    	deliveryObject.setGenreName(givenGenre);
    	System.out.println("The input type is -> "+deliveryObject.getInputType()+" And the name of the item is: "+deliveryObject.getGenreName());
    	inputManagement.checkType(deliveryObject);

    	//Deleting by genreID at least works.
    	//deleteManagement.removeGenre(1);
    	
    	System.out.println("And now moving on to search testing!");
    	
    	//Something goes wrong with the searches
    	System.out.println("The search results for the search: "+givenGenre);
    	searchManagement.searchAll(givenGenre);
    	

    	
    	deliveryObject.clearDelivery();
    	System.out.println("After clearing the deliveryObject, it's type is -> "+deliveryObject.getInputType());

    	
    /*
    	//Testing purposes (These should actually be moved into an actual test class, to be honest)
    	givenSongList.add("Black Sabbath");
    	givenSongList.add("The Wizard");
    	givenSongList.add("Behind the Wall of Sleep");
    	givenSongList.add("N.I.B.");
    	givenSongList.add("Evil Woman (Crow Cover");
    	givenSongList.add("Sleeping Village");
    	givenSongList.add("Warning (The Aynsley Dunbar Retaliation Cover)");
    	//Testing purposes (These should actually be moved into an actual test class, to be honest)
    	givenSongDurations.add(380);
    	givenSongDurations.add(264);
    	givenSongDurations.add(217);
    	givenSongDurations.add(368);
    	givenSongDurations.add(205);
    	givenSongDurations.add(226);
    	givenSongDurations.add(628);
    	//Testing purposes (These should actually be moved into an actual test class, to be honest)
    */
    	

    	
    	//Just in case, clear the deliveryObject for now, still.
    	
    	/*
    	ArrayList<String> genreArray = new ArrayList<String>();
    	genreArray.add(genreNimi);
    	this.deliveryObject.setGenreName(genreArray);    	
    
    	deliveryObject.setInputType(inputType);
    	System.out.println(deliveryObject.getInputType());

    
    	inputManagement.checkType(deliveryObject);
    	
    	
    	searchManagement.searchCars("car");
    	
    	searchManagement.searchAll("Testing");
    	*/
    }

}