package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.fragment.TopDiseasesFragment;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.PatientDetails_Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCasectivity extends AppCompatActivity {
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    Button start_progress;
    LinearLayout ll_address, ll_age, ll_Dob, ll_phone_num, ll_phone_email, ll_otherProblem;
    RadioGroup rg_age, rg_phone, rg_address, rg_gender;
    RadioButton rb_doB, rb_age, rb_phone, rb_email, rb_yes, rb_no, rb_male, rb_female, rb_other;
    TextView tv_submit;
    ImageView back, iv_date;
    EditText pt_name, pt_age, pt_DOB, pt_phone, pt_email, pt_full_Address, pt_State, pt_Area, pt_city, pt_pinCode;
    String currentTime, dob = "", name, age = "";
    private Calendar calendar;
    Date dateNow = null;
    String ss, TOKEN, stateName, cityName, areaName, phoneNumber, email, address, pincode;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken, p_number;
    CustomProgressDialog progressDialog;
    String Age_and_DOB = "";
    String gender  ="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_casectivity);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            p_number = bundle.getString("p_number");
        }

        initi();
        rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_doB.isChecked()) {
                    ll_Dob.setVisibility(View.VISIBLE);
                    ll_age.setVisibility(View.GONE);
                    pt_age.setText("");
                    ss = "dob";
                    age = "";
                } else if (rb_age.isChecked()) {
                    ll_Dob.setVisibility(View.GONE);
                    ll_age.setVisibility(View.VISIBLE);
                    pt_DOB.setText("");
                    ss = "age";
                    dob = "";
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (pt_name.getText().toString().isEmpty()){
                    Toast.makeText(NewCasectivity.this, "Please Enter Patient Name", Toast.LENGTH_SHORT).show();
                }
                else if (ss.equals("dob")){
                    if (pt_DOB.getText().toString().isEmpty()){
                        Toast.makeText(NewCasectivity.this, "Please Enter DOB ", Toast.LENGTH_SHORT).show();
                    }
                }else if (ss.equals("age")){
                    if (pt_age.getText().toString().isEmpty()){
                        Toast.makeText(NewCasectivity.this, "Please Enter  Age", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    //patient_details();
                }*/
                String P_name = pt_name.getText().toString();
                String P_DOB = pt_DOB.getText().toString();
                String P_Age = pt_age.getText().toString();
                Age_and_DOB = pt_DOB.getText().toString();
                Age_and_DOB = pt_age.getText().toString();
                if (P_name.equals("")){
                    Toast.makeText(NewCasectivity.this, "Enter Patient name", Toast.LENGTH_SHORT).show();
                }
                else if (pt_DOB.getText().toString().isEmpty()) {
                    Toast.makeText(NewCasectivity.this, "Please Select DOB or Age", Toast.LENGTH_SHORT).show();
                }
                else if (gender.equals("")) {
                    Toast.makeText(NewCasectivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
               else if (pt_full_Address.getText().toString().isEmpty()) {
                    Toast.makeText(NewCasectivity.this, "Please Enter Patient Address", Toast.LENGTH_SHORT).show();
                } else {
                    patient_details();
                }
            }
        });
    }

    private void initi() {
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back);
        start_progress = findViewById(R.id.start_progress);
        ll_address = findViewById(R.id.ll_address);
        ll_age = findViewById(R.id.ll_age);
        rg_age = findViewById(R.id.rg_age);
        rb_doB = findViewById(R.id.rb_doB);
        rb_age = findViewById(R.id.rb_age);
        ll_Dob = findViewById(R.id.ll_Dob);
        rg_phone = findViewById(R.id.rg_phone);
        rb_phone = findViewById(R.id.rb_phone);
        rb_email = findViewById(R.id.rb_email);
        rb_other = findViewById(R.id.rb_other);
        rb_female = findViewById(R.id.rb_female);
        rb_male = findViewById(R.id.rb_male);
        rg_gender = findViewById(R.id.rg_gender);
        iv_date = findViewById(R.id.iv_date);
        ll_phone_num = findViewById(R.id.ll_phone_num);
        ll_phone_email = findViewById(R.id.ll_phone_email);
        //startProgress = NewCase.findViewById(R.id.start_progress);
        rg_address = findViewById(R.id.rg_address);
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);
        tv_submit = findViewById(R.id.tv_submit);
        pt_name = findViewById(R.id.pt_name);
        pt_age = findViewById(R.id.pt_age);
        pt_DOB = findViewById(R.id.pt_DOB);
        pt_phone = findViewById(R.id.pt_phone);
        pt_email = findViewById(R.id.pt_email);
        pt_full_Address = findViewById(R.id.pt_full_Address);
        pt_State = findViewById(R.id.pt_State);
        pt_Area = findViewById(R.id.pt_Area);
        pt_city = findViewById(R.id.pt_city);
        pt_pinCode = findViewById(R.id.pt_pinCode);
//        ll_otherProblem= findViewById(R.id.ll_otherProblem);
//        rg_other = findViewById(R.id.rg_other);
//        rb_yes_other = findViewById(R.id.rb_yes_other);
//        rb_no_other = findViewById(R.id.rb_no_other);

        pt_phone.setText(p_number);//phoneNumber
        start_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);
            }
        });
        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //DoB
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
                        NewCasectivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                pt_DOB.setText(dateFormat1.format(calendar.getTime()));
                                dob = dateFormat1.format(calendar.getTime());
                                Toast.makeText(NewCasectivity.this, "" + dob, Toast.LENGTH_SHORT).show();

                            }
                        }, year, month, dayOfMonth);
                try {
                    dateNow = formatter.parse(currentTime);
//                    Date date_to = formatter.parse(orderCloseTime);
//                    if (date_to.before(dateNow)) {
//                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + 24 * 60 * 60 * 2000);
//                    } else {
//                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() + 24 * 60 * 60 * 1000);
//                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        //phone number
        rg_phone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_phone.isChecked()) {
                    ll_phone_num.setVisibility(View.VISIBLE);
                    ll_phone_email.setVisibility(View.GONE);
                } else if (rb_email.isChecked()) {
                    ll_phone_num.setVisibility(View.GONE);
                    ll_phone_email.setVisibility(View.VISIBLE);
                }
            }
        });
        //Address
        rg_address.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_yes.isChecked()) {
                    ll_address.setVisibility(View.VISIBLE);
                } else if (rb_no.isChecked()) {
                    ll_address.setVisibility(View.GONE);
                }
            }
        });
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
    }

    private void patient_details() {
        name = pt_name.getText().toString();
        TOKEN = "Bearer " + deviceToken;
        stateName = pt_State.getText().toString();
        cityName = pt_city.getText().toString();
        areaName = pt_Area.getText().toString();
        phoneNumber = pt_phone.getText().toString();
        email = pt_email.getText().toString();
        address = pt_full_Address.getText().toString();
        pincode = pt_pinCode.getText().toString();

        Call<PatientDetails_Response> details_apiCall = ApiService.apiHolders().patient_details(TOKEN, name, dob, age, phoneNumber,
                email, stateName, cityName, areaName, address, gender, pincode);
        details_apiCall.enqueue(new Callback<PatientDetails_Response>() {
            @Override
            public void onResponse(Call<PatientDetails_Response> call, Response<PatientDetails_Response> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String p_id = response.body().getId();
                    Bundle bundle = new Bundle();
                    bundle.putString("P_ID", p_id);
                    editor.putString("gender", gender);
                    editor.commit();
                    Intent i = new Intent(NewCasectivity.this, PatientsProblemActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<PatientDetails_Response> call, Throwable t) {

            }
        });
    }

}