package database;

import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
	private Scanner sc = null;
	private SQLop sql = null;
	private Calendar calendar = null;
	public Booking(Scanner scan, SQLop sqls,  Calendar cal) {
		sc = scan;
		sql = sqls;
		calendar = cal;
	}
	
	public void createBooking(String[] vals){
		try {
			if (calendar.checkAvaible(") ", vals[0], vals[2], vals[3])) {
				calendar.splitCalendar("0", vals[0], vals[2], vals[3]);
				String query = "INSERT INTO booking(lid, uid, checkin, checkout"
						+ ") VALUES(";
				
				query = CommandLine.getQuery(query, vals);
				sql.insertop(query);
				System.out.println("Your booking has succesfully being completed!.");
			}
			else {
				System.out.println("Sorry the listing you choose is not avaible in the given dates!.");
			}
		} catch (Exception e) {
			System.out.println("Booking can not be created may be cause "
					+ "by the format please try again or isting"
					+ "does not exist!.");
		}
	}
	
	public String getBid(String[] vals) {
		String query = "SELECT bid "
				+ "FROM booking "
				+ "WHERE lid =" +"'" + vals[0] + "' AND "
				+ "checkin = " +"'" + vals[1] + "' AND "
				+ "checkout = " +"'" + vals[2] + "'";
		try {
			return sql.executequery(query).get(1).get(0);
			
		} catch (Exception e) {
			return "0";
		}
	}
	
	public void checkBooking(String lid) {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE checkin >= CURDATE() AND "
				+ "cancelation IS NULL AND "
				+ "(uid = " + "'" + CommandLine.userid + "' OR "
				+ "lid IN (SELECT lid FROM "
				+ "listing WHERE hid = " + "'" + CommandLine.userid + "' AND "
				+ "lid =" + "'" + lid + "'))";
		try {
			CommandLine.printlist(sql.executequery(query));
		} catch (Exception e) {
			System.out.println("You dont have booking avaible.");
		}
	}
	
	public void prevBooking(String lid) {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE checkout < CURDATE() AND "
				+ "cancelation IS NULL AND "
				+ "(uid = " + "'" + CommandLine.userid + "' OR "
				+ "lid IN (SELECT lid FROM "
				+ "listing WHERE hid = " + "'" + CommandLine.userid + "' AND "
				+ "lid =" + "'" + lid + "'))"; 
		try {
			CommandLine.printlist(sql.executequery(query));
		} catch (Exception e) {
			System.out.println("You dont have previous bookings!.");
		}
	}
	
	public boolean isPrevS(String bid, String lid) {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE checkout < CURDATE() AND "
				+ "bid = " + "'" + bid + "' AND "
				+ "cancelation IS NULL AND "
				+ "(uid = " + "'" + CommandLine.userid + "' OR "
				+ "lid IN (SELECT lid FROM "
				+ "listing WHERE hid = " + "'" + CommandLine.userid + "' AND "
				+ "lid =" + "'" + lid + "'))"; 
		
		try {
			 if (sql.executequery(query).get(1).get(0) != null){
				 return true;
			 }
		} catch (Exception e) {
			System.out.println("You dont have previos bookings!.");
		}
		return false;
	}
	
	
	public void cancelBooking(String num, String[] vals) {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE checkin >= CURDATE() AND "
				+ "cancelation IS NULL AND "
				+ "(uid = " + "'" + CommandLine.userid + "' OR "
				+ "lid IN (SELECT lid FROM "
				+ "listing WHERE hid = " + "'" + CommandLine.userid + "' AND "
						+ "lid =" + "'" + vals[0] + "'))";
		try {
			
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			if (ans.get(1).get(0) != "null") {
				if (calendar.checkYourCalendar(vals[0], vals[1], vals[2])) {
					calendar.makeAvaible(vals[0], vals[1], vals[2]);
					query = "UPDATE booking SET cancelation = " + "'" + num + "'"
						+ "WHERE checkin = " + "'" + vals[1] + "'" + " AND "
										+ "checkout = " + "'" + vals[2] + "' AND"
												+ "(uid = " + "'" + CommandLine.userid + "' OR "  
												+ "lid IN (SELECT lid FROM "
												+ "listing WHERE hid = " + "'" + CommandLine.userid + "' AND "
														+ "lid =" + "'" + vals[0] + "'))"; 
					sql.insertop(query);
					System.out.println("You have succesfully cancel your booking!.");
				}
				else {
					System.out.println("You did not select any of your existing bookings.");
				}
			}
			else {
				System.out.println("You do not have any bookings.");
			}
		} catch (Exception e) {
			System.out.println("You must select a correct date.");
		}
	}
}
