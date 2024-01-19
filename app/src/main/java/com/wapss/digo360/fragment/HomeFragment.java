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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.wapss.digo360.R;
import com.wapss.digo360.activity.AboutDigo;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.activity.NotificationActivity;
import com.wapss.digo360.activity.Pages;
import com.wapss.digo360.activity.PatientRegistrationCheckActivity;
import com.wapss.digo360.activity.SearchPage;
import com.wapss.digo360.activity.TopDiseasesActivity;
import com.wapss.digo360.activity.Total_Reports;
import com.wapss.digo360.adapter.BannerAdapter;
import com.wapss.digo360.adapter.HelpAdapter;
import com.wapss.digo360.adapter.MostSearchDiseaseAdapter;
import com.wapss.digo360.adapter.TopDiagnosiAdapter;
import com.wapss.digo360.adapter.TopDiseaseAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.interfaces.MostSearchDiseaseListener;
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
    TextView tv_viewAll, btn_search;
    private BottomSheetDialog bottomSheetDialog;
    ViewPager viewPager;
    CustomProgressDialog progressDialog;
    List<BannerResponse.Result> bannerResponses;
    List<SettingHomeResponse.Banner> settingBanner;
    List<SettingHomeResponse.Slider> sliderList;
    List<SettingHomeResponse.Search> searchList;
    List<HelpResponse.Result> helpResponse;
    List<TopDiseaseResponse.Result> topDiseaseResponse;
    private int currentPage = 0;
    private final long DELAY_MS = 3000; // Delay in milliseconds before flipping to the next page
    private final long PERIOD_MS = 3000; // Time period between each auto-flipping
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String deviceToken, Token;
    TopDiagnosiAdapter topDiagnosiAdapter;
    MostSearchDiseaseAdapter mostSearchDiseaseAdapter;
    RecyclerView rv_diagnosis, rv_top_diseases,rv_most_search_diseases;
    View home;
    HelpAdapter helpAdapter;
    LinearLayout ll_faq, ll_viewAllDisease;
    TopDiseaseAdapter topDiseaseAdapter;
    ImageView iv_image1, iv_image2, iv_image3;
    TextView tv_disease1, tv_disease2, tv_disease3, others, male, female;
    LinearLayout btn_fever, btn_reports,btn_all_reports,btn_male_reports,btn_female_reports;

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
        btn_search = home.findViewById(R.id.btn_search);
        tv_viewAll = home.findViewById(R.id.tv_viewAll);
        notification = home.findViewById(R.id.notification);
        viewPager = home.findViewById(R.id.view_pager);
        iv_banner1 = home.findViewById(R.id.iv_banner1);
        // progressDialog = new CustomProgressDialog(getContext());
        rv_diagnosis = home.findViewById(R.id.rv_diagnosis);
        rv_top_diseases = home.findViewById(R.id.rv_top_diseases);
        ll_viewAllDisease = home.findViewById(R.id.ll_viewAllDisease);
        btn_fever = home.findViewById(R.id.btn_fever);
        btn_reports = home.findViewById(R.id.btn_reports);
        btn_all_reports = home.findViewById(R.id.btn_all_reports);
        btn_male_reports = home.findViewById(R.id.btn_male_reports);
        btn_female_reports = home.findViewById(R.id.btn_female_reports);

        others = home.findViewById(R.id.others);
        male = home.findViewById(R.id.male);
        female = home.findViewById(R.id.female);

        iv_image1 = home.findViewById(R.id.iv_image1);
        iv_image2 = home.findViewById(R.id.iv_image2);
        iv_image3 = home.findViewById(R.id.iv_image3);

        tv_disease1 = home.findViewById(R.id.tv_disease1);
        tv_disease2 = home.findViewById(R.id.tv_disease2);
        tv_disease3 = home.findViewById(R.id.tv_disease3);

        rv_most_search_diseases = home.findViewById(R.id.rv_most_search_diseases);

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
        btn_all_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "HOME");
                Intent i = new Intent(getContext(), Total_Reports.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_male_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "MALE");
                Intent i = new Intent(getContext(), Total_Reports.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_female_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "FEMALE");
                Intent i = new Intent(getContext(), Total_Reports.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "OTHER");
                Intent i = new Intent(getContext(), Total_Reports.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchPage.class);
                startActivity(intent);
            }
        });
        ll_viewAllDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                TopDiseasesFragment topDiseasesFragment = new TopDiseasesFragment();
