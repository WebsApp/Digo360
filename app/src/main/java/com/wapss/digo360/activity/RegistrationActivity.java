package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.Designation_Adapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.AreaResponse;
import com.wapss.digo360.response.CityResponse;
import com.wapss.digo360.response.Degree_Response;
import com.wapss.digo360.response.RegistrationResponse;
import com.wapss.digo360.response.SpecializationResponse;
import com.wapss.digo360.response.StateResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    TextView tv_registration;
    Spinner sp_state, sp_designation, sp_specialization, sp_dr, sp_city, sp_study_year, sp_degree, sp_area;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    CustomProgressDialog progressDialog;
    List<StateResponse.Result> stateResponse;
    private ArrayList<String> stringStateArrayList = new ArrayList<String>();
    List<SpecializationResponse.Result> specResponse;
    private ArrayList<String> stringSpecArrayList = new ArrayList<String>();
    List<Degree_Response.Result> DegreeResponse;
    private ArrayList<String> stringDigreeArrayList = new ArrayList<String>();
    List<CityResponse.Result> cityResponse;
    private ArrayList<String> stringCityArrayList = new ArrayList<String>();
    List<AreaResponse.Result> areaResponse;
    private ArrayList<String> stringAreaArrayList = new ArrayList<String>();
    RadioGroup rg_gneder;
    private Calendar calendar;
    RadioButton rb_male, rb_female, rb_other;
    String gender, deviceToken, experienceLevel;
    List<String> items = Arrays.asList("Select Your Designation", "EXPERIENCE", "INTERN", "STUDENT");
    List<String> study_year_list = Arrays.asList("Select Your Study Year", "FIRST YEAR", "SECOND YEAR", "THIRD YEAR", "FOURTH YEAR");
    String[] drOther = {"Dr", "Other"};
    EditText et_name, et_address, et_PinCode, et_email, et_desig, et_spec, et_DOB, et_s_year, et_degree, et_state, et_city, et_area;
    String dr,tnc, name, pin, area, title, pinCode, email, address, Token, currentTime, stateId, cityId, study_Year, specialization_Id, std_year, stryear;
    LinearLayout cv_study_year, cv_specialization, cv_degree, cv_city, cv_area;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int textColor = Color.parseColor("#800080");
    private String strSpec = "";
    private String specializationId = "";
    private String studyYear = "";
    private String dob = "";
    private String degreeId;
    ImageView iv_date;
    Date dateNow = null;
    CheckBox checkBoxTerms;
    String loginStatus ="true";
    MaterialSpinner spinner_degree,spinner_spec;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        /*Id Initialize*/
        checkBoxTerms = findViewById(R.id.checkBoxTerms);
        spinner_degree = findViewById(R.id.spinner_degree);
        spinner_spec = findViewById(R.id.spinner_spec);
        et_area = findViewById(R.id.et_area);
        cv_area = findViewById(R.id.cv_area);
        cv_city = findViewById(R.id.cv_city);
        et_city = findViewById(R.id.et_city);
        sp_city = findViewById(R.id.sp_city);
        et_state = findViewById(R.id.et_state);
        cv_degree = findViewById(R.id.cv_degree);
        cv_specialization = findViewById(R.id.cv_specialization);
        cv_study_year = findViewById(R.id.cv_study_year);
        //et_degree = findViewById(R.id.et_degree);
        ///et_s_year = findViewById(R.id.et_s_year);
        et_DOB = findViewById(R.id.et_DOB);
        iv_date = findViewById(R.id.iv_date);
        //et_spec = findViewById(R.id.et_spec);
        et_desig = findViewById(R.id.et_desig);
        tv_registration = findViewById(R.id.tv_registration);
        sp_state = findViewById(R.id.sp_state);
