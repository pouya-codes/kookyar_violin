package com.PouyaApp.KookYarVioliN;

import com.kookyar.common.KookyarFonts;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kookyar.common.PersianReshape;

public class Help extends AppCompatActivity {
    public String fonts = "BZar.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Typeface face = KookyarFonts.get(this, fonts);
        TextView help = (TextView) findViewById(R.id.helpTV);
        help.setTypeface(face);
        help.setText(PersianReshape.forDisplay(this, help.getText().toString()));
    }
}
