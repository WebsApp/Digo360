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

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.Report_Adapter;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Report_Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Total_Reports extends AppCompatActivity {

    ImageView back,select_date;
    TextView txt_select_date;
    Report_Adapter reportAdapter;
    List<Report_Model> report_list = new ArrayList<>();
    RecyclerView rv_reports;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken,report_enum,currentTime,selected_date;
    LinearLayout blank_layout;
    CustomProgressDialog progressDialog;
    private Calendar calendar;
    Date dateNow = null;
    EditText edit_user_name;

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
            report_enum = bundle.getString("P_ID");
        }
        edit_user_name = findViewById(R.id.edit_user_name);
        blank_layout = findViewById(R.id.blank_layout);
        rv_reports = findViewById(R.id.rv_reports);
        back = findViewById(R.id.back);
        select_date = findViewById(R.id.select_date);
        txt_select_date = findViewById(R.id.txt_select_date);
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
                                selected_date = dateFormat1.format(calendar.getTime());
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_reports.setLayoutManager(layoutManager);;
        total_Report();
    }

    private void total_Report() {

    }
}