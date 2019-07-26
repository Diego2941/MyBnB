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

	 
	 public ArrayList<ArrayList<String>> executequery(String query)throws Exception{
		 	ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
		    	ResultSet rs = st.executeQuery(query);
		    	ResultSetMetaData rsmd = rs.getMetaData();
		    	int columnCount = rsmd.getColumnCount();
		    	ArrayList<String> temp = new ArrayList<String>();
		    	for (int i = 1; i <= columnCount; i++ ) {
		    	  temp.add(rsmd.getColumnName(i)+":");
		    	}
		    	ans.add(temp);
		    	while(rs.next()) {
		    		temp = new ArrayList<String>();
		    		for (int i = 1; i <= columnCount; i++ ) {
				    	  temp.add(rs.getString(rsmd.getColumnName(i)));
				    	}
		    		ans.add(temp);
		    	}
	    	return ans;
	 }
}
