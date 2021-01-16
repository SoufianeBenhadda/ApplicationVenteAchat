package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.Node;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ArticleController implements Initializable {

	
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfArticle;
	@FXML
	private TextField tfPrix;
	@FXML
	private TextField tfQuantite;
	@FXML
	private TableView<Article> tvGestion;
	@FXML
	private TableColumn<Article, Integer> tvId;
	@FXML
	private TableColumn<Article , String> tvArticle;
	@FXML
	private TableColumn<Article , Float> tvPrix;
	@FXML
	private TableColumn<Article , Integer> tvQuantite;
	@FXML
	private TableColumn<Article , java.sql.Date> tvDate;
	@FXML
	private Button btnAjouter;
	@FXML
	private Button btnModifier;
	@FXML
	private Button btnSupprimer;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showArticles();
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		
		if(event.getSource() == btnAjouter) {
			addArticle();
		}
		else if(event.getSource() == btnSupprimer) {
			deleteArticle();
		}
		else if(event.getSource() == btnModifier) {
			updateArticle();
		}
	}
	@FXML
	private void handleMouseAction() {
		Article article = tvGestion.getSelectionModel().getSelectedItem();
		tfId.setText(""+article.getId());
		tfArticle.setText(article.getNom_article());
		tfQuantite.setText(""+article.getQuantite());
		tfPrix.setText(""+article.getPrix());
	}
	
	
	
	
	  public ObservableList<Article> getArticleList(){
	        ObservableList<Article> articleList = FXCollections.observableArrayList();
	        Connection conn = SqlConnection.getConnection();
	        String query = "SELECT * FROM article";
	        Statement st;
	        ResultSet rs;
	        
	        try{
	            st = conn.createStatement();
	            rs = st.executeQuery(query);
	            Article Article;
	            while(rs.next()){
	                Article = new Article(rs.getInt("id"), rs.getString("nom_article"), rs.getFloat("prix"), rs.getInt("quantite"), rs.getDate("created_at"));
	                articleList.add(Article);
	            }
	                
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return articleList;
	    }
	public void showArticles() {
		ObservableList<Article> list = getArticleList();
		tvId.setCellValueFactory(new PropertyValueFactory<Article , Integer>("id"));
		tvArticle.setCellValueFactory(new PropertyValueFactory<Article , String>("nom_article"));
		tvPrix.setCellValueFactory(new PropertyValueFactory<Article , Float>("prix"));
		tvQuantite.setCellValueFactory(new PropertyValueFactory<Article , Integer>("quantite"));
		tvDate.setCellValueFactory(new PropertyValueFactory<Article , java.sql.Date>("created_at"));
		
        tvGestion.setItems(list);

	}
	
	private void addArticle(){
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        String query = "INSERT INTO article VALUES (" + tfId.getText() + ",'" + tfArticle.getText() + "','" + tfPrix.getText() + "',"
                + tfQuantite.getText() + ",'" + sqlDate + "')";
        SqlConnection.executeQuery(query);
        showArticles();
    }
    private void updateArticle(){
        String query = "UPDATE  article SET nom_article  = '" + tfArticle.getText() + "', prix = '" + tfPrix.getText() + "', quantite = " +
                tfQuantite.getText() + " WHERE id = " + tfId.getText() + "";
        SqlConnection.executeQuery(query);
        showArticles();
    }
    private void deleteArticle(){  	
    	Article article = tvGestion.getSelectionModel().getSelectedItem();
    	String query = "DELETE FROM article WHERE id = " + article.getId() + "";
    	SqlConnection.executeQuery(query);
    	showArticles();
    }
    
    
    
    
    
    

}
