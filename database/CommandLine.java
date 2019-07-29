package database;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class CommandLine {
	private Scanner sc = null;
	private SQLop sql = null;
	private User user = null;
	private Listing listing = null;
	private Booking booking = null;
	private Amenity amenity = null;
	private Calendar calendar = null;
	private Report report = null;
//	private Search search = null;
	private String admin = "Admin";
	private String adminpassword = "password";
	private String username = "";
	public static String userid = "";
	
	public boolean startSession() {
		boolean success = true;
		if (sc == null) {
			sc = new Scanner(System.in);
		}
		if (sql == null) {
			sql = new SQLop();
		}
		if (user == null) {
			user = new User(sc, sql);
		}
		if (listing == null) {
			listing = new Listing(sc, sql);
		}
		if (amenity == null) {
			amenity = new Amenity(sc, sql);
		}
		if (calendar == null) {
			calendar = new Calendar(sc, sql, listing);
		}
		if (booking == null) {
			booking = new Booking(sc, sql, calendar);
		}
		if (report == null) {
			report = new Report(sc, sql);
		}
//		if (search == null) {
//          search = new Search(sc, sql);
//      }
		try {
			success = sql.connect(this.getCredentials());
		} catch (ClassNotFoundException e) {
			success = false;
			System.err.println("Establishing connection triggered an exception!");
			e.printStackTrace();
			sc = null;
			sql = null;
			user = null;
			listing = null;
			booking = null;
			amenity = null;
			calendar = null;
			report = null;

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
		user = null;
		listing = null;
		booking = null;
		amenity = null;
		calendar = null;
		report = null;
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
//			MockData.mockUser(user);
//			MockData.mockListing(listing);
//			MockData.mockCalendar(calendar);
//			MockData.mockAmenity(amenity);
//			MockData.mockBooking(booking);
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
		            		adminOperation();
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
	
	public void userOperation() {
		String[] user = login();
		String query = "SELECT uid, name, utype\n"
				+ "FROM cscc43db.user \n"
				+ "WHERE name= '" + user[0]
				+ "' AND password= "
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
	
	public void adminOperation() {
		String[] user = login();
		if (user[0].equals(admin) && user[1].equals(adminpassword)){
			reportOperation();
		}
		else {
			System.out.println("The username or password is incorrect"
					+ "please try again!.");
		}
		
	}
	
	public void renterOperation() {
	      // ask for inputs
	      String line = "";
	      while(! line.equalsIgnoreCase("logout")) {
	    	  System.out.println("Hi, " + username + " ! \n"
		              + "Please enter which operations to perform( " + "'h'" + " for help " + "'back'" + " for previos page):\n");
		      line = sc.nextLine();
	          switch(line) {
		          case "h":
	          			help("Renter");
	          			break;
		          
		          case "1":
		        	  
		             
		              break;
		          case "2":
		        	  booking.checkBooking("0");

//		            String query;
//		            System.out.println("1. Search by distance\n"
//		                + "2. Search by postal code\n"
//		                + "3. Search by address");
//		            System.out.println("Please enter which option you want to use for searching");
//		            line = sc.nextLine();
//		            switch(line) {
//		              case "1":
//		                query = search.searchByLocation();
//		                search.filterByAmenities(query);
//		                break;
//		              case "2":
//		                query = search.searchByPostal();
//		                break;
//		              case "3":
//		                query = search.searchByAddress();
//                        break;
//		              default:
//		                System.out.println("Invalid report. Back to previous page");
//		                break;
//		            }
		        	  
		              break;
		              
		          case "3":
		              bookingInput();
		              break;
		          
		          case "4":
		        	  booking.checkBooking("0");
		        	  booking.cancelBooking("0", idAndTimeInput());
		              break;
		          
		          case "5":
		        	  booking.prevBooking("0");
		        	  
		              break;
		          
		          case "6":
		        	  commentRankInput("rentercomment");
		        	  break;
		        	  
		          case "7":
		        	  commentRankInput("hostrank");
		        	  break;

		          case "8":
		        	  line = deleteInput();
		        	  break;
		          
		          case "logout":
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
	      while(! line.equalsIgnoreCase("logout")) {
	    	  System.out.println("Hi, " + username + " ! \n"
		              + "Please enter which operations to perform( " + "'h'" + " for help " + "'back'" + " for previos page):\n");
	    	  line = sc.nextLine();
	          switch(line) {
	          	  case "h":
	          		   help("Host");
	          		   break;
	          		   
		          case "1":
		        	  listing.getListing();
		              break;
		              
		          case "2":
		        	  listing.getListing();
		        	  booking.checkBooking(lidInput());
		              break;
		              
		          case "3":
		        	  String[] vals = listingInput();
						String lid = listing.getLd(vals);
						if (!lid.equals("0")){
							ammenitiesInput(lid);
							calendarInput(lid);
						}
		              break;
		              
		          case "4":
		        	  createListByToolKit();
		              break;
		              
		          case "5":
		        	  listing.getListing();
		        	  booking.cancelBooking("1", idAndTimeInput());
		              break;
		              
		          case "6":
		        	  listing.getListing();
		        	  calendar.changePrice(idAndTimeInput());
		        	  break;
		        	  
		          case "7":
		        	  listing.getListing();
		        	  calendar.changeAvaible(idAndTimeInput());
		        	  break;
		        	  
		          case "8":
		        	  booking.prevBooking(lidInput());
		        	  break;
		        	  
		          case "9":
		        	  commentRankInput("hostcomment");
		        	  break;
		        	  
		          case "10":
		        	  commentRankInput("renterrank");
                    break;
                    
		          case "11":
		        	  listing.getListing();
		        	  listing.removeListing();
		        	  break;
		        	  
		          case "12":
		        	  line = deleteInput();
		        	  break;
		            
		          case "logout":
		        	  System.out.println("Signing out.\n");
		        	  username = "";
		        	  userid = "";
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
		        	report.bookingSpecificDateCityReport();
		              break;
		          case "2":
		        	  report.bookingSpecificDatePostal();
		              break;
		          case "3":
		        	  report.listingCountry();
		              break;
		          case "4":
		        	  report.listingCountryCity();
		              break;
		          case "5":
		        	  report.listingCountryCityPost();
		              break;
		          case "6":
		        	  report.rankHostPerCountry();
		              break;
		          case "7":
		        	  report.rankHostByCity();
		              break;
		          case "8":
		        	  report.commercialHost();
		              break;
		          case "9":
		        	  report.rankRenterInTime();
		              break;
		          case "10":
		        	  report.rankRenterTimePerCity();
		              break;
		          case "11":
		            report.largeCancelInYear();
	                break;
		          case "12":
                  report.renterPopularComments();
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
	
	public void commentRankInput(String use) {
		 System.out.print(use + ": ");
	     String comment = sc.nextLine();
	     String[] vals = idAndTimeInput();
	     String bid = booking.getBid(vals);
	     System.out.println(bid);
	     if (booking.isPrevS(bid, vals[0])){
	    	 user.addRankComment(use, comment, bid);
	     }
	     else {
	    	 System.out.print("You can not comment or rank a booking that has not yet finished.");
	     }
	}
	
	private void createListByToolKit() {
	  String[] vals = new String[9];
	  String apply;
	  System.out.println("Welcome to Host ToolKit!");
	  System.out.println("Please enter the type of listing");
	  vals[2] = sc.nextLine();
	  System.out.println("Please enter the city");
      vals[4] = sc.nextLine();
      System.out.println("Please enter the country");
      vals[5] = sc.nextLine();
      String query = "SELECT AVG(price) FROM listing NATURAL JOIN calendar "
          + "Where ltype = '" + vals[2] + "' and city = '" + vals[4] + "' and country = '"
          + vals[5] + "'";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        String price = ans.get(1).get(0);
        if (price == null) {
          System.out.println("No suggestion price, since there is no similar listing before");
          System.out.println("Please enter the prefer anually price: ");
          price = sc.nextLine();
        }else {
          System.out.println("The average price of similar listing in this "
              + "city is: " + price);
          System.out.println("Do you want to apply this price annually for your listing?"
              + " Enter (y/n): ");
          apply = sc.nextLine();
          while (!(apply.equals("y") || apply.equals("n"))) {
            System.out.println("Invalid input, please enter y or n: ");
            apply = sc.nextLine();
          }
          if (apply.equals("n")) {
            System.out.println("Please enter the prefer anually price: ");
            price = sc.nextLine();
          }
        }
        System.out.println("Now please finalize the information for new listing: ");
        System.out.print("Name: ");
        vals[0] = sc.nextLine();
        System.out.print("Address: ");
        vals[3] = sc.nextLine();
        System.out.print("Postal Code: ");
        vals[6] = sc.nextLine();
        System.out.print("Latitude: ");
        vals[7] = sc.nextLine();
        System.out.print("Longitude: ");
        vals[8] = sc.nextLine();
        vals[1] = userid;
        query = "SELECT amenities from amenity natural join listing where "
            + "ltype = '" + vals[2] + "' and city = '" + vals[4] + "' and country = '"
            + vals[5] +  "' group by amenities having count(*) = (SELECT "
            + "count(*) FROM listing where ltype = '" + vals[2] + "' and "
                + "city = '" + vals[4] + "' and country = '"
            + vals[5] + "')";
        try {
          ans = sql.executequery(query);
          System.out.println("Suggest amenities from similar listing (empty if there is no suggestion)");
          printlist(ans);
        }catch (Exception e) {
          System.out.println("Wrong input format");
        }
        listing.createListing(vals);
        String lid = listing.getLd(vals);
        ammenitiesInput(lid);
        String[] calendarValue = new String[4];
        calendarValue[0] = lid;
        calendarValue[1] = "" + getdate();
        calendarValue[2] = "" + getdate().plusYears(1);
        calendarValue[3] = price;
        calendar.createCalendar(calendarValue);
        System.out.println("Listing successfully created!\n");
      } catch (Exception e) {
        System.out.println("wrong input format");
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
                    + "hostrank int(1), "
                    + "renterrank int(1), "
                    + "PRIMARY KEY (bid), "
         			+ "FOREIGN KEY (uid) REFERENCES user(uid)"
         			+ " ON DELETE CASCADE, "
         			+ "FOREIGN KEY (lid) REFERENCES listing(lid)" 
         			+ " ON DELETE CASCADE)";
            
         vals[3] = "CREATE TABLE IF NOT EXISTS "
                    + "calendar(lid int, "
                    + "startdate date, "
                    + "enddate date, "
                    + "price float, "
                    + "avaible bool, "
                    + "PRIMARY KEY (lid, startdate, enddate), "
                    + "FOREIGN KEY (lid) REFERENCES listing(lid)"
                    + " ON DELETE CASCADE)";
        
         vals[4] = "CREATE TABLE IF NOT EXISTS "
        		+ "amenity(lid int, "
        		+ "amenities VARCHAR(255), "
        		+ "PRIMARY KEY (lid, amenities), "
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
	 
	 public String deleteInput() {
		 System.out.println("To delete your account press (y) !!!.: ");
		 String ans = sc.nextLine();
		 if(ans.equals("y")) {
			 user.deleteUser();
			 return "";
		 }
		 return "back";
		 
	 }
	 
	public String lidInput() {
			System.out.println("Listing ID: ");
			return sc.nextLine();
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
		user.createAccount(vals);
	}
	
	public String[] idAndTimeInput() {
		String[] vals = new String[3];
		System.out.print("ListingID ");
		vals[0] = sc.nextLine();
		System.out.print("Checkin: ");
		vals[1] = sc.nextLine();
		System.out.print("Checkout: ");
		vals[2] = sc.nextLine();
		return vals;
	}
	
	public void bookingInput() {
		String[] vals = new String[4];
		String[] temp = idAndTimeInput();
		vals[0] = temp[0];
		vals[2] = temp[1];
		vals[3] = temp[2];
		vals[1] = userid;
		booking.createBooking(vals);
	}
	
	public String[] listingInput() {
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
		listing.createListing(vals);
		return vals;
	}
	
	public void ammenitiesInput(String lid) {
		System.out.println("Amenities: ");
		String[] vals = sc.nextLine().split(",");
		amenity.createAmmenities(vals, lid);
	}
	
	public String[] calendarUpdateInput(){
		String[] vals = new String[4];
		System.out.println("ListingID: ");
		vals[1] = sc.nextLine();
		System.out.println("Start Date: ");
		vals[2] = sc.nextLine();
		System.out.println("End Date: ");
		vals[3] = sc.nextLine();
		vals[0] = "1";
		return vals;
	}
	
	public void calendarInput(String lid) {
		String vals[] = new String[4];
		System.out.println("Anual for a year starting from today: ");
		String year = sc.nextLine();
		vals[0] = lid;
		vals[1] = "" + getdate();
		vals[2] = "" + getdate().plusYears(1);
		vals[3] = year;
		calendar.createCalendar(vals);
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
		              + "12. (most popular comments from renters)\n"
		              + "back. (back to previous page):\n======================================";
				break;
				
			case "Renter":
				ans = "1. (search)\n"
				        + "2. (check your bookings)\n"
				        + "3. (create booking)\n"
				        + "4. (cancel booking)\n"
				        + "5. (check previous bookings)\n"
				        + "6. (comment)\n"
				        + "7. (rate)\n"
				        + "8. (delete account)"
				        + "logout. (logout account):";
				break;
				
			case "Host":
				ans = "1. (check your listings)\n"
						+ "2. (check booking by lid)\n"
			            + "3. (create listing)\n"
			            + "4. (create listing by Host ToolKit)\n"			              
			            + "5. (cancel booking)\n"
			            + "6. (change price)\n"
			            + "7. (change availability)\n"
			            + "8. (check previos bookings by lid)\n"
			            + "9. (comment)\n"
			            + "10. (rate)\n"
			            + "11. (remove listing)\n"
			            + "12. (delete account)\n"
			            + "logout. (logout account):";
				break;
				
			default:
				ans = "sigup. (Create account)\n"
					+ "login. (User operation)\n"
					+ "reports. (Admin operations)\n"
					+ "exit. (exit the program):\n";
				break;
		}

		System.out.println(ans);
	}
	
	public static String getQuery(String query, String[] vals) {
		int counter;
		for (counter = 0; counter < vals.length - 1; counter++) {
			query = query.concat("'" + vals[counter] + "',");
		}
		query = query.concat("'" + vals[counter] + "');");
		return query;
	}
	
	public LocalDate getdate() {
		LocalDate myObj = LocalDate.now();
		return myObj;
	}
	
	public static void printlist(ArrayList<ArrayList<String>> lst) {
	  String str = "----------------------";
		System.out.println(new String(new char[lst.get(0).size()]).replace("\0", str));
		for (int i = 0; i < lst.size(); i++) {
			for (int j = 0; j < lst.get(i).size(); j++) {
				System.out.print(String.format("|%-20s|", lst.get(i).get(j)));
			}
			System.out.println("");
		}
		System.out.println(new String(new char[lst.get(0).size()]).replace("\0", str));
	}
	
}
