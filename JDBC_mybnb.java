package database;


import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JDBC_mybnb {

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while(! line.equalsIgnoreCase("exit")) {
            System.out.println("Please enter which operations to perform:"
            		+ "user(User operation), reports(Admin operations)"
            		+ "or exit(exit the program).");
            line = br.readLine();
            switch(line) {
            	case "users":
            		break;
            	case "eports":
            		break;
            	default:
            		break;
            }
        }
        
        br.close();
    }
    
    public static Connection getConnectiion() throws ClassNotFoundException{
        try {
        	String dbClassName = "com.mysql.jdbc.Driver";
        	String CONNECTION = "jdbc:mysql://localhost:3306/cscc43db";
        	Class.forName(dbClassName);
            String user = "root";
            String password = "root";
            Connection conn = DriverManager.getConnection(CONNECTION, user, password);
            System.out.println("Successfully connected to MySQL!");
            return conn;
            
        } catch (SQLException e) {

            System.err.println("Connection error occured!");
        }
        return null;
    }

}