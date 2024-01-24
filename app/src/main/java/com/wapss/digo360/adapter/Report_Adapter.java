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
import com.wapss.digo360.response.Patient_Count_Response;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.utility.EncryptionUtils;

import java.util.List;

public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.ViewHolder>{

    public static List<Patient_Count_Response.Result> ItemList;
    Context context;

    public Report_Adapter(Context context, List<Patient_Count_Response.Result> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Report_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_layout, parent, false);
        Report_Adapter.ViewHolder viewHolder = new Report_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Report_Adapter.ViewHolder holder, int position) {
        //Report_Model report_model = report_models.get(position);
        holder.pt_id.setText(ItemList.get(position).getPatientDetail().getPid());
        holder.pt_gender.setText(ItemList.get(position).getPatientDetail().getGender());
        holder.pt_diseases.setText(ItemList.get(position).getDisease().getName());
        holder.pt_age.setText(ItemList.get(position).getPatientDetail().getDob());
        String ACC_Id = ItemList.get(position).getPatientDetail().getAccountId();
        try {
            String decrypttext = EncryptionUtils.decrypt(ItemList.get(position).getPatientDetail().getName(), ACC_Id);
            holder.pt_name.setText(decrypttext);
//            String decrypttext1 = EncryptionUtils.decrypt(ItemList.get(position).getId(), ACC_Id);
//            Log.d("decrypttext",decrypttext1);
//            holder.pt_id.setText(decrypttext1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            String decrypttext = EncryptionUtils.decrypt(ItemList.get(position).getPatientDetail().getAge(), ACC_Id);
//            Log.d("decrypttext",decrypttext);
//            holder.pt_age.setText(decrypttext);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
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
