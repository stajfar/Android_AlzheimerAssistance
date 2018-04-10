package com.alzheimer.alzheimerpatient;




import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;

import businesslogic_layer.SMSCommandAnalyzer;


public class MainActivity extends Activity {
IntentFilter intentFilter;
	
	private BroadcastReceiver intentreceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			SMSCommandAnalyzer.AnalizeSmsContent(context, intent.getExtras().getString("sms"));
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.aboutus:
			Intent i = new Intent("com.alzheimer.alzheimerassistant.ABOUTUS");
			startActivity(i);
			break;
		case R.id.preferences:
			Intent PrefsIntent = new Intent(
					"com.alzheimer.alzheimerpatient.PREFS");
			startActivity(PrefsIntent);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
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
