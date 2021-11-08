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
	private Text helptext;
	
	
	@FXML
	protected void initialize() throws FileNotFoundException {
		
//		test
		
//		helptext.setText("muutettu teksti");
//		if(helptext == null) {
//			System.out.println("Null");
//		} else {
		try {
			File file = new File("MusicArch/src/main/resources/helpfin");
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();					
//				helptext.setText(data);
				System.out.println(data);
			}
			myReader.close();
		} catch(FileNotFoundException e) {
		    throw new FileNotFoundException("HelpPage not found");
		}
	}
//	}
}

