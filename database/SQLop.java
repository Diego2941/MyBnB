package database;

import java.sql.*;
import java.util.ArrayList;

public class SQLop {
	private Connection conn = null;
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://localhost:3306/";
	private Statement st = null;
	
	public boolean connect(String[] cred) throws ClassNotFoundException {
		Class.forName(dbClassName);
		boolean success = true;
		String user = cred[0];
		String pass = cred[1];
		String connection = CONNECTION + cred[2];
		try {
			conn = DriverManager.getConnection(connection, user, pass);
			st = conn.createStatement();
		} catch (SQLException e) {
			success = false;
			System.err.println("Connection could not be established!");
			e.printStackTrace();
		}
		return success;
	}
	
	public void disconnect() {
		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Exception occured while disconnecting!");
			e.printStackTrace();
		} finally {
			st = null;
			conn = null;
		}
	}
	
	 public void insertop(String query)throws Exception{
	    	try {
		    	st.executeUpdate(query);
		    	
		    } catch (SQLException e) {
		    	System.err.println(e);
		        System.err.println("Connection error occured!");

		    }
	 }

	 
	 public String[] findUserPass(String query)throws Exception{
		 	String[] ans = new String[2];
	    	try {
		    	ResultSet rs = st.executeQuery(query);
		    	while(rs.next()) {
		    		ans[0] = rs.getString("uid");
		    		ans[1] = rs.getString("name");
		    		ans[2] = rs.getString("utype");
		    	}
		    	
		    } catch (SQLException e) {
		    	System.err.println(e);
		        System.err.println("Connection error occured!");

		    }
	    	return ans;
	 }


  public void reportBookingByDate(String start, String end) {
    try {
      Statement query = conn.createStatement();
      // 1. select from booking
      // 2. set date
      // 3. group by city
      String sql = "SELECT count(*), city FROM booking Natural Join listings WHERE checkin > '"
      + start + "' and checkout < '" + end + "';";
      ResultSet rs = query.executeQuery(sql);
      // find the number of histories in the result
      while (rs.next()) {
        System.out.println(rs.getString(1) + " " + rs.getString(2));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
