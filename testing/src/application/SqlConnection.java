package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SqlConnection {
	public static Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "root","");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
	public static void executeQuery(String query) {
        Connection conn = SqlConnection.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
