package com.wapss.digo360.activity;

import static android.text.TextUtils.replace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wapss.digo360.R;
import com.wapss.digo360.fragment.CASE_Fragment;
import com.wapss.digo360.fragment.FAQ_Fragment;
import com.wapss.digo360.fragment.HomeFragment;
import com.wapss.digo360.fragment.Profile_Fragment;
import com.wapss.digo360.fragment.TopDiseasesFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity  {

    //BottomNavigationView bottomNavigationView;
    private MeowBottomNavigation nav_view;
    String page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        nav_view = findViewById(R.id.nav_view);
        nav_view.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        nav_view.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_person_search_24));
        nav_view.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_person_history));
        nav_view.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_person_outline_24));

        nav_view.show(1,true);
        replace(new HomeFragment());
        nav_view.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;
                    case 2:
                        replace(new FAQ_Fragment());
                        break;
                    case 3:
                        replace(new TopDiseasesFragment());
                        break;
                    case 4:
                        replace(new Profile_Fragment());
                        break;
                }
                return null;
            }
        });
        //loading the default fragment
/*        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            // getting the string back
//            page = bundle.getString("page", null);
//            if (page.equals("my_profile")){
//                loadFragment(new Profile_Fragment());
//            }else {
//                loadFragment(new HomeFragment());
//            }
//        }else {
//            loadFragment(new HomeFragment());
//        }
        loadFragment(new HomeFragment());*/
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    /*@Override
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
    }*/
}