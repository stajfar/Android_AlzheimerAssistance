package com.alzheimer.alzheimerassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {

	MediaPlayer oursong;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		oursong=MediaPlayer.create(Splash.this, R.raw.music);
		
		SharedPreferences sharedPref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		Boolean checkMusicPref= sharedPref.getBoolean("chkBox", true);
		if (checkMusicPref == true) {
			oursong.start();
		}
		
		
		Thread timer=new Thread()
		{
			public void run(){
				try{
					sleep(4000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}finally
				{
					Intent IntentMainActivity=new Intent("com.alzheimer.alzheimerassistant.MENU");
					startActivity(IntentMainActivity);					
				}			
				
			}
		};
		
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		oursong.stop();
		finish();
	}
	

}