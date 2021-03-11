package de.hskl.bitcoin.real_degrees.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hskl.bitcoin.real_degrees.R;
import de.hskl.bitcoin.real_degrees.util.DBManager;
import de.hskl.bitcoin.real_degrees.util.Utilities;

public class LoadingActivity extends AppCompatActivity {
    long millisInFuture = 3_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final Intent toMasterView = new Intent(this, MasterViewActivity.class);

        checkDarkMode();

        DBManager db = DBManager.getInstance(this);
        if (!db.isContentComplete()) {
            db.fillWithContent();
        }


        /* variable Anonyme Implementierung der abstrakten Klasse CountDownTimer.
         *Timer der den Startbildschirm für 3 Sekunden darstellt und bei Ablauf per Intent auf
         *das Hauptmenü weiterleitet.
         */

        new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                startActivity(toMasterView);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toHomeScreen = new Intent(Intent.ACTION_MAIN);
        toHomeScreen.addCategory(Intent.CATEGORY_HOME);
        startActivity(toHomeScreen);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkDarkMode() {
        if (Utilities.isDarkModeOn(this)) {
            TextView tv = (TextView) findViewById(R.id.loading_tv_text);
            ImageView iv_logo = (ImageView) findViewById(R.id.loading_iv_logo);
            ImageView iv_font = (ImageView) findViewById(R.id.loading_iv_font);

            tv.setTextColor(getResources().getColor(R.color.white));
            iv_font.setImageDrawable(getResources().getDrawable(R.drawable.logo_rd_font_dark));
            iv_logo.setImageDrawable(getResources().getDrawable(R.drawable.logo_rd_loading_dark));

        }
    }
}