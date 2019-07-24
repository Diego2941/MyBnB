package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine {
	private Scanner sc = null;
	private SQLop sql = null;
	
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
		System.out.print("Username: ");
		cred[0] = sc.nextLine();
		System.out.print("Password: ");
		cred[1] = sc.nextLine();
		System.out.print("Database: ");
		cred[2] = sc.nextLine();
		return cred;
	}
	
	public boolean execute() {
		if (sc != null && sql != null) {
			System.out.println("");
			System.out.println("***************************");
			System.out.println("******ACCESS GRANTED*******");
			System.out.println("***************************");
			System.out.println("");
			
			String input = "";
			 while(! input.equalsIgnoreCase("exit")) {
				 System.out.println("Please enter which operations to perform:"
		            		+ "user(User operation), reports(Admin operations)"
		            		+ "or exit(exit the program).");
		            input = sc.nextLine();
		            switch(input) {
		            	case "users":
		            		break;
		            	case "reports":
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
			return true;
		} else {
			System.out.println("");
			System.out.println("Connection could not been established! Bye!");
			System.out.println("");
			return false;
		}
	}
	
	 public void reportOperation() {
	      // ask for inputs
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
}
