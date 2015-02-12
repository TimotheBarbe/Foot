package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrettyDate {

	public static String getPrettyDate() {
		Calendar now = Calendar.getInstance();
		String rep = new SimpleDateFormat("dd-MM-yy").format(now.getTime());
		rep += " " + now.get(Calendar.HOUR_OF_DAY) + "h";
		rep += now.get(Calendar.MINUTE)+"m";
		rep += now.get(Calendar.SECOND)+"s";
		return rep;
	}
}
