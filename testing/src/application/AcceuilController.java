package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

public class AcceuilController {
	@FXML
	private AnchorPane allpanel;
	@FXML
	private Button btn_acceuil;
	@FXML
	private Button btn_achat;

	// Event Listener on Button[#btn_achat].onAction
	@FXML
	public void handleButtonAction() throws IOException {			
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("Achat.fxml"));
		allpanel.getChildren().setAll(pane);				
	}


}
