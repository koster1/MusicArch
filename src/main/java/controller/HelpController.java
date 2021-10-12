package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import view.View;



public class HelpController {
	
	
	
	private View view;
	
	@FXML
	private AnchorPane helpPage;
	
	@FXML
	private Text helptext;
	
	@FXML
	protected void initialize() {
//		
//		helptext.setText("");
//		
//		try( FileReader tiedosto = new FileReader ("./model/help.txt");
//			BufferedReader puskuroituVirta = new BufferedReader(tiedosto);)
//		{
//			String line = puskuroituVirta.readLine();
//			System.out.println("tähän tulee line " + line);
//
//		}
//	catch (IOException e) {
//		System.err.println("Poikkeus tiedoston käsittelyssä");
//	}
	}
}

