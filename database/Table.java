package database;

public class Table {
	private SQLop sql = null;
	
	public Table(SQLop s){
		sql = s;
	}
	
	 public  void initiateTables() {
		 String[] vals = new String[5];
         vals[0] = "CREATE TABLE IF NOT EXISTS "
                 + "user(uid int NOT NULL AUTO_INCREMENT, "
                 + "name varchar(255), "
                 + "password varchar(16), "
                 + "utype bool, "
                 + "uaddress varchar(255), "
                 + "birth date, "
                 + "ocupation varchar(255), "
                 + "sin int(9), "
                 + "payment int(9), "
                 + "UNIQUE KEY (name, password), "
                 + "PRIMARY KEY (uid))";
         
         vals[1] = "CREATE TABLE IF NOT EXISTS "
                 + "listing(lid int NOT NULL AUTO_INCREMENT, "
                 + "name varchar(255), "
                 + "hid int, "
                 + "ltype varchar(255), "
                 + "address varchar(255), "
                 + "city varchar(255), "
                 + "country varchar(255), "
                 + "postcode int(6), "
                 + "latitude float, "
                 + "longitude float, "
                 + "PRIMARY KEY (lid), "
                 + "FOREIGN KEY (hid) REFERENCES user(uid) "
                 + " ON DELETE CASCADE)";
      
                    
         vals[2] = "CREATE TABLE IF NOT EXISTS "
                    + "booking(bid int NOT NULL AUTO_INCREMENT, "
                    + "lid int, "
                    + "uid int, "
                    + "checkin date, "
                    + "checkout date, "
                    + "cancelation bool, "
                    + "hostcomment varchar(255), "
                    + "rentercomment varchar(255), "
                    + "hostrank int(1), "
                    + "renterrank int(1), "
                    + "PRIMARY KEY (bid), "
         			+ "FOREIGN KEY (uid) REFERENCES user(uid)"
         			+ " ON DELETE CASCADE, "
         			+ "FOREIGN KEY (lid) REFERENCES listing(lid)" 
         			+ " ON DELETE CASCADE)";
            
         vals[3] = "CREATE TABLE IF NOT EXISTS "
                    + "calendar(lid int, "
                    + "startdate date, "
                    + "enddate date, "
                    + "price float, "
                    + "avaible bool, "
                    + "PRIMARY KEY (lid, startdate, enddate), "
                    + "FOREIGN KEY (lid) REFERENCES listing(lid)"
                    + " ON DELETE CASCADE)";
        
         vals[4] = "CREATE TABLE IF NOT EXISTS "
        		+ "amenity(lid int, "
        		+ "amenities VARCHAR(255), "
        		+ "PRIMARY KEY (lid, amenities), "
        		+ "FOREIGN KEY (lid) REFERENCES listing(lid)"
        		+ " ON DELETE CASCADE)";
        
        	try {
        		for(int counter = 0; counter < vals.length; counter++) {
        			sql.insertop(vals[counter]);
        		}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Tables can not be created.");
        }
    }
	 
	public void drop() {
		String[] vals = new String[5];
		vals[0] = "DROP TABLE IF EXISTS amenity";
		vals[1] = "DROP TABLE IF EXISTS calendar";
		vals[2] = "DROP TABLE IF EXISTS booking";
		vals[3] = "DROP TABLE IF EXISTS listing";
		vals[4] = "DROP TABLE IF EXISTS user";
		try {
    		for(int counter = 0; counter < vals.length; counter++) {
    			sql.insertop(vals[counter]);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Tables can not be created.");
		}
	}
}
