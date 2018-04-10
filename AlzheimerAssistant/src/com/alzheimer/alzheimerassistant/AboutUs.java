package com.alzheimer.alzheimerassistant;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		TextView tv=(TextView)findViewById(R.id.TvAboutUs);
		
		
		tv.setText("This is the text of About Us");
		
		
	}
	

}