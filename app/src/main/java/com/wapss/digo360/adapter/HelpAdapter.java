package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.model.Help_Model;
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.response.NotificationResponse;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    public List<Help_Model> help_models;
    Context context;

    public HelpAdapter(List<Help_Model> help_models, Context context) {
        this.help_models = help_models;
        this.context = context;
    }

    @NonNull
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_layout,parent,false);
        return new HelpAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        Help_Model helpModel = help_models.get(position);
        holder.faq_heading.setText(helpModel.getQuestion());
        holder.heading_desc.setText(helpModel.getAnswer());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.hide_layout.setVisibility(View.VISIBLE);
                holder.btn_add.setVisibility(View.GONE);
                holder.btn_down.setVisibility(View.VISIBLE);
            }
        });
        holder.btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.hide_layout.setVisibility(View.GONE);
                holder.btn_add.setVisibility(View.VISIBLE);
                holder.btn_down.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return help_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heading_desc,faq_heading;
        ImageView btn_down,btn_add;
        LinearLayout hide_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            faq_heading = itemView.findViewById(R.id.faq_heading);
            heading_desc = itemView.findViewById(R.id.heading_desc);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_down = itemView.findViewById(R.id.btn_down);
            hide_layout = itemView.findViewById(R.id.hide_layout);
        }
    }
}
