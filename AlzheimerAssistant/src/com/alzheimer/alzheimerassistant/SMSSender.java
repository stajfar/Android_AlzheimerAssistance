package com.alzheimer.alzheimerassistant;

import businesslogic_layer.LocationObj;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSSender extends Activity implements View.OnClickListener {
	
	EditText etsmsphonenumber,etsmsmessage,etpositionid;
	Button   btsmssend,btfindpositionid;
	IntentFilter intentFilter;
	
	private BroadcastReceiver intentreceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			TextView tvmessage=(TextView)findViewById(R.id.tvmessage);
			tvmessage.setText(intent.getExtras().getString("sms"));	
			
			
			Bundle basket=new Bundle();
			basket.putString("LatLogs", intent.getExtras().getString("sms"));
			
			Intent a=new Intent(SMSSender.this,MainActivity.class);
			a.putExtras(basket);
			startActivity(a);		
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smssender);	
		intentFilter=new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");		
		
		InitializeVars();
	}

	private void InitializeVars() {
		// TODO Auto-generated method stub
		etsmsphonenumber=(EditText)findViewById(R.id.etsmsphonenumber);
		etsmsmessage=(EditText)findViewById(R.id.etsmsmessage);
		btsmssend=(Button)findViewById(R.id.btsmssend);
		
		btfindpositionid=(Button)findViewById(R.id.btfindpositionid);
		etpositionid=(EditText)findViewById(R.id.etpositionid);
		
		
		btsmssend.setOnClickListener(this);
		btfindpositionid.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btsmssend:
			String phoneNumber=etsmsphonenumber.getText().toString();
			String message=etsmsmessage.getText().toString();
			sendSms(phoneNumber,message);			
			break;
		case R.id.btfindpositionid:
			LocationObj LocObj=new LocationObj();
			String test=LocObj.findPositionByRowID(SMSSender.this,Long.parseLong(etpositionid.getText().toString()) );
			
			etpositionid.setText(test);
			break;		
		}
		
	}

	private void sendSms(String phoneNumber, String message) {
		// TODO Auto-generated method stub
		String SENT="Message Sent";
		String DELIVERED="Message Delivered";
		
		PendingIntent sentPI=PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		PendingIntent receivedPI=PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		registerReceiver(new BroadcastReceiver() {			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(SMSSender.this, "SMS Sent", Toast.LENGTH_LONG).show();
					break;					
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic Fialure", Toast.LENGTH_LONG).show();
					break;					
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_LONG).show();
					break;
				}				
			}
		}, new IntentFilter(SENT));
		
		
		registerReceiver(new BroadcastReceiver() {			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "Message Delivered", Toast.LENGTH_LONG).show();
					break;					
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "Message not Delivered", Toast.LENGTH_LONG).show();
					break;					
				}				
			}
		}, new IntentFilter(DELIVERED));
		
		SmsManager sms=SmsManager.getDefault();
		// The last parameter is set to null in order to prevent request for the delivery report.
		//to set it please replace null with receivedPI
		sms.sendTextMessage(phoneNumber, null, message, sentPI,null);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		registerReceiver(intentreceiver, intentFilter);
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterReceiver(intentreceiver);
		super.onPause();
	}
	

}