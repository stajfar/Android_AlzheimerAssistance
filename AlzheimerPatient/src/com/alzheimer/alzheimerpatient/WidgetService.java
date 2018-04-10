package com.alzheimer.alzheimerpatient;



import com.google.android.gms.maps.model.LatLng;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;
import businesslogic_layer.SMSSender;


public class WidgetService extends Service {

	private LocationManager lm;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		 super.onStartCommand(intent, flags, startId);	
		
		 lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		 
		 Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		 //Create Async task obj to find the location in a working thread and send SMS to Assistant
		 AsyncTaskGps updater=new AsyncTaskGps();
		 updater.execute(lm);		
		 
		stopSelf();
		return START_STICKY;
		}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	String provider;
	public class AsyncTaskGps extends AsyncTask<Object, Void, LatLng> implements LocationListener {
		private Location location;
	    private LocationListener mylistener=this;
		@Override
		protected LatLng doInBackground(Object... arg0) {
			final LocationManager lm = (LocationManager) arg0[0];
			Looper.prepare();
			Looper mylooper = Looper.myLooper();
			
			// Request GPS updates. The third param is the looper to use, which defaults the the one for
			// the current thread.
						
			provider=lm.getBestProvider(new Criteria(), false);
			//lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			
			final Handler myhandler=new Handler(mylooper);
			myhandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					releaseLocationListener();
					myhandler.removeCallbacks(this);
					Looper.myLooper().quit();//
				}
			}, 120000);
			
			
			//Handler myhandler=new 
			Looper.loop(); // start waiting...when this is done, we'll have the location in this.location
			LatLng latlongi;
			if(location != null){
				latlongi=new LatLng(location.getLatitude(), location.getLongitude());
				return latlongi;
			}
			
			 
			return null;
		}
		
		@Override
		protected void onPostExecute(LatLng result) {
			// notify someone we are done...
			
			SharedPreferences getpref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			String phoneNumber=getpref.getString("Pairedphonenumber", "5554");
			if(result != null){
				Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
				 SMSSender smsobj=new SMSSender();
				 smsobj.sendSms(phoneNumber,Double.toString(result.latitude)+","+Double.toString(result.longitude));
				 releaseLocationListener();
				}
			else
			{
				 SMSSender smsobj=new SMSSender();
				 smsobj.sendSms(phoneNumber,"It is not to possible to obtain location, waiting for location change...");
				 releaseLocationListener();
			}
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// Store the location, then get the current thread's looper and tell it to
			// quit looping so it can continue on doing work with the new location.
			this.location = location;
			Looper.myLooper().quit();
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
			/* TODO Auto-generated method stub
			AlertDialog.Builder builder=new AlertDialog.Builder(getBaseContext());
			builder.setTitle("GPS is OFF");
			builder.setCancelable(false);
			
			builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent startGps=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(startGps);
					
				}
			});
			builder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();				
				}
			});
			
			AlertDialog alert=builder.create();
			alert.show();
			*/
		}
		private void releaseLocationListener()
		{
			 lm.removeUpdates(mylistener);
		}
		
	}
	
}
