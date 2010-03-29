package gov.nih.nci.cagrid.labviewer.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HL7v3DateParser {

	
	public static Date convertHL7DateToDate(String value) {
		
		if (value == null || value.length() < 12)
			return null;

		
		int year = Integer.parseInt(value.substring(0,4)); 
		int month = Integer.parseInt(value.substring(4,6));
		int day = Integer.parseInt(value.substring(6,8));
		int hour = Integer.parseInt(value.substring(8,10));
		int second = Integer.parseInt(value.substring(10,12));
		
		Calendar calendar = new GregorianCalendar();
		calendar.set(year,month, day, hour, second);
		
		return calendar.getTime();
	}
}
