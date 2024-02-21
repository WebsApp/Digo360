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
import com.wapss.digo360.interfaces.HomeTopDiseaseListener;
import com.wapss.digo360.interfaces.ListDiseaseListener;
import com.wapss.digo360.interfaces.TopDiseaseListener;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

public class HomeTopDiseaseAdapter extends RecyclerView.Adapter<HomeTopDiseaseAdapter.ViewHolder>{
    public static List<TopDiseaseResponse.Result> ItemList;
    private Context context;
    HomeTopDiseaseListener listener;

    public HomeTopDiseaseAdapter(Context context, List<TopDiseaseResponse.Result> ItemList,HomeTopDiseaseListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public HomeTopDiseaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_home, parent, false);
        HomeTopDiseaseAdapter.ViewHolder viewHolder = new HomeTopDiseaseAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTopDiseaseAdapter.ViewHolder holder, int position) {
       // holder.iv_image1.setText(ItemList.get(position).getName());
        holder.tv_disease1.setText(ItemList.get(position).getName());
        if (ItemList.get(position).getImage()==null){
            holder.iv_image1.setBackgroundResource(R.drawable.ivicon);
        }
        else {
            Picasso.with(context)
                    .load(ItemList.get(position).getImage())
                    .into(holder.iv_image1);
        }
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image1;
        TextView tv_disease1;
        LinearLayout item1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image1 = (ImageView) itemView.findViewById(R.id.iv_image1);
            tv_disease1 = (TextView) itemView.findViewById(R.id.tv_disease1);
            item1 = (LinearLayout) itemView.findViewById(R.id.item1);

            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
