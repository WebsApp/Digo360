package com.wapss.digo360.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wapss.digo360.R;
import com.wapss.digo360.fragment.CASE_Fragment;
import com.wapss.digo360.fragment.FAQ_Fragment;
import com.wapss.digo360.fragment.HomeFragment;
import com.wapss.digo360.fragment.Profile_Fragment;
import com.wapss.digo360.fragment.TopDiseasesFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //loading the default fragment
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int id = item.getItemId();
        if (id == R.id.home){
            fragment = new HomeFragment();
        }
        else if (id == R.id.search)
        {
            fragment = new FAQ_Fragment();
        }
        else if (id == R.id.history)
        {
            fragment = new TopDiseasesFragment();
        }
        else if (id == R.id.settings)
        {
            fragment = new Profile_Fragment();
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}