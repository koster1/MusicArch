package controller;
import java.util.*;
import model.*;

/**
 * 
 */
public class Controller {

    private String searchInput;
    private InputManagement inputManagement;
    private DeliveryObject deliveryObject;


    /**
     * Default constructor
     */
    public Controller() {
    	this.deliveryObject = new DeliveryObject();
    	this.inputManagement = new InputManagement();
    }
    
    public void setInputManagement(String genreNimi) {
    	ArrayList<String> genreArray = new ArrayList<String>();
    	genreArray.add(genreNimi);
    	this.deliveryObject.setGenreName(genreArray);
    }


}