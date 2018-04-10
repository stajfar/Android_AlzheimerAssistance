package businesslogic_layer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSSend {

	
	Context context;

	public SMSSend(Context contex) {
		super();		
		this.context = contex;
	}


	public void sendSms(String phoneNumber, String message) {
		// TODO Auto-generated method stub
		String SENT = "Message Sent";
		String DELIVERED = "Message Delivered";

		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
				new Intent(SENT), 0);
		PendingIntent receivedPI = PendingIntent.getBroadcast(context, 0,
				new Intent(DELIVERED), 0);

		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(context, "SMS Sent", Toast.LENGTH_LONG)
							.show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(context, "Generic Fialure",
							Toast.LENGTH_LONG).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(context, "No Service", Toast.LENGTH_LONG)
							.show();

					break;
				}
			}
		}, new IntentFilter(SENT));

		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(context, "Message Delivered",
							Toast.LENGTH_LONG).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(context, "Message not Delivered",
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		// The last parameter is set to null in order to prevent request for the
		// delivery report.
		// to set it please replace null with receivedPI
		sms.sendTextMessage(phoneNumber, null, message, sentPI, null);

	}

}
