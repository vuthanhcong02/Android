package com.btl.quizapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.quizapp.model.QuizCategory;
import com.btl.quizapp.R;

import java.util.ArrayList;

public class QuizCategoryAdapter extends RecyclerView.Adapter<QuizCategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QuizCategory> quizCategories;
    private OnItemClickListener listener;

    public QuizCategoryAdapter(Context context, ArrayList<QuizCategory> quizCategories) {
        this.context = context;
        this.quizCategories = quizCategories;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Thêm một phương thức để lấy ID của category theo vị trí
    public int getCategoryId(int position) {
        if (position >= 0 && position < quizCategories.size()) {
            return quizCategories.get(position).getCategoryId();
        }
        return -1; // hoặc một giá trị đặc biệt để chỉ ra lỗi
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizCategory category = quizCategories.get(position);
        holder.categoryName.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return quizCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);

            // Xử lý sự kiện khi một phân loại quiz được chọn
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Sử dụng phương thức mới để lấy ID của category và chuyển sang QuestionsActivity
                            int categoryId = getCategoryId(position);
                            Log.d("QuizCategoryAdapter", "Clicked category ID: " + categoryId);
                            listener.onItemClick(categoryId);
                        }
                    }
                }
            });
        }
    }
}
