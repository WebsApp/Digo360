package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.wapss.digo360.R;
import com.wapss.digo360.adapter.Onboard_Adapter;

public class OnboardScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tvNext;
    private Onboard_Adapter adapter;
    ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_screen);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // bind views
        viewPager = findViewById(R.id.pagerIntroSlider);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tvNext = findViewById(R.id.tvNext);
        iv_icon = findViewById(R.id.iv_icon);
        // init slider pager adapter
        adapter = new Onboard_Adapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        // set adapter
        viewPager.setAdapter(adapter);

        // set dot indicators
        tabLayout.setupWithViewPager(viewPager);

        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((viewPager.getCurrentItem() + 1) < adapter.getCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(OnboardScreen.this, GetStartedPage.class));
                    finish();
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getCount() - 1) {
                    tvNext.setText(R.string.next);
                } else {
                    tvNext.setText(R.string.next);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}