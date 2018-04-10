package businesslogic_layer;


import java.text.SimpleDateFormat;
import java.util.Calendar;




public class DateTimeObj {
	
	public DateTimeObj() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCurrentDateTime(){
		
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		String dateTime="";
		Calendar c = Calendar.getInstance(); 
		dateTime=dfDate.format(c.getTime());
		return dateTime;
		
	}

}
