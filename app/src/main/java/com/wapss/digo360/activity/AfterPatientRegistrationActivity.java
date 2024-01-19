package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wapss.digo360.R;

public class AfterPatientRegistrationActivity extends AppCompatActivity {

    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    TextView tv_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_patient_registration);
//        progressBar = findViewById(R.id.progressBar);
//        tv_start = findViewById(R.id.tv_start);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        //CurrentProgress = 20;
//        tv_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        CurrentProgress = CurrentProgress + 50;
////                        progressBar.setProgress(CurrentProgress);
////                        progressBar.setMax(100);
////                        startActivity(new Intent(AfterPatientRegistrationActivity.this, QuestionsActivity.class));
////                    }
////                },3000);
////               CurrentProgress = 30;
//                startActivity(new Intent(AfterPatientRegistrationActivity.this, QuestionsActivity.class));
//            }
//        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AfterPatientRegistrationActivity.this, QuestionsActivity.class));
            }
        },3000);
    }
}