package com.wapss.digo360.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.TopDiseaseAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter2;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopDiseasesActivity extends AppCompatActivity {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    List<TopDiseaseResponse.Result> topDiseaseResponse;
    List<SearchResponse.Result> searchResponse;
    TopDiseaseAdapter topDiseaseAdapter;
    TopDiseaseAdapter2 topDiseaseAdapter2;
    RecyclerView rv_disease;
    SearchView sv_search;
    String cName;
    ImageView notification, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_diseases);

        help = findViewById(R.id.help);
        notification = findViewById(R.id.notification);
        rv_disease = findViewById(R.id.rv_disease);
        progressDialog = new CustomProgressDialog(this);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "DISEASE");
                Intent i = new Intent(TopDiseasesActivity.this, HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopDiseasesActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        callDisease();
    }
    private void searchSku(String newText) {
        if (newText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Diseases Name", Toast.LENGTH_SHORT).show();
            callDisease();
        } else {
            callSearchDisease(newText);
        }
    }

    private void callSearchDisease(String newText) {
        // progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<SearchResponse> banner_apiCall = ApiService.apiHolders().SearchADiseasePi(Token, 10, 0, newText);
        banner_apiCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    // progressDialog.dismiss();
                    assert response.body() != null;
                    //topDiseaseResponse = response.body().getResult();

                    searchResponse = response.body().getResult();

//                    topDiseaseAdapter = new TopDiseaseAdapter(getContext(), topDiseaseResponse, new TopDiseaseListener() {
//                        @Override
//                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {
//                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
//                            startActivity(intent);
//                        }
//                    });
                    topDiseaseAdapter2 = new TopDiseaseAdapter2(getApplicationContext(), searchResponse, new TopDiseaseListener2() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            Intent intent = new Intent(TopDiseasesActivity.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapter2);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDisease() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<TopDiseaseResponse> banner_apiCall = ApiService.apiHolders().DiseaseAPi( Token,50, 0);
        banner_apiCall.enqueue(new Callback<TopDiseaseResponse>() {
            @Override
            public void onResponse(Call<TopDiseaseResponse> call, Response<TopDiseaseResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topDiseaseResponse = response.body().getResult();

                    topDiseaseAdapter = new TopDiseaseAdapter(getApplicationContext(), topDiseaseResponse, new TopDiseaseListener() {
                        @Override
                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {
                            Intent intent = new Intent(TopDiseasesActivity.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapter);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopDiseaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}