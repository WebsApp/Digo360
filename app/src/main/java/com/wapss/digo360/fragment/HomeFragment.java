package com.wapss.digo360.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wapss.digo360.R;
import com.wapss.digo360.activity.NotificationActivity;
import com.wapss.digo360.adapter.BannerAdapter;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.BannerResponse;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ImageView notification, help;
    TextView tv_viewAll;
    private BottomSheetDialog bottomSheetDialog;
    ViewPager viewPager;
    CustomProgressDialog progressDialog;
    List<BannerResponse.Result> bannerResponses;
    private int currentPage = 0;
    private final long DELAY_MS = 3000; // Delay in milliseconds before flipping to the next page
    private final long PERIOD_MS = 3000; // Time period between each auto-flipping

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        help = home.findViewById(R.id.help);
        tv_viewAll = home.findViewById(R.id.tv_viewAll);
        notification = home.findViewById(R.id.notification);
        viewPager = home.findViewById(R.id.view_pager);
        progressDialog = new CustomProgressDialog(getContext());

        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity().getWindow().getContext(), R.color.purple));

        tv_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopDiseasesFragment topDiseasesFragment = new TopDiseasesFragment();
                fragmentTransaction.replace(R.id.main_container, topDiseasesFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetTheme);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.help,
                        (RelativeLayout) home.findViewById(R.id.container));

                ImageView btn_cancel = view1.findViewById(R.id.btn_cancel);
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
        });
      //  Banner("");
        return home;
    }

    private void Banner(String token) {
        progressDialog.showProgressDialog();
        Call<BannerResponse> banner_apiCall = ApiService.apiHolders().banner(token);
        banner_apiCall.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    String response1 = response.body().toString();
                    bannerResponses = response.body().getResult();
                    BannerAdapter bannerAdapter = new BannerAdapter(getContext(), bannerResponses);
                    viewPager.setAdapter(bannerAdapter);

//                    // The_slide_timer
//                    java.util.Timer timer = new java.util.Timer();
//                    timer.scheduleAtFixedRate(new The_slide_timer(), 2000, 3000);
                    // Auto-scrolling with Timer
                    final Handler handler = new Handler(Looper.getMainLooper());
                    final Runnable update = () -> {
                        if (currentPage == bannerResponses.size()) {
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

                } else {

                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}