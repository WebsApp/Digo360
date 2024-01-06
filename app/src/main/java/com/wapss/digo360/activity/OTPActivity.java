package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.LoginResponse;
import com.wapss.digo360.response.OTP_Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    TextView tv_verify,tv_phone,timer;
    ImageView otp_back;
    EditText otp1,otp2,otp3,otp4;
    String otp,phone;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    CustomProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        tv_verify = findViewById(R.id.tv_verify);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        tv_phone = findViewById(R.id.tv_phone);
        timer = findViewById(R.id.timer);
        otp_back = findViewById(R.id.otp_back);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();

        phone = loginPref.getString("phone", null);
      //  passWord = loginPref.getString("passWord", null);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        //tv_phone.setText("We have sent an OTP to +91" +phone+" Please wait for 2 min Before resend Attempt ");

        //timer.setText("Your OTP is 7832");

        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                otp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
//                //callVerifyOTP(otp,phone);
                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OTPActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView1 = inflater.inflate(R.layout.otp_success_layout,null);
                builder.setCancelable(false);
                builder.setView(dialogView1);
                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(OTPActivity.this,RegistrationCheckActivity.class);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                },2000);
            }
        });
        otp_move();
        otp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void callVerifyOTP(String otp, String phone) {
        progressDialog.showProgressDialog();
        Call<OTP_Response> login_apiCall = ApiService.apiHolders().OTP_Verify(otp,phone);
        login_apiCall.enqueue(new Callback<OTP_Response>() {
            @Override
            public void onResponse(Call<OTP_Response> call, Response<OTP_Response> response) {
                if(response.code() == 201) {
                    assert response.body() != null;
                    String deviceToken = response.body().getResult().getToken();
                    Boolean latestLogin = response.body().getResult().getLatest();
                    editor.putString("deviceToken", deviceToken);
                   // editor.putString("latestLogin",latestLogin);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "OTP Verify Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if (latestLogin) {
                        intent = new Intent(OTPActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                    }else {
                        intent = new Intent(OTPActivity.this, LoadingPage.class);
                        startActivity(intent);
                    }
                    progressDialog.hideProgressDialog();

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OTP_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }

    private void otp_move() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_bg));
                otp1.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp1.getText().toString().length() == 1) {
                    otp1.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                } else {
                    otp1.clearFocus();
                }
            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_bg));
                otp2.setTextColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp2.getText().toString().length() == 1) {
                    otp2.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                } else {
                    otp2.clearFocus();
                    otp1.requestFocus();
                    otp1.setCursorVisible(true);
                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_bg));
                otp3.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp3.getText().toString().length() == 1) {
                    otp3.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);
                } else {
                    otp3.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                }
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp4.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple_bg));
                otp4.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (otp4.getText().toString().length() == 1) {
                    otp4.clearFocus();
                    otp4.requestFocus();
                   // otp5.setCursorVisible(true);
                } else {
                    otp4.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                }
            }
        });
    }
}