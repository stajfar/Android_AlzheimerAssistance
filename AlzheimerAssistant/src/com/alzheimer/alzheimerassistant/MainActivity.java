package com.alzheimer.alzheimerassistant;

import businesslogic_layer.LocationObj;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener,
		LocationListener {

	private LatLng targetLocation;
	private GoogleMap map;
	Button btAnimateCamera;
	TextView tvlonglat;
	double lat, longi;
	LocationManager lm;
	String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InitializeVars();

		// turn on the GPS
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		provider = lm.getBestProvider(new Criteria(), false);
		Location location = lm.getLastKnownLocation(provider);

		if (provider == null) {
			onProviderDisabled(provider);
		}
		//Assistant's last known position
		if (location != null) {
			lat = location.getLatitude();
			longi = location.getLongitude();			
		}

		//get latest position of Patient for SQLite 
		LocationObj LocObj = new LocationObj();
		String LatestPosition = LocObj.findLatestPosition(this);
		tvlonglat.setText("Target Last Position: "+LatestPosition);
		

		String[] pos = LatestPosition.split(",");
		targetLocation = new LatLng(Double.parseDouble(pos[0]),	Double.parseDouble(pos[1]));
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(targetLocation,16);
		map.animateCamera(update);
		
		map.addMarker(new MarkerOptions().title("Here I am")
				.position(targetLocation)
				.snippet("Last known position given by SQLite"));

	}

	private void getLatLongiFromBundle() {
		// TODO Auto-generated method stub
		Bundle gotbundle = getIntent().getExtras();
		String LatLongis = gotbundle.getString("LatLogs", "null is received");
		tvlonglat.setText(LatLongis);

	}

	private void InitializeVars() {
		// TODO Auto-generated method stub
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mymap))
				.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);		
		map.setMyLocationEnabled(true);

		tvlonglat = (TextView) findViewById(R.id.tvlonglat);
		btAnimateCamera = (Button) findViewById(R.id.btfocustarget);
		btAnimateCamera.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btfocustarget:
			// Animate map to the last given position of target
			LatLng latlongi;
			LocationObj LocObj = new LocationObj();
			String LatestPosition = LocObj.findLatestPosition(this);

			String[] pos = LatestPosition.split(",");
			latlongi = new LatLng(Double.parseDouble(pos[0]),Double.parseDouble(pos[1]));
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlongi,16);
			map.animateCamera(update);
			break;

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		/* TODO Auto-generated method stub
		lat = location.getLatitude();
		longi = location.getLongitude();
		tvlonglat.setText(String.valueOf(lat) + " " + String.valueOf(longi));   */

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("GPS is OFF");
		builder.setCancelable(false);

		builder.setPositiveButton("Enable GPS",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent startGps = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(startGps);

					}
				});
		builder.setNeutralButton("Cancle",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lm.requestLocationUpdates(provider, 6000, 200, this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		lm.removeUpdates(this);// remove updates of location on pause
	}
}