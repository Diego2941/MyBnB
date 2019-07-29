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
		String query = "INSERT INTO amenity(lid, amenities) VALUES(" + "'" + lid +"', ";

		try {
			for(int i=0; i < vals.length; i++) {
				sql.insertop(query + "'"+ vals[i] +"')");
			}
			System.out.println("You have succesfully uploaded your Amenities");
		} catch (Exception e) {
			System.out.println("Ammenieites can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
}
