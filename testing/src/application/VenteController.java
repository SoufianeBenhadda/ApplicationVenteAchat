package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class VenteController {
	 @FXML
	    private AnchorPane venteAnchor;
	 @FXML
	    private TableView<Vente> tvvente;

	 @FXML
	    private TableColumn<Vente, Integer> idventecol;

	    @FXML
	    private TableColumn<Vente, String> produitcol;

	    @FXML
	    private TableColumn<Vente, String> clientcol;

	    @FXML
	    private TableColumn<Vente, Integer> quantitecol;

	    @FXML
	    private TableColumn<Vente, Float> prixdeventecol;

	    @FXML
	    private TableColumn<Vente, Date> datecol;

	    @FXML
	    private TableColumn<Vente, Double> totalcol;

	    @FXML
	    private TableColumn<Vente, Float> benificecol;

	    @FXML
	    private TextField tfidproduit;
	    @FXML
	    private TextField tfprixachat;

	    @FXML
	    private TextField tfproduit;

	    @FXML
	    private TextField tfquantite;

	    @FXML
	    private TextField tfprixdevente;

	    @FXML
	    private TextField tfclient;
	    @FXML
	    private TextField tfquantiteproduit;
	    @FXML
	    private DatePicker tfventejour;
	    


	    
	    @FXML
	    private void initialize() throws SQLException {
	    	showVentes();	    	
	    	}
	    @FXML
	    void handleAddButton() throws SQLException {
	    	addVente();
	    }
	    
	    @FXML
	    void handleModifierButton() {
	    	updateVente();
	    }
	    @FXML
	    void handleSupprimerButton() {
	    	deleteVente();
	    }
	    @FXML
	    void handleSearchButton() {
	    	venteJour();
	    }
	    @FXML
		private void handleMouseAction() {
			Vente vente = tvvente.getSelectionModel().getSelectedItem();
			
			tfproduit.setText(vente.getProduit());
			tfclient.setText(""+vente.getClient());
			tfprixdevente.setText(""+vente.getPrixVente());
			tfquantiteproduit.setText(""+vente.getQuantite());
			
			tfidproduit.setEditable(false);
			tfproduit.setEditable(false);
			tfprixachat.setEditable(false);


		//	tfprixachat.setText(""+vente.get());

		}
	    
	    @FXML
	    void purchase() throws SQLException {
	    	
	    	Connection conn = SqlConnection.getConnection();	    	
	    	String select = "SELECT * FROM article WHERE id = "+ tfidproduit.getText()+"";
	       	Statement st = conn.createStatement();
	    	ResultSet rs;
	        rs = st.executeQuery(select);
	        if(rs.next() == false) {
	        	JOptionPane.showInputDialog(this, "id not found!!!!!!!");
	        	
	        }
	        else {
	        	String product = rs.getString("nom_article");   	       
	        	String prixachat =""+rs.getFloat("prix");   
	        	

	        	tfproduit.setText(product);
	        	tfprixachat.setText(prixachat);
	        	
	        	
	        }
	    }
	    
	    public ObservableList<Vente> getVenteList(){
	        ObservableList<Vente> venteList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	        String query = "SELECT * FROM vente";
	        Statement st;
	        ResultSet rs;
	        
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);
	            Vente vente;
	            while(rs.next()){
	            	vente = new Vente(rs.getInt("id"), rs.getString("article"), rs.getString("client"), rs.getFloat("prixVente"),rs.getInt("quantite"), rs.getDate("dateVente"),rs.getDouble("total"),rs.getFloat("benifice"));
	                venteList.add(vente);
	                
	            }
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return venteList;
	    }
	    
	    public void showVentes() {
			ObservableList<Vente> list = getVenteList();
			
			idventecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("id"));
			produitcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("produit"));
			clientcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("client"));
			quantitecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("quantite"));
			prixdeventecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("prixVente"));
			datecol.setCellValueFactory(new PropertyValueFactory<Vente , java.sql.Date>("dateVente"));
			totalcol.setCellValueFactory(new PropertyValueFactory<Vente , Double>("total"));
			benificecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("benifice"));

	        tvvente.setItems(list);

		}
	    
	    private void addVente() throws SQLException {
	    
	    	Connection conn = SqlConnection.getConnection();   	
	    	String select = "SELECT * FROM article WHERE id = "+ tfidproduit.getText() +"";
	       	Statement st = conn.createStatement();
	    	ResultSet rs;
	        rs = st.executeQuery(select);
	       	            
	        while(rs.next()) {
	       	       
	        	float prixachat =rs.getFloat("prix");   
	        	int quantiteProduit =rs.getInt("quantite");  

	        	
	        	float prixVente = Float.parseFloat(tfprixdevente.getText());		        
	 	    	int quantiteVente = Integer.parseInt(tfquantiteproduit.getText());  
	 	    	
	 	        double total = prixVente * quantiteVente;
	 	        float benifice = (float)(total - (quantiteVente * prixachat)) ;
	 	        
	 	       
	 	        if(quantiteVente >= quantiteProduit) {
	 	        	JOptionPane.showInputDialog(venteAnchor, "Quantité !!!!!!!!!!");
	 	        	continue;
	 	        }
	 	        else {
	 	        	quantiteProduit -= quantiteVente;
	 	        	String query3 = "UPDATE  article SET quantite  ="+quantiteProduit+" WHERE id = "+tfidproduit.getText()+"";
	 	        	SqlConnection.executeQuery(query3); 	        	
	 	        }
	 	        
	 	       java.util.Date date=new java.util.Date();
	 	       java.sql.Date sqlDate=new java.sql.Date(date.getTime());	
	 	       String query1 = "INSERT INTO vente (article,client,prixVente,quantite,dateVente,total,benifice,isFacture) VALUES ('" + tfproduit.getText() + "','" + tfclient.getText() + "',"
		               + tfprixdevente.getText() + "," + tfquantiteproduit.getText() + ",'" + sqlDate + "'," + total + "," + benifice + ",false)";
	 	       System.out.println(query1);
		        SqlConnection.executeQuery(query1);	        
	        }        		       
	        showVentes();
	        
	    }
	    
	    private void updateVente() {
	    	Vente vente = tvvente.getSelectionModel().getSelectedItem();
	            String query = "UPDATE  vente SET client  = '" + tfclient.getText() + "', prixVente = '" + tfprixdevente.getText() + "', quantite = " +
	                    tfquantiteproduit.getText() + " WHERE id = " + vente.getId() + "";
	            SqlConnection.executeQuery(query);
	            showVentes();
	    }
	    private void deleteVente(){  	
	    	Vente vente = tvvente.getSelectionModel().getSelectedItem();
	    	String query = "DELETE FROM vente WHERE id = " + vente.getId() + "";
	    	SqlConnection.executeQuery(query);
	    	showVentes();
	    }
	    
	    public ObservableList<Vente> getVenteJourList(){
	        ObservableList<Vente> venteJourList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	    	LocalDate localDate = tfventejour.getValue();
	        String query = "SELECT * FROM vente WHERE dateVente = '"+ localDate + "'";
	        Statement st;
	        ResultSet rs;
	        
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);	          
	            Vente vente;
	            while(rs.next()){
	            	vente = new Vente(rs.getInt("id"), rs.getString("article"), rs.getString("client"), rs.getFloat("prixVente"),rs.getInt("quantite"), rs.getDate("dateVente"),rs.getDouble("total"),rs.getFloat("benifice"));
	            	venteJourList.add(vente);
	            }
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return venteJourList;
	    }
	    @FXML
	    private void venteJour() {
	    
	    	ObservableList<Vente> list = getVenteJourList();
			idventecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("id"));
			produitcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("produit"));
			clientcol.setCellValueFactory(new PropertyValueFactory<Vente , String>("client"));
			quantitecol.setCellValueFactory(new PropertyValueFactory<Vente , Integer>("quantite"));
			prixdeventecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("prixVente"));
			datecol.setCellValueFactory(new PropertyValueFactory<Vente , java.sql.Date>("dateVente"));
			totalcol.setCellValueFactory(new PropertyValueFactory<Vente , Double>("total"));
			benificecol.setCellValueFactory(new PropertyValueFactory<Vente , Float>("benifice"));

	        tvvente.setItems(list);
	        
	    	
	    }
	    

	    
	    
	    
	    
	    
}
