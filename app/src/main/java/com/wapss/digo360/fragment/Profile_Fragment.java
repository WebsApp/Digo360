package com.wapss.digo360.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.AboutDigo;
import com.wapss.digo360.activity.ChooseLanguageActivity;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.activity.LoginActivity;
import com.wapss.digo360.activity.MyProfile;
import com.wapss.digo360.activity.ReferPage;

public class Profile_Fragment extends Fragment {

    LinearLayout profile_layout,about_layout,version_layout,logout_layout,btn_language,btn_refer;
    LinearLayout ll_logOut;
    ImageView btn_faq;
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
        View profile =  inflater.inflate(R.layout.fragment_profile_, container, false);

        btn_refer = profile.findViewById(R.id.btn_refer);
        btn_language = profile.findViewById(R.id.btn_language);
        btn_faq = profile.findViewById(R.id.btn_faq);
        logout_layout = profile.findViewById(R.id.logout_layout);
        version_layout = profile.findViewById(R.id.version_layout);
        profile_layout = profile.findViewById(R.id.profile_layout);
        about_layout = profile.findViewById(R.id.about_layout);
        ll_logOut = profile.findViewById(R.id.ll_logOut);

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyProfile.class));
            }
        });
        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutDigo.class));
            }
        });
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReferPage.class));
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "PROFILE");
                Intent i = new Intent(getContext(), HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        ll_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChooseLanguageActivity.class));
            }
        });
        return profile;
    }
}