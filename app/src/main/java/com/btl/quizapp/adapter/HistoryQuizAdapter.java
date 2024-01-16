package com.btl.quizapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.quizapp.R;
import com.btl.quizapp.model.HistoryQuiz;

import java.util.ArrayList;

public class HistoryQuizAdapter extends RecyclerView.Adapter<HistoryQuizAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HistoryQuiz> historyQuizList;

    public HistoryQuizAdapter(Context context, ArrayList<HistoryQuiz> historyQuizList) {
        this.context = context;
        this.historyQuizList = historyQuizList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryQuiz historyQuiz = historyQuizList.get(position);
        holder.tvUsername.setText(historyQuiz.getUsername());
        holder.tvCategory.setText(historyQuiz.getCategory_name());
        holder.tvScore.setText(String.valueOf(historyQuiz.getScore()));
    }

    @Override
    public int getItemCount() {
        return historyQuizList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvScore, tvCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvCategory = itemView.findViewById(R.id.tvCategoryQuiz);
        }
    }
}
