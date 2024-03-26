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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.TopDiseaseAdapeter3;
import com.wapss.digo360.adapter.TopDiseaseAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter2;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.ListDiseaseListener;
import com.wapss.digo360.interfaces.MostDiseaseClick;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.MostSearchClickResponse;
import com.wapss.digo360.response.MostSearchResponse;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;
import com.wapss.digo360.response.TopSearchDiseaseResponse;

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
    List<TopDiseaseResponse.Result> topSearch;
    List<MostSearchResponse.Result> topSearch1;
    List<SearchResponse.Result> searchResponse;
    TopDiseaseAdapter topDiseaseAdapter;
    TopDiseaseAdapter2 topDiseaseAdapter2;
    TopDiseaseAdapeter3 topDiseaseAdapeter3;
    RecyclerView rv_disease,rv_top_search;
    SearchView sv_search;
    String cName;
    ImageView notification, help;
    LinearLayout viewAll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_diseases);

        help = findViewById(R.id.help);
        notification = findViewById(R.id.notification);
        rv_disease = findViewById(R.id.rv_disease);
        rv_top_search = findViewById(R.id.rv_top_search);
        viewAll2 = findViewById(R.id.viewAll2);
        sv_search = findViewById(R.id.sv_search);
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

        callDisease(); //all List
        callTopSearch(); //top Search
    }

    private void callTopSearch() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<MostSearchResponse> banner_apiCall = ApiService.apiHolders().MostDiseaseAPi( Token,5, 0);
        banner_apiCall.enqueue(new Callback<MostSearchResponse>() {
            @Override
            public void onResponse(Call<MostSearchResponse> call, Response<MostSearchResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topSearch1 = response.body().getResult();
                    if (topSearch1.size()==0){
                        viewAll2.setVisibility(View.GONE);
                    }

                    topDiseaseAdapter2 = new TopDiseaseAdapter2(getApplicationContext(), topSearch1, new MostDiseaseClick() {
                        @Override
                        public void onItemClickedItem(MostSearchResponse.Result item, int position) {
                            String diseaseId = item.getDiseaseId();
                            String diseaseName = item.getName();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.putString("diseaseName",diseaseName);
                            editor.commit();
                            Intent intent = new Intent(TopDiseasesActivity.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 5);
                    rv_top_search.setLayoutManager(layoutManager);
                    rv_top_search.setAdapter(topDiseaseAdapter2);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    viewAll2.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MostSearchResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                viewAll2.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void searchSku(String newText) {
        if (newText.isEmpty()) {
           // Toast.makeText(getApplicationContext(), "Please Enter Diseases Name", Toast.LENGTH_SHORT).show();
            callDisease();
            callTopSearch(); //top Search
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
//                    if (searchResponse.size()>0)
//                    {
//                        viewAll2.setVisibility(View.GONE);
//                    }
                    topDiseaseAdapeter3 = new TopDiseaseAdapeter3(getApplicationContext(), searchResponse, new ListDiseaseListener() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            String diseaseName = item.getName();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.putString("diseaseName",diseaseName);
                            editor.commit();
                            Intent intent = new Intent(TopDiseasesActivity.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapeter3);
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
        Call<SearchResponse> banner_apiCall = ApiService.apiHolders().SearchADiseasePi( Token,50, 0,"");
        banner_apiCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    searchResponse = response.body().getResult();

                    topDiseaseAdapeter3 = new TopDiseaseAdapeter3(getApplicationContext(), searchResponse, new ListDiseaseListener() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            String diseaseName = item.getName();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.putString("diseaseName",diseaseName);
                            editor.commit();
                            Intent intent = new Intent(TopDiseasesActivity.this, PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapeter3);
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