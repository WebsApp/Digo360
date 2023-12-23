package com.wapss.digo360.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.NewCasectivity;
import com.wapss.digo360.activity.PatientRegistrationCheckActivity;
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

public class TopDiseasesFragment extends Fragment {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View TopDis = inflater.inflate(R.layout.fragment_top_diseases, container, false);
        rv_disease = TopDis.findViewById(R.id.rv_disease);
        sv_search = TopDis.findViewById(R.id.sv_search);
        progressDialog = new CustomProgressDialog(getContext());
        //shared Pref
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        callDisease();

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

        return TopDis;
    }

    private void searchSku(String newText) {
        if (newText.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Diseases Name", Toast.LENGTH_SHORT).show();
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
                    topDiseaseAdapter2 = new TopDiseaseAdapter2(getContext(), searchResponse, new TopDiseaseListener2() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapter2);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDisease() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<TopDiseaseResponse> banner_apiCall = ApiService.apiHolders().DiseaseAPi(Token, 50, 0);
        banner_apiCall.enqueue(new Callback<TopDiseaseResponse>() {
            @Override
            public void onResponse(Call<TopDiseaseResponse> call, Response<TopDiseaseResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topDiseaseResponse = response.body().getResult();

                    topDiseaseAdapter = new TopDiseaseAdapter(getContext(), topDiseaseResponse, new TopDiseaseListener() {
                        @Override
                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapter);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopDiseaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}