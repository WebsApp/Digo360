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
import com.wapss.digo360.interfaces.TopDiseaseListener2;
import com.wapss.digo360.response.SearchResponse;
import com.wapss.digo360.response.TopDiseaseResponse;

import java.util.List;

public class TopDiseaseAdapter2 extends RecyclerView.Adapter<TopDiseaseAdapter2.ViewHolder>{
    public static List<SearchResponse.Result> ItemList;
    private Context context;
    TopDiseaseListener2 listener;

    public TopDiseaseAdapter2(Context context, List<SearchResponse.Result> ItemList,TopDiseaseListener2 listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public TopDiseaseAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_disease, parent, false);
        TopDiseaseAdapter2.ViewHolder viewHolder = new TopDiseaseAdapter2.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopDiseaseAdapter2.ViewHolder holder, int position) {
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
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_disease = (TextView) itemView.findViewById(R.id.tv_disease);
            item = (LinearLayout) itemView.findViewById(R.id.item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
