package database;

import java.util.ArrayList;
import java.util.Scanner;

public class Report {
	private Scanner sc = null;
	private SQLop sql = null;
	public Report(Scanner scan, SQLop sqls) {
		sc = scan;
		sql = sqls;
	}
	
    public void rankRenterTimePerCity() {
        String start, end;
        System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
        start = sc.nextLine();
        System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
        end = sc.nextLine();
        String query = "SELECT uid, city, count(*) as numBookings FROM booking Natural Join listing WHERE checkin >= '"
                 + start + "' and checkout < '" + end + "' and cancelation IS NULL group by uid, city order by numBookings DESC";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Rank renters by number of bookings within a specified time per city:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void rankRenterInTime() {
        String start, end;
        System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
        start = sc.nextLine();
        System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
        end = sc.nextLine();
        String query = "SELECT uid, count(*) as numBookings FROM booking WHERE checkin >= '"
                 + start + "' and checkout <= '" + end + "' and cancelation IS NULL group by uid order by numBookings DESC";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Rank renters by number of bookings within a specified time:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void commercialHost() {
        String query = "SELECT b.hid, b.city, b.country FROM (SELECT count(*) "
            + "as countTotal, city, country From listing Group BY city, country) "
            + "a, (SELECT count(*) as countHost, hid, city, country From listing "
            + "Group BY hid, city, country) b WHERE a.city = b.city and a.country "
            + "= b.country and b.countHost >= (a.countTotal/10)";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Commercial hosts by country and city:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println(e);
       }
    }

      public void rankHostByCity() {
        String query = "SELECT hid, city, country, count(*) as numListings FROM "
            + "listing group by city, country, hid order by numListings DESC, hid";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Host rank by number of listings by city:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void rankHostPerCountry() {
        String query = "SELECT hid, country, count(*) as "
            + "numListings FROM listing group by country, hid ORDER BY "
            + "numListings DESC, hid;";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Host rank by number of listings per country:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void listingCountryCity() {
        String query = "SELECT count(*) as '#listing', city, country FROM listing "
            + "group by country, city order by country";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Total number of listings by country and city:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void listingCountryCityPost() {
        String query = "SELECT count(*) as '#listing', postcode, city, country "
            + "FROM listing group by country, city, postcode order by country";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Total number of listings by country, city and postal code:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void listingCountry() {
        String query = "SELECT count(*) as '#listing', country FROM listing group by country";
        try {
           ArrayList<ArrayList<String>> ans = sql.executequery(query);
           System.out.println("Total number of listings by country:");
           CommandLine.printlist(ans);
       } catch (Exception e) {
         System.out.println("Does not exist such report format.");
       }
    }

      public void bookingSpecificDatePostal() {
          String start, end;
          System.out.println("Please enter the starting date(yyyy-mm-dd):\n");
          start = sc.nextLine();
          System.out.println("Please enter the ending date(yyyy-mm-dd):\n");
          end = sc.nextLine();
          String query = "SELECT count(postcode) as '#Bookings', postcode, city FROM booking Natural Join listing WHERE checkin >= '"
                   + start + "' and checkout <= '" + end + "' and cancelation is NULL group by city, postcode;";
          try {
             ArrayList<ArrayList<String>> ans = sql.executequery(query);
             System.out.println("Total # of bookings by postal code in specified date range within city:");
             CommandLine.printlist(ans);
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
          	      + start + "' and checkout <= '" + end + "' and cancelation is NULL group by city;";
           try {
  			ArrayList<ArrayList<String>> ans = sql.executequery(query);
  			System.out.println("Total # of bookings in specified date range by city:");
  			CommandLine.printlist(ans);
  		} catch (Exception e) {
  		  System.out.println("Does not exist such report format.");
  		}
  	 }
      
      public void renterPopularComments() {
          String query = "SELECT a.lid, a.rentercomment, a.hostcomment, "
              + "MAX(a.count) as numOfOccurance FROM (SELECT lid, rentercomment, "
              + "hostcomment, count(*) as count FROM booking group by lid, rentercomment order by "
              + "count desc) a WHERE rentercomment IS NOT NULL group by a.lid order by lid";
          try {
             ArrayList<ArrayList<String>> ans = sql.executequery(query);
             System.out.println("Most popular comments from renters");
             CommandLine.printlist(ans);
         } catch (Exception e) {
           System.out.println("Does not exist such report format.");
         }
      }

       public void largeCancelInYear() {
          String year;
          System.out.println("Please enter the year:\n");
          year = sc.nextLine();
          String query = "SELECT uid, utype as 'Renter:0 Host:1', count(*) as numOfCancel FROM booking "
              + "NATURAL JOIN user WHERE cancelation = 0 and checkin >= '"
              + year + "-01-01' and checkout < '"
              + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' and utype = 0 "
                  + "group by uid HAVING numOfCancel = (SELECT MAX(a.count) as "
                  + "numOfCancel FROM (SELECT count(*) as count FROM booking NATURAL"
                  + " JOIN user WHERE cancelation = 0 and checkin >= '"
                  + year + "-01-01' and checkout < '"
                  + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' and "
                      + "utype = 0 GROUP BY uid) a) "
                  + "UNION SELECT uid, utype as 'Renter:0 Host:1', numOfCancel "
                  + "FROM (SELECT hid, count(*) as numOfCancel FROM booking "
                  + "NATURAL JOIN listing WHERE cancelation = 1 and checkin >= '"
                  + year + "-01-01' and checkout < '"
                  + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' "
                      + "group "
                  + "by uid HAVING numOfCancel = (SELECT MAX(a.count) as numOfCancel"
                  + " FROM (SELECT count(*) as count FROM booking NATURAL JOIN user "
                  + "WHERE cancelation = 1 and checkin >= '"
                  + year + "-01-01' and checkout < '"
                  + Integer.toString((Integer.parseInt(year)+1)) + "-01-01' "
                      + " GROUP BY uid) a)) b JOIN user where hid = uid";
          try {
             ArrayList<ArrayList<String>> ans = sql.executequery(query);
             System.out.println("Hosts and renters with the largest number of cancellation within a specified year:");
             CommandLine.printlist(ans);
         } catch (Exception e) {
           System.out.println(e);
         }
      }
}
