package com.PouyaApp.KookYarVioliN;

import com.kookyar.common.KookyarFonts;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kookyar.common.PersianReshape;

public class About extends AppCompatActivity {
    public String fonts = "BZar.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Typeface face = KookyarFonts.get(this, fonts);

        TextView tv1 = (TextView) findViewById(R.id.aboutText);
        tv1.setTypeface(face);
        tv1.setText(PersianReshape.forDisplay(this, tv1.getText().toString()));

        TextView tv2 = (TextView) findViewById(R.id.aboutText1);
        tv2.setTypeface(face);
        tv2.setText(PersianReshape.forDisplay(this, tv2.getText().toString()));

        TextView tv3 = (TextView) findViewById(R.id.aboutText2);
        tv3.setTypeface(face);
        tv3.setText(PersianReshape.forDisplay(this, tv3.getText().toString()));

        TextView tv4 = (TextView) findViewById(R.id.aboutText3);
        tv4.setTypeface(face);
        tv4.setText(PersianReshape.forDisplay(this, tv4.getText().toString()));
    }
}
