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

import com.squareup.picasso.Picasso;
import com.wapss.digo360.R;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

public class TopSearchDiseaseAdapter extends RecyclerView.Adapter<TopSearchDiseaseAdapter.ViewHolder>{
    public static List<TopDiseaseResponse.Result> ItemList;
    private Context context;

    TopDiseaseListener listener;
    @NonNull
    @Override
    public TopSearchDiseaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        TopSearchDiseaseAdapter.ViewHolder viewHolder = new TopSearchDiseaseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopSearchDiseaseAdapter.ViewHolder holder, int position) {
        holder.tv_disease.setText(ItemList.get(position).getName());
        if (ItemList.get(position).getImage()==null){
            holder.iv_image.setBackgroundResource(R.drawable.ivicon);
        }
        else {
            Picasso.with(context)
                    .load(ItemList.get(position).getImage())
                    .into(holder.iv_image);
        }
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_disease;
        ImageView iv_image;
        LinearLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image2);
            tv_disease = (TextView) itemView.findViewById(R.id.tv_disease2);
            item = (LinearLayout) itemView.findViewById(R.id.item1);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
