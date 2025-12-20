package com.PouyaApp.KookYarVioliN;




import com.PouyaApp.KookYarVioliN.R;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainMenu extends Activity implements View.OnClickListener{

	Button tunerB,exitB,nazarB,helpB ;
	public String fonts="BZar.ttf";
    Toast       exitToast;
    SharedPreferences mPrefs;
	final String welcomeScreenShownPref = "welcomeScreenShown";
	final String welcomeScreenShownPrefV5 = "welcomeScreenShownV5";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu) ;
		setFace();
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		// second argument is the default to use if the preference can't be
		// found
		Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref,
				false);

		if (!welcomeScreenShown) {
			// here you can launch another activity if you like
			// the code below will display a popup

			String title = getResources().getString(R.string.show);

			String payamHead = getResources().getString(R.string.payamHead);
			String payam = getResources().getString(R.string.payam);
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(title)
					.setMessage(payamHead + "\n" + payam)
					.setPositiveButton(R.string.edame,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
			SharedPreferences.Editor editor = mPrefs.edit();
			editor.putBoolean(welcomeScreenShownPref, true);
			editor.commit(); // Very important to save the preference
		}
//        exitToast = Toast.makeText(getApplicationContext(), "برای خروج، کلید بازگشت را دوباره بزنید",
//                Toast.LENGTH_SHORT);
		
		Boolean welcomeScreenShownV5 = mPrefs.getBoolean(welcomeScreenShownPrefV5,
				false);
		if (!welcomeScreenShownV5) {
			// here you can launch another activity if you like
			// the code below will display a popup

			String title = getResources().getString(R.string.changeV4);


			String payam = getResources().getString(R.string.changeV3Text);
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(title)
					.setMessage(payam)
					.setPositiveButton(R.string.edame,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
			SharedPreferences.Editor editor = mPrefs.edit();
			editor.putBoolean(welcomeScreenShownPrefV5, true);
			editor.commit(); // Very important to save the preference

        
        
		}
	}
	protected void setFace() {
		
		tunerB = (Button) findViewById(R.id.TunerButton);
		tunerB.setOnClickListener(this) ;
		

		exitB = (Button) findViewById(R.id.exitButton);
		exitB.setOnClickListener(this) ;
		
		nazarB = (Button) findViewById(R.id.mail) ;
		nazarB.setOnClickListener(this);
		helpB = (Button) findViewById(R.id.rahnama) ;
		helpB.setOnClickListener(this);
		Typeface face = Typeface.createFromAsset(getAssets(), "font/"+fonts+"");
		tunerB.setTypeface(face);
        String str_tuner = (String) tunerB.getText().toString();
        tunerB.setText(PersianReshape.reshape(str_tuner));
        
        exitB.setTypeface(face);
        String str_exit = (String) exitB.getText().toString();
        exitB.setText(PersianReshape.reshape(str_exit)); 
        
        nazarB.setTypeface(face);
        String str_about= (String) nazarB.getText().toString();
        nazarB.setText(PersianReshape.reshape(str_about)); 
        
        helpB.setTypeface(face);
        String str_help = (String) helpB.getText().toString();
        helpB.setText(PersianReshape.reshape(str_help)); 
        
        

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent activity ;
		
		switch (arg0.getId()) {
		
		case R.id.TunerButton:{
			activity = new Intent("android.intent.action.ViolinTuner") ;
			startActivity(activity);
			break ;
		}
		case R.id.rahnama:{
			
	
				activity = new Intent("android.intent.action.helpViolin");
			startActivity(activity);
			break ;
		}

//		case R.id.about:{
//			
//			activity = new Intent("android.intent.action.AboutViolin");
//		
//			startActivity(activity);
//			break ;
//		}
		case R.id.mail: {
			String emailAddress[] = { "KookYar@gmail.com" };
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					emailAddress);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"کوک ویولن");
			emailIntent.setType("plane/text");
			PackageInfo pInfo = null;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String version = pInfo.versionName;
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"App Version = " + version + "\nAPI version = "
							+ android.os.Build.VERSION.SDK_INT
							+ "\nPhone Model = " + Build.MANUFACTURER + " "
							+ Build.MODEL
							+ "\n-----------------------------------\n");
			activity = new Intent("android.intent.action.About");

			startActivity(emailIntent);
			break;
		}
		
		case R.id.exitButton:{
			 backButtonHandler();
			break ;
		}
		}

		
	}
    @Override
    public void onBackPressed() {
    	 backButtonHandler();
    
}


	public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainMenu.this);
        // Setting Dialog Title
        alertDialog.setTitle("آیا میخواهید واقعا از کوک یار خارج بشوید ؟!");
        // Setting Dialog Message
        alertDialog.setMessage("اگه تونستین سازتونو با کوک یار کوک کنین، قبل خارج شدن به کوک یار ستاره بدهید :)");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_launcher);
        // Setting Positive "Yes" Button
        alertDialog.setNegativeButton("بله",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setPositiveButton("خیر",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        alertDialog.setNeutralButton("ستاره به کوک یار",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
            			Intent intent = new Intent("android.intent.action.EDIT");
            			intent.setData(Uri.parse("bazaar://details?id=com.PouyaApp.KookYarVioliN"));
            			startActivity(intent);
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


}
