package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.Report_Adapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Report_Model;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.Patient_Count_Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Total_Reports extends AppCompatActivity {

    ImageView back;
    ImageView select_date;
    ImageView to_date_picker;
    TextView txt_select_date,txt_to_date;
    Report_Adapter reportAdapter;
    List<Report_Model> report_list = new ArrayList<>();
    RecyclerView rv_reports;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,report_enum,currentTime,fromDate,to_date,Token,keyword;
    LinearLayout blank_layout;
    CustomProgressDialog progressDialog;
    private Calendar calendar;
    Date dateNow = null;
    EditText edit_user_name;

    LottieAnimationView animationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_reports);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressDialog = new CustomProgressDialog(Total_Reports.this);
        //Extract the data…
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            report_enum = bundle.getString("PAGE_NAME");
        }
        animationView = findViewById(R.id.animationView);
        edit_user_name = findViewById(R.id.edit_user_name);
        blank_layout = findViewById(R.id.blank_layout);
        rv_reports = findViewById(R.id.rv_reports);
        back = findViewById(R.id.back);
        select_date = findViewById(R.id.select_date);
        txt_select_date = findViewById(R.id.txt_select_date);
        txt_to_date = findViewById(R.id.txt_to_date);
        to_date_picker = findViewById(R.id.to_date_picker);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                        Total_Reports.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                txt_select_date.setText(dateFormat1.format(calendar.getTime()));
                                fromDate = dateFormat1.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);
                try {
                    dateNow = formatter.parse(currentTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
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
                        Total_Reports.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                txt_to_date.setText(dateFormat1.format(calendar.getTime()));
                                to_date = dateFormat1.format(calendar.getTime());
                            }
                        }, year, month, dayOfMonth);
                try {
                    dateNow = formatter.parse(currentTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                //datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_reports.setLayoutManager(layoutManager);;
        total_Report();
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient_Name_Search();
                Patient_Date_Search();
            }
        });
    }

    private void Patient_Date_Search() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<Patient_Count_Response> count_api = ApiService.apiHolders().Patient_date_Search(Token,50,0,fromDate,to_date);
        count_api.enqueue(new Callback<Patient_Count_Response>() {
            @Override
            public void onResponse(Call<Patient_Count_Response> call, Response<Patient_Count_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<Patient_Count_Response.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Report_Model m_model = new Report_Model(arrayList.get(i).getPatientDetail().getPid(),
                                arrayList.get(i).getPatientDetail().getName(),
                                arrayList.get(i).getPatientDetail().getGender(),
                                arrayList.get(i).getPatientDetail().getAge(),
                                arrayList.get(i).getPatientDetail().getAccountId(),
                                arrayList.get(i).getDisease().getName());
                        report_list.add(m_model);
                    }
                    if (report_list.size()>0){
                        reportAdapter = new Report_Adapter(report_list,getApplicationContext());
                        rv_reports.setAdapter(reportAdapter);
                        blank_layout.setVisibility(View.GONE);
                    }
                    else {
                        blank_layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Patient_Count_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
                Toast.makeText(Total_Reports.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Patient_Name_Search() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        keyword = edit_user_name.getText().toString();
        Call<Patient_Count_Response> count_api = ApiService.apiHolders().Patient_name_Search(Token,50,0,keyword);
        count_api.enqueue(new Callback<Patient_Count_Response>() {
            @Override
            public void onResponse(Call<Patient_Count_Response> call, Response<Patient_Count_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<Patient_Count_Response.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Report_Model m_model = new Report_Model(arrayList.get(i).getPatientDetail().getPid(),
                                arrayList.get(i).getPatientDetail().getName(),
                                arrayList.get(i).getPatientDetail().getGender(),
                                arrayList.get(i).getPatientDetail().getAge(),
                                arrayList.get(i).getPatientDetail().getAccountId(),
                                arrayList.get(i).getDisease().getName());
                        report_list.add(m_model);
                    }
                    if (report_list.size()>0){
                        reportAdapter = new Report_Adapter(report_list,getApplicationContext());
                        rv_reports.setAdapter(reportAdapter);
                        blank_layout.setVisibility(View.GONE);
                    }
                    else {
                        blank_layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Patient_Count_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
                Toast.makeText(Total_Reports.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void total_Report() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        //Token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NDE1ZDQzLWRiOGMtNGMxZi04ZTZkLWZjMzE1NjQ0ZDhmMCIsImlhdCI6MTcwNTU2NDgzMywiZXhwIjoxNzM3MTAwODMzfQ.9gunlTjcc7S-OnrOcZo_n3P_whGhE5EqbjAuGHCSIiQ";

        Call<Patient_Count_Response> count_api = ApiService.apiHolders().PatientsCount(Token,50,0,report_enum);
        count_api.enqueue(new Callback<Patient_Count_Response>() {
            @Override
            public void onResponse(Call<Patient_Count_Response> call, Response<Patient_Count_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<Patient_Count_Response.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Report_Model m_model = new Report_Model(arrayList.get(i).getPatientDetail().getPid(),
                                arrayList.get(i).getPatientDetail().getName(),
                                arrayList.get(i).getPatientDetail().getGender(),
                                arrayList.get(i).getPatientDetail().getAge(),
                                arrayList.get(i).getPatientDetail().getAccountId(),
                                arrayList.get(i).getDisease().getName());
                        report_list.add(m_model);
                    }
                    if (report_list.size()>0){
                        reportAdapter = new Report_Adapter(report_list,getApplicationContext());
                        rv_reports.setAdapter(reportAdapter);
                        blank_layout.setVisibility(View.GONE);
                    }
                    else {
                        blank_layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Patient_Count_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
                Toast.makeText(Total_Reports.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}