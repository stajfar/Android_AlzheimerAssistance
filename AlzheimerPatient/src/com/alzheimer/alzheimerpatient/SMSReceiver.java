package com.alzheimer.alzheimerpatient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import businesslogic_layer.LocationObj;



public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SharedPreferences getPref=PreferenceManager.getDefaultSharedPreferences(context);
		String PrefNumber= getPref.getString("Pairedphonenumber", "5554");
		
		
		
		Bundle bundle=intent.getExtras();
		SmsMessage[] messages=null;
		String str="";
		if (bundle != null) {
			Object[] pdus=(Object[])bundle.get("pdus");
			messages=new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] =SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += "message from"+ messages[i].getOriginatingAddress();
				str +=" :";
				str += messages[i].getMessageBody().toString();
				str += "\n";
				//check to see if the number is paired 
				
				if (! messages[i].getOriginatingAddress().equals(PrefNumber) ) {
					return;
				}
				
				
				// Save SMS data into SQLite Database
				LocationObj LocObj=new LocationObj();
				LocObj.setTitle(messages[i].getOriginatingAddress());
				LocObj.setPosition( messages[i].getMessageBody().toString());
				LocObj.setDateTime("Today Date");
				
				LocObj.InsertEntry_Location(context, LocObj);				
				
			}		
			
			//Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			
			Intent broadcastIntent=new Intent();
			broadcastIntent.setAction("SMS_RECEIVED_ACTION");
			broadcastIntent.putExtra("sms", str);
			context.sendBroadcast(broadcastIntent);
			
			Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage("com.alzheimer.alzheimerpatient");
			context.startActivity(LaunchIntent);
			
			
		}
		

	}

}
