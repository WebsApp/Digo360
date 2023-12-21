package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wapss.digo360.R;
import com.wapss.digo360.response.SettingHomeResponse;

import java.util.List;

public class TopDiagnosiAdapter extends RecyclerView.Adapter<TopDiagnosiAdapter.ViewHolder>{

    public static List<SettingHomeResponse.Slider> ItemList;
    private Context context;


    public TopDiagnosiAdapter(Context context, List<SettingHomeResponse.Slider> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public TopDiagnosiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_diagnosis, parent, false);
        TopDiagnosiAdapter.ViewHolder viewHolder = new TopDiagnosiAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopDiagnosiAdapter.ViewHolder holder, int position) {

        Picasso.with(context)
                .load(ItemList.get(position).getImage())
                .into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
