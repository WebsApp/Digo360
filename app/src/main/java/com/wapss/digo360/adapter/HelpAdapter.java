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
import com.wapss.digo360.response.HelpResponse;
import com.wapss.digo360.response.NotificationResponse;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder>{
    public static List<HelpResponse.Result> ItemList;
    private Context context;
    int flag = 0;

    public HelpAdapter(Context context, List<HelpResponse.Result> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }
    @NonNull
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_help, parent, false);
        HelpAdapter.ViewHolder viewHolder = new HelpAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(ItemList.get(position).getQuestion());
        holder.tv_answer.setText(ItemList.get(position).getAnswer());

        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                    holder.answer.setVisibility(View.VISIBLE);
                    flag=1;
                }else {
                    holder.answer.setVisibility(View.GONE);
                    flag=0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_answer;
        ImageView iv_add;
        LinearLayout answer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_add);
            answer = (LinearLayout) itemView.findViewById(R.id.answer);
        }
    }
}
