package businesslogic_layer;



import android.content.IntentFilter;
import android.telephony.SmsManager;




public class SMSSender   {
	
	IntentFilter intentFilter;
	
	public SMSSender()	{
		
		intentFilter=new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
	}	

	public void sendSms(String phoneNumber, String message) {	
		
		SmsManager sms=SmsManager.getDefault();
		// The last parameter is set to null in order to prevent request for the delivery report.
		//to set it please replace null with receivedPI
		sms.sendTextMessage(phoneNumber, null, message, null,null);	
		
	}


}