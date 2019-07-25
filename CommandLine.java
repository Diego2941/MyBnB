package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine {
	private Scanner sc = null;
	private SQLop sql = null;
	private String adminname = "Admin";
	private String adminpassword = "password";
	private String username = "";
	
	public boolean startSession() {
		boolean success = true;
		if (sc == null) {
			sc = new Scanner(System.in);
		}
		if (sql == null) {
			sql = new SQLop();
		}
		try {
			success = sql.connect(this.getCredentials());
		} catch (ClassNotFoundException e) {
			success = false;
			System.err.println("Establishing connection triggered an exception!");
			e.printStackTrace();
			sc = null;
			sql = null;
		}
		return success;
	}
	
	public void endSession() {
		if (sql != null)
			sql.disconnect();
		if (sc != null) {
			sc.close();
		}
		sql = null;
		sc = null;
	}
	
	private String[] getCredentials() {
		String[] cred = new String[3];
		cred[0] = "root";
		cred[1] = "root";
		cred[2] = "cscc43db";
		return cred;
	}
	
	private String[] userlogin() {
		String[] cred = new String[2];
		System.out.print("Username: ");
		cred[0] = sc.nextLine();
		System.out.print("Password: ");
		cred[1] = sc.nextLine();
		return cred;
	}
	
	private String[] adminlogin() {
		String[] cred = new String[2];
		System.out.print("Username: ");
		cred[0] = sc.nextLine();
		System.out.print("Password: ");
		cred[1] = sc.nextLine();
		return cred;
	}
	
	public boolean execute() {
		if (sc != null && sql != null) {
			System.out.println("\n*****************************");
			System.out.println("******Welcome to MyBnB*******");
			System.out.println("*****************************\n");
			try {
				initiateTables();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String input = "";
			 while(! input.equalsIgnoreCase("exit")) {
				 System.out.println("Please enter which operations to perform:"
		            		+ "sigup(Create account)"
						 	+ "login(User operation), "
		            		+ "reports(Admin operations) "
		            		+ "or exit(exit the program).");
		            input = sc.nextLine();
		            switch(input) {
		            	case "signup":
						try {
							createAccount();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            		break;
		            	case "login":
		            		break;
		            	case "reports":
		            		reportOperation();
		            		break;
		            	case "exit":
		                    System.out.println("Thakns for visiting!");
		                    break;
		            	default:
		            		System.out.println("Invalid operation. Please try again!");
		            		break;
		            }
		       }
			return true;
		} else {
			System.out.println("");
			System.out.println("Connection could not been established! Bye!");
			System.out.println("");
			return false;
		}
	}
	
	public void userOperation() {
	      // ask for inputs
	      String line = "";
	      System.out.println("Hi, " + username + " ! "
	              + "Please enter which operations to perform:\n"
	              + "1. (create account)\n"
	              + "2. (signin an existing account)\n"
	              + "3. (create booking)\n"
	              + "4. (cancel booking)\n"
	              + "5. (change price)\n"
	              + "6. (update price)\n"
	              + "7. (change availability)\n"
	              + "8. (comment)\n"
	              + "9. (rate)\n"
	              + "10. (back to previous page):");
	      while(! line.equalsIgnoreCase("exit")) {
	        line = sc.nextLine();
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
	          case "exit":
	        	  System.out.println("Signing out.");
	        	  username = "";
	              break;
	          default:
	              System.out.println("Invalid report. Please try again!");
	              break;
	          }
	      }
	   }
	
	 public void reportOperation() {
	      // ask for inputs
	      String line = "";
	      System.out.println("Hi, Admin! "
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
	      while(! line.equalsIgnoreCase("exit")) {
	        line = sc.nextLine();
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
	          case "exit":
	        	  System.out.println("Living the reports page.");
	              break;
	          default:
	              System.out.println("Invalid report. Please try again!");
	              break;
	          }
	      }
	 }
	 
	 public  void initiateTables() throws Exception {
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
                    + "name varchar(255), "
                    + "password varchar(16), "
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
            
        sql.insertop(listing);
        sql.insertop(user);
        sql.insertop(booking);
        sql.insertop(calendar);
    }
	    
	public void createAccount() throws Exception {
		int counter;
		String[] vals = new String[8];
		System.out.print("Name: ");
		vals[0] = sc.nextLine();
		System.out.print("Password: ");
		vals[1] = sc.nextLine();
		System.out.print("UserType: ");
		vals[2] = sc.nextLine();
		System.out.print("Address: ");
		vals[3] = sc.nextLine();
		System.out.print("Day of birth: ");
		vals[4] = sc.nextLine();
		System.out.print("Occupation: ");
		vals[5] = sc.nextLine();
		System.out.print("SIN: ");
		vals[6] = sc.nextLine();
		System.out.print("Payment Info: ");
		vals[7] = sc.nextLine();
		String query = "INSERT INTO user(name, password, utype, "
				+ "uaddress, birth, ocupation, sin, payment) VALUES(";
		for (counter = 0; counter < vals.length - 1; counter++) {
			query = query.concat("'" + vals[counter] + "',");
		}
		query = query.concat("'" + vals[counter] + "');");
		sql.insertop(query);
	}
}
