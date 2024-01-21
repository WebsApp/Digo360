package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.interfaces.PatientsViewListener;
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.PatientsDetailsViewResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

public class PatientsDetailsViewAdapter extends RecyclerView.Adapter<PatientsDetailsViewAdapter.ViewHolder>{
    public static List<PatientsDetailsViewResponse.Result> ItemList;
    private Context context;
    PatientsViewListener listener;

    public PatientsDetailsViewAdapter(Context context, List<PatientsDetailsViewResponse.Result> ItemList, PatientsViewListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public PatientsDetailsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_patients_details, parent, false);
        PatientsDetailsViewAdapter.ViewHolder viewHolder = new PatientsDetailsViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsDetailsViewAdapter.ViewHolder holder, int position) {
        holder.pt_name.setText(ItemList.get(position).getPatientDetail().getName());
        holder.pt_dob.setText(ItemList.get(position).getPatientDetail().getDob());
        holder.pt_gender.setText(ItemList.get(position).getPatientDetail().getGender());
        holder.pt_age.setText(ItemList.get(position).getPatientDetail().getAge());
        holder.pt_date.setText(ItemList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pt_name,pt_dob,pt_gender,pt_age,pt_date;
        LinearLayout items;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pt_name = (TextView) itemView.findViewById(R.id.pt_name);
            pt_dob = (TextView) itemView.findViewById(R.id.pt_dob);
            pt_gender = (TextView) itemView.findViewById(R.id.pt_gender);
            pt_age = (TextView) itemView.findViewById(R.id.pt_age);
            pt_date = (TextView) itemView.findViewById(R.id.pt_date);
            items = (LinearLayout) itemView.findViewById(R.id.items);

            items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
