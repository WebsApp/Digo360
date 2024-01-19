package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.TopDiseaseAdapeter3;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.ListDiseaseListener;
import com.wapss.digo360.response.MostSearchClickResponse;
import com.wapss.digo360.response.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPage extends AppCompatActivity {
    RecyclerView rv_disease;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    SearchView sv_search;
    String cName;
    TopDiseaseAdapeter3 topDiseaseAdapeter3;
    List<SearchResponse.Result> searchResponse;
    LinearLayout ll_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        rv_disease = findViewById(R.id.rv_disease);
        sv_search = findViewById(R.id.sv_search);
        ll_search = findViewById(R.id.ll_search);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cName = s;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cName = newText;
                searchSku(newText);
                return false;
            }
        });
    }

    private void searchSku(String newText) {
        if (newText.isEmpty()) {
           // Toast.makeText(getApplicationContext(), "Please Enter Diseases Name", Toast.LENGTH_SHORT).show();
            rv_disease.setVisibility(View.GONE);
            ll_search.setVisibility(View.VISIBLE);
        } else {
            callSearchDisease(newText);
        }
    }

    private void callSearchDisease(String newText) {
        // progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<SearchResponse> banner_apiCall = ApiService.apiHolders().SearchADiseasePi( Token,50, 0,newText);
        banner_apiCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    // progressDialog.dismiss();
                    assert response.body() != null;
                    searchResponse = response.body().getResult();
                    rv_disease.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.GONE);

                    topDiseaseAdapeter3 = new TopDiseaseAdapeter3(getApplicationContext(), searchResponse, new ListDiseaseListener() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.commit();
                            Intent intent = new Intent(SearchPage.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapeter3);
                } else {
                    progressDialog.dismiss();
                    ll_search.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressDialog.dismiss();
                ll_search.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMostSearchClick(String diseaseId) {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<MostSearchClickResponse> banner_apiCall = ApiService.apiHolders().MostSearchclick( Token,diseaseId);
        banner_apiCall.enqueue(new Callback<MostSearchClickResponse>() {
            @Override
            public void onResponse(Call<MostSearchClickResponse> call, Response<MostSearchClickResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MostSearchClickResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}