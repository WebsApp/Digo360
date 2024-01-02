package com.wapss.digo360.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.HelpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQ_Fragment extends Fragment {
    RecyclerView rv_faq;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    CustomProgressDialog progressDialog;
    List<HelpResponse.Result> helpResponse;
    HelpAdapter helpAdapter;
    LinearLayout ll_faq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity().getWindow().getContext(), R.color.purple));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View faq = inflater.inflate(R.layout.fragment_f_a_q_, container, false);
        rv_faq = faq.findViewById(R.id.rv_faq);
        progressDialog = new CustomProgressDialog(getContext());
        ll_faq = faq.findViewById(R.id.ll_faq);
        //shared Pref
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        ///callHelpAPI();

        return faq;
    }

    private void callHelpAPI() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<HelpResponse> banner_apiCall = ApiService.apiHolders().helpAPi(Token, 10, 0, "");
        banner_apiCall.enqueue(new Callback<HelpResponse>() {
            @Override
            public void onResponse(Call<HelpResponse> call, Response<HelpResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    helpResponse = response.body().getResult();

                    helpAdapter = new HelpAdapter(getContext(), helpResponse);
                    rv_faq.setAdapter(helpAdapter);
                    rv_faq.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    progressDialog.dismiss();
                    ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HelpResponse> call, Throwable t) {
                progressDialog.dismiss();
                ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}