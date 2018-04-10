package com.alzheimer.alzheimerassistant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String menuItems[] = { "Display User Location", "Send Command To User", "Track User Location","Send Command To Patient"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this,
				android.R.layout.simple_list_item_1, menuItems));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String selectedItem="";
		switch (position) {
		case 0:
			selectedItem="MainActivity";
			break;
		case 1:
			selectedItem="SMSSender";
			break;
		case 2:
			selectedItem="TrackUser";
			break;
		case 3:
			selectedItem="MenuCommand";
			break;
		}

		
		try {
			Class<?> ourClass = Class
					.forName("com.alzheimer.alzheimerassistant." + selectedItem);
			Intent ourIntent = new Intent(Menu.this, ourClass);
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
