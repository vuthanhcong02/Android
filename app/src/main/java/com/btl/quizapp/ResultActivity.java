package com.btl.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.btl.quizapp.database.DatabaseHelper;
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ResultActivity", "onCreate: Activity created");

        setContentView(R.layout.activity_result);

        // Nhận điểm từ Intent
        int score = getIntent().getIntExtra("SCORE", 0);
        Log.d("ResultActivity", "Score: " + score);

        // Lấy userId từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String userIdString = preferences.getString("id_Infor", "-1");
        int userId = Integer.parseInt(userIdString);

        // Lấy categoryId từ Intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        // Lưu điểm vào bảng HistoryQuizTable
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long result = databaseHelper.addQuizHistory(score, userId, categoryId);
        Log.d("ResultActivity", "Quiz history added, result: " + result);

        // Hiển thị điểm trên TextView
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Your score: " + score);

        // Xử lý sự kiện khi nhấn nút Back về MainActivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent); // Đóng ResultActivity và quay về MainActivity
                finish();
            }
        });
    }


}
