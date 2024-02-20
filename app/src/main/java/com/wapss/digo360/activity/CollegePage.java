package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.Collage_Response;
import com.wapss.digo360.response.FaqResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollegePage extends AppCompatActivity {

    ImageView collage_back,btn_faq,to_date_picker,select_date;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,Token,pageName,currentTime;
    CustomProgressDialog progressDialog;
    EditText edit_college_name,et_degree,et_zipcode,et_state,et_city;
    TextView tv_update,et_Passout,et_admission_year;
    private Calendar calendar;
    Date dateNow = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        collage_back = findViewById(R.id.collage_back);
        btn_faq = findViewById(R.id.btn_faq);
        et_Passout = findViewById(R.id.et_Passout);
        et_admission_year = findViewById(R.id.et_admission_year);
        edit_college_name = findViewById(R.id.edit_college_name);
        et_city = findViewById(R.id.et_city);
        et_state = findViewById(R.id.et_state);
        et_zipcode = findViewById(R.id.et_zipcode);
        et_degree = findViewById(R.id.et_degree);
        tv_update = findViewById(R.id.tv_update);
        select_date = findViewById(R.id.select_date);
        to_date_picker = findViewById(R.id.to_date_picker);
        progressDialog = new CustomProgressDialog(CollegePage.this);
        et_degree.setText("MD");

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        calendar = Calendar.getInstance();
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CollegePage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                et_admission_year.setText(dateFormat1.format(calendar.getTime()));
                            }
                        }, year, month, dayOfMonth);
//                try {
//                    dateNow = formatter.parse(currentTime);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
                //datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        to_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CollegePage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                et_Passout.setText(dateFormat1.format(calendar.getTime()));
                            }
                        }, year, month, dayOfMonth);
//                try {
//                    dateNow = formatter.parse(currentTime);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
                //datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        collage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "COLLAGE");
                Intent i = new Intent(CollegePage.this, HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_college_name.getText().toString().isEmpty()){
                    Toast.makeText(CollegePage.this, "Enter Collage Name", Toast.LENGTH_SHORT).show();
                } else if (et_admission_year.getText().toString().isEmpty()) {
                    Toast.makeText(CollegePage.this, "Enter Admission Year", Toast.LENGTH_SHORT).show();
                } else if (et_Passout.getText().toString().isEmpty()) {
                    Toast.makeText(CollegePage.this, "Enter Pass-out Year", Toast.LENGTH_SHORT).show();
                }
                else {
                    collage_api();
                }
            }
        });
    }
    private void collage_api() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        String collegeName = edit_college_name.getText().toString();
        String startDate = et_admission_year.getText().toString();
        String endDate = et_Passout.getText().toString();
        //String degree = et_degree.getText().toString();
        Call<Collage_Response> collage = ApiService.apiHolders().collage_res(Token,collegeName,startDate,endDate,"MD");
        collage.enqueue(new Callback<Collage_Response>() {
            @Override
            public void onResponse(Call<Collage_Response> call, Response<Collage_Response> response) {
                if (response.isSuccessful()) {
                    progressDialog.hideProgressDialog();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            startActivity(new Intent(CollegePage.this,MyProfile.class));
//                        }
//                    },2000);
                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    Date date9 = null;
                    Date date10 = null;//You will get date object relative to server/client timezone wherever it is parsed
                    try {
                        date9 = dateFormat1.parse(response.body().getStartDate());
                        date10 = dateFormat1.parse(response.body().getEndDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    DateFormat formatter9 = new SimpleDateFormat("yyyy-mm-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
                    String dateStr = formatter9.format(date9);
                    String date_pass = formatter9.format(date10);

//                    et_admission_year.setText(dateStr);
//                    et_Passout.setText(date_pass);
//                    edit_college_name.setText(response.body().getCollegeName());

                    Toast.makeText(CollegePage.this, "Collage Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.hideProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<Collage_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}