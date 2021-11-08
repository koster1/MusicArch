package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class HelpController {
	
	//This controller will be needed later
	@FXML
	private AnchorPane helpPage;
	
	@FXML
	private Label helptext;
	
	
	@FXML
	 protected void initialize() { 
	}
	 
	public static void main(String[] args) throws IOException {
		
		try {
			File file = new File("/src/main/resources/helpfin.txt");
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();					
				System.out.println(data);
			}
			myReader.close();
		} catch(FileNotFoundException e) {
		    throw new FileNotFoundException("HelpPage not found");
		}
		
	}
	
}

