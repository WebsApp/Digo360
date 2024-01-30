package com.wapss.digo360.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.activity.NewCasectivity;
import com.wapss.digo360.activity.NotificationActivity;
import com.wapss.digo360.activity.PatientRegistrationCheckActivity;
import com.wapss.digo360.activity.TopDiseasesActivity;
import com.wapss.digo360.adapter.TopDiseaseAdapeter3;
import com.wapss.digo360.adapter.TopDiseaseAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter2;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.ListDiseaseListener;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.MostSearchClickResponse;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;
import com.wapss.digo360.utility.Internet_Check;

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
    List<TopDiseaseResponse.Result> topSearch;
    List<SearchResponse.Result> searchResponse;
    TopDiseaseAdapter topDiseaseAdapter;
    TopDiseaseAdapter2 topDiseaseAdapter2;
    TopDiseaseAdapeter3 topDiseaseAdapeter3;
    RecyclerView rv_disease,rv_top_search;
    SearchView sv_search;
    String cName;
    ImageView notification, help;
    LinearLayout viewAll2,blank_layout;
    private Dialog noInternetDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View TopDis = inflater.inflate(R.layout.fragment_top_diseases, container, false);

        /*network Connection Check*/
        if(!Internet_Check.isInternetAvailable(getContext())) {
            noInternetDialog = new Dialog(getContext());
            noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            noInternetDialog.setContentView(R.layout.no_internet_layout);
            noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            noInternetDialog.setCancelable(false);

            TextView retryButton = noInternetDialog.findViewById(R.id.retry_button);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Internet_Check.isInternetAvailable(getContext())) {
                        noInternetDialog.dismiss();
                    }
                }
            });
            noInternetDialog.show();
            noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        else {

        }
        blank_layout = TopDis.findViewById(R.id.blank_layout);
        help = TopDis.findViewById(R.id.help);
        notification = TopDis.findViewById(R.id.notification);
        rv_disease = TopDis.findViewById(R.id.rv_disease);
        rv_top_search = TopDis.findViewById(R.id.rv_top_search);
        viewAll2 = TopDis.findViewById(R.id.viewAll2);
        sv_search = TopDis.findViewById(R.id.sv_search);
        progressDialog = new CustomProgressDialog(getContext());
        //shared Pref
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "DISEASE");
                Intent i = new Intent(getContext(), HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotificationActivity.class);
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

        return TopDis;
    }

    private void callTopSearch() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<TopDiseaseResponse> banner_apiCall = ApiService.apiHolders().DiseaseAPi( Token,5, 0);
        banner_apiCall.enqueue(new Callback<TopDiseaseResponse>() {
            @Override
            public void onResponse(Call<TopDiseaseResponse> call, Response<TopDiseaseResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topSearch = response.body().getResult();
                    if (topSearch.size()==0){
                        viewAll2.setVisibility(View.GONE);
                    }

                    topDiseaseAdapter2 = new TopDiseaseAdapter2(getContext(), topSearch, new TopDiseaseListener2() {
                        @Override
                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.commit();
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
                    rv_top_search.setLayoutManager(layoutManager);
                    rv_top_search.setAdapter(topDiseaseAdapter2);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    viewAll2.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopDiseaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                viewAll2.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void searchSku(String newText) {
        if (newText.isEmpty()) {
            // Toast.makeText(getApplicationContext(), "Please Enter Diseases Name", Toast.LENGTH_SHORT).show();
            callDisease();
            callTopSearch(); //top Search
            blank_layout.setVisibility(View.GONE);
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
                    blank_layout.setVisibility(View.VISIBLE);
                    searchResponse = response.body().getResult();
//                    if (searchResponse.size()>0)
//                    {
//                        viewAll2.setVisibility(View.GONE);
//                    }

                    topDiseaseAdapeter3 = new TopDiseaseAdapeter3(getContext(), searchResponse, new ListDiseaseListener() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.commit();
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapeter3);
                } else {
                    progressDialog.dismiss();
                    //  ll_faq.setVisibility(View.VISIBLE);
                    blank_layout.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                blank_layout.setVisibility(View.VISIBLE);
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

                    topDiseaseAdapeter3 = new TopDiseaseAdapeter3(getContext(), searchResponse, new ListDiseaseListener() {
                        @Override
                        public void onItemClickedItem(SearchResponse.Result item, int position) {
                            String diseaseId = item.getId();
                            callMostSearchClick(diseaseId);
                            editor.putString("diseaseId", diseaseId);
                            editor.commit();
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
                    rv_disease.setLayoutManager(layoutManager);
                    rv_disease.setAdapter(topDiseaseAdapeter3);
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
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MostSearchClickResponse> call, Throwable t) {
                progressDialog.dismiss();
                //ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}