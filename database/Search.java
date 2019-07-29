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
    
    public void filterByAmenities(String oldQuery) {
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
    }
  
    public void filterByDate(String oldQuery) {
      System.out.println("Filtering by Date...");
      oldQuery = "(" + oldQuery + ") bd";
      String start, end, query;
      System.out.println("Please enter the latitude");
      start = sc.nextLine();
      System.out.println("Please enter the longitude");
      end = sc.nextLine();
      query = "";
      try {
        ArrayList<ArrayList<String>> ans = sql.executequery(query);
        System.out.println("We found " + (ans.size() - 1) + " listings:");
        CommandLine.printlist(ans);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    
    public void filterByPrice(String oldQuery) {
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
    }
}
