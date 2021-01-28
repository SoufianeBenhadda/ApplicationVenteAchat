package application;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FactureController {
	
	@FXML
    private TextField tffacture;
   
    @FXML
    private BorderPane facturePane;
	
    @FXML
    private void searchClientFacture() throws SQLException, JRException {
    	
    	Connection conn = SqlConnection.getConnection();    	
    	
    	String query = "select * from vente where client = '"+ tffacture.getText()+"' and isFacture = false";
    	JRDesignQuery update = new JRDesignQuery();
    	update.setText(query);
    	 	
    	JasperDesign jDesign = JRXmlLoader.load("D:\\git_repository\\ApplicationVenteAchat\\testing\\src\\application\\Invoice.jrxml");
    	jDesign.setQuery(update);
    	JasperReport jReport = JasperCompileManager.compileReport(jDesign);
    	JasperPrint jPrint = JasperFillManager.fillReport(jReport, null,conn);
    	JasperViewer.viewReport(jPrint);
    	
    	String query2 = "UPDATE vente set isFacture = true WHERE client = '"+ tffacture.getText()+"'";
    	SqlConnection.executeQuery(query2);
    
  
    	}
    

    
    
}
