package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class GUIController {
	
	//TestiLomake
    @FXML private TextField GenreAddTxtField;
    @FXML private Label GenreAddLabel;
    @FXML private Button SendGenre;
    @FXML private Label GenreAddTitle;
    String Genretxt;

    @FXML void SendGenreClicked(ActionEvent event) {    	
    	Genretxt = GenreAddTxtField.getText();
}



}
