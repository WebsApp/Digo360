package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wapss.digo360.R;

public class PatientsProblemActivity extends AppCompatActivity {
    TextView tv_submit;
    ImageView back;
    LinearLayout bp_layout,pulse_layout,sugar_layout,allergy_layout,surgery_layout,other_layout;
    RadioGroup rg_bp,rg_pulse,rg_sugar,rg_allergy,rg_surgery,rg_other;
    RadioButton rb_yes_bp,rb_no_bp,rb_yes_pulse,rb_no_pulse,rb_yes_sugar,rb_no_sugar,rb_yes_allergy,rb_no_allergy,rb_yes_surgery,rb_no_surgery,rb_yes_other,rb_no_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_problem);
        tv_submit = findViewById(R.id.tv_submit);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        back = findViewById(R.id.back);
        bp_layout = findViewById(R.id.bp_layout);
        pulse_layout = findViewById(R.id.pulse_layout);
        sugar_layout = findViewById(R.id.sugar_layout);
        allergy_layout = findViewById(R.id.allergy_layout);
        surgery_layout = findViewById(R.id.surgery_layout);
        other_layout = findViewById(R.id.other_layout);

        rg_bp = findViewById(R.id.rg_bp);
        rg_pulse = findViewById(R.id.rg_pulse);
        rg_sugar = findViewById(R.id.rg_sugar);
        rg_allergy = findViewById(R.id.rg_allergy);
        rg_surgery = findViewById(R.id.rg_surgery);
        rg_other = findViewById(R.id.rg_other);

        rb_yes_bp = findViewById(R.id.rb_yes_bp);
        rb_no_bp = findViewById(R.id.rb_no_bp);
        rb_yes_pulse = findViewById(R.id.rb_yes_pulse);
        rb_no_pulse = findViewById(R.id.rb_no_pulse);
        rb_no_sugar = findViewById(R.id.rb_no_sugar);
        rb_yes_sugar = findViewById(R.id.rb_yes_sugar);
        rb_yes_allergy = findViewById(R.id.rb_yes_allergy);
        rb_no_allergy = findViewById(R.id.rb_no_allergy);
        rb_yes_surgery = findViewById(R.id.rb_yes_surgery);
        rb_no_surgery = findViewById(R.id.rb_no_surgery);
        rb_yes_other = findViewById(R.id.rb_yes_other);
        rb_no_other = findViewById(R.id.rb_no_other);
        rg_bp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_bp.isChecked()) {
                    bp_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_bp.isChecked()) {
                    bp_layout.setVisibility(View.GONE);

                }
            }
        });
        rg_pulse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_pulse.isChecked()) {
                    pulse_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_pulse.isChecked()) {
                    pulse_layout.setVisibility(View.GONE);

                }
            }
        });
        rg_sugar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_sugar.isChecked()) {
                    sugar_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_sugar.isChecked()) {
                    sugar_layout.setVisibility(View.GONE);

                }
            }
        });
        rg_allergy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_allergy.isChecked()) {
                    allergy_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_allergy.isChecked()) {
                    allergy_layout.setVisibility(View.GONE);

                }
            }
        });
        rg_surgery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_surgery.isChecked()) {
                    surgery_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_surgery.isChecked()) {
                    surgery_layout.setVisibility(View.GONE);

                }
            }
        });
        rg_other.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_other.isChecked()) {
                    other_layout.setVisibility(View.VISIBLE);
                } else if (rb_no_other.isChecked()) {
                    other_layout.setVisibility(View.GONE);

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientsProblemActivity.this,AfterPatientRegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}