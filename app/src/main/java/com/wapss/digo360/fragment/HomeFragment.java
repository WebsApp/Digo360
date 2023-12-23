package com.wapss.digo360.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.wapss.digo360.R;
import com.wapss.digo360.activity.NotificationActivity;
import com.wapss.digo360.adapter.BannerAdapter;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.TopDiagnosiAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.response.BannerResponse;
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.response.SettingHomeResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ImageView notification, help, iv_banner1;
    TextView tv_viewAll;
    private BottomSheetDialog bottomSheetDialog;
    ViewPager viewPager;
    CustomProgressDialog progressDialog;
    List<BannerResponse.Result> bannerResponses;
    List<SettingHomeResponse.Banner> settingBanner;
    List<SettingHomeResponse.Slider> sliderList;
    List<HelpResponse.Result> helpResponse;
    List<TopDiseaseResponse.Result> topDiseaseResponse;
    private int currentPage = 0;
    private final long DELAY_MS = 3000; // Delay in milliseconds before flipping to the next page
    private final long PERIOD_MS = 3000; // Time period between each auto-flipping
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken;
    TopDiagnosiAdapter topDiagnosiAdapter;
    RecyclerView rv_diagnosis, rv_top_diseases;
    View home;
    HelpAdapter helpAdapter;
    LinearLayout ll_faq, ll_viewAllDisease;
    TopDiseaseAdapter topDiseaseAdapter;
    ImageView iv_image1,iv_image2,iv_image3;
    TextView tv_disease1,tv_disease2,tv_disease3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        home = inflater.inflate(R.layout.fragment_home, container, false);
        help = home.findViewById(R.id.help);
        tv_viewAll = home.findViewById(R.id.tv_viewAll);
        notification = home.findViewById(R.id.notification);
        viewPager = home.findViewById(R.id.view_pager);
        iv_banner1 = home.findViewById(R.id.iv_banner1);
        // progressDialog = new CustomProgressDialog(getContext());
        rv_diagnosis = home.findViewById(R.id.rv_diagnosis);
        rv_top_diseases = home.findViewById(R.id.rv_top_diseases);
        ll_viewAllDisease = home.findViewById(R.id.ll_viewAllDisease);

        iv_image1 = home.findViewById(R.id.iv_image1);
        iv_image2 = home.findViewById(R.id.iv_image2);
        iv_image3 = home.findViewById(R.id.iv_image3);

        tv_disease1 = home.findViewById(R.id.tv_disease1);
        tv_disease2 = home.findViewById(R.id.tv_disease2);
        tv_disease3 = home.findViewById(R.id.tv_disease3);

        progressDialog = new CustomProgressDialog(getContext());
        //shared Pref
        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);

        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity().getWindow().getContext(), R.color.purple));

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        ll_viewAllDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopDiseasesFragment topDiseasesFragment = new TopDiseasesFragment();
                fragmentTransaction.replace(R.id.main_container, topDiseasesFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelpAPI(deviceToken);
            }
        });
        CallAPI();
        callTopDiseases();
        return home;
    }

    private void callTopDiseases() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<TopDiseaseResponse> banner_apiCall = ApiService.apiHolders().DiseaseAPi(Token, 3, 0);
        banner_apiCall.enqueue(new Callback<TopDiseaseResponse>() {
            @Override
            public void onResponse(Call<TopDiseaseResponse> call, Response<TopDiseaseResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topDiseaseResponse = response.body().getResult();

                    if (topDiseaseResponse!=null){
                        tv_disease1.setText(topDiseaseResponse.get(0).getDiseaseName());
                        tv_disease2.setText(topDiseaseResponse.get(1).getDiseaseName());
                        tv_disease3.setText(topDiseaseResponse.get(2).getDiseaseName());

                        String img1 =topDiseaseResponse.get(0).getDiseaseImage();
                        String img2 = topDiseaseResponse.get(1).getDiseaseImage();
                        String img3 =topDiseaseResponse.get(2).getDiseaseImage();

                        if (img1==null){
                            iv_image1.setBackgroundResource(R.drawable.ivicon);
                        }
                        else {
                            Picasso.with(getContext())
                                    .load(img1)
                                    .into(iv_image1);
                        }
                        if (img2==null){
                            iv_image2.setBackgroundResource(R.drawable.ivicon);
                        }
                        else {
                            Picasso.with(getContext())
                                    .load(img2)
                                    .into(iv_image2);
                        }
                        if (img3==null){
                            iv_image3.setBackgroundResource(R.drawable.ivicon);
                        }
                        else {
                            Picasso.with(getContext())
                                    .load(img3)
                                    .into(iv_image3);
                        }
                    }

                    topDiseaseAdapter = new TopDiseaseAdapter(getContext(), topDiseaseResponse, new TopDiseaseListener() {
                        @Override
                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {

                        }
                    });
                    rv_top_diseases.setAdapter(topDiseaseAdapter);
                    rv_top_diseases.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


                } else {
                    progressDialog.dismiss();
                    ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopDiseaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callHelpAPI(String deviceToken) {
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

                    showHelpButtonSheet(helpResponse);

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

    private void showHelpButtonSheet(List<HelpResponse.Result> helpResponse) {
        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetTheme);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.help,
                (RelativeLayout) home.findViewById(R.id.container));

        ImageView btn_cancel = view1.findViewById(R.id.btn_cancel);
        RecyclerView rv_help = view1.findViewById(R.id.rv_help);
        ll_faq = view1.findViewById(R.id.ll_faq);

        if (helpResponse.size() == 0) {
            ll_faq.setVisibility(View.VISIBLE);
        }

        helpAdapter = new HelpAdapter(getContext(), helpResponse);
        rv_help.setAdapter(helpAdapter);
        rv_help.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(false);
    }

    private void CallAPI() {
        progressDialog.showProgressDialog();
        String Token = "Bearer " + deviceToken;
        Call<SettingHomeResponse> banner_apiCall = ApiService.apiHolders().homeAPi(Token);
        banner_apiCall.enqueue(new Callback<SettingHomeResponse>() {
            @Override
            public void onResponse(Call<SettingHomeResponse> call, Response<SettingHomeResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    // String response1 = response.body().toString();
                    settingBanner = response.body().getResult().getBanner();
                    sliderList = response.body().getResult().getSlider();
                    String banner1 = response.body().getResult().getBanner1();
                    iv_banner1.setImageDrawable(Drawable.createFromPath(banner1));
                    callBanner(settingBanner);//Banner
                    callTopDiagnosis(sliderList);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingHomeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callTopDiagnosis(List<SettingHomeResponse.Slider> sliderList) {
        topDiagnosiAdapter = new TopDiagnosiAdapter(getContext(), sliderList);
        rv_diagnosis.setAdapter(topDiagnosiAdapter);
        rv_diagnosis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void callBanner(List<SettingHomeResponse.Banner> settingBanner) {
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), settingBanner);
        viewPager.setAdapter(bannerAdapter);

//                    // The_slide_timer
//                    java.util.Timer timer = new java.util.Timer();
//                    timer.scheduleAtFixedRate(new The_slide_timer(), 2000, 3000);
        // Auto-scrolling with Timer
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = () -> {
            if (currentPage == settingBanner.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}