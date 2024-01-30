package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.LoginResponse;
import com.wapss.digo360.response.Patient_Check_Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRegistrationCheckActivity extends AppCompatActivity {
    TextView tv_check,tv_previous;
    ImageView back;
    EditText et_phone;
    String regex = "^[6-9][0-9]{9}$";
    String keyword,TOKEN;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration_check);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        et_phone = findViewById(R.id.et_phone);
        et_phone = findViewById(R.id.et_phone);
        back = findViewById(R.id.back);
        tv_check = findViewById(R.id.tv_check);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (et_phone.getText().toString().isEmpty()) {
                    final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.
                            AlertDialog.Builder(getApplication());
                    LayoutInflater inflater1 = getLayoutInflater();
                    View dialogView1 = inflater1.inflate(R.layout.invalid_phone_layout,null);
                    builder1.setCancelable(false);
                    builder1.setView(dialogView1);
                    final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                    alertDialog1.setCanceledOnTouchOutside(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog1.dismiss();
                        }
                    },2000);
                }
                else {
                    number_Chack_api(keyword);
                }*/
                keyword = et_phone.getText().toString();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(keyword);
                if (matcher.matches()){
                    number_Check_api(keyword);
                }
                else {
                    final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(PatientRegistrationCheckActivity.this);
                    LayoutInflater inflater1 = getLayoutInflater();
                    View dialogView1 = inflater1.inflate(R.layout.invalid_phone_layout,null);
                    builder1.setCancelable(false);
                    builder1.setView(dialogView1);
                    final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                    alertDialog1.setCanceledOnTouchOutside(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog1.dismiss();
                        }
                    },2000);
                }
            }
        });
       /* tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationCheckActivity.this, PreviousCases.class);
                startActivity(intent);
            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void number_Check_api(String keyword) {
        progressDialog.showProgressDialog();
        TOKEN = "Bearer " + deviceToken;
        int limit = 10;
        int offset = 0;
        Call<Patient_Check_Response> patient_check = ApiService.apiHolders().Patient_check(TOKEN,limit,offset,keyword);
        patient_check.enqueue(new Callback<Patient_Check_Response>() {
            @Override
            public void onResponse(Call<Patient_Check_Response> call, Response<Patient_Check_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    String total = String.valueOf(response.body().getTotal());
                    if (total.equals("0")){
                        Bundle bundle = new Bundle();
                        bundle.putString("p_number",keyword);
                        Intent intent = new Intent(PatientRegistrationCheckActivity.this,NewCasectivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("p_number",keyword);
                        Intent intent = new Intent(PatientRegistrationCheckActivity.this,PreviousCases.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Patient_Check_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }

}