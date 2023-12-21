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
import com.wapss.digo360.response.NotificationResponse;
import com.wapss.digo360.response.SettingHomeResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public static List<NotificationResponse.Result> ItemList;
    private Context context;

    public NotificationAdapter(Context context, List<NotificationResponse.Result> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//        Date date9 = null;//You will get date object relative to server/client timezone wherever it is parsed
//        try {
////            date = dateFormat.parse("2017-04-26T20:55:00.000Z");
//            date9 = dateFormat1.parse(ItemList.get(position).getCreatedAt());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        DateFormat formatter9 = new SimpleDateFormat("dd-MM-yyyy | hh:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'
//        String dateStr = formatter9.format(date9);
//
//        Log.d("datecheck",dateStr);

        holder.tv_title.setText(ItemList.get(position).getTitle());
        holder.tv_des.setText(ItemList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_des,tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
