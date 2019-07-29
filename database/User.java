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
			System.out.println("You have succesfully created your accouunt!.");
		} catch (Exception e) {
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
			System.out.println("You have succesfully deleted your accouunt!.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User can not be deleted.");
		}
		
	}
	
	public void addRankComment(String user, String comment, String bid) {
		String query = "UPDATE booking SET " + user + " = '" + comment
				+ "' WHERE bid = " + "'" + bid + "'";
		try {
			sql.insertop(query);
			System.out.println("Comment was succesfully added");
		} catch (Exception e) {
			System.out.println("Comment can not be added!.");
		}
	}
}
