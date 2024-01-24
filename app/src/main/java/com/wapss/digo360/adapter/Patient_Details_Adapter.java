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
import com.wapss.digo360.interfaces.ExsitingpatientsResponse;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.model.Patient_Info_Model;
import com.wapss.digo360.response.Patient_Check_Response;
import com.wapss.digo360.response.Patient_Count_Response;
import com.wapss.digo360.utility.EncryptionUtils;

import java.util.List;

public class Patient_Details_Adapter extends RecyclerView.Adapter<Patient_Details_Adapter.ViewHolder> {

    public static List<Patient_Check_Response.Result> checkList;
    Context context;
    ExsitingpatientsResponse onItemClickedItem;

    public Patient_Details_Adapter(Context context, List<Patient_Check_Response.Result> checkList,ExsitingpatientsResponse onItemClickedItem) {
        this.checkList = checkList;
        this.context = context;
        this.onItemClickedItem = onItemClickedItem;
    }

    @NonNull
    @Override
    public Patient_Details_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.privous_patient_layout,parent,false);
        return new Patient_Details_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_Details_Adapter.ViewHolder holder, int position) {
        holder.pt_gender.setText(checkList.get(position).getGender());
        String ACC_Id = checkList.get(position).getAccount().getId();
        try {
            String decrypttext = EncryptionUtils.decrypt(checkList.get(position).getName(), ACC_Id);
            holder.pt_name.setText(decrypttext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (checkList == null) return 0;
        return checkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pt_name,pt_gender;
        LinearLayout item_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_gender = itemView.findViewById(R.id.pt_gender);
            pt_name = itemView.findViewById(R.id.pt_name);
            item_layout = itemView.findViewById(R.id.item_layout);

            item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickedItem.onItemClickedItem(checkList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
