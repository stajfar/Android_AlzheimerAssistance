package businesslogic_layer;


import android.content.Context;
import android.widget.Toast;



public class SMSCommandAnalyzer  {
	
	

	
	
	public SMSCommandAnalyzer() {
		super();
		
	}



	public static void AnalizeSmsContent(Context context,String message)
	{
		switch (message) {
		case "CourseTrack_00":
			Toast.makeText(context, "222CourseTrack_00", Toast.LENGTH_LONG).show();
			break;
		case "FineTrack_00":
			Toast.makeText(context, "22FineTrack_00", Toast.LENGTH_LONG).show();
			break;		
		case "CourseTrack_05":
			Toast.makeText(context, "CourseTrack_05", Toast.LENGTH_LONG).show();	
			break;
		case "CourseTrack_02":
				
			break;
		case "FineTrack_05":
						
			break;
		case "FineTrack_02":
					
			break;
		case "StopTracking":
			
			break;
		
		}
	}


	

}
