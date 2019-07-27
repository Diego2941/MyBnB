package database;

public class MockData {
	
	public static void mock(User user) {
		String [] a = {"a", "a", "1", "1 Military", "1999-09-08", "No", "104264547", "454125458"};
		String [] b = {"b", "b", "0", "2 Military", "1998-05-08", "Teacher", "004564547", "724574459"};
		String [] c = {"c", "c", "0", "3 Military", "1970-06-08", "Rest", "100425517", "866981139"};
		String [] d = {"d", "d", "1", "4 Military", "1980-07-08", "Prof", "165426447", "178425589"};
		String [] e = {"e", "e", "1", "5 Military", "1990-08-08", "No", "844426547", "421421589"};
		String [] f = {"f", "f", "0", "6 Military", "2000-09-08", "idk", "999424547", "754445849"};
		String [] g = {"g", "g", "1", "7 Military", "2010-10-08", "Student", "105664547", "545254589"};
		String [] h = {"h", "h", "0", "8 Military", "1986-11-08", "CEO", "100644547", "654464589"};
		String [] i = {"i", "i", "1", "9 Military", "1995-12-08", "Chef", "104455547", "455454589"};
		String [] j = {"j", "j", "0", "10 Military", "1963-10-08", "Jobless", "152447577", "102554589"};
		user.createAccount(a);
		user.createAccount(b);
		user.createAccount(c);
		user.createAccount(d);
		user.createAccount(e);
		user.createAccount(f);
		user.createAccount(g);
		user.createAccount(h);
		user.createAccount(i);
		user.createAccount(j);
	}
}