//                fragmentTransaction.replace(R.id.main_container, topDiseasesFragment);
//                fragmentTransaction.addToBackStack(null).commit();
                Intent intent = new Intent(getContext(), TopDiseasesActivity.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "HOME");
                Intent i = new Intent(getContext(), HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_fever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getContext(), PatientRegistrationCheckActivity.class));
            }
        });
        CallAPI();
        callTopDiseases();
        return home;
    }
    private void callHelpAPI() {
        // progressDialog.showProgressDialog();
        // Token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjdjZWQ0ODY4LWYwN2QtNDBhMi05NzZlLWMyNjYwYzRhYzRkNSIsImlhdCI6MTcwNTMwNDA5MiwiZXhwIjoxNzM2ODQwMDkyfQ.b63hddX2A1z-o_JdkWQiyIaak5SUNGyuxqshB0EGMYs";

    }

    private void callTopDiseases() {
        progressDialog.showProgressDialog();
         String Token = "Bearer " + deviceToken;
        Call<TopDiseaseResponse> banner_apiCall = ApiService.apiHolders().DiseaseAPi(Token,3, 0);
        banner_apiCall.enqueue(new Callback<TopDiseaseResponse>() {
            @Override
            public void onResponse(Call<TopDiseaseResponse> call, Response<TopDiseaseResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    assert response.body() != null;
                    topDiseaseResponse = response.body().getResult();

                    if (topDiseaseResponse != null) {
                        tv_disease1.setText(topDiseaseResponse.get(0).getName());
                        tv_disease2.setText(topDiseaseResponse.get(1).getName());
                        tv_disease3.setText(topDiseaseResponse.get(2).getName());

                        String img1 = topDiseaseResponse.get(0).getImage();
                        String img2 = topDiseaseResponse.get(1).getImage();
                        String img3 = topDiseaseResponse.get(2).getImage();

                        if (img1 == null) {
                            iv_image1.setBackgroundResource(R.drawable.ivicon);
                        } else {
                            Picasso.with(getContext())
                                    .load(img1)
                                    .into(iv_image1);
                        }
                        if (img2 == null) {
                            iv_image2.setBackgroundResource(R.drawable.ivicon);
                        } else {
                            Picasso.with(getContext())
                                    .load(img2)
                                    .into(iv_image2);
                        }
                        if (img3 == null) {
                            iv_image3.setBackgroundResource(R.drawable.ivicon);
                        } else {
                            Picasso.with(getContext())
                                    .load(img3)
                                    .into(iv_image3);
                        }
                    }

                    topDiseaseAdapter = new TopDiseaseAdapter(getContext(), topDiseaseResponse, new TopDiseaseListener() {
                        @Override
                        public void onItemClickedItem(TopDiseaseResponse.Result item, int position) {
                            Intent intent = new Intent(getContext(), PatientRegistrationCheckActivity.class);
                            startActivity(intent);
                        }
                    });
                    rv_top_diseases.setAdapter(topDiseaseAdapter);
                    rv_top_diseases.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


                } else {
                    progressDialog.dismiss();
                    //ll_faq.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopDiseaseResponse> call, Throwable t) {
                progressDialog.dismiss();
               // ll_faq.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void callHelpAPI(String deviceToken) {
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
    }*/
    /*private void showHelpButtonSheet(List<HelpResponse.Result> helpResponse) {
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
    }*/
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
                    searchList = response.body().getSearch();
                    String banner1 = response.body().getResult().getBanner1();
                    iv_banner1.setImageDrawable(Drawable.createFromPath(banner1));
                    //summary
                    String males = response.body().getSummary().getMaleCount();
                    String females = response.body().getSummary().getFemaleCount();
                    String other = response.body().getSummary().getOtherCount();
                    others.setText(other);
                    male.setText(males);
                    female.setText(females);

                    callBanner(settingBanner);//Banner
                    callTopDiagnosis(sliderList);//Top Diagnosis
                    callMostSearchDisease(searchList);//most Search Disease
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

    private void callMostSearchDisease(List<SettingHomeResponse.Search> searchList) {
        mostSearchDiseaseAdapter = new MostSearchDiseaseAdapter(getContext(), searchList, new MostSearchDiseaseListener() {
            @Override
            public void onItemClickedItem(SettingHomeResponse.Search item, int position) {

            }
        });
        rv_most_search_diseases.setAdapter(mostSearchDiseaseAdapter);
        rv_most_search_diseases.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void callTopDiagnosis(List<SettingHomeResponse.Slider> sliderList) {
        topDiagnosiAdapter = new TopDiagnosiAdapter(getContext(), sliderList);
        rv_diagnosis.setAdapter(topDiagnosiAdapter);
        rv_diagnosis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void callBanner(List<SettingHomeResponse.Banner> settingBanner) {
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), settingBanner);
        viewPager.setAdapter(bannerAdapter);
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