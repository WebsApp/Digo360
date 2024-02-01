package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.PatientsDetailsViewAdapter;
import com.wapss.digo360.adapter.QuestionAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.AnswerListener;
import com.wapss.digo360.interfaces.PatientsViewListener;
import com.wapss.digo360.response.PatientsDetailsViewResponse;
import com.wapss.digo360.response.QuestionResponse;
import com.wapss.digo360.utility.EncryptionUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patient_Details extends AppCompatActivity {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    RecyclerView rv_patientsDetails;
    String P_ID,acc_id;
    PatientsDetailsViewAdapter questionAdapter;
    List<PatientsDetailsViewResponse.Result> responsess;
    FloatingActionButton fb_check_problem;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        rv_patientsDetails = findViewById(R.id.rv_patientsDetails);
        back = findViewById(R.id.back);
        fb_check_problem = findViewById(R.id.fb_check_problem);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            P_ID = bundle.getString("P_ID");
            acc_id = bundle.getString("acc_id");
        }
        callApI(P_ID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fb_check_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("P_ID",P_ID);
                Intent intent = new Intent(Patient_Details.this,PatientsProblemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void callApI(String pId) {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<PatientsDetailsViewResponse> banner_apiCall = ApiService.apiHolders().PatientsDetailsView(Token, pId,50,0,"");
        banner_apiCall.enqueue(new Callback<PatientsDetailsViewResponse>() {
            @Override
            public void onResponse(Call<PatientsDetailsViewResponse> call, Response<PatientsDetailsViewResponse> response) {
                if (response.code() == 404) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                } else if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    responsess = response.body().getResult();
                    questionAdapter = new PatientsDetailsViewAdapter(getApplicationContext(),responsess,acc_id, new PatientsViewListener() {
                        @Override
                        public void onItemClickedItem(PatientsDetailsViewResponse.Result item, int position) {
                            //PDF page
                        }
                    });
                    rv_patientsDetails.setAdapter(questionAdapter);
                    rv_patientsDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PatientsDetailsViewResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}