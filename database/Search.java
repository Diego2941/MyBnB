package database;

import java.util.ArrayList;
import java.util.Scanner;

public class Search {
  private Scanner sc = null;
  private SQLop sql = null;
  
  public Search(Scanner scan, SQLop sqls) {
      sc = scan;
      sql = sqls;
  }
  
  public void performSearch() {
    String line;
    String query;
    System.out.println("1. Search by distance\n"
        + "2. Search by postal code\n"
        + "3. Search by address");
    System.out.println("Please enter which option you want to use for searching");
    line = sc.nextLine();
    switch(line) {
      case "1":
        query = searchByLocation();
        applyFilters(query);
        break;
      case "2":
        query = searchByPostal();
        applyFilters(query);
        break;
      case "3":
        query = searchByAddress();
        applyFilters(query);
        break;
      default:
        System.out.println("Invalid report. Back to previous page");
        break;
    }
  }
  
  public void applyFilters(String query) {
    String line, ans;
    Boolean am = true, da = true, pr = true;
    System.out.println("Would you like to apply filter on the result? (y/n): ");
    ans = sc.nextLine();
    while (ans.equals("y") && (am || da || pr)) {
      if (am) {
        System.out.println("1. Filter by Amenities");
      }
      if (da) {
        System.out.println("2. Filter by Date");   
      }
      if (pr) {
        System.out.println("3. Filter by price");
      }
      System.out.println("Please enter your option: ");
      line = sc.nextLine();
      switch(line) {
        case "1":
          query = filterByAmenities(query);
          am = false;
          break;
        case "2":
          query = filterByDate(query);
          da = false;
          break;
        case "3":
          query = filterByPrice(query);
          pr = false;
          break;
          default:
            System.out.println("Invalid input!");
            break;
      }
      System.out.println("Would you like to apply filter on the result? (y/n)");
      ans = sc.nextLine();
    }
    if (!(am || da || pr)) {
      System.out.println("No more filters to apply");
    }
    performRank(query);
  }
  
  private void performRank(String query) {
    String line;
    System.out.println("Would you like to rank the result by price? (y/n)");
    line = sc.nextLine();
    if (line.equals("y")) {
      System.out.println("1. Ascending\n2.Descending");
      System.out.println("Please enter your option:");
      line = sc.nextLine();
      if (line.equals("1")) {
        rankByPriceAsce(query);
      }else if (line.equals("2")) {
        rankByPriceDesc(query);
      }else {
        System.out.println("Invalid Input! Back to previous page");
      }
    }
  }

  public String searchByLocation() {
    System.out.println("Searching by specific location...");
    String lat, lon, distance, query;
    System.out.println("Please enter the latitude");
    lat = sc.nextLine();
    System.out.println("Please enter the longitude");
    lon = sc.nextLine();
    System.out.println("Please enter the distance range for vicinity or \"d\" to use default 20");
    // calculate by squaring the difference between latitude and longitude
    distance = sc.nextLine();
    if (distance.equals("d")) {
      distance = "20";
    }
    query = "SELECT *, (POW(ABS((" + lat + " - latitude)),2) + "
        + "POW(ABS((" + lon + " - longitude)),2)) AS distance FROM listing WHERE(POW"
        + "(ABS((" + lat + " - latitude)),2) + POW(ABS((" + lat + " - longitude)),2)) <= " + distance + " Order "
        + "by distance";
    try {
      ArrayList<ArrayList<String>> ans = sql.executequery(query);
      System.out.println("We found " + (ans.size() - 1) + " listings:");
      CommandLine.printlist(ans);
    } catch (Exception e) {
      System.out.println("Invalid input format. Back to the previous page");
    }
    return query;
  }
  
