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
				+ "startdate= " + "'" + vals[1] + "' AND "
				+ "enddate= " + "'" + vals[2] + "' ";
		
		sql.insertop(query);
	}
	
	public void changePrice(String[] vals) {
		listing.getListing();
		if (checkAvaible(vals[0], vals[1], vals[2])) {
			System.out.print("Newprice: ");
			String price = sc.nextLine();
			try {
				updatePrice(price, vals);
			} catch (Exception e) {
				System.out.println("You can not change the price. It may be that "
						+ " you do not have that listing or it is unavaible");
			}
		}
		else {
			System.out.println("You can not change the price. It may be that "
					+ " you do not have that listing or it is unavaible");
		}
	}
	
	public void calendarUpdate(String[] vals) {
		String query = "UPDATE calendar SET type = " + "'" + vals[0] + "' "
				+ "WHERE lid = " + "'" + vals[1] + "' AND "
						+ "startdate = " + "'" + vals[2] + "' AND "
								+ "enddate = " + "'" + vals[3] + "'";
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public boolean checkAvaible(String lid, String start, String end) {
		String query = "SELECT * FROM calendar "
				+ "WHERE lid = " + "'" + lid + "' AND "
						+ "type IS NULL AND "
						+ "startdate= " + "'" + start + "' AND "
								+ "enddate= " + "'" + end + "'";
		System.out.println(query);
		try {
			if (sql.executequery(query).get(1).get(0) != null) {
				return true;
			}
		} catch (Exception e) {

		}
		
		return false;
	}
	
	public void changeAvaible(String[] vals) {
		String query  = "SELECT * FROM calendar "
				+ "WHERE lid = " + "'" + vals[0] + "' AND "
				+ "startdate = " + "'" + vals[1] + "' AND "
						+ "enddate = " + "'" + vals[2] + "' AND"
								+ " (type IS NULL OR type = 1)";
		try {
			CommandLine.printlist(sql.executequery(query));
			if (sql.executequery(query).get(1).get(0) != null) {
				if (sql.executequery(query).get(1).get(4) != null) {
					mergeCalendar(vals[0], vals[1], vals[2]);
				}
				else {
					splitCalendar("1", vals[0], vals[1], vals[2]);
				}
				
				System.out.println("You have succefully change the avaibility "
						+ "of one of your listings.");	
				
			}
			else {
				System.out.println("You can not change avaibility that is"
						+ " not in your listings.");	
			}
		} catch (Exception e) {
			System.out.println("The given input is not in correct format or "
					+ "Days for the given id does not exists.");
		}
	}
	
	
	
	public String getPrice(String lid, String start, String end) {
		String price = "0";
		String query = "SELECT AVG(price)"
				+ "FROM calendar "
				+ "WHERE lid =" + "'" + lid + "' AND "
				+ "startdate >= " + "'" + start + "' AND "
				+ "enddate <= " + "'" + end + "'";
		try {
			price = sql.executequery(query).get(1).get(0);
			
		} catch (Exception e) {

		}
		return price;
	}
	
	public void calendarUpdateID(String[] vals) {
		String query = "UPDATE calendar SET lid = " + "'" + vals[0] + "' "
				+ "WHERE type = null "
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
	
	public void splitCalendar(String user, String id, String start, String end) throws Exception {
		String date;
		System.out.println(start + ":" + end);
		String query = "SELECT MAX(startdate)"
				+ "FROM calendar "
				+ "WHERE lid =" + "'" + id + "' AND "
				+ "type IS NULL AND startdate <= " + "'" + start + "'";
		
			System.out.println(query);
			String firststart = sql.executequery(query).get(1).get(0);
			query = "SELECT MIN(enddate)"
					+ "FROM calendar "
					+ "WHERE lid =" + "'" + id + "' AND "
					+ "type IS NULL AND enddate >= " + "'" + end + "'";
			
			System.out.println(query);
			String secondend = sql.executequery(query).get(1).get(0);
			System.out.println(firststart + ":" + secondend);
			String price = getPrice(id, firststart, secondend);
			
			if (!firststart.equals(start)) {
				date = sql.executequery("SELECT DATE_SUB(" + "\"" + start + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);;
				String[] after = {id, firststart, date, price};
				createCalendar(after);
			}
			
			if (!secondend.equals(end)) {
				date = sql.executequery("SELECT DATE_ADD(" + "\"" + end + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);
				String[] before = {id, date, secondend, price};
				createCalendar(before);	
			}
			
			if (!secondend.equals(end) || !firststart.equals(start)) {
				String[] book = {id, start, end, price};
				createCalendar(book);
				
				String[] update = {user, id, start, end};
				calendarUpdate(update);
				
				query = "DELETE "
						+ "FROM calendar "
						+ "WHERE lid =" + "'" + id + "' AND "
								+ "type IS NULL AND startdate = " + "'" + firststart + "' AND "
								+ " enddate = " + "'" + secondend + "'";
				sql.insertop(query);
			}
			else {
				String[] update = {user, id, start, end};
				calendarUpdate(update);
			}
	}
	

	
	public void mergeCalendar(String id, String start, String end) throws Exception {
		String query = "SELECT MAX(enddate)"
				+ "FROM calendar "
				+ "WHERE lid =" + "'" + id + "' AND "
				+ "type IS NOT NULL AND enddate < " + "'" + start + "'";
		
			System.out.println(query);
			String firstend = sql.executequery(query).get(1).get(0);
			
			query = "SELECT MIN(startdate)"
					+ "FROM calendar "
					+ "WHERE lid =" + "'" + id + "' AND "
					+ "type IS NOT NULL AND startdate > " + "'" + end + "'";
			
			System.out.println(query);
			String secondstart = sql.executequery(query).get(1).get(0);
			System.out.println(firstend + " : " + secondstart);
			if (firstend != null) {
				firstend = sql.executequery("SELECT DATE_ADD(" + "\"" + firstend + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);
			}
			else {
				firstend = sql.executequery("SELECT MIN(startdate) FROM calendar WHERE lid= " + "'" + id + "'").get(1).get(0);
			}
			
			if (secondstart != null) {
				secondstart = sql.executequery("SELECT DATE_ADD(" + "\"" + firstend + "\"" + ", INTERVAL 1 DAY)").get(1).get(0);
			}
			
			else {
				secondstart = sql.executequery("SELECT MAX(enddate) FROM calendar WHERE lid= " + "'" + id + "'").get(1).get(0);
			}

			query = "DELETE "
					+ "FROM calendar "
					+ "WHERE lid =" + "'" + id + "' AND "
					+ "startdate >= " + "'" + firstend + "' AND "
					+ "enddate <= " + "'" + secondstart + "'";
				
				
			sql.insertop(query);
			String price = getPrice(id, firstend, secondstart);
			String[] newSet = {id, firstend, secondstart, price};
			createCalendar(newSet);
			
	}

}
