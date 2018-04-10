package com.alzheimer.alzheimerassistant;



import businesslogic_layer.SMSSend;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuCommand extends ListActivity {

	String menuItems[] = { "Get Single Coarse Location","Get Single Fine Location","Coarse Tracking Each 5 minute", "Coarse Tracking Each 2 minute",
			"Fine Tracking Each 5 minute","Fine Tracking Each 2 minute","Stop Tracking"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(MenuCommand.this,
				android.R.layout.simple_list_item_1, menuItems));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		SharedPreferences getpref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String phoneNumber=getpref.getString("Pairedphonenumber", "5554");
		SMSSend smsObj=new SMSSend(this); 
		super.onListItemClick(l, v, position, id);
		String selectedItem="";
		switch (position) {
		case 0:
			selectedItem="CourseTrack_00";
			smsObj.sendSms(phoneNumber, selectedItem);
			break;
		case 1:
			selectedItem="FineTrack_00";
			smsObj.sendSms(phoneNumber, selectedItem);
			break;		
		case 2:
			selectedItem="CourseTrack_05";
			smsObj.sendSms(phoneNumber, selectedItem);			
			break;
		case 3:
			selectedItem="CourseTrack_02";
			smsObj.sendSms(phoneNumber, selectedItem);			
			break;
		case 4:
			selectedItem="FineTrack_05";
			smsObj.sendSms(phoneNumber, selectedItem);			
			break;
		case 5:
			selectedItem="FineTrack_02";
			smsObj.sendSms(phoneNumber, selectedItem);			
			break;
		case 6:			
			selectedItem="StopTracking";
			smsObj.sendSms(phoneNumber, selectedItem);
			break;
		
		}

		
		try {
			Class<?> ourClass = Class
					.forName("com.alzheimer.alzheimerassistant." + selectedItem);
			Intent ourIntent = new Intent(MenuCommand.this, ourClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
					"com.alzheimer.alzheimerassistant.PREFS");
			startActivity(PrefsIntent);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
	}

}
