package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.response.Degree_Response;
import com.wapss.digo360.response.SpecializationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test_Spec extends AppCompatActivity {
    List<Degree_Response.Result> DegreeResponse;
    private ArrayList<String> stringDigreeArrayList = new ArrayList<String>();
    MaterialSpinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spec);

        spinner = findViewById(R.id.spinner);
        spc_api();
    }

    private void spc_api() {
        String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImI2ZTk1OWI4LTg1MTMtNDY4NS05YmVjLWI4Y2Q5OTA4ZWJiOCIsImlhdCI6MTcwODU3ODk5NywiZXhwIjoxNzQwMTE0OTk3fQ.QcTOc9teqsq1poe99DEhNAworjhe6SBLwqcAKIS8B1o";
        String Token = "Bearer " + TOKEN;
        Call<Degree_Response> degree_apiCall = ApiService.apiHolders().getDegreeData(Token);
        degree_apiCall.enqueue(new Callback<Degree_Response>() {
            @Override
            public void onResponse(Call<Degree_Response> call, Response<Degree_Response> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    DegreeResponse = response.body().getResult();
                    List<Degree_Response.Result> degreeList = new ArrayList<Degree_Response.Result>();

                    for (int i = 0; i < DegreeResponse.size(); i++) {
                        Degree_Response.Result response2 = new Degree_Response.Result();
                        response2.setId(DegreeResponse.get(i).getId());
                        response2.setName(DegreeResponse.get(i).getName());
                        degreeList.add(response2);

                    }
                    for (int i = 0; i < degreeList.size(); i++) {
                        // stringDigreeArrayList.add(DegreeResponse.get(i).getId());
                        stringDigreeArrayList.add(DegreeResponse.get(i).getName());

                    }
                    spinner.setItems(stringDigreeArrayList);
                    //spinner.setSelectedIndex(0);
                    spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            if (position == 0) {
                                spinner.setHint("Select Your Degree");
                            }
                            else {
                                Toast.makeText(Test_Spec.this, ""+item, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Degree_Response> call, Throwable t) {

            }
        });

    }
}