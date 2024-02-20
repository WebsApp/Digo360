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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.PagesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pages extends AppCompatActivity {

    ImageView back;
    TextView txt_header,txt_Desc;
    String Token;
    String pageId;
    CustomProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        txt_Desc = findViewById(R.id.txt_Desc);
        txt_header = findViewById(R.id.txt_header);
        back = findViewById(R.id.back);
        progressDialog = new CustomProgressDialog(Pages.this);
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        assert bundle != null;
        pageId = bundle.getString("PAGE_ID");
        pages_Api(pageId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void pages_Api(String pageId) {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<PagesResponse> pages_apiCall = ApiService.apiHolders().getPage(pageId,Token);
        pages_apiCall.enqueue(new Callback<PagesResponse>() {
            @Override
            public void onResponse(Call<PagesResponse> call, Response<PagesResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    txt_header.setText(response.body().getTitle());
                    txt_Desc.setText(response.body().getDesc());
                }
                else {
                    progressDialog.hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<PagesResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}