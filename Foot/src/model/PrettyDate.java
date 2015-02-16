package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class PrettyDate {

	
	/**
	 * @return la date actuelle
	 */
	public static String getPrettyDate() {
		Calendar now = Calendar.getInstance();
		String rep = new SimpleDateFormat("dd-MM-yy").format(now.getTime())
				+ " ";
		if (now.get(Calendar.HOUR_OF_DAY) < 10) {
			rep += "0";
		}
		rep += now.get(Calendar.HOUR_OF_DAY) + "h";
		if (now.get(Calendar.MINUTE) < 10) {
			rep += "0";
		}
		rep += now.get(Calendar.MINUTE) + "m";
		if (now.get(Calendar.SECOND) < 10) {
			rep += "0";
		}
		rep += now.get(Calendar.SECOND) + "s";
		return rep;
	}
}
