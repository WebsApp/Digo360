package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.response.SpecializationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test_Spec extends AppCompatActivity {
   /* Spinner sp_specialization;
    EditText et_spec;
    List<SpecializationResponse.Result> specResponse;
    private ArrayList<String> stringSpecArrayList = new ArrayList<String>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spec);
//        sp_specialization = findViewById(R.id.sp_specialization);
//        et_spec = findViewById(R.id.et_spec);
        //spc_api();
    }

   /* private void spc_api() {
        String degreeId = "8e580121-96ec-4a5d-87d4-d65e5a80d5d8";
        String Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDE1ZDQzLWRiOGMtNGMxZi04ZTZkLWZjMzE1NjQ0ZDhmMCIsImlhdCI6MTcwNTY2MDA2MiwiZXhwIjoxNzM3MTk2MDYyfQ.DLpBOutEtY2JQBZ6BFeKw2b7bOURnYm5xleK6diqp1Q";
        int limit = 50;
        int  offset = 0;
        Call<SpecializationResponse> Specialization_apiCall = ApiService.apiHolders().getSpecData(degreeId,Token,limit,offset);
        Specialization_apiCall.enqueue(new Callback<SpecializationResponse>() {
            @Override
            public void onResponse(Call<SpecializationResponse> call, Response<SpecializationResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    specResponse = response.body().getResult();
                    List<SpecializationResponse.Result> specList = new ArrayList<SpecializationResponse.Result>();
                    for (int i = 0; i < specResponse.size(); i++) {
                        SpecializationResponse.Result specialization_response = new SpecializationResponse.Result();
                        specialization_response.setId(specResponse.get(i).getId());
                        specialization_response.setId(specResponse.get(i).getName());
                        specList.add(specialization_response);
                    }
                    for (int i = 0; i < specList.size(); i++) {
                        // stringArrayList.add(stateResponse.get(i).getId());
                        stringSpecArrayList.add(specResponse.get(i).getName());
                    }

                    ArrayAdapter<String> spec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringSpecArrayList);
                    spec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_specialization.setAdapter(spec);
                    sp_specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {

                            } else {
                                String specializationId = specResponse.get(i).getId();
                                et_spec.setText(specResponse.get(i).getName());
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SpecializationResponse> call, Throwable t) {
                //progressDialog.hideProgressDialog();
            }
        });

    }*/
}