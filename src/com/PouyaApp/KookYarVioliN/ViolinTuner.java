package com.PouyaApp.KookYarVioliN;

import java.io.File;
import java.io.IOException;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;

import com.PouyaApp.KookYarVioliN.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ViolinTuner extends Activity implements OnClickListener,OnItemSelectedListener {

	private static final String TAG = "Kuk yar";
	private PdUiDispatcher dispatcher;
	public String fonts = "BZar.ttf";
	private ImageView ivKamancheh;
	private Button Kamancheh1, Kamancheh2, Kamancheh3, Kamancheh4;
	private TextView pitchLabel;
	private PitchView pitchView;
	private double trigeeredNote;
	OnSharedPreferenceChangeListener listener;
	private int laFrequens = 440;
	private int sensitiveLevel = 65;
	private int pressure ;

	private double[] Miditone = new double[4];

	private PdService pdService = null;

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.kamancheh_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.help_tuner:
			Intent i = new Intent("android.intent.action.helpTunerViolin");
			startActivity(i);
			break;
		case R.id.bazgasht:
			finish();
			break;
		case R.id.settings_menu:
			Intent j = new Intent("android.intent.action.settingViolin");
			startActivity(j);

			break;

		}
		return true;
	}

	private void prefsListnner() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefs,
					String key) {
				// Implementation

				laFrequens = Integer.parseInt(prefs.getString("ref", "440"));
				pitchView.setLaFrequnse(laFrequens);

				if (prefs.getString("sensitive", "2").compareTo("3") == 0) {
					sensitiveLevel = 55;

				} else if (prefs.getString("sensitive", "2").compareTo("1") == 0) {
					sensitiveLevel = 75;
				} else {
					sensitiveLevel = 65;
				}
			

			}
		};
		prefs.registerOnSharedPreferenceChangeListener(listener);
	}

	private final ServiceConnection pdConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			pdService = ((PdService.PdBinder) service).getService();
			try {
				initPd();
				loadPatch();
			} catch (IOException e) {
				Log.e(TAG, e.toString());
				finish();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// this method will never be called
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kamancheh_tuner);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initGui();
		prefsListnner() ;
		initSystemServices();
		bindService(new Intent(this, PdService.class), pdConnection,
				BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(pdConnection);
	}

	private void initGui() {

		Typeface face = Typeface.createFromAsset(getAssets(), "font/" + fonts
				+ "");

		ivKamancheh = (ImageView) findViewById(R.id.ivkamancheh);
		Kamancheh1 = (Button) findViewById(R.id.kamancheh_1);
		Kamancheh1.setOnClickListener(this);
		Kamancheh1.setTypeface(face);
		String str_b1 = (String) Kamancheh1.getText().toString();
		Kamancheh1.setText(PersianReshape.reshape(str_b1));

		// -----------------------------------------------
		Kamancheh2 = (Button) findViewById(R.id.kamancheh_2);
		Kamancheh2.setOnClickListener(this);
		Kamancheh2.setTypeface(face);
		String str_b2 = (String) Kamancheh2.getText().toString();
		Kamancheh2.setText(PersianReshape.reshape(str_b2));

		// ------------------------------------------------
		Kamancheh3 = (Button) findViewById(R.id.kamancheh_3);
		Kamancheh3.setOnClickListener(this);
		Kamancheh3.setTypeface(face);
		String str_b3 = (String) Kamancheh3.getText().toString();
		Kamancheh3.setText(PersianReshape.reshape(str_b3));

		// ------------------------------------------------
		Kamancheh4 = (Button) findViewById(R.id.kamancheh_4);
		Kamancheh4.setOnClickListener(this);
		Kamancheh4.setTypeface(face);
		String str_b4 = (String) Kamancheh4.getText().toString();
		Kamancheh4.setText(PersianReshape.reshape(str_b4));

		// ------------------------------------------------
		pitchLabel = (TextView) findViewById(R.id.pitch_label);
		pitchView = (PitchView) findViewById(R.id.pitch_view);
		// ------------------------------------------------

		SharedPreferences getprefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		laFrequens = Integer.parseInt(getprefs.getString("ref", "440"));
		pitchView.setLaFrequnse(laFrequens);

		if (getprefs.getString("sensitive", "2").compareTo("3") == 0) {
			sensitiveLevel = 55;

		} else if (getprefs.getString("sensitive", "2").compareTo("1") == 0) {
			sensitiveLevel = 75;
		} else {
			sensitiveLevel = 65;
		}
		
		Spinner kookSpinner = (Spinner) findViewById(R.id.kookSpiner) ;
		ArrayAdapter<CharSequence> kookAdapter = ArrayAdapter.createFromResource(this, R.array.kouk, android.R.layout.simple_spinner_item) ;
		kookAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		kookSpinner.setAdapter(kookAdapter);
		kookSpinner.setOnItemSelectedListener(this);

		
		Kamancheh1.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh2.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh3.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh4.setBackgroundResource(android.R.drawable.btn_default);
		
		pitchLabel.setText(PersianReshape
				.reshape("سیم مورد نظر جهت کوک کردن را انتخاب کنید"));
	}

	private void initPd() throws IOException {
		// Configure the audio glue
		AudioParameters.init(this);
		int sampleRate = AudioParameters.suggestSampleRate();
		pdService.initAudio(sampleRate, 2, 2, 10.0f);
		start();

		// Create and install the dispatcher
		dispatcher = new PdUiDispatcher();
		PdBase.setReceiver(dispatcher);
		dispatcher.addListener("pitch", new PdListener.Adapter() {
			@Override
			public void receiveFloat(String source, final float x) {

				if (pressure >= sensitiveLevel) {
					pitchView.setCurrentPitch(x);

				} else {

					pitchView.setCurrentPitch(12);
				}
			}
		});
		dispatcher.addListener("midi", new PdListener.Adapter() {
			@Override
			public void receiveFloat(String source, final float y) {

				pitchView.setCenterPitch(y);
				PdBase.sendFloat("midinote", y);
				PdBase.sendBang("trigger");
			}
		});
		dispatcher.addListener("pressure", new PdListener.Adapter() {
			@Override
			public void receiveFloat(String source, final float z) {
				pressure = (int) z;
			}
		});
	}

	private void start() {
		if (!pdService.isRunning()) {
			Intent intent = new Intent(ViolinTuner.this, ViolinTuner.class);
			pdService.startAudio(intent, R.drawable.ic_launcher,
					PersianReshape.reshape("کوک یار"),
					PersianReshape.reshape("بازگشت به کوک یار"));
		}
	}

	private void loadPatch() throws IOException {
		File dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.tuner),
				dir, true);
		File patchFile = new File(dir, "tuner.pd");
		PdBase.openPatch(patchFile.getAbsolutePath());
	}

	private void initSystemServices() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				if (pdService == null)
					return;
				if (state == TelephonyManager.CALL_STATE_IDLE) {
					start();
				} else {
					pdService.stopAudio();
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
	}


	private void triggerNote(float n) {

		PdBase.sendFloat("frequens", midiToFrequnes((n)));
		pitchView.setMidiRef((n));
		trigeeredNote = n ;
	}

	@Override
	public void onClick(View v) {

		Kamancheh1.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh2.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh3.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh4.setBackgroundResource(android.R.drawable.btn_default);
		switch (v.getId()) {
		case R.id.kamancheh_1:
			triggerNote((float) Miditone[0]);
			pitchLabel.setText(PersianReshape.reshape("سیم اول"));
			ivKamancheh.setImageResource(R.drawable.violin1);
			Kamancheh1.setBackgroundColor(Color.YELLOW);
			break;
		case R.id.kamancheh_2:
			triggerNote((float) Miditone[1]);
			pitchLabel.setText(PersianReshape.reshape("سیم دوم"));
			ivKamancheh.setImageResource(R.drawable.violin2);

			Kamancheh2.setBackgroundColor(Color.YELLOW);
			break;
		case R.id.kamancheh_3:
			triggerNote((float) Miditone[2]);
			pitchLabel.setText(PersianReshape.reshape("سیم سوم"));
			ivKamancheh.setImageResource(R.drawable.violin3);
			Kamancheh3.setBackgroundColor(Color.YELLOW);
			break;
		case R.id.kamancheh_4:
			triggerNote((float) Miditone[3]);
			pitchLabel.setText(PersianReshape.reshape("سیم چهارم"));
			ivKamancheh.setImageResource(R.drawable.violin4);
			Kamancheh4.setBackgroundColor(Color.YELLOW);
			break;
		}

	}
	private   float midiToFrequnes(float i) {
		double frequens = laFrequens*Math.pow(2, ((i-69)/12)) ;
		return (float) frequens;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Kamancheh1.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh2.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh3.setBackgroundResource(android.R.drawable.btn_default);
		Kamancheh4.setBackgroundResource(android.R.drawable.btn_default);
		ivKamancheh.setImageResource(R.drawable.violin);
		pitchLabel.setText(PersianReshape
				.reshape("سیم مورد نظر جهت کوک کردن را انتخاب کنید"));
		pitchView.setCenterPitch(0);
		// TODO Auto-generated method stub
		switch (position) {
		case 0 :
			double[] selectedMidiTone0 = {76,69,62,55} ;
			Miditone = selectedMidiTone0 ;
			break ;
		case 1 :
			double[] selectedMidiTone1 = {74,69,62,57} ;
			Miditone = selectedMidiTone1 ;
			break ;			
		case 2 :
			double[] selectedMidiTone2 = {76,69,64,57} ;
			Miditone = selectedMidiTone2 ;
			break ;		
		case 3 :
			double[] selectedMidiTone3 = {74,69,62,55} ;
			Miditone = selectedMidiTone3 ;
			break ;		
		case 4 :
			double[] selectedMidiTone4 = {76,69,62,52} ;
			Miditone = selectedMidiTone4 ;
			break ;
		case 5 :
			double[] selectedMidiTone5 = {76,69,64,52} ;
			Miditone = selectedMidiTone5 ;
			break ;
		case 6 :
			double[] selectedMidiTone6 = {76,69,62,57} ;
			Miditone = selectedMidiTone6 ;
			break ;
		case 7 :
			double[] selectedMidiTone7 = {76,69,59,55} ;
			Miditone = selectedMidiTone7 ;
			break ;
		case 8 :
			double[] selectedMidiTone8 = {76,69,62,52.5} ;
			Miditone = selectedMidiTone8 ;
			break ;
		case 9 :
			double[] selectedMidiTone9 = {76,69,64,52.5} ;
			Miditone = selectedMidiTone9 ;
			break ;
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}