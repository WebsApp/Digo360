package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.model.Patient_Details_Model;
import com.wapss.digo360.model.Patient_Info_Model;

import java.util.List;

public class Patient_Adapter extends RecyclerView.Adapter<Patient_Adapter.ViewHolder>{

    public List<Patient_Details_Model> patient_details;
    Context context;

    public Patient_Adapter(List<Patient_Details_Model> patient_details, Context context) {
        this.patient_details = patient_details;
        this.context = context;
    }

    @NonNull
    @Override
    public Patient_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_details_layout,parent,false);
        return new Patient_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_Adapter.ViewHolder holder, int position) {
        Patient_Details_Model pT_Model = patient_details.get(position);
        holder.pt_name.setText(pT_Model.getName());
        holder.pt_dob.setText(pT_Model.getDob());
        holder.pt_gender.setText(pT_Model.getGender());
        holder.pt_age.setText(pT_Model.getAge());
        holder.pt_date.setText(pT_Model.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return patient_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pt_name,pt_dob,pt_gender,pt_age,pt_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_date = itemView.findViewById(R.id.pt_date);
            pt_age = itemView.findViewById(R.id.pt_age);
            pt_gender = itemView.findViewById(R.id.pt_gender);
            pt_dob = itemView.findViewById(R.id.pt_dob);
            pt_name = itemView.findViewById(R.id.pt_name);
        }
    }
}