//        sp_study_year = findViewById(R.id.sp_study_year);
        //sp_degree = findViewById(R.id.sp_degree);
        rg_gneder = findViewById(R.id.rg_gneder);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        rb_other = findViewById(R.id.rb_other);
        sp_designation = findViewById(R.id.sp_designation);
        //sp_specialization = findViewById(R.id.sp_specialization);
        sp_dr = findViewById(R.id.sp_dr);
        et_name = findViewById(R.id.et_name);
        sp_area = findViewById(R.id.sp_area);
        et_address = findViewById(R.id.et_address);
        et_PinCode = findViewById(R.id.et_PinCode);
        et_email = findViewById(R.id.et_email);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        /*Function Code*/
        callStateDate();
        /*Dr choose*/
        ArrayAdapter<String> Dr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, drOther);
        Dr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dr.setAdapter(Dr);
        sp_dr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dr = sp_dr.getSelectedItem().toString();
                if (dr.equals("Dr")){
                    ((TextView) sp_dr.getChildAt(0)).setTextColor(textColor);
                    ((TextView) sp_dr.getChildAt(0)).setTextSize(20);
                    ((TextView) sp_dr.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    title = (String) adapterView.getItemAtPosition(i);
                    tv_registration.setVisibility(View.VISIBLE);
                }
                else {
                    final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(RegistrationActivity.this);
                    LayoutInflater inflater1 = getLayoutInflater();
                    View dialogView1 = inflater1.inflate(R.layout.other_layout,null);
                    builder1.setCancelable(false);
                    builder1.setView(dialogView1);
                    final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                    alertDialog1.setCanceledOnTouchOutside(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog1.dismiss();
                            tv_registration.setVisibility(View.GONE);
                        }
                    },2000);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*Gender Choose*/
        rg_gneder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_male.isChecked()) {
                    gender = "MALE";
                } else if (rb_female.isChecked()) {
                    gender = "FEMALE";
                } else if (rb_other.isChecked()) {
                    gender = "OTHER";
                }
            }
        });

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        calendar = Calendar.getInstance();
        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                et_DOB.setText(dateFormat1.format(calendar.getTime()));
                                dob = dateFormat1.format(calendar.getTime());
                            }
                        }, year-20, month, dayOfMonth);
                calendar.set(year-20,month,dayOfMonth);
                long value=calendar.getTimeInMillis();

                datePickerDialog.getDatePicker().setMaxDate(value);
                datePickerDialog.show();
            }
        });
        /*EXP level choose*/
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Select Your Designation","EXPERIENCE", "INTERN", "STUDENT");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) {

                }
                else {
                    strSpec = items.get(position);
                    if (strSpec.equals("EXPERIENCE")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.VISIBLE);
                        cv_study_year.setVisibility(View.GONE);
                        study_Year = null;
                        experienceLevel = "EXPERIENCE";
                        et_desig.setText(strSpec);
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                    else if (strSpec.equals("INTERN")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.GONE);
                        cv_study_year.setVisibility(View.GONE);
                        study_Year = null;
                        experienceLevel = "INTERN";
                        et_desig.setText(strSpec);
                        specialization_Id = null;
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                    else if (strSpec.equals("STUDENT")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_study_year.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.GONE);
                        specialization_Id = null;
                        experienceLevel = "STUDENT";
                        et_desig.setText(strSpec);
                        stryear = "STUDENT";
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                }
            }
        });
        /*Study Year*/
        MaterialSpinner spinner_study = (MaterialSpinner) findViewById(R.id.spinner_study);
        spinner_study.setItems("Select Your Study Year", "FIRST YEAR", "SECOND YEAR", "THIRD YEAR", "FOURTH YEAR");
        spinner_study.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
               /* if (position == 0) {

                }
                else {
                    strSpec = items.get(position);
                    if (strSpec.equals("EXPERIENCE")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.VISIBLE);
                        cv_study_year.setVisibility(View.GONE);
                        study_Year = null;
                        experienceLevel = "EXPERIENCE";
                        et_desig.setText(strSpec);
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                    else if (strSpec.equals("INTERN")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.GONE);
                        cv_study_year.setVisibility(View.GONE);
                        study_Year = null;
                        experienceLevel = "INTERN";
                        et_desig.setText(strSpec);
                        specialization_Id = null;
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                    else if (strSpec.equals("STUDENT")) {
                        cv_degree.setVisibility(View.VISIBLE);
                        cv_study_year.setVisibility(View.VISIBLE);
                        cv_specialization.setVisibility(View.GONE);
                        specialization_Id = null;
                        experienceLevel = "STUDENT";
                        et_desig.setText(strSpec);
                        stryear = "STUDENT";
                        callDegreeAPI();
                        stringDigreeArrayList.clear();
                    }
                }*/
                if (position == 0) {

                } else {
                    studyYear = study_year_list.get(position);
                }
            }
        });

        if (checkBoxTerms.isChecked()){
            tnc = "YES";
        }
        else {
            tnc = "YES";
        }
        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                String DOB = et_DOB.getText().toString();
                pinCode = et_PinCode.getText().toString();
                email = et_email.getText().toString();
                address = et_address.getText().toString();
                String level = et_desig.getText().toString();
                //String degree = et_degree.getText().toString();
                //String Specialization = et_spec.getText().toString();
                String state = et_state.getText().toString();
                String city = et_city.getText().toString();
                String area = et_area.getText().toString();
                //String study_Year = et_s_year.getText().toString();
                if (name.equals("")){
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (DOB.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Select Your DOB", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (level.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Select Your Experience Level", Toast.LENGTH_SHORT).show();
                }
                else if (level.equals("EXPERIENCE")) {
//                    if (degree.equals("")){
//                        Toast.makeText(RegistrationActivity.this, "Please Select Your Degree", Toast.LENGTH_SHORT).show();
//                    }
//                    else if (Specialization.equals("")) {
//                        Toast.makeText(RegistrationActivity.this, "Please Select Your Specialization", Toast.LENGTH_SHORT).show();
//                    }
                    if (state.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your State", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your City", Toast.LENGTH_SHORT).show();
                    }
                    else if (area.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your Area", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        callRegistrationAPI();
                    }
                }
                else if (level.equals("INTERN")) {
//                    if (degree.equals("")){
//                        Toast.makeText(RegistrationActivity.this, "Please Select Your Degree", Toast.LENGTH_SHORT).show();
//                    }
                    if (state.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your State", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your City", Toast.LENGTH_SHORT).show();
                    }
                    else if (area.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your Area", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        callRegistrationAPI();
                    }
                }
                else if (level.equals("STUDENT")) {
//                    if (degree.equals("")){
//                        Toast.makeText(RegistrationActivity.this, "Please Select Your Degree", Toast.LENGTH_SHORT).show();
//                    }
//                    if (study_Year.equals("")){
//                        Toast.makeText(RegistrationActivity.this, "Please Select Your Study Year", Toast.LENGTH_SHORT).show();
//                    }
                    if (state.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your State", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your City", Toast.LENGTH_SHORT).show();
                    }
                    else if (area.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Please Select Your Area", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        callRegistrationAPI();
                    }
                }
                else {
                    callRegistrationAPI();
                }
            }
        });
    }
    private void callRegistrationAPI() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<RegistrationResponse> state_apiCall = ApiService.apiHolders().Registration(Token, title, name, gender, dob, email, experienceLevel,
                degreeId, study_Year, specialization_Id, address, stateId, cityId, area, pinCode,"Yes");
        state_apiCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    //editor.putString("DR_NAME", response.body().getName());
                    //specResponse = response.body().();
                    editor.putString("loginStatus", loginStatus);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Successfully Registration", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, ChooseLanguageActivity.class);
                    startActivity(intent);

                }
                else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
    private void callDegreeAPI() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<Degree_Response> degree_apiCall = ApiService.apiHolders().getDegreeData(Token);
        degree_apiCall.enqueue(new Callback<Degree_Response>() {
            @Override
            public void onResponse(Call<Degree_Response> call, Response<Degree_Response> response) {
               /* if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
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
//                    stringDigreeArrayList.add(0, "Select Suitable Degree");
                    ArrayAdapter<String> degree = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringDigreeArrayList);
                    degree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_degree.setAdapter(degree);
                    sp_degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    degreeId = DegreeResponse.get(i).getId();
                                    et_degree.setText(DegreeResponse.get(i).getName());
                                    stringDigreeArrayList.clear();
                                    callSpecializationAPI(degreeId);
                                    stringSpecArrayList.clear();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
                else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }*/

                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
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
                    spinner_degree.setItems(stringDigreeArrayList);
                    //spinner.setSelectedIndex(0);
                    spinner_degree.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                degreeId = DegreeResponse.get(position).getId();
                                //et_degree.setText(DegreeResponse.get(position).getName());
                                //stringDigreeArrayList.clear();
                                stringSpecArrayList.clear();
                                callSpecializationAPI(degreeId);
                        }
                    });
                }
                else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Degree_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
    private void callSpecializationAPI(String degreeId) {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<SpecializationResponse> Specialization_apiCall = ApiService.apiHolders().getSpecData(degreeId,Token,50,0);
        Specialization_apiCall.enqueue(new Callback<SpecializationResponse>() {
            @Override
            public void onResponse(Call<SpecializationResponse> call, Response<SpecializationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
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

                    /*ArrayAdapter<String> spec = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringSpecArrayList);
                    spec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_specialization.setAdapter(spec);
                    sp_specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    specializationId = specResponse.get(i).getId();
                                    et_spec.setText(specResponse.get(i).getName());
                                    stringDigreeArrayList.clear();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
//                            if (i == 0) {
//
//                            } else {
//                                specializationId = specResponse.get(i).getId();
//                                et_spec.setText(specResponse.get(i).getName());
//                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });*/
                    spinner_spec.setItems(stringSpecArrayList);
                    //spinner.setSelectedIndex(0);
                    spinner_spec.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            if (position == 0) {

                            }
                            else {
                                specializationId = specResponse.get(position).getId();
                                //et_spec.setText(specResponse.get(position).getName());
                                stringDigreeArrayList.clear();
                            }
                        }
                    });
                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SpecializationResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
    private void callStateDate() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<StateResponse> state_apiCall = ApiService.apiHolders().getStateData(Token);
        state_apiCall.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                /*if (response.isSuccessful()) {
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
                    sp_state.setAdapter(state);

                    sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            int keyNameStr = (stateResponse.get(i).getId());
                            StateId = String.valueOf(keyNameStr);
                            Log.d("Sp_Id", StateId);
                            //String stateName = state_spinner.getSelectedItem().toString();
                            //callCity(StateId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else {
                    progressDialog.hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }*/
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    stateResponse = response.body().getResult();
                    List<StateResponse.Result> statelist = new ArrayList<StateResponse.Result>();

                    for (int i = 0; i < stateResponse.size(); i++) {
                        StateResponse.Result state_Response = new StateResponse.Result();
                        state_Response.setId(stateResponse.get(i).getId());
                        state_Response.setName(stateResponse.get(i).getName());
                        statelist.add(state_Response);

                    }
                    for (int i = 0; i < statelist.size(); i++) {
                        stringStateArrayList.add(stateResponse.get(i).getName());

                    }
                    ArrayAdapter<String> state = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringStateArrayList);
                    state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_state.setAdapter(state);
                    sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            stateId = String.valueOf(stateResponse.get(i).getId());
                            et_state.setText(stateResponse.get(i).getName());
                            cv_city.setVisibility(View.VISIBLE);
                            callCity(stateId);
                            stringCityArrayList.clear();
                            et_city.getText().clear();
                            et_area.getText().clear();
                            et_PinCode.getText().clear();
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
    private void callCity(String stateId) {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<CityResponse> state_apiCall = ApiService.apiHolders().getCityData(stateId, Token);
        state_apiCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    cityResponse = response.body().getResult();
                    List<CityResponse.Result> citylist = new ArrayList<CityResponse.Result>();

                    for (int i = 0; i < cityResponse.size(); i++) {
                        CityResponse.Result city_Response = new CityResponse.Result();
                        city_Response.setId(cityResponse.get(i).getId());
                        city_Response.setName(cityResponse.get(i).getName());
                        stringAreaArrayList.clear();
                        citylist.add(city_Response);

                    }
                    for (int i = 0; i < citylist.size(); i++) {
                        stringCityArrayList.add(cityResponse.get(i).getName());

                    }
                    ArrayAdapter<String> city = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringCityArrayList);
                    city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(city);
                    sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            cityId = String.valueOf(cityResponse.get(i).getId());
                            et_city.setText(cityResponse.get(i).getName());
                            cv_area.setVisibility(View.VISIBLE);
                            callArea(cityId);
                            stringCityArrayList.clear();
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
            public void onFailure(Call<CityResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
    private void callArea(String cityId) {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<AreaResponse> state_apiCall = ApiService.apiHolders().getAreaData(cityId, Token,50,0);
        state_apiCall.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
                    assert response.body() != null;
                    areaResponse = response.body().getResult();
                    List<AreaResponse.Result> arealist = new ArrayList<AreaResponse.Result>();

                    for (int i = 0; i < areaResponse.size(); i++) {
                        AreaResponse.Result area_Response = new AreaResponse.Result();
                        area_Response.setId(areaResponse.get(i).getId());
                        area_Response.setName(areaResponse.get(i).getName());
                        area_Response.setName(areaResponse.get(i).getPincode());
                        arealist.add(area_Response);

                    }
                    for (int i = 0; i < arealist.size(); i++) {
                        stringAreaArrayList.add(areaResponse.get(i).getName());

                    }
                    ArrayAdapter<String> city = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stringAreaArrayList);
                    city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(city);
                    sp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            area = String.valueOf(areaResponse.get(i).getId());
                            pin = String.valueOf(areaResponse.get(i).getPincode());
                            et_area.setText(areaResponse.get(i).getName());
                            et_PinCode.setText(pin);
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
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}