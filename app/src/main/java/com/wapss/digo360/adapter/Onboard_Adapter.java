package com.wapss.digo360.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.wapss.digo360.activity.Onboard_Item;

public class Onboard_Adapter extends FragmentPagerAdapter {

    public Onboard_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return Onboard_Item.newInstance(position);

    }

    @Override
    public int getCount() {
        return 3;
    }
}
