package com.btl.quizapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.SessionManager;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ResultActivity", "onCreate: Activity created");

        setContentView(R.layout.activity_result);

        // Nhận điểm từ Intent
        int score = getIntent().getIntExtra("SCORE", 0);
        Log.d("ResultActivity", "Score: " + score);

        // Lấy ID người dùng từ SharedPreferences (hoặc từ nơi nào bạn lưu trữ)
        int userId = getLoggedInUserId(); // Thay thế bằng cách bạn lấy được ID người dùng

        // Lấy ID category từ Intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", 1);

        // Lưu điểm vào bảng HistoryQuizTable
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long result = databaseHelper.addQuizHistory(score, userId, categoryId);
        Log.d("ResultActivity", "Quiz history added, result: " + result);

//        // Hiển thị điểm trên TextView
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Your score: " + score);
//
//        // Xử lý sự kiện khi nhấn nút Back về MainActivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();// Đóng ResultActivity và quay về MainActivity
            }
        });
    }
    // Hàm giả định để lấy ID người dùng đã đăng nhập
    private int getLoggedInUserId() {
        // Thay thế bằng cách lấy ID người dùng từ SharedPreferences hoặc nơi khác
        // Giả sử bạn có một lớp SessionManager để quản lý thông tin đăng nhập
        // và lưu trữ ID người dùng trong SharedPreferences
        SessionManager sessionManager = new SessionManager(this);
        return sessionManager.getLoggedInUserId();
    }

}
