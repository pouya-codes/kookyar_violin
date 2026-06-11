package com.PouyaApp.KookYarVioliN;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kookyar.common.PersianReshape;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button tunerB, exitB, nazarB, helpB;
    public String fonts = "BZar.ttf";
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    final String welcomeScreenShownPrefV5 = "welcomeScreenShownV5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setFace();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            String title = getResources().getString(R.string.show);
            String payamHead = getResources().getString(R.string.payamHead);
            String payam = getResources().getString(R.string.payam);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(title)
                    .setMessage(payamHead + "\n" + payam)
                    .setPositiveButton(R.string.edame, (dialog, which) -> dialog.dismiss())
                    .show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.apply();
        }

        Boolean welcomeScreenShownV5 = mPrefs.getBoolean(welcomeScreenShownPrefV5, false);
        if (!welcomeScreenShownV5) {
            String title = getResources().getString(R.string.changeV4);
            String payam = getResources().getString(R.string.changeV3Text);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(title)
                    .setMessage(payam)
                    .setPositiveButton(R.string.edame, (dialog, which) -> dialog.dismiss())
                    .show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPrefV5, true);
            editor.apply();
        }
    }

    protected void setFace() {
        tunerB = (Button) findViewById(R.id.TunerButton);
        tunerB.setOnClickListener(this);
        exitB = (Button) findViewById(R.id.exitButton);
        exitB.setOnClickListener(this);
        nazarB = (Button) findViewById(R.id.mail);
        nazarB.setOnClickListener(this);
        helpB = (Button) findViewById(R.id.rahnama);
        helpB.setOnClickListener(this);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/" + fonts);
        tunerB.setTypeface(face);
        tunerB.setText(PersianReshape.reshape(tunerB.getText().toString()));
        exitB.setTypeface(face);
        exitB.setText(PersianReshape.reshape(exitB.getText().toString()));
        nazarB.setTypeface(face);
        nazarB.setText(PersianReshape.reshape(nazarB.getText().toString()));
        helpB.setTypeface(face);
        helpB.setText(PersianReshape.reshape(helpB.getText().toString()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.TunerButton) {
            startActivity(new Intent(MainMenu.this, ViolinTuner.class));
        } else if (id == R.id.rahnama) {
            startActivity(new Intent(MainMenu.this, Help.class));
        } else if (id == R.id.mail) {
            String[] emailAddress = {"KookYar@gmail.com"};
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddress);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\u06A9\u0648\u06A9 \u0648\u06CC\u0648\u0644\u0646");
            emailIntent.setType("text/plain");
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String version = pInfo.versionName;
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "App Version = " + version + "\nAPI version = "
                                + android.os.Build.VERSION.SDK_INT
                                + "\nPhone Model = " + Build.MANUFACTURER + " " + Build.MODEL
                                + "\n-----------------------------------\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(emailIntent);
        } else if (id == R.id.exitButton) {
            backButtonHandler();
        }
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        new AlertDialog.Builder(MainMenu.this)
                .setTitle("\u0622\u06CC\u0627 \u0645\u06CC\u062E\u0648\u0627\u0647\u06CC\u062F \u0648\u0627\u0642\u0639\u0627 \u0627\u0632 \u06A9\u0648\u06A9 \u06CC\u0627\u0631 \u062E\u0627\u0631\u062C \u0628\u0634\u0648\u06CC\u062F \u061F!")
                .setMessage("\u0627\u06AF\u0647 \u062A\u0648\u0646\u0633\u062A\u06CC\u0646 \u0633\u0627\u0632\u062A\u0648\u0646\u0648 \u0628\u0627 \u06A9\u0648\u06A9 \u06CC\u0627\u0631 \u06A9\u0648\u06A9 \u06A9\u0646\u06CC\u0646\u060C \u0642\u0628\u0644 \u062E\u0627\u0631\u062C \u0634\u062F\u0646 \u0628\u0647 \u06A9\u0648\u06A9 \u06CC\u0627\u0631 \u0633\u062A\u0627\u0631\u0647 \u0628\u062F\u0647\u06CC\u062F :)")
                .setIcon(R.drawable.ic_launcher)
                .setNegativeButton("\u0628\u0644\u0647", (dialog, which) -> finish())
                .setPositiveButton("\u062E\u06CC\u0631", (dialog, which) -> dialog.cancel())
                .setNeutralButton("\u0633\u062A\u0627\u0631\u0647 \u0628\u0647 \u06A9\u0648\u06A9 \u06CC\u0627\u0631", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.PouyaApp.KookYarVioliN"));
                    startActivity(intent);
                })
                .show();
    }
}
