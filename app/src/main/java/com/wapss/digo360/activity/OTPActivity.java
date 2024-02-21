package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    TextView tv_verify,tv_phone,timer,YourOtp, count_time, btn_resend;
    ImageView otp_back;
    EditText otp1,otp2,otp3,otp4;
    String otp,phone,deviceToken;
    Boolean latestLogin;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    CustomProgressDialog progressDialog;
    private CountDownTimer countDownTimer;
    long timerDuration = 120000;
    long timerInterval = 1000;
    String loginStatus ="true",fcm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        YourOtp = findViewById(R.id.YourOtp);
        tv_verify = findViewById(R.id.tv_verify);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        tv_phone = findViewById(R.id.tv_phone);
        timer = findViewById(R.id.timer);
        otp_back = findViewById(R.id.otp_back);
        count_time = findViewById(R.id.count_time);
        btn_resend =  findViewById(R.id.btn_resend);
        // Set the interval for updating the timer (every second in this example)
        //progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        phone = loginPref.getString("phone", null);
        fcm = loginPref.getString("fb_token",null);
      //  passWord = loginPref.getString("passWord", null);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        tv_phone.setText("We have sent an OTP to " + " +91-"  + phone + " " + "Please wait for 2 min Before resend Attempt .");
        YourOtp.setText("Your OTP is 7832");
        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
                if (otp1.getText().toString().isEmpty() || otp2.getText().toString().isEmpty() || otp3.getText().toString().isEmpty() ||
                        otp4.getText().toString().isEmpty()){
                    Toast.makeText(OTPActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    callVerifyOTP(otp,phone);
                }
            }
        });
        otp_move();
        CountDown();
        otp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountDown();
                otp1.getText().clear();
                otp2.getText().clear();
                otp3.getText().clear();
                otp4.getText().clear();
                otp1.requestFocus();
                count_time.setVisibility(View.VISIBLE);
            }
        });
    }

    private void CountDown() {
        countDownTimer = new CountDownTimer(timerDuration, timerInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timerTextView with the remaining time
                count_time.setText( millisUntilFinished / 1000 + " Sec");
                btn_resend.setVisibility(View.GONE);
                tv_verify.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFinish() {
                // Timer finished, you can perform actions here
                btn_resend.setVisibility(View.VISIBLE);
                count_time.setVisibility(View.GONE);
                tv_verify.setVisibility(View.GONE);
            }
        };
        // Start the timer
        countDownTimer.start();
    }
    @Override
    protected void onDestroy() {
        // Stop the timer when the activity is destroyed to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
    private void callVerifyOTP(String otp, String phone) {
//        progressDialog.showProgressDialog();
        Call<OTP_Response> login_apiCall = ApiService.apiHolders().OTP_Verify(otp,phone,fcm);
        login_apiCall.enqueue(new Callback<OTP_Response>() {
            @Override
            public void onResponse(Call<OTP_Response> call, Response<OTP_Response> response) {
                if (response.code() == 401){
                    invalidOTP();
                }
                else {
                    if(response.code() == 201) {
                        assert response.body() != null;
                        deviceToken = response.body().getToken();
                        latestLogin = response.body().getLatest();
                        editor.putString("deviceToken", deviceToken);
                        editor.putBoolean("latestLogin",latestLogin);
                        editor.commit();
                        final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(OTPActivity.this);
                        LayoutInflater inflater1 = getLayoutInflater();
                        View dialogView1 = inflater1.inflate(R.layout.otp_success_layout,null);
                        builder1.setCancelable(false);
                        builder1.setView(dialogView1);
                        final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.show();
                        alertDialog1.setCanceledOnTouchOutside(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               // Toast.makeText(OTPActivity.this, ""+latestLogin, Toast.LENGTH_SHORT).show();
                                Intent intent;
                                if (latestLogin) {
                                    startActivity(new Intent(OTPActivity.this,RegistrationActivity.class));
                                }else {
                                    editor.putString("loginStatus", loginStatus);
                                    editor.commit();
                                    intent = new Intent(OTPActivity.this, LoadingPage.class);
                                    startActivity(intent);
                                }
                                alertDialog1.dismiss();
                            }
                        },2000);
                        //progressDialog.hideProgressDialog();

                    } else {
                        //progressDialog.hideProgressDialog();
                    }
                }
            }
            @Override
            public void onFailure(Call<OTP_Response> call, Throwable t) {
                //progressDialog.hideProgressDialog();

            }
        });
    }
    private void invalidOTP() {
        final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(OTPActivity.this);
        LayoutInflater inflater1 = getLayoutInflater();
        View dialogView1 = inflater1.inflate(R.layout.invalidotp_layout,null);
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
    private void otp_move() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp1.setBackgroundDrawable(getResources().getDrawable(R.drawable.otp_bg));
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
                    otp1.requestFocus();
                    otp1.setBackgroundDrawable(getResources().getDrawable(R.drawable.time_bg));
                    //otp1.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp2.setBackgroundDrawable(getResources().getDrawable(R.drawable.otp_bg));
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
                    otp2.setBackgroundDrawable(getResources().getDrawable(R.drawable.time_bg));
                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp3.setBackgroundDrawable(getResources().getDrawable(R.drawable.otp_bg));
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
                    otp3.setBackgroundDrawable(getResources().getDrawable(R.drawable.time_bg));
                }
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp4.setBackgroundDrawable(getResources().getDrawable(R.drawable.otp_bg));
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
                    otp4.setBackgroundDrawable(getResources().getDrawable(R.drawable.time_bg));
                }
            }
        });
    }
}