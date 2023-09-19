package in.ezeon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains static method to handle dates.
 * @author sheik
 *
 */
public class DateUtil {
    public static final String[] MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
    /**
     * This methods converts string-date into Date Object.
     * @param dateAsString string formatted date (Ex. 11/03/2017 : DD/MM/YYYY)
     * @return returns a Date Object for input date-string.
     */
    public static Date stringToDate(String dateAsString) {  
		//we take date as a string format "dateAsString" and we are returning date as date object "Date"
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.parse(dateAsString);  //parse() --> receives string as input, converts into an object of the calling class.
		} catch (ParseException e) {              // this time parse() convert string into Date Object
			e.printStackTrace();
			return null;
		}
	}
    
    /**
     * This method converts Date object to String.
     * @param date Date object to be converted to String.
     * @return String date in DD/MM/YYYY format
     */
	public static String dateToString(Date date) {   //we take date as a object format "dateAsString" and we are returning date as string
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);  //format() jo date object lekar values return karta hai
	}
	
	/**
	 * The method returns Year and Month for given Date in Year,Month format : Ex. 2016,01 ; 2016,02 and so on...
	 * @param date year and month will be extracted for this date.
	 * @return return year and month for input date.
	 */
	public static String getYearAndMonth(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy, MM");  //(MM) use krenge to month ki numeric value provide krega (Ex: 01, 02)
		return df.format(date);                                  //  if (MMM) <-- ye 3rd character ka month name provide krega (Ex: Jan, Feb)                    
	} 
	
	/**
	 * Returns Year for input date.
	 * @param date
	 * @return 
	 */
	public static Integer getYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");  
		return new Integer(df.format(date));                                                   
	}
	
	/**
	 * The method returns Month Name for given Month number.
	 * 01: Jan, 02: Feb,....., 12: Dec
	 * @param monthNo month number between 1 to 12
	 * @return returns month name for input month number
	 */
	public static String getMonthName(Integer monthNo) {
		return MONTHS[monthNo-1];
	}
}