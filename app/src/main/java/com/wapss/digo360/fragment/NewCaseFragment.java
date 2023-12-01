package com.wapss.digo360.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wapss.digo360.R;

public class NewCaseFragment extends Fragment {
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    Button start_progress;
    LinearLayout ll_address,ll_age,ll_Dob,ll_phone_num,ll_phone_email,ll_otherProblem;
    RadioGroup rg_age,rg_phone,rg_address,rg_other;
    RadioButton rb_doB,rb_age,rb_phone,rb_email,rb_yes,rb_no,rb_yes_other,rb_no_other;
    TextView tv_submit;
    ImageView back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View NewCase = inflater.inflate(R.layout.fragment_new_case, container, false);

        progressBar = NewCase.findViewById(R.id.progressBar);
        back = NewCase.findViewById(R.id.back);
        start_progress = NewCase.findViewById(R.id.start_progress);
        ll_address = NewCase.findViewById(R.id.ll_address);
        ll_age = NewCase.findViewById(R.id.ll_age);
        rg_age = NewCase.findViewById(R.id.rg_age);
        rb_doB = NewCase.findViewById(R.id.rb_doB);
        rb_age = NewCase.findViewById(R.id.rb_age);
        ll_Dob = NewCase.findViewById(R.id.ll_Dob);
        rg_phone = NewCase.findViewById(R.id.rg_phone);
        rb_phone = NewCase.findViewById(R.id.rb_phone);
        rb_email = NewCase.findViewById(R.id.rb_email);
        ll_phone_num = NewCase.findViewById(R.id.ll_phone_num);
        ll_phone_email = NewCase.findViewById(R.id.ll_phone_email);
        //startProgress = NewCase.findViewById(R.id.start_progress);
        rg_address = NewCase.findViewById(R.id.rg_address);
        rb_yes = NewCase.findViewById(R.id.rb_yes);
        rb_no = NewCase.findViewById(R.id.rb_no);
        tv_submit = NewCase.findViewById(R.id.tv_submit);
        ll_otherProblem= NewCase.findViewById(R.id.ll_otherProblem);
        rg_other = NewCase.findViewById(R.id.rg_other);
        rb_yes_other = NewCase.findViewById(R.id.rb_yes_other);
        rb_no_other = NewCase.findViewById(R.id.rb_no_other);

        start_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentProgress = CurrentProgress + 10;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);
            }
        });

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopDiseasesFragment topDiseasesFragment = new TopDiseasesFragment();
                fragmentTransaction.replace(R.id.main_container, topDiseasesFragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        //submit
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //DoB
        rg_age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_doB.isChecked()) {
                    ll_Dob.setVisibility(View.VISIBLE);
                    ll_age.setVisibility(View.GONE);
                } else if (rb_age.isChecked()) {
                    ll_Dob.setVisibility(View.GONE);
                    ll_age.setVisibility(View.VISIBLE);
                }
            }
        });
        //phone number
        rg_phone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_phone.isChecked()) {
                    ll_phone_num.setVisibility(View.VISIBLE);
                    ll_phone_email.setVisibility(View.GONE);
                } else if (rb_email.isChecked()) {
                    ll_phone_num.setVisibility(View.GONE);
                    ll_phone_email.setVisibility(View.VISIBLE);
                }
            }
        });

        //Address
        rg_address.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_yes.isChecked()) {
                    ll_address.setVisibility(View.VISIBLE);
                } else if (rb_no.isChecked()) {
                    ll_address.setVisibility(View.GONE);
                }
            }
        });
        //other issue
        rg_other.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle RadioButton selection changes here
                if (rb_yes_other.isChecked()) {
                    ll_otherProblem.setVisibility(View.VISIBLE);
                    tv_submit.setVisibility(View.VISIBLE);
                } else if (rb_no_other.isChecked()) {
                    ll_otherProblem.setVisibility(View.GONE);
                    tv_submit.setVisibility(View.VISIBLE);
                }
            }
        });
        return NewCase;
    }
}