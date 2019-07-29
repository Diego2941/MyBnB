package database;

import java.util.Scanner;

public class Calendar {
	private Scanner sc = null;
	private SQLop sql = null;
	private Listing listing = null;
	public Calendar(Scanner scan, SQLop sqls, Listing lid) {
		sc = scan;
		sql = sqls;
		listing = lid;
	}
	
	public void updatePrice(String price, String[] vals) throws Exception {
		String query = "UPDATE calendar SET price= " + "'" + price + "' "
				+ "WHERE lid= " + "'" + vals[0] + "' AND "
				+ "(startdate BETWEEN " + "'" + vals[1] + "'AND '"
				+ vals[2] + "')";
		
		sql.insertop(query);
	}
	
	public void changePrice(String[] vals) {
		if (checkAvaible("OR avaible = '1') ", vals[0], vals[1], vals[2])) {
			try {
				splitCalendar("1", vals[0], vals[1], vals[2]);
				makeAvaible(vals[0], vals[1], vals[2]);
				System.out.print("Newprice(number): ");
				String price = sc.nextLine();
				updatePrice(price, vals);
				System.out.println("You have succesfully change the price!.");
			} catch (Exception e) {
				System.out.println("You can not change the price you have wrong format.");
			}
		}
		else {
			System.out.println("You can not change the price. It may be that "
					+ " you do not have that listing or it is unavaible");
		}
	}
	
	public void calendarUpdate(String[] vals) {
		String query = "UPDATE calendar SET avaible = " + "'" + vals[0] + "' "
				+ "WHERE lid = " + "'" + vals[1] + "' AND "
						+ "startdate = " + "'" + vals[2] + "' AND "
								+ "enddate = " + "'" + vals[3] + "'";
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public boolean checkAvaible(String use, String lid, String start, String end) {
		String from = "SELECT MIN(startdate) FROM calendar "
				+ "WHERE lid = " + "'" + lid + "' "
				+ "AND ('" + start + "' BETWEEN startdate AND enddate)";
		
		String to = "SELECT MAX(enddate) FROM calendar "
				+ "WHERE lid = " + "'" + lid + "' "
						+ "AND ('" + end + "' BETWEEN startdate AND enddate)";

		
		try {
			String first = sql.executequery(from).get(1).get(0);
			String second = sql.executequery(to).get(1).get(0);
			
			String query = "SELECT * FROM calendar "
					+ "WHERE lid = " + "'" + lid + "' "
					+ "AND (enddate BETWEEN " + "'" + first + "'AND '"
							+ second + "')";
			
			String query1 = "SELECT * FROM calendar "
					+ "WHERE lid = " + "'" + lid + "' AND (avaible IS NULL " + use
					+ "AND (enddate BETWEEN " + "'" + first + "'AND '"
							+ second + "')";
			
			if (sql.executequery(query).get(1).get(0) != null && sql.executequery(query).size() == sql.executequery(query1).size()) {
				
				return true;
			}
		} catch (Exception e) {

		}
		
		return false;
	}
	
	public boolean checkYourCalendar(String lid, String start, String end) {
		String query = "SELECT * FROM calendar "
				+ "WHERE lid = " + "'" + lid + "' AND "
						+ "startdate >= CURDATE() AND "
						+ "avaible =0 AND "
						+ "startdate= " + "'" + start + "' AND "
								+ "enddate= " + "'" + end + "'";
		
		try {
			if (sql.executequery(query).get(1).get(0) != null) {
				return true;
			}
		} catch (Exception e) {

		}
		
		return false;
	}
	
	public void changeAvaible(String[] vals) {
		try {
			if (checkAvaible("OR avaible = '1') ", vals[0], vals[1], vals[2])) {
				System.out.println("Do you want it to be avaible (1) or unavaible(any)");
				String query = sc.nextLine();
				splitCalendar("1", vals[0], vals[1], vals[2]);
				if (query.equals("1")) {
					makeAvaible(vals[0], vals[1], vals[2]);	
				}
				System.out.println("You have succefully change the avaibility "
						+ "of one of your listings.");
			}
			else {
				System.out.println("You can not change avaibility that is"
						+ " not in your listings or are booked by renters.");	
			}
		} catch (Exception e) {
			System.out.println("The given input is not in correct format or "
					+ "Days for the given id does not exists.");
		}
	}
	
	
	
	public String getPrice(String lid, String start) {
		String price = "0";
		String query = "SELECT price "
				+ "FROM calendar "
				+ "WHERE lid =" + "'" + lid + "' AND "
				+ "('" + start + "' BETWEEN startdate AND enddate)";
		try {
			price = sql.executequery(query).get(1).get(0);
			
		} catch (Exception e) {

		}
		return price;
	}
	
	public void calendarUpdateID(String[] vals) {
		String query = "UPDATE calendar SET lid = " + "'" + vals[0] + "' "
				+ "WHERE avaible = null "
						+ "startdate = " + "'" + vals[1] + "' AND "
								+ "enddate = " + "'" + vals[2] + "'";
		try {
			sql.insertop(query);
		} catch (Exception e) {
			System.out.println("ID can not be updated.");
		}
	}
	
	public void createCalendar(String[] vals) {
		String query = "INSERT INTO calendar(lid, startdate, "
				+ "enddate, price) VALUES(";
		query = CommandLine.getQuery(query, vals);
		
		try {
			sql.insertop(query);
		} catch (Exception e) {
			System.out.println("Date format for calendar was wrong");
		}
	}
	
	public void splitCalendar(String use, String id, String start, String end) throws Exception {
		String price, price2, date;
		String temp = " avaible IS NULL ";
		if (use.equals("1")) {
			temp = " (avaible IS NULL OR avaible = 1) ";
		}
		String query = "SELECT MIN(startdate) FROM calendar "
				+ "WHERE lid = " + "'" + id + "' AND " + temp
				+ "AND ('" + start + "' BETWEEN startdate AND enddate)";
		
			String from = sql.executequery(query).get(1).get(0);
			query =  "SELECT MAX(enddate) FROM calendar "
					+ "WHERE lid = " + "'" + id + "' AND " + temp
					+ "AND ('" + end + "' BETWEEN startdate AND enddate)";
			
			
			String to = sql.executequery(query).get(1).get(0);
			price = getPrice(id, from);
			price2 = getPrice(id, to);
			
			query = "DELETE "
					+ "FROM calendar "
					+ "WHERE lid = " + "'" + id + "' AND " + temp
					+ "AND (startdate BETWEEN " + "'" + from + "'AND '"
							+ to + "')";
			
			sql.insertop(query);
			
			if (!from.equals(start)) {
				date = sql.executequery("SELECT DATE_SUB(" + "\"" + start + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);;
				String[] after = {id, from, date, price};
				createCalendar(after);
			}
			
			if (!to.equals(end)) {
				date = sql.executequery("SELECT DATE_ADD(" + "\"" + end + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);
				String[] before = {id, date, to, price2};
				createCalendar(before);	
			}
			
				String[] newone = {id, start, end, price};
				createCalendar(newone);
				String[] update = {use, id, start, end};	
				calendarUpdate(update);

	}
	

	
	public void makeAvaible(String id, String start, String end) throws Exception {
			String query;
			String price = getPrice(id, start);
			query = "DELETE "
					+ "FROM calendar "
					+ "WHERE lid =" + "'" + id + "' "
					+ "AND startdate = " + "'" + start + "' AND "
					+ "enddate='" + end + "'";
				
			sql.insertop(query);
			String[] newSet = {id, start, end, price};
			createCalendar(newSet);
//			
	}

}
