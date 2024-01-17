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

import java.util.List;

public class Page_Faq_Adapter extends RecyclerView.Adapter<Page_Faq_Adapter.ViewHolder> {
    public List<Help_Model> faq_models;
    Context context;

    public Page_Faq_Adapter(List<Help_Model> faq_models, Context context) {
        this.faq_models = faq_models;
        this.context = context;
    }

    @NonNull
    @Override
    public Page_Faq_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_layout,parent,false);
        return new Page_Faq_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Page_Faq_Adapter.ViewHolder holder, int position) {
        Help_Model faqModel = faq_models.get(position);
        holder.faq_heading.setText(faqModel.getQuestion());
        holder.heading_desc.setText(faqModel.getAnswer());
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
        return 0;
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
