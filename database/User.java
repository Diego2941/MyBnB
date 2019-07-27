package database;

import java.util.Scanner;

public class User {
	private Scanner sc = null;
	private SQLop sql = null;
	public User(Scanner scan, SQLop sqls) {
		sc = scan;
		sql = sqls;
	}
	
	public void createAccount(String[] vals) {
		String query = "INSERT INTO user(name, password, utype, "
				+ "uaddress, birth, ocupation, sin, payment) VALUES(";
		query = CommandLine.getQuery(query, vals);
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Account can not be created may be cause "
					+ "by the format please try again!.");
		}
	}
	
	public void deleteUser() {
		String query = "DELETE "
				+ "FROM user "
				+ "WHERE uid =" + "'" + CommandLine.userid + "'";
		
		try {
			sql.insertop(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User can not be deleted.");
		}
		
	}
}
