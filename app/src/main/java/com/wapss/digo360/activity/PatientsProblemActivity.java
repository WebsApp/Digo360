package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.response.Patient_Consultation_Response;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class PatientsProblemActivity extends AppCompatActivity {
    TextView tv_submit;
    ImageView back;
    LinearLayout bp_layout, pulse_layout, sugar_layout, allergy_layout, surgery_layout, other_layout;
    RadioGroup rg_bp, rg_pulse, rg_sugar, rg_allergy, rg_surgery, rg_other;
    RadioButton rb_yes_bp, rb_no_bp, rb_yes_pulse, rb_no_pulse, rb_yes_sugar, rb_no_sugar, rb_yes_allergy, rb_no_allergy, rb_yes_surgery, rb_no_surgery, rb_yes_other, rb_no_other;
    String patientDetailId, bp_yes,btn_pulse;
    EditText et_before_bp, et_after_bp,et_temp,et_pulse,et_weight,et_height,et_sugar,et_surgery,et_allergy,et_other_problem;
    String TOKEN,bp,pulseRate,weight,height,temperature,sugar,allergy,surgery,other,diseaseId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_problem);
        tv_submit = findViewById(R.id.tv_submit);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        et_pulse = findViewById(R.id.et_pulse);
        et_temp = findViewById(R.id.et_temp);
        et_before_bp = findViewById(R.id.et_before_bp);
        et_after_bp = findViewById(R.id.et_after_bp);
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            patientDetailId = bundle.getString("P_ID");
        }

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
                    bp_yes = "Yes";

                } else if (rb_no_bp.isChecked()) {
                    bp_layout.setVisibility(View.GONE);
                    bp_yes = "No";
                }
            }
        });
        rg_pulse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_yes_pulse.isChecked()) {
                    pulse_layout.setVisibility(View.VISIBLE);
                    btn_pulse = "Yes";
                } else if (rb_no_pulse.isChecked()) {
                    pulse_layout.setVisibility(View.GONE);
                    btn_pulse = "No";
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
//                Intent intent = new Intent(PatientsProblemActivity.this, AfterPatientRegistrationActivity.class);
//                startActivity(intent);
                /*if (validation()) {
                    //callAPI here
                }*/
                consultattion_Api_call();

            }
        });
    }

    private void consultattion_Api_call() {
        TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDE1ZDQzLWRiOGMtNGMxZi04ZTZkLWZjMzE1NjQ0ZDhmMCIsImlhdCI6MTcwNTU2NDgzMywiZXhwIjoxNzM3MTAwODMzfQ.9gunlTjcc7S-OnrOcZo_n3P_whGhE5EqbjAuGHCSIiQ";
        /*bp = et_before_bp.getText().toString() + "/" + et_after_bp.getText().toString();
        pulseRate  = et_pulse.getText().toString();
        weight = et_weight.getText().toString();
        height = et_height.getText().toString();
        temperature = et_temp.getText().toString();
        sugar = et_sugar.getText().toString();
        allergy = et_allergy.getText().toString();
        surgery = "No";
        other = et_other_problem.getText().toString();
        patientDetailId = "ea5d1161-2470-454b-9bd1-c3dea1f0428b";
        diseaseId = "8bfb43d4-b51a-11ee-bc39-7085c2fdc8cc";*/
        bp = "80/120";
        pulseRate  = et_pulse.getText().toString();
        weight = et_weight.getText().toString();
        height = et_height.getText().toString();
        temperature = et_temp.getText().toString();
        sugar = et_sugar.getText().toString();
        allergy = et_allergy.getText().toString();
        surgery = "No";
        other = et_other_problem.getText().toString();
        patientDetailId = "ea5d1161-2470-454b-9bd1-c3dea1f0428b";
        diseaseId = "8bfb43d4-b51a-11ee-bc39-7085c2fdc8cc";

        Call<Patient_Consultation_Response> consalt_apiCall = ApiService.apiHolders().consalt_details(TOKEN,bp,pulseRate,weight,
                height,temperature,sugar,allergy,surgery,other,patientDetailId,diseaseId);
        consalt_apiCall.enqueue(new Callback<Patient_Consultation_Response>() {
            @Override
            public void onResponse(Call<Patient_Consultation_Response> call, Response<Patient_Consultation_Response> response) {
                if (response.isSuccessful()){
                    Toast.makeText(PatientsProblemActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient_Consultation_Response> call, Throwable t) {

            }
        });
    }

    /*private boolean validation() {
        if (Objects.equals(bp_yes, "Yes")) {
            if (et_before_bp.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Bp before Lunch", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (et_after_bp.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Bp after Lunch", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }*/
}