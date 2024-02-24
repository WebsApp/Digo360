package com.wapss.digo360.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_login,txt_tnc,txt_privacy_policy;
    CardView btn_card;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;
    String fb_token, deviceId, phone, phoneNum;
    EditText et_phone;
    CustomProgressDialog progressDialog;
    SharedPreferences loginPref,loginPref2;
    SharedPreferences.Editor editor,editor2;
    String regex = "^[6-9][0-9]{9}$";
    private Dialog noInternetDialog;
    Dialog dialog;
    String maintenance,version,versionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initi();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );

        //forcefully upgrade check
        // Get the package manager instance
        PackageManager packageManager = getPackageManager();

        try {
            // Get the package information
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            // Retrieve the version information
            versionName = packageInfo.versionName;
            // int versionCode = packageInfo.versionCode;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!Objects.equals(maintenance, "false") || !Objects.equals(version, versionName)){
            popUpMaintencae(maintenance,version);
        }

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
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(phone);
                if (matcher.matches()) {
                    callLogin(phone, deviceId);
                }
                else {
//                    final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
//                    LayoutInflater inflater1 = getLayoutInflater();
//                    View dialogView1 = inflater1.inflate(R.layout.invalid_phone_layout,null);
//                    builder1.setCancelable(false);
//                    builder1.setView(dialogView1);
//                    final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
//                    alertDialog1.show();
//                    alertDialog1.setCanceledOnTouchOutside(false);
//
                    noInternetDialog = new Dialog(LoginActivity.this);
                    noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    noInternetDialog.setContentView(R.layout.invalid_phone_layout);
                    noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    noInternetDialog.setCancelable(false);
                    noInternetDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            noInternetDialog.dismiss();
                        }
                    },2000);
                    noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
            }
        });
        txt_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("page", "Terms And Condition");
                bundle.putString("urls", "https://classpoint.in/terms_and_conditions.php");
                Intent intent = new Intent(LoginActivity.this, Privacy_Policy.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        txt_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("page", "Privacy Policy");
                bundle.putString("urls", "https://classpoint.in/terms_and_conditions.php");
                Intent intent = new Intent(LoginActivity.this, Privacy_Policy.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void popUpMaintencae(String maintenance, String version) {
        dialog.setContentView(R.layout.maintance);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        TextView tv_version = dialog.findViewById(R.id.tv_version);
        TextView tv_update = dialog.findViewById(R.id.tv_update);
        dialog.show();
        if (maintenance!=null) {
            if (maintenance.equals("true")) {
                tv_version.setText("Under Maintenance");
                tv_update.setVisibility(View.GONE);
            } else {
                tv_version.setText("New Version Available");
            }
        }
        tv_update .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.wapss.digo360"));
                startActivity(intent);
            }
        });
    }


    private void callLogin(String phone, String DeviceId) {
        progressDialog.showProgressDialog();
        Call<LoginResponse> login_apiCall = ApiService.apiHolders().login(phone, DeviceId);
        login_apiCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 201)  {
                    LoginResponse userLogin_response = response.body();
                    assert response.body() != null;
                    phoneNum = response.body().getPhoneNumber();
                    editor.putString("phone", phoneNum);
                     editor.putString("fcm",fb_token);
                    editor.commit();
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
        dialog = new Dialog(LoginActivity.this);
        btn_card = findViewById(R.id.btn_card);
        txt_privacy_policy = findViewById(R.id.txt_privacy_policy);
        txt_tnc = findViewById(R.id.txt_tnc);
        tv_login = findViewById(R.id.tv_login);
        et_phone = findViewById(R.id.et_phone);
        deviceId = DeviceUtils.getDeviceId(getApplicationContext());
        Log.d("d_ID", deviceId);
        progressDialog = new CustomProgressDialog(this);
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();

        loginPref2 = getSharedPreferences("login_pref2", Context.MODE_PRIVATE);
        editor2 = loginPref2.edit();

        maintenance = loginPref2.getString("maintenance", null);
        version = loginPref2.getString("version", null);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}