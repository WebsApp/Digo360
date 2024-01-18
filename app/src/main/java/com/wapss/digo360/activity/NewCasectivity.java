package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wapss.digo360.R;
import com.wapss.digo360.fragment.TopDiseasesFragment;

public class NewCasectivity extends AppCompatActivity {
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    Button start_progress;
    LinearLayout ll_address,ll_age,ll_Dob,ll_phone_num,ll_phone_email,ll_otherProblem;
    RadioGroup rg_age,rg_phone,rg_address,rg_other;
    RadioButton rb_doB,rb_age,rb_phone,rb_email,rb_yes,rb_no,rb_yes_other,rb_no_other;
    TextView tv_submit;
    ImageView back;
    EditText pt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_casectivity);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        initi();
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCasectivity.this,PatientsProblemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initi() {
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back);
        start_progress = findViewById(R.id.start_progress);
        ll_address = findViewById(R.id.ll_address);
        ll_age = findViewById(R.id.ll_age);
        rg_age = findViewById(R.id.rg_age);
        rb_doB =findViewById(R.id.rb_doB);
        rb_age = findViewById(R.id.rb_age);
        ll_Dob = findViewById(R.id.ll_Dob);
        rg_phone = findViewById(R.id.rg_phone);
        rb_phone = findViewById(R.id.rb_phone);
        rb_email = findViewById(R.id.rb_email);
        ll_phone_num = findViewById(R.id.ll_phone_num);
        ll_phone_email = findViewById(R.id.ll_phone_email);
        //startProgress = NewCase.findViewById(R.id.start_progress);
        rg_address = findViewById(R.id.rg_address);
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);
        tv_submit = findViewById(R.id.tv_submit);
//        ll_otherProblem= findViewById(R.id.ll_otherProblem);
//        rg_other = findViewById(R.id.rg_other);
//        rb_yes_other = findViewById(R.id.rb_yes_other);
//        rb_no_other = findViewById(R.id.rb_no_other);
        start_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);
            }
        });

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //DoB
        rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_doB.isChecked()) {
                    ll_Dob.setVisibility(View.VISIBLE);
                    ll_age.setVisibility(View.GONE);
                } else if (rb_age.isChecked()) {
                    ll_Dob.setVisibility(View.GONE);
                    ll_age.setVisibility(View.VISIBLE);
                }
            }
        });
        //phone number
        rg_phone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_phone.isChecked()) {
                    ll_phone_num.setVisibility(View.VISIBLE);
                    ll_phone_email.setVisibility(View.GONE);
                } else if (rb_email.isChecked()) {
                    ll_phone_num.setVisibility(View.GONE);
                    ll_phone_email.setVisibility(View.VISIBLE);
                }
            }
        });

        //Address
        rg_address.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_yes.isChecked()) {
                    ll_address.setVisibility(View.VISIBLE);
                } else if (rb_no.isChecked()) {
                    ll_address.setVisibility(View.GONE);
                }
            }
        });
    }
}