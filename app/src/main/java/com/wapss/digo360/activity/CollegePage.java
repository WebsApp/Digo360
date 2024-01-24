package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.wapss.digo360.response.Collage_Response;
import com.wapss.digo360.response.FaqResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollegePage extends AppCompatActivity {

    ImageView collage_back,btn_faq;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,Token,pageName;
    CustomProgressDialog progressDialog;
    EditText et_Passout,et_admission_year,edit_college_name,et_degree,et_zipcode,et_state,et_city;
    TextView tv_update;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        collage_back = findViewById(R.id.collage_back);
        btn_faq = findViewById(R.id.btn_faq);
        et_Passout = findViewById(R.id.et_Passout);
        et_admission_year = findViewById(R.id.et_admission_year);
        edit_college_name = findViewById(R.id.edit_college_name);
        et_city = findViewById(R.id.et_city);
        et_state = findViewById(R.id.et_state);
        et_zipcode = findViewById(R.id.et_zipcode);
        et_degree = findViewById(R.id.et_degree);
        tv_update = findViewById(R.id.tv_update);
        et_degree.setText("MD");
        collage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CollegePage.this, HelpPage.class));
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_college_name.getText().toString().isEmpty()){
                    Toast.makeText(CollegePage.this, "Enter Collage Name", Toast.LENGTH_SHORT).show();
                } else if (et_admission_year.getText().toString().isEmpty()) {
                    Toast.makeText(CollegePage.this, "Enter Admission Year", Toast.LENGTH_SHORT).show();
                } else if (et_Passout.getText().toString().isEmpty()) {
                    Toast.makeText(CollegePage.this, "Enter Pass-out Year", Toast.LENGTH_SHORT).show();
                }
                else {
                    collage_api();
                }
            }
        });
    }

    private void collage_api() {
        //progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        String collegeName = edit_college_name.getText().toString();
        String startDate = et_admission_year.getText().toString();
        String endDate = et_Passout.getText().toString();
        String degree = et_degree.getText().toString();
        Call<Collage_Response> collage = ApiService.apiHolders().collage_res(Token,collegeName,startDate,endDate,degree);
        collage.enqueue(new Callback<Collage_Response>() {
            @Override
            public void onResponse(Call<Collage_Response> call, Response<Collage_Response> response) {
                if (response.isSuccessful()) {
                    //progressDialog.hideProgressDialog();
                    Toast.makeText(CollegePage.this, "Hi", Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
            @Override
            public void onFailure(Call<Collage_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}