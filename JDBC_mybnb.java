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
        initiateTables();
        while(! line.equalsIgnoreCase("exit")) {
            System.out.println("Please enter which operations to perform:\n"
                    + "user(User operation)\nreport(Admin operations)"
                    + "\nexit(exit the program):\n");
            line = br.readLine();
            switch(line) {
                case "user":
                    break;
                case "report":
                  reportOperation();
                    break;
                case "exit":
                  System.out.println("GoodBye!");
                  break;
                default:
                  System.out.println("Invalid operation. Please try again!");
                    break;
            }
        }
        
        br.close();
    }
    
    public static void reportOperation() throws Exception {
      // ask for inputs
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line = "";
      System.out.println("Hi, Brother Diego! "
              + "Please enter which operations to perform:\n"
              + "1. (bookings in specified date range by city)\n"
              + "2. (bookings by postal code in specified city)\n"
              + "3. (listings by country)\n"
              + "4. (listings by country and city)\n"
              + "5. (listings by country, city and postal code)\n"
              + "6. (host list ranked by number of listings per country)\n"
              + "7. (host list ranked on number of listings by city)\n"
              + "8. (commercial hosts by country and city)\n"
              + "9. (report renters by number of bookings within a specified date)\n"
              + "10. (report renters by number of bookings within a specified date grouped by city)\n"
              + "11. (report hosts with the largest number of cancellation within a specified year)\n"
              + "12. (back to previous page):");
      while(! line.equalsIgnoreCase("q")) {
        line = br.readLine();
          switch(line) {
          case "1":
              
              break;
          case "2":
              
              break;
          case "3":
              
              break;
          case "4":
              
              break;
          case "5":
              
              break;
          case "6":
              
              break;
          case "7":
              
              break;
          case "8":
              
              break;
          case "9":
              break;
          case "10":
              break;
          default:
              System.out.println("Invalid report. Please try again!");
              break;
          }
      }
    }
    
    public static Connection getConnection() throws Exception{
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
            return null;
        }
    }
    
    public static void createTable(String query)throws Exception{
        Connection conn = null;
        PreparedStatement table = null;
        try {
            conn = getConnection();
            table = conn.prepareStatement(query);
            table.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println(e);
            System.err.println("Connection error occured!");
            
        } finally {
            table.close();
            conn.close();
        }
            
    }
    
    public static void initiateTables() throws Exception {
            String listing, user, booking, calendar;
            listing = "CREATE TABLE IF NOT EXISTS "
                    + "listings(lid int NOT NULL AUTO_INCREMENT, "
                    + "name varchar(255), "
                    + "hid int, "
                    + "ltype varchar(255), "
                    + "adress varchar(255), "
                    + "city varchar(255), "
                    + "postcode int, "
                    + "country varchar(255), "
                    + "latitude float, "
                    + "longitude float, "
                    + "aid int, "
                    + "PRIMARY KEY (lid))";
            
            user = "CREATE TABLE IF NOT EXISTS "
                    + "user(uid int NOT NULL AUTO_INCREMENT, "
                    + "password varchar(16), "
                    + "name varchar(255), "
                    + "utype bool, "
                    + "uaddress varchar(255), "
                    + "birth date, "
                    + "ocupation varchar(255), "
                    + "sin int(9), "
                    + "payment varchar(255), "
                    + "PRIMARY KEY (uid))";
                    
            booking = "CREATE TABLE IF NOT EXISTS "
                    + "booking(bid int NOT NULL AUTO_INCREMENT, "
                    + "lid int, "
                    + "uid int, "
                    + "checkin datetime, "
                    + "checkout datetime, "
                    + "cancelation bool, "
                    + "hostcomment varchar(255), "
                    + "rentercomment varchar(255), "
                    + "PRIMARY KEY (bid))";
            
            calendar = "CREATE TABLE IF NOT EXISTS "
                    + "calendar(lid int, "
                    + "startdate date, "
                    + "enddate date, "
                    + "price float)";
            
            createTable(listing);
            createTable(user);
            createTable(booking);
            createTable(calendar);
    }

}