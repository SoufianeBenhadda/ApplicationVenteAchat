package application;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
//import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class FactureController {
	
	@FXML
    private TextField tffacture;
	@FXML
	private TextField tfbalance;
   
	
 
    @FXML
    private void searchClientFacture() throws SQLException, JRException {
    	
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("balance", Double.parseDouble(tfbalance.getText()));
    	
       	Connection conn = SqlConnection.getConnection();    
       	String query = "select * from vente where client = '"+ tffacture.getText()+"' and isFacture = false";
    	JRDesignQuery update = new JRDesignQuery();
    	update.setText(query);	    	 	
    	JasperDesign jDesign = JRXmlLoader.load("D:\\git_repository\\ApplicationVenteAchat\\testing\\src\\application\\Invoice.jrxml");
    	jDesign.setQuery(update);
    	JasperReport jReport = JasperCompileManager.compileReport(jDesign);
    	JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters,conn);
    	JasperViewer.viewReport(jPrint, false);
    
    	//	String query2 = "UPDATE vente set isFacture = true WHERE client = '"+ tffacture.getText()+"'";
    	//SqlConnection.executeQuery(query2);
     	
    	}

  
    

    

    
    
}
