package database;

import java.util.Scanner;

public class Amenity {
	private Scanner sc = null;
	private SQLop sql = null;
	
	public Amenity(Scanner scan, SQLop sqls) {
		sc = scan;
		sql = sqls;
	}
	
	public void createAmmenities(String[] vals, String lid) {
		String query = "INSERT INTO amenity(lid, wifi, tv, parking, gym, "
				+ "pool, kitchen) VALUES(" + "'" + lid +"', ";

		query = CommandLine.getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ammenieites can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
}
