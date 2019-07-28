package database;

import java.util.Scanner;

public class Listing {
	private Scanner sc = null;
	private SQLop sql = null;
	public Listing(Scanner scan, SQLop sqls) {
		sc = scan;
		sql = sqls;
	}
	
	public void getListing() {
		String query = "SELECT *"
				+ "FROM listing "
				+ "WHERE hid = " + "'" + CommandLine.userid + "'";
		try {
			CommandLine.printlist(sql.executequery(query));
		} catch (Exception e) {

			System.out.print("Disi not found any listings in your account!.");
		}
		
	}
	
	public String getLd(String[] vals) {
		String query = "SELECT lid "
				+ "FROM listing "
				+ "WHERE name = " + "'" + vals[0] + "' AND "
				+ "hid = " + "'" + vals[1] + "' AND "
				+ "ltype = " + "'" + vals[2] + "' AND "
				+ "address = " + "'" + vals[3] + "' AND "
				+ "city = " + "'" + vals[4] + "' AND "
				+ "country = " + "'" + vals[5] + "' AND "
				+ "postcode = " + "'" + vals[6] + "' AND "
				+ "latitude = " + "'" + vals[7] + "' AND "
				+ "longitude = " + "'" + vals[8] + "'";
		try {
			
			query = sql.executequery(query).get(1).get(0);
			return query;
		} catch (Exception e) {
			return "0";
		}
	}
	
	public void createListing(String[] vals) {
		String query = "INSERT INTO listing(name, hid, ltype, address, "
				+ "city, country, postcode, latitude, "
				+ "longitude) VALUES(";

		query = CommandLine.getQuery(query, vals);
		try {
			sql.insertop(query);
			System.out.println("Listing was succesfully created!.");
		} catch (Exception e) {
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
			System.out.println("The selected listing was succesfully deleted!.");
		} catch (Exception e) {
			System.out.println("Listing can not be deleted!.");
		}
		
	}
}