  public String searchByPostal() {
    System.out.println("Searching by specific Postal Code...");
    String post, range, query = "";
    System.out.println("Please enter the postal code");
    post = sc.nextLine();
    System.out.println("Please enter the range for adjacent postal codes or \"d\" to use default 30");
    range = sc.nextLine();
    if (range.equals("d")) {
      range = "30";
    }
    try {
      int range1 = Integer.parseInt(post) - Integer.parseInt(range);
      int range2 = Integer.parseInt(post) + Integer.parseInt(range);
      query = "SELECT * FROM listing WHERE (postcode between " + range1 + " and " + range2 + ")";
      ArrayList<ArrayList<String>> ans = sql.executequery(query);
      System.out.println("We found " + (ans.size() - 1) + " listings:");
      CommandLine.printlist(ans);
    } catch (Exception e) {
      System.out.println("Invalid input format. Back to the previous page");
    }
    return query;
  }
  
  public String searchByAddress() {
    System.out.println("Searching by specific address...");
    String address, query;
    System.out.println("Please enter the address");
    address = sc.nextLine();
    query = "SELECT * FROM listing WHERE address = '" + address + "'";
    try {
      ArrayList<ArrayList<String>> ans = sql.executequery(query);
      System.out.println("We found " + (ans.size() - 1) + " listings:");
      CommandLine.printlist(ans);
    } catch (Exception e) {
      System.out.println("Invalid input format. Back to the previous page");
    }
    return query;
  }
    
    public String filterByAmenities(String oldQuery) {
      System.out.println("Filtering by amenities...");
      oldQuery = "(" + oldQuery + ") ba";
      String amenities, query;
      System.out.println("Please enter amenities you want, seperate by comma");
      amenities = sc.nextLine();
      String[] amenity = amenities.split(",");
      amenities = "";
      for(int i = 0; i < amenity.length; i++) {
        amenities = amenities + "amenities = '" + amenity[i] + "'";
        if (i != amenity.length - 1) {
          amenities = amenities + " or ";
        }
      }
      query = "SELECT * FROM " + oldQuery + " NATURAL JOIN (SELECT lid FROM amenity "
          + "WHERE " + amenities
          + " group by lid Having count(lid) = " + amenity.length + ") a";
      System.out.println(query);
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
      return query;
    }
  
    public String filterByDate(String oldQuery) {
      System.out.println("Filtering by Date...");
      oldQuery = "(" + oldQuery + ") bd";
      String start, end, query;
      System.out.println("Please enter the checkin date");
      start = sc.nextLine();
      System.out.println("Please enter the checkout date");
      end = sc.nextLine();
      query = "SELECT distinct * FROM calendar NATURAL JOIN listing WHERE "
          + "((enddate >= '"+ start +"'AND startdate <= '"+ start +"') OR "
          + "(enddate >= '" + end + "' AND startdate <= '" + end + "') OR "
          + "(enddate <= '" + end + "' AND startdate >= '"+ start +"')) AND "
          + "lid not in (SELECT lid From calendar NATURAL JOIN listing WHERE "
          + "((enddate >= '"+ start +"' AND startdate <= '"+ start +"') OR "
          + "(enddate >= '" + end + "' AND startdate <= '" + end + "') OR "
          + "(enddate <= '" + end + "' AND startdate >= '"+ start +"')) and "
          + "avaible is not null group by lid)";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
      return query;
    }
    
    public String filterByPrice(String oldQuery) {
      System.out.println("Filtering by Price...");
      oldQuery = "(" + oldQuery + ") bp";
      String start, end, query;
      System.out.println("Please enter the prefer lowest price");
      start = sc.nextLine();
      System.out.println("Please enter the prefer highest price");
      end = sc.nextLine();
      query = "SELECT * FROM "+ oldQuery + " NATURAL JOIN calendar WHERE (price between "
          + start + " and " + end + ")";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
      return query;
    }
    
    public String rankByPriceAsce(String oldQuery) {
      String query;
      oldQuery = "(" + oldQuery + ") rbp";
      query = "SELECT * FROM "+ oldQuery + " NATURAL JOIN calendar ORDER BY price";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
      return query;
    }
    
    public String rankByPriceDesc(String oldQuery) {
      String query;
      oldQuery = "(" + oldQuery + ") rbp";
      query = "SELECT * FROM "+ oldQuery + " NATURAL JOIN calendar ORDER BY price desc";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
      return query;
    }
}
