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
		calendar.splitCalendar("1", vals[0], vals[2], vals[3]);
		String query = "INSERT INTO booking(lid, uid, checkin, checkout, "
				+ "hostcomment, rentercomment) VALUES(";
		query = CommandLine.getQuery(query, vals);
		try {
				if (calendar.checkAvaible(vals[0], vals[2], vals[3])) {
					sql.insertop(query);
				}
				else {
					System.out.println("Sorry the listing you choose is not avaible in the given dates.");
				}
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Booking can not be created may be cause "
					+ "by the format please try again or isting"
					+ "does not exist!.");
		}
	}
	
	public void cancelBooking(String num, String[] vals) {
		String query = "SELECT * "
				+ "FROM booking "
				+ "WHERE uid = " + "'" + CommandLine.userid + "' AND " 
				+ "checkin >= CURDATE() AND "
				+ "cancelation IS NULL";
		try {
			ArrayList<ArrayList<String>> ans = sql.executequery(query);
			CommandLine.printlist(ans);
			if (ans.get(1).get(0) != "null") {
				if (calendar.checkAvaible(vals[0], vals[1], vals[2])) {
					calendar.mergeCalendar(vals[0], vals[1], vals[2]);
					query = "UPDATE booking SET cancelation = " + "'" + num + "'"
						+ "WHERE uid = " + "'" + CommandLine.userid + "' OR"
								+ " lid = " + "'" + CommandLine.userid + "' AND "
								+ " checkin = " + "'" + vals[1] + "'" + " AND "
										+ "checkout = " + "'" + vals[2] + "'"; 
					sql.insertop(query);
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
