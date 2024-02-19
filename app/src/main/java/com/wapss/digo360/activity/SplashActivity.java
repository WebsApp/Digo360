package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wapss.digo360.R;
import com.wapss.digo360.authentication.CustomProgressDialog;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String loginStatus,languageToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.splash));

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        loginStatus = loginPref.getString("loginStatus", "");
        languageToLoad = loginPref.getString("languageToLoad", "");
//        startActivity(new Intent(SplashActivity.this, PatientsProblemActivity.class));
//        finish();
       // progressDialog.showProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*if (loginStatus.equals("true"))
                {
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,getResources().getDisplayMetrics());
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getResources().updateConfiguration(config,getResources().getDisplayMetrics());
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }*/
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },2000);
    }
}