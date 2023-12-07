package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.AreaResponse;
import com.wapss.digo360.response.CityResponse;
import com.wapss.digo360.response.OTP_Response;
import com.wapss.digo360.response.StateResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    TextView tv_registration;
    Spinner state_spinner, sp_designation, sp_specialization, sp_dr, city_spinner,area_spinner;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    CustomProgressDialog progressDialog;
    List<StateResponse.Result> stateResponse;
    List<CityResponse.Result> cityResponse;
    List<AreaResponse.Result> areaResponse;
    private ArrayList<String> stringStateArrayList = new ArrayList<String>();
    private ArrayList<String> stringCityArrayList = new ArrayList<String>();
    private ArrayList<String> stringAreaArrayList = new ArrayList<String>();
    RadioGroup rg_gneder;
    RadioButton rb_male, rb_female;
    String gender;
    String[] Desg = {"Select Designation", "PENDING", "RESOLVED", "NOT RESOLVED", "IN PROCESS"};

    String[] Spec = {"Select Specialization", "Orthopedic Surgeon", "Dermatologist", "Neurologist", "Cardiologist"};
    String[] drOther = {"DR", "Other"};
    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tv_registration = findViewById(R.id.tv_registration);
        state_spinner = findViewById(R.id.state_spinner);
        rg_gneder = findViewById(R.id.rg_gneder);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        sp_designation = findViewById(R.id.sp_designation);
        sp_specialization = findViewById(R.id.sp_specialization);
        sp_dr = findViewById(R.id.sp_dr);
        et_name = findViewById(R.id.et_name);
        city_spinner = findViewById(R.id.city_spinner);
        area_spinner = findViewById(R.id.area_spinner);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        //designation
        ArrayAdapter<String> designation = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Desg);
        designation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_designation.setAdapter(designation);
        sp_designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String partnerS = sp_designation.getSelectedItem().toString();
                String design = partnerS;
                //Toast.makeText(getApplicationContext(),design,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Doctor
        ArrayAdapter<String> Dr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, drOther);
        Dr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dr.setAdapter(Dr);
        sp_dr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String partnerS = sp_dr.getSelectedItem().toString();
                String dr = partnerS;
                //Toast.makeText(getApplicationContext(),design,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spec
        ArrayAdapter<String> spec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Spec);
        spec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_specialization.setAdapter(spec);
        sp_specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String partnerS = sp_specialization.getSelectedItem().toString();
                String Spec = partnerS;
                //Toast.makeText(getApplicationContext(),design,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rg_gneder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_male.isChecked()) {
                    gender = "Male";
                } else if (rb_female.isChecked()) {
                    gender = "Female";
                }
            }
        });

        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, ChooseLanguageActivity.class);
                startActivity(intent);
            }
        });
        callStateDate();
    }

    private void callStateDate() {
        progressDialog.showProgressDialog();
        Call<StateResponse> state_apiCall = ApiService.apiHolders().getStateData(10, 0, "ACTIVE");
        state_apiCall.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    stateResponse = response.body().getResult();
                    List<StateResponse.Result> stateResponseList = new ArrayList<StateResponse.Result>();


                    for (int i = 0; i < stateResponse.size(); i++) {
                        StateResponse.Result response1 = new StateResponse.Result();
                        response1.setId(stateResponse.get(i).getId());
                        response1.setName(stateResponse.get(i).getName());
                        stateResponseList.add(response1);

                    }
                    for (int i = 0; i < stateResponseList.size(); i++) {
                        // stringArrayList.add(stateResponse.get(i).getId());
                        stringStateArrayList.add(stateResponse.get(i).getName());

                    }
                    ArrayAdapter<String> state = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringStateArrayList);
                    state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    state_spinner.setAdapter(state);

                    state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String stateName = state_spinner.getSelectedItem().toString();
                            callCity(stateName);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }

    private void callCity(String stateName) {
        progressDialog.showProgressDialog();
        Call<CityResponse> state_apiCall = ApiService.apiHolders().getCityData(stateName, 10, 0, "ACTIVE");
        state_apiCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    cityResponse = response.body().getResult();
                    List<CityResponse.Result> cityResponseList = new ArrayList<CityResponse.Result>();
                    for (int i = 0; i < cityResponse.size(); i++) {
                        CityResponse.Result response1 = new CityResponse.Result();
                        response1.setId(cityResponse.get(i).getId());
                        response1.setName(cityResponse.get(i).getName());
                        cityResponseList.add(response1);

                    }
                    for (int i = 0; i < cityResponseList.size(); i++) {
                        // stringArrayList.add(stateResponse.get(i).getId());
                        stringCityArrayList.add(cityResponseList.get(i).getName());

                    }
                    ArrayAdapter<String> city = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringCityArrayList);
                    city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city_spinner.setAdapter(city);
                    city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String cityName = city_spinner.getSelectedItem().toString();
                            callArea(cityName);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    // Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }

    private void callArea(String cityName) {
        progressDialog.showProgressDialog();
        Call<AreaResponse> state_apiCall = ApiService.apiHolders().getAreaData(cityName, 10, 0, "ACTIVE");
        state_apiCall.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    areaResponse = response.body().getResult();
                    List<AreaResponse.Result> areaResponse1 = new ArrayList<AreaResponse.Result>();
                    for (int i = 0; i < areaResponse.size(); i++) {
                        AreaResponse.Result response1 = new AreaResponse.Result();
                        response1.setId(areaResponse.get(i).getId());
                        response1.setName(areaResponse.get(i).getName());
                        areaResponse1.add(response1);

                    }
                    for (int i = 0; i < areaResponse1.size(); i++) {
                        // stringArrayList.add(stateResponse.get(i).getId());
                        stringAreaArrayList.add(areaResponse1.get(i).getName());

                    }
                    ArrayAdapter<String> area = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringAreaArrayList);
                    area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    area_spinner.setAdapter(area);
                    area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String cityName = area_spinner.getSelectedItem().toString();
                           // callArea(cityName);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    // Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}