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
import com.wapss.digo360.interfaces.AnswerListener;
import com.wapss.digo360.interfaces.MostSearchDiseaseListener;
import com.wapss.digo360.model.Patient_Details_Model;
import com.wapss.digo360.response.QuestionResponse;
import com.wapss.digo360.response.SettingHomeResponse;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    public List<QuestionResponse.DiseaseAnswer> ItemList;
    Context context;
    AnswerListener listener;
    String questionId;

    public QuestionAdapter(Context context, List<QuestionResponse.DiseaseAnswer> ItemList,String questionId, AnswerListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.questionId = questionId;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_question, parent, false);
        QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        holder.tv_answer.setText(ItemList.get(position).getAnswer());
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
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition(),questionId);
                }
            });
        }
    }
}
