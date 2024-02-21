package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.Profile_Response;
import com.wapss.digo360.response.Update_Profile_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Profile extends AppCompatActivity {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,Token;
    CustomProgressDialog progressDialog;
    ImageView back;
    EditText edit_name,et_email,et_address;
    TextView tv_update;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        progressDialog = new CustomProgressDialog(Update_Profile.this);
        /*Shared Pref*/
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressDialog = new CustomProgressDialog(Update_Profile.this);
        back = findViewById(R.id.back);
        et_address = findViewById(R.id.et_address);
        et_email = findViewById(R.id.et_email);
        edit_name = findViewById(R.id.edit_name);
        tv_update = findViewById(R.id.tv_update);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_Update();
            }
        });
    }
    private void profile_Update() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        String name = edit_name.getText().toString();
        String email = et_email.getText().toString();
        String address = et_address.getText().toString();
        Call<Update_Profile_Response> update_apiCall = ApiService.apiHolders().update_res(Token,name,email,address);
        update_apiCall.enqueue(new Callback<Update_Profile_Response>() {
            @Override
            public void onResponse(Call<Update_Profile_Response> call, Response<Update_Profile_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                }
                else {
                    progressDialog.hideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<Update_Profile_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}