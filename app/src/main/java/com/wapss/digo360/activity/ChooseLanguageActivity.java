package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wapss.digo360.R;
import com.wapss.digo360.authentication.CustomProgressDialog;

import java.util.Locale;

public class ChooseLanguageActivity extends AppCompatActivity {
    TextView tv_language,txt_skip;
    ImageView back;
    CardView cv_english,cv_hindi;
    String languageToLoad;
    RadioGroup rg_hindi,rg_english;
    RadioButton rb_hindi,rb_english;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        back = findViewById(R.id.back);
        tv_language = findViewById(R.id.tv_language);
        cv_english = findViewById(R.id.cv_english);
        cv_hindi = findViewById(R.id.cv_hindi);
        rg_hindi = findViewById(R.id.rg_hindi);
        rg_english = findViewById(R.id.rg_english);
        txt_skip = findViewById(R.id.txt_skip);
        rb_hindi = findViewById(R.id.rb_hindi);
        rb_english = findViewById(R.id.rb_english);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        rg_english.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_english.isChecked()) {
                    languageToLoad="en";
                    languageFunction(languageToLoad);
                }
            }
        });
        rg_hindi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_hindi.isChecked()) {
                    languageToLoad="hi";
                    languageFunction(languageToLoad);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("languageToLoad", languageToLoad);
                editor.commit();
                Intent intent = new Intent(ChooseLanguageActivity.this,LoadingPage.class);
                startActivity(intent);

            }
        });
        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLanguageActivity.this,LoadingPage.class);
                startActivity(intent);
            }
        });
    }

    private void languageFunction(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
    }
}