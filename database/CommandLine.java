package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine {
	private Scanner sc = null;
	private SQLop sql = null;
	private String admin = "Admin";
	private String adminpassword = "password";
	private String username = "";
	private String userid = "";
	
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
	
	private String[] login() {
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
				 System.out.println("Please enter which operations to perform( " + "'h'" + " for help " + "'exit'" + " to exit):\n");
		            input = sc.nextLine();
		            switch(input) {
		            	case "h":
		            		help("Menu");
		            		break;
		            	case "signup":
							createAccount();
		            		break;
		            	case "login":
		            		userOperation();
		            		break;
		            	case "reports":
		            		reportOperation();
		            		break;
		            	case "exit":
		                    System.out.println("Thanks for visiting!");
		                    username = "";
				        	userid = "";
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
	
	public void printlist(ArrayList<ArrayList<String>> lst) {
		System.out.println("");
		for (int i = 0; i < lst.size(); i++) {
			for (int j = 0; j < lst.get(i).size(); j++) {
				System.out.print(String.format("|%-20s|", lst.get(i).get(j)));
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public void userOperation() {
		String[] user = login();
		String query = "SELECT uid, name, utype\n"
				+ "FROM cscc43db.user \n"
				+ "WHERE uid= " + user[0]
				+ " AND password= "
				+ "'" + user[1] + "'";
		try {
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			printlist(ans);
			if (ans.get(1).get(0) != null) {
				userid = ans.get(1).get(0);
				username = ans.get(1).get(1);
				String usertype = ans.get(1).get(2);
				
				if (usertype.equals("true")){
					hostOperation();
				}
				else {
					renterOperation();
				}
			}
			
		} catch (Exception e) {
			System.out.println("The username or password is incorrect"
					+ "please try again!.");
		}
		
	}
	
	public void renterOperation() {
	      // ask for inputs
	      String line = "";
	      while(! line.equalsIgnoreCase("back")) {
	    	  System.out.println("Hi, " + username + " ! \n"
		              + "Please enter which operations to perform( " + "'h'" + " for healp " + "'back'" + " for previos page):\n");
		      line = sc.nextLine();
	          switch(line) {
		          case "h":
	          			help("Renter");
	          			break;
		          case "1":
		              
		              break;
		          case "2":
		              
		              break;
		          case "3":
		              createBooking();
		              break;
		          case "4":
		              
		              break;
		          case "5":
		              
		              break;
		          case "6":
		        	  
		        	  break;
		          
		          case "7":
		        	  
		        	  break;
		          case "back":
		        	  System.out.println("Signing out.");
		        	  username = "";
		        	  userid = "";
		              break;
		              
		          default:
		              System.out.println("Invalid report. Please try again!");
		              break;
	          }
	      }
	   }
	
	public void hostOperation() {
	      // ask for inputs
	      String line = "";
	      while(! line.equalsIgnoreCase("back")) {
	    	  System.out.println("Hi, " + username + " ! \n"
		              + "Please enter which operations to perform( " + "'h'" + " for help " + "'back'" + " for previos page):\n");
	    	  line = sc.nextLine();
	          switch(line) {
		          case "1":
		              
		              break;
		          case "2":
						createListing();
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
		          case "back":
		        	  System.out.println("Signing out.\n");
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
	      while(! line.equalsIgnoreCase("back")) {
	    	  System.out.println("Hi, " + admin + " ! \n"
		              + "Please enter which operations to perform(" + "'h'" + "for help" + "'back'" + "for previos page):\n");
	    	  
	    	  line = sc.nextLine();
	          switch(line) {
		          case "1":
		        	  bookingSpecificDateCityReport();
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
		          case "11":
		     
	                break;
		          case "back":
		        	  System.out.println("Left from the reports page.");
		              break;
		          default:
		              System.out.println("Invalid report. Please try again!");
		              break;
	          }
	      }
	 }
	 
	 public void help(String str) {
			String ans = "";
			switch(str) {
				case "Report":
					ans = "1. (delete account)\n"
					        + "2. (search)\n"
					        + "3. (create booking)\n"
					        + "4. (cancel booking)\n"
					        + "5. (change price)\n"
					        + "6. (comment)\n"
					        + "7. (rate)\n"
					        + "exit. (back to previous page):";
					break;
				case "Renter":
					ans = "1. (delete account)\n"
					        + "2. (search)\n"
					        + "3. (create booking)\n"
					        + "4. (cancel booking)\n"
					        + "5. (change price)\n"
					        + "6. (comment)\n"
					        + "7. (rate)\n"
					        + "exit. (back to previous page):";
					break;
				case "Host":
					ans = "1. (delete account)\n"
				              + "2. (create listing)"
				              + "3. (cancel booking)\n"
				              + "4. (change price)\n"
				              + "5. (update price)\n"
				              + "6. (change availability)\n"
				              + "7. (comment)\n"
				              + "8. (rate)\n"
				              + "exit. (back to previous page):";
					break;
				default:
					ans = "sigup(Create account)\n"
						+ "login(User operation)\n"
						+ "reports(Admin operations)\n"
						+ "exit(exit the program):\n";
					break;
			}

			System.out.println(ans);
		}
	 public void bookingSpecificDateCityReport() {
		 String start, end;
		 System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
         start = sc.nextLine();
         System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
         end = sc.nextLine();
         String query = "SELECT count(*), city FROM booking Natural Join listings WHERE checkin > '"
        	      + start + "' and checkout < '" + end + "' group by city;";
         try {
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			printlist(ans);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Does not exist such report format.");
		}
	 }
	 
	 public  void initiateTables() {
		 String[] vals = new String[4];
         vals[0] = "CREATE TABLE IF NOT EXISTS "
                    + "listings(lid int NOT NULL AUTO_INCREMENT, "
                    + "name varchar(255), "
                    + "hid int, "
                    + "ltype varchar(255), "
                    + "adress varchar(255), "
                    + "city varchar(255), "
                    + "country varchar(255), "
                    + "postcode int, "
                    + "latitude float, "
                    + "longitude float, "
                    + "aid int, "
                    + "PRIMARY KEY (lid))";
            
         vals[1] = "CREATE TABLE IF NOT EXISTS "
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
                    
         vals[2] = "CREATE TABLE IF NOT EXISTS "
                    + "booking(bid int NOT NULL AUTO_INCREMENT, "
                    + "lid int, "
                    + "uid int, "
                    + "checkin date, "
                    + "checkout date, "
                    + "cancelation bool, "
                    + "hostcomment varchar(255), "
                    + "rentercomment varchar(255), "
                    + "PRIMARY KEY (bid))";
            
         vals[3] = "CREATE TABLE IF NOT EXISTS "
                    + "calendar(lid int, "
                    + "startdate date, "
                    + "enddate date, "
                    + "price float)";
        
        	try {
        		for(int counter = 0; counter < vals.length; counter++) {
        			sql.insertop(vals[counter]);
        		}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Tables can not be created.");
        }
    }
	    
	public void createAccount() {
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
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Account can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void createBooking(){
		int counter;
		String[] vals = new String[7];
		System.out.print("ListingID ");
		vals[0] = sc.nextLine();
		System.out.print("Checkin: ");
		vals[2] = sc.nextLine();
		System.out.print("Checkout: ");
		vals[3] = sc.nextLine();
		
		vals[1] = "";
		vals[4] = "0";
		vals[5] = "";
		vals[6] = "";
		
		String query = "INSERT INTO booking(lid, uid, utype, checkin"
				+ "checkout, cancellation, hostcomment, rentercomment) VALUES(";
		for (counter = 0; counter < vals.length - 1; counter++) {
			query = query.concat("'" + vals[counter] + "',");
		}
		query = query.concat("'" + vals[counter] + "');");
		try {
			sql.insertop(query);
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Booking can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void createListing() {
		int counter;
		String[] vals = new String[9];
		System.out.print("Name: ");
		vals[0] = sc.nextLine();
		System.out.print("ListingType: ");
		vals[2] = sc.nextLine();
		System.out.print("Address: ");
		vals[3] = sc.nextLine();
		System.out.print("City: ");
		vals[4] = sc.nextLine();
		System.out.print("Country: ");
		vals[5] = sc.nextLine();
		System.out.print("Postal Code: ");
		vals[6] = sc.nextLine();
		System.out.print("Latitude: ");
		vals[7] = sc.nextLine();
		System.out.print("Longitude: ");
		vals[8] = sc.nextLine();
		System.out.print("Amenities: ");
		vals[9] = sc.nextLine();
		
		vals[1] = userid;
		
		String query = "INSERT INTO listing(name, hid, ltype, address"
				+ "city, country, postcode, latitude"
				+ "logitude, aid) VALUES(";
		for (counter = 0; counter < vals.length - 1; counter++) {
			query = query.concat("'" + vals[counter] + "',");
		}
		query = query.concat("'" + vals[counter] + "');");
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Listing can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
}
