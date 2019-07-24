

import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JDBCExample {

    private static final String dbClassName = "com.mysql.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/cscc43db";
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        //Register JDBC driver
        Class.forName(dbClassName);
        //Database credentials
        System.out.println("Connecting to database...");
        
            try {
                //Establish connection
                String user = "root";
                String password = "Xin980609!";
                Connection conn = DriverManager.getConnection(CONNECTION, user, password);
                System.out.println("Successfully connected to MySQL!");
                
    //          //Execute a query
    //          System.out.println("Preparing a statement...");
    //          Statement stmt = conn.createStatement();
    //          String sql = "SELECT * FROM Sailors;";
    //          ResultSet rs = stmt.executeQuery(sql);
    //          
    //          //STEP 5: Extract data from result set
    //          while(rs.next()){
    //              //Retrieve by column name
    //              int sid  = rs.getInt("sid");
    //              String sname = rs.getString("sname");
    //              int rating = rs.getInt("rating");
    //              int age = rs.getInt("age");
    //          
    //              //Display values
    //              System.out.print("ID: " + sid);
    //              System.out.print(", Name: " + sname);
    //              System.out.print(", Rating: " + rating);
    //              System.out.println(", Age: " + age);
    //          }
                
                
                System.out.println("Closing connection...");
    //          rs.close();
    //          stmt.close();
                conn.close();
                System.out.println("Success!");
            } catch (Exception e) {
                System.err.println(e);
                System.err.println("Connection error occured!");
            }
        while(! line.equalsIgnoreCase("q")) {
            System.out.println("Please enter a number");
            line = br.readLine();
        }
        
        br.close();
    }

}