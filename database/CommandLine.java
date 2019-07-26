package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

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
			initiateTables();
			//mock();
			String input = "";
			 while(! input.equalsIgnoreCase("exit")) {
				 System.out.println("Please enter which operations to perform( " + "'h'" + " for help " + "'exit'" + " to exit):\n");
		            input = sc.nextLine();
		            switch(input) {
		            	case "h":
		            		help("Menu");
		            		break;
		            	case "signup":
							accountInput();
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
		System.out.println("\n-----------------------------------------------");
		for (int i = 0; i < lst.size(); i++) {
			for (int j = 0; j < lst.get(i).size(); j++) {
				System.out.print(String.format("|%-20s|", lst.get(i).get(j)));
			}
			System.out.println("");
		}
		System.out.println("-----------------------------------------------\n");
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
			if (ans.get(1).get(0) != null) {
				userid = ans.get(1).get(0);
				username = ans.get(1).get(1);
				String usertype = ans.get(1).get(2);
				
				if (usertype.equals("1")){
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
	
    public void help(String str) {
			String ans = "";
			switch(str) {
				case "Report":
					ans = "1. (bookings in specified date range by city)\n"
			              + "2. (bookings by postal code in specified date range within city)\n"
			              + "3. (listings by country)\n"
			              + "4. (listings by country and city)\n"
			              + "5. (listings by country, city and postal code)\n"
			              + "6. (rank host by number of listings per country)\n"
			              + "7. (rank host on number of listings by city)\n"
			              + "8. (commercial hosts by country and city)\n"
			              + "9. (renters rank by number of bookings within a specified time)\n"
			              + "10. (renters rank by number of bookings within a specified time per city)\n"
			              + "11. (hosts and renters with the largest number of cancellation within a specified year)\n"
			              + "back. (back to previous page):";
					break;
				case "Renter":
					ans = "1. (delete account)\n"
					        + "2. (search)\n"
					        + "3. (create booking)\n"
					        + "4. (cancel booking)\n"
					        + "5. (comment)\n"
					        + "6. (rate)\n"
					        + "exit. (back to previous page):";
					break;
				case "Host":
					ans = "1. (delete account)\n"
				              + "2. (create listing)\n"
				              + "3. (cancel booking)\n"
				              + "4. (change price)\n"
				              + "5. (change availability)\n"
				              + "6. (comment)\n"
				              + "7. (rate)\n"
				              + "8. (remove listing)\n"
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
		        	  deleteUser();
		              line = "back";
		              break;
		          case "2":
		              
		              break;
		          case "3":
		              bookingInput();
		              break;
		          case "4":
		        	  cancelBooking();
		              break;
		          case "5":
		              
		              break;
		          case "6":
		        	  
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
		              deleteUser();
		              line = "back";
		              break;
		          case "2":
						listingInput();
						ammenitiesInput();
						calendarInput();
		              break;
		          case "3":
		              cancelBooking();
		              break;
		          case "4":
		              changePrice();
		              break;
		          case "5":
		              
		              break;
		          case "6":
		              
		        	  break;
		          case "7":
		        	  break;
		          case "8":
		        	  removeListing();
		        	  break;
		          case "h":
		            help("Host");
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
		            bookingSpecificDatePostal();
		              break;
		          case "3":
		            listingCountry();
		              break;
		          case "4":
		            listingCountryCity();
		              break;
		          case "5":
		            listingCountryCityPost();
		              break;
		          case "6":
		            rankHostPerCountry();
		              break;
		          case "7":
		            rankHostByCity();
		              break;
		          case "8":
		            commercialHost();
		              break;
		          case "9":
		            rankRenterInTime();
		              break;
		          case "10":
		            rankRenterTimePerCity();
		              break;
		          case "11":
		            largeCancelInYear();
	                break;
		          case "12":
                    renterPopularComments();
                    break;
		          case "back":
		        	  System.out.println("Left from the reports page.");
		              break;
		          case "h":
		            help("Report");
		            break;
		          default:
		              System.out.println("Invalid report. Please try again!");
		              break;
	          }
	      }
	}
	 
    private void renterPopularComments() {
      String query = "SELECT a.lid, a.rentercomment, a.hostcomment, "
          + "MAX(a.count) as numOfOccurance FROM (SELECT lid, rentercomment, "
          + "hostcomment, count(*) as count FROM booking group by lid order by "
          + "count desc, lid) a group by a.lid order by numOfOccurance desc";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         System.out.println("Does not exist such report format.");
     }
  }

    private void largeCancelInYear() {
      String year;
      System.out.println("Please enter the year:\n");
      year = sc.nextLine();
      String query = "SELECT uid, utype, count(*) as numOfCancel FROM booking "
          + "NATURAL JOIN user WHERE cancelation = 1 and checkin >= '"
          + year + "-01-01' and checkout < '"
          + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' and utype = 0 "
              + "group by uid HAVING numOfCancel = (SELECT MAX(a.count) as "
              + "numOfCancel FROM (SELECT count(*) as count FROM booking NATURAL"
              + " JOIN user WHERE cancelation = 1 and checkin >= '"
              + year + "-01-01' and checkout < '"
              + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' and "
                  + "utype = 0 GROUP BY uid) a) "
              + "UNION SELECT uid, utype, count(*) as numOfCancel FROM booking "
              + "NATURAL JOIN user WHERE cancelation = 1 and checkin >= '"
              + year + "-01-01' and checkout < '"
              + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' "
                  + "and utype = 1 group "
              + "by uid HAVING numOfCancel = (SELECT MAX(a.count) as numOfCancel"
              + " FROM (SELECT count(*) as count FROM booking NATURAL JOIN user "
              + "WHERE cancelation = 1 and checkin >= '"
              + year + "-01-01' and checkout < '"
              + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' "
                  + "and utype = 1 GROUP BY uid) a)";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         System.out.println("Does not exist such report format.");
     }
  }

    private void rankRenterTimePerCity() {
      String start, end;
      System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
      start = sc.nextLine();
      System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
      end = sc.nextLine();
      String query = "SELECT uid, city, count(*) as numBookings FROM booking Natural Join listing WHERE checkin >= '"
               + start + "' and checkout < '" + end + "' and cancelation = 0 group by uid, city order by numBookings DESC";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         System.out.println("Does not exist such report format.");
     }
  }

    private void rankRenterInTime() {
      String start, end;
      System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
      start = sc.nextLine();
      System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
      end = sc.nextLine();
      String query = "SELECT uid, count(*) as numBookings FROM booking WHERE checkin >= '"
               + start + "' and checkout <= '" + end + "' group by uid order by numBookings DESC";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         System.out.println("Does not exist such report format.");
     }
  }

    private void commercialHost() {
      String query = "SELECT b.hid, b.city, b.country FROM (SELECT count(*) "
          + "as countTotal, city, country From listing Group BY city, country) "
          + "a, (SELECT count(*) as countHost, hid, city, country From listing "
          + "Group BY hid, city, country) b WHERE a.city = b.city and a.country "
          + "= b.country and b.countHost >= (a.countTotal/10)";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void rankHostByCity() {
      String query = "SELECT hid, city, country, count(*) as numListings FROM "
          + "listing group by city, country, hid order by numListings DESC;";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void rankHostPerCountry() {
      String query = "SELECT hid, country, count(*) as "
          + "numListings FROM listing group by country, hid ORDER BY "
          + "numListings DESC;";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void listingCountryCity() {
      String query = "SELECT count(*) as '#listing', city, country FROM listing "
          + "group by country, city order by country";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void listingCountryCityPost() {
      String query = "SELECT count(*) as '#listing', postcode, city, country "
          + "FROM listing group by country, city, postcode order by country";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void listingCountry() {
      String query = "SELECT count(*) as '#listing', country FROM listing group by country";
      try {
         ArrayList<ArrayList<String>> ans = sql.executequery(query);
         printlist(ans);
     } catch (Exception e) {
         
         System.out.println("Does not exist such report format.");
     }
  }

    private void bookingSpecificDatePostal() {
        String start, end;
        System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
        start = sc.nextLine();
        System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
        end = sc.nextLine();
        String query = "SELECT count(postcode) as '#Bookings', postcode, city FROM booking Natural Join listing WHERE checkin >= '"
                 + start + "' and checkout <= '" + end + "' group by city, postcode;";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           printlist(ans);
       } catch (Exception e) {
           
           System.out.println("Does not exist such report format.");
       }
    }
  
	 public void bookingSpecificDateCityReport() {
		 String start, end;
		 System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
         start = sc.nextLine();
         System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
         end = sc.nextLine();
         String query = "SELECT count(*) as '#Bookings', city FROM booking Natural Join listing WHERE checkin >= '"
        	      + start + "' and checkout <= '" + end + "' group by city;";
         try {
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			printlist(ans);
		} catch (Exception e) {
			System.out.println("Does not exist such report format.");
		}
	 }
	 
	 public  void initiateTables() {
		 String[] vals = new String[5];
         vals[0] = "CREATE TABLE IF NOT EXISTS "
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
         
         vals[1] = "CREATE TABLE IF NOT EXISTS "
                 + "listing(lid int NOT NULL AUTO_INCREMENT, "
                 + "name varchar(255), "
                 + "hid int, "
                 + "ltype varchar(255), "
                 + "address varchar(255), "
                 + "city varchar(255), "
                 + "country varchar(255), "
                 + "postcode int, "
                 + "latitude float, "
                 + "longitude float, "
                 + "aid int, "
                 + "PRIMARY KEY (lid), "
                 + "FOREIGN KEY (hid) REFERENCES user(uid) "
                 + " ON DELETE CASCADE)";
      
                    
         vals[2] = "CREATE TABLE IF NOT EXISTS "
                    + "booking(bid int NOT NULL AUTO_INCREMENT, "
                    + "lid int, "
                    + "uid int, "
                    + "checkin date, "
                    + "checkout date, "
                    + "cancelation bool, "
                    + "hostcomment varchar(255), "
                    + "rentercomment varchar(255), "
                    + "PRIMARY KEY (bid), "
         			+ "FOREIGN KEY (uid) REFERENCES user(uid)"
         			+ " ON DELETE CASCADE, "
         			+ "FOREIGN KEY (lid) REFERENCES listing(lid)" 
         			+ " ON DELETE CASCADE)";
            
         vals[3] = "CREATE TABLE IF NOT EXISTS "
                    + "calendar(lid int NOT NULL AUTO_INCREMENT, "
                    + "startdate date, "
                    + "enddate date, "
                    + "price float, "
                    + "PRIMARY KEY (lid, startdate, enddate), "
                    + "FOREIGN KEY (lid) REFERENCES listing(lid)"
                    + " ON DELETE CASCADE)";
        
         vals[4] = "CREATE TABLE IF NOT EXISTS "
        		+ "amenity(lid int NOT NULL AUTO_INCREMENT, "
        		+ "wifi bool, "
        		+ "tv bool, "
        		+ "parking bool, "
        		+ "gym bool, "
        		+ "pool bool, "
        		+ "kitchen bool, "
        		+ "PRIMARY KEY (lid), "
        		+ "FOREIGN KEY (lid) REFERENCES listing(lid)"
        		+ " ON DELETE CASCADE)";
        
        	try {
        		for(int counter = 0; counter < vals.length; counter++) {
        			sql.insertop(vals[counter]);
        		}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Tables can not be created.");
        }
    }
	 
	public void accountInput() {
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
		createAccount(vals);
	}
	
	
	public void createAccount(String[] vals) {
		String query = "INSERT INTO user(name, password, utype, "
				+ "uaddress, birth, ocupation, sin, payment) VALUES(";
		query = getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Account can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void bookingInput() {
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
		createBooking(vals);
	}
	
	
	public void createBooking(String[] vals){
		String query = "INSERT INTO booking(lid, uid, checkin, checkout, "
				+ "cancellation, hostcomment, rentercomment) VALUES(";
		query = getQuery(query, vals);
		try {
				sql.insertop(query);
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Booking can not be created may be cause "
					+ "by the format please try again or isting"
					+ "does not exist!.");
		}
	}

	
	public void cancelBooking() {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE uid = " + "'" + userid + "' AND " 
				+ " checkin >= CURDATE() AND "
						+ "cancelation = 0";
		try {
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			if (ans.size() > 1) {
				printlist(ans);
				System.out.print("Checkin: ");
				String cin = sc.nextLine();
				System.out.print("Checkout: ");
				String cout = sc.nextLine();
				query = "UPDATE booking SET cancelation = 1"
						+ "WHERE uid = " + "'" + userid + "' OR"
								+ " lid = " + "'" + userid + "' AND "
								+ " checkin = " + cin + " AND "
										+ "checkout = " + cout; 
			}
			else {
				System.out.println("You do not have any bookings.");
			}
		} catch (Exception e) {
			System.out.println("You must select a correct date.");
		}
	}
	
	public void changePrice() {
		
	}

	
	public void listingInput() {
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
		vals[1] = userid;
		createListing(vals);
	}
	
	public void createListing(String[] vals) {
		String query = "INSERT INTO listing(name, hid, ltype, address, "
				+ "city, country, postcode, latitude, "
				+ "longitude) VALUES(";

		query = getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Listing can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void removeListing() {
		System.out.println("ListingId you want to remove: ");
		String id = sc.nextLine();
		String query = "DELETE "
				+ "FROM listing "
				+ "WHERE lid =" + "'" + id + "'";
		
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Listing can not be deleted.");
		}
		
	}
	
	public void ammenitiesInput() {
		System.out.println("Amenities: ");
		String[] vals = sc.nextLine().split(",");
		createAmmenities(vals);
	}
	
	public void createAmmenities(String[] vals) {
		String query = "INSERT INTO amenity(wifi, tv, parking, gym, "
				+ "pool, kitchen) VALUES(";

		query = getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ammenieites can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void calendarInput() {
		String vals[] = new String[3];
		System.out.println("Anual for a year starting from today: ");
		String year = sc.nextLine();
		vals[0] = "" + getdate();
		vals[1] = "" + getdate().plusYears(1);
		vals[2] = year;
		createCalendar(vals);
	}
	
	public void createCalendar(String[] vals) {
		String query = "INSERT INTO calendar(startdate, "
				+ "enddate, price) VALUES(";
		query = getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			System.out.println("Date format was wrong");
		}
	}
	
	public String getQuery(String query, String[] vals) {
		int counter;
		for (counter = 0; counter < vals.length - 1; counter++) {
			query = query.concat("'" + vals[counter] + "',");
		}
		query = query.concat("'" + vals[counter] + "');");
		return query;
	}
	
	public void deleteUser() {
		String query = "DELETE "
				+ "FROM user "
				+ "WHERE uid =" + "'" + userid + "'";
		
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User can not be deleted.");
		}
		
	}
	
	public void checkAvaible() {
		String query = "SELECT * "
				+ "FROM  listing "
				+ "WHERE not exists (SELECT 1"
				+ "FROM bookings "
				+ "WHERE listing.lid = booking.lid and"
				+ "";
	}
	
	
	public LocalDate getdate() {
		LocalDate myObj = LocalDate.now();
		return myObj;
	}
	
	public void mock() {
		String [] a = {"a", "a", "1", "1 Military", "1999-09-08", "No", "104264547", "454125458"};
		String [] b = {"b", "b", "0", "2 Military", "1998-05-08", "Teacher", "004564547", "724574459"};
		String [] c = {"c", "c", "0", "3 Military", "1970-06-08", "Rest", "100425517", "866981139"};
		String [] d = {"d", "d", "1", "4 Military", "1980-07-08", "Prof", "165426447", "178425589"};
		String [] e = {"e", "e", "1", "5 Military", "1990-08-08", "No", "844426547", "421421589"};
		String [] f = {"f", "f", "0", "6 Military", "2000-09-08", "idk", "999424547", "754445849"};
		String [] g = {"g", "g", "1", "7 Military", "2010-10-08", "Student", "105664547", "545254589"};
		String [] h = {"h", "h", "0", "8 Military", "1986-11-08", "CEO", "100644547", "654464589"};
		String [] i = {"i", "i", "1", "9 Military", "1995-12-08", "Chef", "104455547", "455454589"};
		String [] j = {"j", "j", "0", "10 Military", "1963-10-08", "Jobless", "152447577", "102554589"};
		createAccount(a);
		createAccount(b);
		createAccount(c);
		createAccount(d);
		createAccount(e);
		createAccount(f);
		createAccount(g);
		createAccount(h);
		createAccount(i);
		createAccount(j);
	}
}
