package database;

public class MockData {
	
	public static void mockUser(User user) {
		String [] a = {"a", "a", "0", "1 Military", "1998-06-09", "Student", "00000000", "123456789"};
		String [] b = {"b", "b", "0", "2 Military", "1998-05-08", "Teacher", "004564547", "724574459"};
		String [] c = {"c", "c", "0", "3 Military", "1970-06-08", "Rest", "100425517", "866981139"};
		String [] d = {"d", "d", "1", "4 Military", "1980-07-08", "Prof", "165426447", "178425589"};
		String [] e = {"e", "e", "1", "5 Military", "1990-08-08", "No", "844426547", "421421589"};
		String [] f = {"f", "f", "0", "6 Military", "2000-09-08", "idk", "999424547", "754445849"};
		String [] g = {"g", "g", "1", "7 Military", "1999-10-08", "Student", "105664547", "545254589"};
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
	public static void mockBooking(Booking booking, User user) {
      String [] c = {"2", "2", "2016-09-01", "2016-09-11"};
      String [] d = {"2", "3", "2018-10-01", "2018-10-11"};
      String [] e = {"2", "1", "2018-11-01", "2018-11-11"};
      String [] f = {"2", "1", "2018-12-01", "2018-12-11"};
      String [] g = {"2", "1", "2019-01-01", "2019-04-11"};
      String [] h = {"3", "2", "2019-10-01", "2019-11-11"};
      String [] i = {"4", "3", "2019-12-01", "2019-12-11"};
      String [] j = {"5", "1", "2019-10-01", "2019-11-11"};
      String [] k = {"6", "1", "2019-10-01", "2019-11-11"};
      String [] l = {"7", "6", "2019-10-01", "2019-11-11"};
      String [] m = {"8", "6", "2019-10-01", "2019-11-11"};
      String [] n = {"8", "6", "2019-12-01", "2019-12-11"};
      booking.createBooking(c);
      booking.createBooking(d);
      booking.createBooking(e);
      booking.createBooking(f);
      booking.createBooking(g);
      booking.createBooking(h);
      booking.createBooking(i);
      booking.createBooking(j);
      booking.createBooking(k);
      booking.createBooking(l);
      booking.createBooking(m);
      booking.createBooking(n);
      user.addRankComment("hostcomment", "good" , "1");
      user.addRankComment("hostcomment", "bad" , "2");
      user.addRankComment("hostcomment", "soso" , "3");
      user.addRankComment("hostcomment", "excelent" , "4");
      user.addRankComment("hostcomment", "good" , "5");
      user.addRankComment("rentercomment", "well" , "1");
      user.addRankComment("rentercomment", "nasty" , "2");
      user.addRankComment("rentercomment", "warm" , "3");
      user.addRankComment("rentercomment", "good" , "4");
      user.addRankComment("rentercomment", "bad" , "5");
      user.addRankComment("renterrank", "4" , "1");
      user.addRankComment("renterrank", "3" , "2");
      user.addRankComment("renterrank", "2" , "3");
      user.addRankComment("renterrank", "5" , "4");
      user.addRankComment("renterrank", "7" , "5");
      user.addRankComment("hostrank", "4" , "1");
      user.addRankComment("hostrank", "4" , "2");
      user.addRankComment("hostrank", "1" , "3");
      user.addRankComment("hostrank", "5" , "4");
      user.addRankComment("hostrank", "5" , "5");
      
      
    }
	public static void mockListing(Listing listing) {
      String [] a = {"list1","4","apt","66 Military","Toronto","Canada","10","1","2",};
      String [] b = {"list2","4","room","6 Military","Shanghai","China","11","1","3",};
      String [] c = {"list3","4","condo","60 Military","Toronto","Canada","12","1","4",};
      String [] d = {"list4","4","room","62 Military","Toronto","Canada","19","1","5",};;
      String [] e = {"list5","4","room","63 Military","Toronto","Canada","18","1","6",};
      String [] f = {"list6","5","room","64 Military","Toronto","Canada","13","1","7",};
      String [] g = {"list7","7","room","65 Military","Toronto","Canada","177","3","5",};
      String [] h = {"list8","9","room","61 Military","Toronto","Canada","12","7","7",};
      String [] i = {"list9","5","apt","67 Military","Toronto","Canada","17","9","3",};
      String [] j = {"list10","7","house","5 Military","Toronto","Canada","121","0","4",};
      String [] k = {"list11","7","house","4 Military","Toronto","Canada","100","0","5",};
      String [] l = {"list12","9","house","3 Military","Toronto","Canada","106","0","6",};
      listing.createListing(a);
      listing.createListing(b);
      listing.createListing(c);
      listing.createListing(d);
      listing.createListing(e);
      listing.createListing(f);
      listing.createListing(g);
      listing.createListing(h);
      listing.createListing(i);
      listing.createListing(j);
      listing.createListing(k);
      listing.createListing(l);
    }
	public static void mockAmenity(Amenity amenity) {
      String [] a = {"wifi", "parking", "gym", "pool", "kitchen", "tv"};
      String [] b = {"wifi", "parking", "kitchen", "tv", "microwave"};
      String [] c = {"wifi"};
      String [] d = {"wifi", "washer", "dryer"};
      String [] e = {"wifi", "parking", "gym", "pool", "kitchen", "tv"};
      String [] f = {"wifi", "parking", "kitchen", "heating"};
      String [] g = {"wifi", "parking", "gym", "pool", "kitchen", "tv", "ac"};
      String [] h = {"wifi", "parking", "gym", "pool", "kitchen", "tv", "ac"};
      String [] i = {"wifi", "parking", "gym", "pool", "kitchen", "tv", "coffee"};
      String [] j = {"wifi", "parking", "kitchen", "heating"};
      String [] k = {"wifi", "parking", "kitchen", "tv"};
      String [] l = {"wifi", "gym", "pool", "kitchen", "tv"};
      amenity.createAmmenities(a, "1");
      amenity.createAmmenities(b, "2");
      amenity.createAmmenities(c, "3");
      amenity.createAmmenities(d, "4");
      amenity.createAmmenities(e, "5");
      amenity.createAmmenities(f, "6");
      amenity.createAmmenities(g, "7");
      amenity.createAmmenities(h, "8");
      amenity.createAmmenities(i, "9");
      amenity.createAmmenities(j, "10");
      amenity.createAmmenities(k, "11");
      amenity.createAmmenities(l, "12");
    }
	public static void mockCalendar(Calendar calendar) {
      String [] a = {"1", "2018-09-01", "2020-09-01", "900"};
      String [] c = {"2", "2016-09-01", "2020-09-01", "600"};
      String [] e = {"3", "2019-09-01", "2020-09-01", "1000"};
      String [] f = {"4", "2019-09-01", "2020-09-01", "1000"};
      String [] g = {"5", "2019-09-01", "2020-09-01", "1000"};
      String [] h = {"6", "2019-09-01", "2020-09-01", "1000"};
      String [] i = {"7", "2019-09-01", "2020-09-01", "1000"};
      String [] j = {"8", "2019-09-01", "2020-09-01", "1000"};
      String [] k = {"9", "2019-09-01", "2020-09-01", "1000"};
      String [] l = {"10", "2019-09-01", "2020-09-01", "1000"};
      String [] m = {"11", "2019-09-01", "2020-09-01", "1000"};
      String [] n = {"12", "2019-09-01", "2020-09-01", "1000"};
      calendar.createCalendar(a);
      calendar.createCalendar(c);
      calendar.createCalendar(e);
      calendar.createCalendar(f);
      calendar.createCalendar(g);
      calendar.createCalendar(h);
      calendar.createCalendar(i);
      calendar.createCalendar(j);
      calendar.createCalendar(k);
      calendar.createCalendar(l);
      calendar.createCalendar(m);
      calendar.createCalendar(n);
    }
}
