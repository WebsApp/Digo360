package com.wapss.digo360.activity;

import static com.wapss.digo360.utility.DateUtils.calculateAge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.fragment.TopDiseasesFragment;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.PatientDetails_Response;
import com.wapss.digo360.response.PendingResponse;
import com.wapss.digo360.response.SettingResponse;
import com.wapss.digo360.utility.DateUtils;

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
    RadioGroup rg_age, rg_phone, rg_address, rg_gender, rg_dob;
    RadioButton rb_doB, rb_age, rb_phone, rb_email, rb_yes, rb_no, rb_male, rb_female, rb_other, rb_age_yes, rb_age_no, rb_doB_yes, rb_dob_no;
    TextView tv_submit;
    ImageView back, iv_date;
    EditText pt_name, pt_age, pt_DOB, pt_phone, pt_email, pt_full_Address, pt_State, pt_Area, pt_city, pt_pinCode;
    String currentTime, dob = "", name;
    private Calendar calendar;
    Date dateNow = null;
    String ss, TOKEN, stateName, cityName, areaName, phoneNumber, email, address, pincode;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken, p_number, age;
    CustomProgressDialog progressDialog;
    String Age_and_DOB = "";
    String gender = "";
    String CheckStatus;
    Dialog dialog;
    RelativeLayout rl_layout;

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

        callPendingAPi();
        /*rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_age_yes.isChecked()) {
                    ll_age.setVisibility(View.VISIBLE);
                    //ss = "dob";
                } else if (rb_age_no.isChecked()) {
                    ll_age.setVisibility(View.GONE);
                    //ss = "age";
                    //dob = "";
                }

            }
        });
        rg_dob.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_doB_yes.isChecked()) {
                    ll_Dob.setVisibility(View.VISIBLE);
                    //ss = "dob";
                } else if (rb_dob_no.isChecked()) {
                    ll_Dob.setVisibility(View.GONE);
                    //ss = "age";
                    //dob = "";
                }

            }
        });*/
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P_name = pt_name.getText().toString();
                String P_DOB = pt_DOB.getText().toString();
                String P_Age = pt_age.getText().toString();
                Age_and_DOB = pt_DOB.getText().toString();
                Age_and_DOB = pt_age.getText().toString();
                if (P_name.equals("")) {
                    Toast.makeText(NewCasectivity.this, "Enter Patient name", Toast.LENGTH_SHORT).show();
                }
                else if (pt_DOB.getText().toString().isEmpty()) {
                    Toast.makeText(NewCasectivity.this, "Please Select DOB", Toast.LENGTH_SHORT).show();
                }
                else if (gender.equals("")) {
                    Toast.makeText(NewCasectivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
//               else if (pt_full_Address.getText().toString().isEmpty()) {
//                    Toast.makeText(NewCasectivity.this, "Please Enter Patient Address", Toast.LENGTH_SHORT).show();
//                }
                else {
                    if (CheckStatus.equals("PENDING")) {
                        // pendingPopUp();
                        Snackbar errorBar;
                        errorBar = Snackbar.make(rl_layout, "Doctor's Profile is Currently Under Verification", Snackbar.LENGTH_LONG);
                        errorBar.setTextColor(getResources().getColor(R.color.white));
                        errorBar.setActionTextColor(getResources().getColor(R.color.white));
                        errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                        errorBar.show();
                    } else {
                        patient_details();
                    }
                }
            }
        });
    }
    private void pendingPopUp() {
        dialog.setContentView(R.layout.pending);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void callPendingAPi() {
        TOKEN = "Bearer " + deviceToken;
        Call<PendingResponse> profile_apiCall = ApiService.apiHolders().pendingDoctor(TOKEN);
        profile_apiCall.enqueue(new Callback<PendingResponse>() {
            @Override
            public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                if (response.code() == 401) {

                } else {
                    if (response.isSuccessful()) {
                        PendingResponse pendingResponse = response.body();
                        assert pendingResponse != null;
                        CheckStatus = pendingResponse.getStatus();
                    } else {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingResponse> call, Throwable t) {
                Toast.makeText(NewCasectivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initi() {
        rl_layout = findViewById(R.id.rl_layout);
        dialog = new Dialog(NewCasectivity.this);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back);
        start_progress = findViewById(R.id.start_progress);
        ll_address = findViewById(R.id.ll_address);
//        rg_dob = findViewById(R.id.rg_dob);
//        rb_doB_yes = findViewById(R.id.rb_doB_yes);
//        rb_dob_no = findViewById(R.id.rb_dob_no);
//        rb_age_no = findViewById(R.id.rb_age_no);
//        rb_age_yes = findViewById(R.id.rb_age_yes);
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
                                int Age = calculateAge(dateFormat1.format(calendar.getTime()));

                                if (Age != -1) {
                                    pt_age.setText(String.valueOf(Age));
                                } else {

                                }
                            }
                        }, year, month, dayOfMonth);
                try {
                    dateNow = formatter.parse(currentTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        pt_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               String newText = String.valueOf(charSequence);
               Calculate(newText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //String newText = editable.toString();

            }
        });
        //phone number
        /*rg_phone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/
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
    private void Calculate(String newText) {
        String ageString = pt_age.getText().toString();
        if (!ageString.isEmpty()) {
            int age = Integer.parseInt(ageString);

            // Calculate birthdate based on current date
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -age);
            Date dob = calendar.getTime();

            // Format the date of birth
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dobString = sdf.format(dob);

            // Display the result
            pt_DOB.setText(dobString);
        } else {

        }
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
        age = pt_age.getText().toString();
        dob = pt_DOB.getText().toString();

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
                    editor.putString("patientDetailId",p_id);
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