package com.wapss.digo360.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.NewCasectivity;

public class TopDiseasesFragment extends Fragment {
    LinearLayout viewAll2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View TopDis = inflater.inflate(R.layout.fragment_top_diseases, container, false);
        viewAll2 = TopDis.findViewById(R.id.viewAll2);
        viewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                NewCaseFragment newCaseFragment = new NewCaseFragment();
//                fragmentTransaction.replace(R.id.main_container, newCaseFragment);
//                fragmentTransaction.addToBackStack(null).commit();
                Intent intent = new Intent(getActivity(), NewCasectivity.class);
                startActivity(intent);
            }
        });
        return TopDis;
    }
}