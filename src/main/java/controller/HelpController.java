package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import view.View;


public class HelpController {
	
	private Controller controller;

	
	@FXML
	private AnchorPane helpPage;
	
	@FXML
	private Text HelpText;


	public HelpController(Controller controller) {
		this.controller = controller;
	}

	
	
	@FXML
	protected void initialize() throws FileNotFoundException {


	char[] buffer = null;
	File file = new File("src/main/resources/helpfin.txt");
	try(FileReader reader = new FileReader(file)){
		buffer = new char[(int) file.length()];
		reader.read(buffer);
		HelpText.setText(new String(buffer));
	}catch(IOException e) {
		System.out.println(e.getMessage());
	}
	}

}


