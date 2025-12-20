package com.PouyaApp.KookYarVioliN;



import com.PouyaApp.KookYarVioliN.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class StartScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		Thread timer = new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					
					sleep(3000) ;
				}catch(Exception e){
					
					e.printStackTrace() ;
				}
				finally{
					
					Intent Menu= new Intent("android.intent.action.MainMenuViolin") ;
					startActivity(Menu) ;
					
				}
			}
			
			
			
			
			
		};
		timer.start() ;
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish() ;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	
		return true;
	}

}
