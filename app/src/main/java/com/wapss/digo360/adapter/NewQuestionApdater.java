package com.wapss.digo360.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wapss.digo360.R;
import com.wapss.digo360.interfaces.AnswerListener;
import com.wapss.digo360.interfaces.NewQuestionInterface;
import com.wapss.digo360.response.QuestionResponse;

import java.util.List;

public class NewQuestionApdater extends RecyclerView.Adapter<NewQuestionApdater.ViewHolder>{

    public List<String> ItemList;
    Context context;
    NewQuestionInterface listener;

    public NewQuestionApdater(Context context, List<String> ItemList,NewQuestionInterface listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public NewQuestionApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_question, parent, false);
        NewQuestionApdater.ViewHolder viewHolder = new NewQuestionApdater.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewQuestionApdater.ViewHolder holder, int position) {
        String item = ItemList.get(position);
        holder.tv_answer.setText(item);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_answer;
        LinearLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_answer = (TextView) itemView.findViewById(R.id.tv_answer);
            item = (LinearLayout) itemView.findViewById(R.id.item);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setBackgroundResource(R.drawable.bg_square_corner2);
                    listener.onItemClickedQuestionItem(ItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
