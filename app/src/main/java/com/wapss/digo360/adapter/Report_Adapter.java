package com.wapss.digo360.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Report_Model;
import com.wapss.digo360.utility.EncryptionUtils;

import java.util.List;

public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.ViewHolder>{

    public List<Report_Model> report_models;
    Context context;

    public Report_Adapter(List<Report_Model> report_models, Context context) {
        this.report_models = report_models;
        this.context = context;
    }

    @NonNull
    @Override
    public Report_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_layout,parent,false);
        return new Report_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Report_Adapter.ViewHolder holder, int position) {
        Report_Model report_model = report_models.get(position);
        holder.pt_id.setText(report_model.getP_ID());
        holder.pt_gender.setText(report_model.getP_gender());
        holder.pt_diseases.setText(report_model.getP_desease());
        String ACC_Id = report_model.getAccount_Id();
        try {
            String decrypttext = EncryptionUtils.decrypt(report_model.getP_name(), ACC_Id);
            Log.d("decrypttext",decrypttext);
            holder.pt_name.setText(decrypttext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String decrypttext = EncryptionUtils.decrypt(report_model.getP_age(), ACC_Id);
            Log.d("decrypttext",decrypttext);
            holder.pt_age.setText(decrypttext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return report_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pt_id,pt_name,pt_gender,pt_age,pt_diseases;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_diseases = itemView.findViewById(R.id.pt_diseases);
            pt_age = itemView.findViewById(R.id.pt_age);
            pt_gender = itemView.findViewById(R.id.pt_gender);
            pt_name = itemView.findViewById(R.id.pt_name);
            pt_id = itemView.findViewById(R.id.pt_id);
        }
    }
}
