package com.btl.quizapp.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.quizapp.model.Question;
import com.btl.quizapp.R;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Question> questionList;
    private SparseArray<Integer> userAnswers;

    public QuestionsAdapter(Context context, ArrayList<Question> questionList, SparseArray<Integer> userAnswers) {
        this.context = context;
        this.questionList = questionList;
        this.userAnswers = userAnswers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);

        // Hiển thị nội dung câu hỏi
        holder.questionTitle.setText(question.getQuestionTitle());

        // Tạo radio button cho từng đáp án
        holder.radioButton1.setText(question.getOption1());
        holder.radioButton2.setText(question.getOption2());
        holder.radioButton3.setText(question.getOption3());
        holder.radioButton4.setText(question.getOption4());

        // Thiết lập sự kiện cho RadioGroup
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Xử lý sự kiện khi có radio button được chọn
                // checkedId sẽ là ID của radio button được chọn
                int selectedOption = -1;

                if (checkedId == R.id.radio_button1) {
                    selectedOption = 1;
                } else if (checkedId == R.id.radio_button2) {
                    selectedOption = 2;
                } else if (checkedId == R.id.radio_button3) {
                    selectedOption = 3;
                } else if (checkedId == R.id.radio_button4) {
                    selectedOption = 4;
                }

                // Lưu câu trả lời của người dùng vào SparseArray
                userAnswers.put(position, selectedOption);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle;
        RadioGroup radioGroup;
        RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.question_title);
            radioGroup = itemView.findViewById(R.id.radio_group);
            radioButton1 = itemView.findViewById(R.id.radio_button1);
            radioButton2 = itemView.findViewById(R.id.radio_button2);
            radioButton3 = itemView.findViewById(R.id.radio_button3);
            radioButton4 = itemView.findViewById(R.id.radio_button4);
        }
    }
}

