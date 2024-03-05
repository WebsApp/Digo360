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
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.SettingResponse;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences loginPref2,loginPref;
    SharedPreferences.Editor editor2,editor;
    String loginStatus,languageToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.splash));
        callAPI();

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        loginStatus = loginPref.getString("loginStatus", "");
        languageToLoad = loginPref.getString("languageToLoad", "");

        loginPref2 = getSharedPreferences("login_pref2", Context.MODE_PRIVATE);
        editor2 = loginPref2.edit();
//        startActivity(new Intent(SplashActivity.this, PatientsProblemActivity.class));
//        finish();
       // progressDialog.showProgressDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginStatus.equals("true"))
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
                }
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
            }
        },2000);
    }
    private void callAPI() {
        Call<SettingResponse> profile_apiCall = ApiService.apiHolders().setting_maint("1");
        profile_apiCall.enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                if (response.code() == 401) {

                } else {
                    if (response.isSuccessful()) {
                        SettingResponse settingResponse = response.body();
                        assert settingResponse != null;
                        String maintenance = settingResponse.getResult().getMaintenance();
                        String version = settingResponse.getResult().getVersion();
                        editor2.putString("maintenance", maintenance);
                        editor2.putString("version", version);
                        editor2.commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}