package com.wapss.digo360.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.SettingHomeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpPage extends AppCompatActivity {
    ImageView back;
    TextView btn_back_home;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,Token,pageName;
    CustomProgressDialog progressDialog;
    LinearLayout blank_layout;
    HelpAdapter helpAdapter;
    List<Help_Model> faq_list = new ArrayList<>();
    RecyclerView rv_help;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        /*Initialization*/
        rv_help  = findViewById(R.id.rv_help);
        back = findViewById(R.id.back);
        blank_layout = findViewById(R.id.blank_layout);
        progressDialog = new CustomProgressDialog(HelpPage.this);
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        assert bundle != null;
        pageName = bundle.getString("PAGE_NAME");

        btn_back_home = findViewById(R.id.btn_back_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_help.setLayoutManager(layoutManager);;
        help_api(pageName);
    }

    private void help_api(String pageName) {
        progressDialog.showProgressDialog();
        Token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDE1ZDQzLWRiOGMtNGMxZi04ZTZkLWZjMzE1NjQ0ZDhmMCIsImlhdCI6MTcwNTQ3ODUwMSwiZXhwIjoxNzM3MDE0NTAxfQ.jrRZjtg3ajeD5xsXmjvIOJ7UIGbIGusiApb-BlTElDI";

        Call<FaqResponse> help_apiCall = ApiService.apiHolders().faqAPi(Token,pageName);
        help_apiCall.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<FaqResponse.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Help_Model m_model = new Help_Model(arrayList.get(i).getQuestion(),arrayList.get(i).getAnswer());
                        faq_list.add(m_model);
                    }
                    if (faq_list.size()>0){
                        helpAdapter = new HelpAdapter(faq_list,getApplicationContext());
                        rv_help.setAdapter(helpAdapter);
                        blank_layout.setVisibility(View.GONE);
                    }
                    else {
                        blank_layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
                Toast.makeText(HelpPage.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}