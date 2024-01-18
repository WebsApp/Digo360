package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.Patient_Details_Adapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Patient_Info_Model;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.Patient_Check_Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousCases extends AppCompatActivity {
    RecyclerView pt_recycler_View;
    Patient_Details_Adapter P_adapter;
    List<Patient_Info_Model> patient_list = new ArrayList<>();
    CustomProgressDialog progressDialog;
    String TOKEN;

    FloatingActionButton flote;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_cases);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        flote = findViewById(R.id.flote);
        pt_recycler_View = findViewById(R.id.pt_recycler_View);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        pt_recycler_View.setLayoutManager(layoutManager);
        flote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent());
            }
        });
        //cases_api();
    }

    private void cases_api() {
        progressDialog.showProgressDialog();
        TOKEN = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDE1ZDQzLWRiOGMtNGMxZi04ZTZkLWZjMzE1NjQ0ZDhmMCIsImlhdCI6MTcwNTQ3ODUwMSwiZXhwIjoxNzM3MDE0NTAxfQ.jrRZjtg3ajeD5xsXmjvIOJ7UIGbIGusiApb-BlTElDI";
        int limit = 10;
        int offset = 0;
        String keyword = "7896541235";
        Call<Patient_Check_Response> patient_check = ApiService.apiHolders().Patient_check(TOKEN,limit,offset,keyword);
        patient_check.enqueue(new Callback<Patient_Check_Response>() {
            @Override
            public void onResponse(Call<Patient_Check_Response> call, Response<Patient_Check_Response> response) {
                if (response.isSuccessful()){
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
                }
            }

            @Override
            public void onFailure(Call<Patient_Check_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}