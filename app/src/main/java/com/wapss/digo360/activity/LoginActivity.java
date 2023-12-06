package com.wapss.digo360.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.authentication.DeviceUtils;
import com.wapss.digo360.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_login;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;
    String fb_token, deviceId, phone;
    EditText et_phone;
    CustomProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initi();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));


        if (!isNotificationPermissionGranted()) {
            // Request notification permission
            requestNotificationPermission();
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        fb_token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.next, token);
                        Log.d(TAG, fb_token);

//                        Toast.makeText(OTP_Verify.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                callLogin(phone, deviceId);
            }
        });
    }

    private void callLogin(String phone, String DeviceId) {
        progressDialog.showProgressDialog();
        Call<LoginResponse> login_apiCall = ApiService.apiHolders().login(phone, DeviceId);
        login_apiCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse userLogin_response = response.body();
                    String phoneNum = response.body().getResult().getMessage();
                    editor.putString("phone", phoneNum);
                    // editor.putString("fcm",fb_token);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                    startActivity(intent);
                    progressDialog.hideProgressDialog();

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }

    private void initi() {
        tv_login = findViewById(R.id.tv_login);
        et_phone = findViewById(R.id.et_phone);
        deviceId = DeviceUtils.getDeviceId(getApplicationContext());
        progressDialog = new CustomProgressDialog(this);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
    }

    private boolean isNotificationPermissionGranted() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        return notificationManager.areNotificationsEnabled();
    }

    private void requestNotificationPermission() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Check if the user granted notification permission
            if (isNotificationPermissionGranted()) {
                // Permission granted, proceed with your app logic
            } else {
                // Permission not granted, handle accordingly
            }
        }
    }
}