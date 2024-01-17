package com.btl.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.quizapp.adapter.QuestionsAdapter;
import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.Question;
import com.btl.quizapp.R;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionsAdapter questionsAdapter;
    private ArrayList<Question> questionList;
    private SparseArray<Integer> userAnswers = new SparseArray<>();
    private int correctAnswers = 0;
    private int totalQuestions = 10; // Số câu hỏi cần trả lời

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Log.d("QuestionsActivity", "onCreate: Activity created");

        recyclerView = findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy ID của category từ Intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        Log.d("QuestionsActivity", "Category ID: " + categoryId);

        // Lấy danh sách câu hỏi ngẫu nhiên từ cơ sở dữ liệu
        questionList = getQuestionsFromDatabase(categoryId);
        Log.d("QuestionsActivity", "Number of questions: " + questionList.size());

        questionsAdapter = new QuestionsAdapter(this, questionList, userAnswers);
        recyclerView.setAdapter(questionsAdapter);

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
            }
        });
    }

    private ArrayList<Question> getQuestionsFromDatabase(int categoryId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Log.d("QuestionsActivity", "Fetching questions from database for category ID: " + categoryId);
        return databaseHelper.getRandomQuestions(categoryId, totalQuestions);
    }

    // Phương thức để tính điểm và hiển thị kết quả
    private void finishQuiz() {
        // Tính điểm
        int score = calculateScore();
        Log.d("QuestionsActivity", "Score: " + score);

        // Chuyển sang ResultActivity và truyền điểm
        Intent intent = new Intent(QuestionsActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);

        // Lấy categoryId từ Intent và truyền vào ResultActivity
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        intent.putExtra("CATEGORY_ID", categoryId);

        // Đặt cờ để xóa các Activity khác ra khỏi stack và sử dụng Activity hiện tại nếu nó đã tồn tại
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }

    // Phương thức để tính điểm dựa trên số câu trả lời đúng
    private int calculateScore() {
        for (int i = 0; i < totalQuestions; i++) {
            Question question = questionList.get(i);
            int userAnswer = userAnswers.get(i, -1); // Lấy câu trả lời của người dùng

            if (userAnswer == question.getAnswer()) {
                correctAnswers++; // Nếu câu trả lời đúng, tăng số câu trả lời đúng
            }
        }
        Log.d("QuestionsActivity", "Correct Answers: " + correctAnswers);
        Log.d("QuestionsActivity", "Total Questions: " + totalQuestions);
        // Bạn có thể thay đổi cách tính điểm theo yêu cầu của bạn
        return correctAnswers * 100 / totalQuestions;
    }
//    private void showScore(int score) {
//        TextView scoreTextView = findViewById(R.id.scoreTextView);
//        scoreTextView.setText("Score: " + score);
//        scoreTextView.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
