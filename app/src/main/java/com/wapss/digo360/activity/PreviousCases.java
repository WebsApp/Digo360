package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.Patient_Details_Adapter;
import com.wapss.digo360.adapter.Report_Adapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.ExsitingpatientsResponse;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Patient_Info_Model;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.Patient_Check_Response;
import com.wapss.digo360.response.Patient_Count_Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousCases extends AppCompatActivity {
    RecyclerView pt_recycler_View;
    Patient_Details_Adapter P_adapter;
    List<Patient_Check_Response.Result> patientsResponse;
    CustomProgressDialog progressDialog;
    String Token,deviceToken,keyword;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    FloatingActionButton flote;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_cases);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressDialog = new CustomProgressDialog(PreviousCases.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            keyword = bundle.getString("p_number");
        }

        back = findViewById(R.id.back);
        flote = findViewById(R.id.flote);
        pt_recycler_View = findViewById(R.id.pt_recycler_View);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        pt_recycler_View.setLayoutManager(layoutManager);
        flote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("p_number",keyword);
                Intent intent = new Intent(PreviousCases.this,NewCasectivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        cases_api();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void cases_api() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;

        Call<Patient_Check_Response> patient_check = ApiService.apiHolders().Patient_check(Token,50,0,keyword);
        patient_check.enqueue(new Callback<Patient_Check_Response>() {
            @Override
            public void onResponse(Call<Patient_Check_Response> call, Response<Patient_Check_Response> response) {
                /*if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<Patient_Check_Response.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Patient_Info_Model m_model = new Patient_Info_Model(arrayList.get(i).getName(),arrayList.get(i).getGender(),
                                arrayList.get(i).getId());
                        patient_list.add(m_model);
                    }
                    if (patient_list.size()>0){
                        P_adapter = new Patient_Details_Adapter(patient_list,getApplicationContext());
                        pt_recycler_View.setAdapter(P_adapter);
                    }
                    else {

                    }
                }*/
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    //blank_layout.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    patientsResponse = response.body().getResult();
                    int total = response.body().getTotal();
                    if (total == 0) {
                        //blank_layout.setVisibility(View.VISIBLE);
                        pt_recycler_View.setVisibility(View.GONE);
                    }
                    P_adapter = new Patient_Details_Adapter(getApplicationContext(), patientsResponse, new ExsitingpatientsResponse() {
                        @Override
                        public void onItemClickedItem(Patient_Check_Response.Result item, int position) {
                            String id = item.getId();
                            String acc_id = item.getAccount().getId();
                            Log.e("pid",""+id);
                            Bundle bundle = new Bundle();
                            bundle.putString("P_ID", id);
                            bundle.putString("acc_id", acc_id);
                            Intent i = new Intent(PreviousCases.this, Patient_Details.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    pt_recycler_View.setAdapter(P_adapter);
                    pt_recycler_View.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    //blank_layout.setVisibility(View.VISIBLE);
                    pt_recycler_View.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Patient_Check_Response> call, Throwable t) {
                progressDialog.dismiss();
                pt_recycler_View.setVisibility(View.GONE);
                Toast.makeText(PreviousCases.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}