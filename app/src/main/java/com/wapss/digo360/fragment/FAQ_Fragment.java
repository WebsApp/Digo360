package com.wapss.digo360.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.response.FaqResponse;
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.utility.Internet_Check;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQ_Fragment extends Fragment {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    List<HelpResponse.Result> helpResponse;
    HelpAdapter helpAdapter;
    RelativeLayout ll_faq;
    String Token;
    List<Help_Model> faq_list = new ArrayList<>();
    RecyclerView rv_faq;
    private Dialog noInternetDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity().getWindow().getContext(), R.color.purple));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View faq = inflater.inflate(R.layout.fragment_f_a_q_, container, false);
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
        progressDialog = new CustomProgressDialog(getContext());
        ll_faq = faq.findViewById(R.id.ll_faq);
        //shared Pref
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        /*network Connection Check*/

        rv_faq  = faq.findViewById(R.id.rv_faq);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_faq.setLayoutManager(layoutManager);;
        faq_api();

        return faq;
    }
    private void faq_api() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<FaqResponse> help_apiCall = ApiService.apiHolders().helpAPi(Token);
        help_apiCall.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    List<FaqResponse.Result> arrayList = response.body().getResult();
                    for (int i = 0;i<arrayList.size();i++){
                        Help_Model m_model = new Help_Model(arrayList.get(i).getQuestion(),arrayList.get(i).getAnswer());
                        faq_list.add(m_model);
                    }
                    if (faq_list.size()>0){
                        helpAdapter = new HelpAdapter(faq_list,getContext());
                        rv_faq.setAdapter(helpAdapter);
                        ll_faq.setVisibility(View.GONE);
                    }
                    else {
                        ll_faq.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                progressDialog.hideProgressDialog();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}