package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.NotificationAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.response.NotificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    ImageView back, iv_noti;
    TextView btn_back_home;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    RecyclerView rv_notification;
    List<NotificationResponse.Result> notification;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        back = findViewById(R.id.back);
        btn_back_home = findViewById(R.id.btn_back_home);
        progressDialog = new CustomProgressDialog(this);
        rv_notification = findViewById(R.id.rv_notification);
        iv_noti = findViewById(R.id.iv_noti);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        /*callNotificationAPI(deviceToken);*/
    }

    /*private void callNotificationAPI(String deviceToken) {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<NotificationResponse> banner_apiCall = ApiService.apiHolders().notificationAPi(Token);
        banner_apiCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    int total = response.body().getTotal();
                    if (total == 0) {
                        iv_noti.setVisibility(View.VISIBLE);
                    }
                    notification = response.body().getResult();
                    notificationAdapter = new NotificationAdapter(getApplicationContext(), notification);
                    rv_notification.setAdapter(notificationAdapter);
                    rv_notification.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                } else {
                    progressDialog.dismiss();
                    iv_noti.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                progressDialog.dismiss();
                iv_noti.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}