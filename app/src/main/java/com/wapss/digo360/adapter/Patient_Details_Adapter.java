package com.wapss.digo360.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.activity.Patient_Details;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Patient_Info_Model;

import java.util.List;

public class Patient_Details_Adapter extends RecyclerView.Adapter<Patient_Details_Adapter.ViewHolder> {

    public List<Patient_Info_Model> patient_models;
    Context context;

    public Patient_Details_Adapter(List<Patient_Info_Model> patient_models, Context context) {
        this.patient_models = patient_models;
        this.context = context;
    }

    @NonNull
    @Override
    public Patient_Details_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.privous_patient_layout,parent,false);
        return new Patient_Details_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_Details_Adapter.ViewHolder holder, int position) {
        Patient_Info_Model pModel = patient_models.get(position);
        holder.pt_name.setText(pModel.getName());
        holder.pt_gender.setText(pModel.getGender());
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("P_ID", pModel.getId());
                Intent i = new Intent(context, Patient_Details.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patient_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pt_name,pt_gender;
        LinearLayout item_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_gender = itemView.findViewById(R.id.pt_gender);
            pt_name = itemView.findViewById(R.id.pt_name);
            item_layout = itemView.findViewById(R.id.item_layout);
        }
    }
}
