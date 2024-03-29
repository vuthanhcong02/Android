package com.btl.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.quizapp.adapter.HistoryQuizAdapter;
import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.HistoryQuiz;

import java.util.ArrayList;

public class HistoryQuizActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, historyQuiz, changePassword,logout;
    TextView username, email,no_historyQuiz;
    View bottomView;
    Button back_to_main;
    private RecyclerView recyclerView;
    private HistoryQuizAdapter historyQuizAdapter;
    private ArrayList<HistoryQuiz> historyQuizList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_quiz);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        no_historyQuiz = findViewById(R.id.no_historyQuiz);
        bottomView = findViewById(R.id.bottomView);
        back_to_main = findViewById(R.id.back_to_main);
        // show account = sharedPreferences
            username = findViewById(R.id.txtUsername);
            email = findViewById(R.id.txtEmail);
            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
            Integer userId_isLogin = Integer.valueOf(preferences.getString("id_Infor", ""));
            String usernameInfor_isLogin = preferences.getString("username_Infor", "");
            String emailInfor_isLogin = preferences.getString("email_Infor", "");
            String passwordInfor_isLogin = preferences.getString("password_Infor", "");
            username.setText(usernameInfor_isLogin);
            email.setText(emailInfor_isLogin);
        //
        historyQuiz = findViewById(R.id.history_quiz);
        changePassword = findViewById(R.id.change_password);
        logout = findViewById(R.id.logout);
        // show score
        recyclerView = findViewById(R.id.rvRecordScore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        historyQuizList = databaseHelper.getAllHistory(userId_isLogin);
        if(historyQuizList.size() == 0){
            no_historyQuiz.setVisibility(View.VISIBLE);
        }else{
            historyQuizAdapter = new HistoryQuizAdapter(this, historyQuizList);
            recyclerView.setAdapter(historyQuizAdapter);
            no_historyQuiz.setVisibility(View.INVISIBLE);
            bottomView.setVisibility(View.INVISIBLE);
        }
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HistoryQuizActivity.this, MainActivity.class);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HistoryQuizActivity.this, MainActivity.class);
            }
        });
        historyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HistoryQuizActivity.this, HistoryQuizActivity.class);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HistoryQuizActivity.this, ChangePasswordActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                redirectActivity(HistoryQuizActivity.this, LoginActivity.class);
            }
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
            Intent intent = new Intent(HistoryQuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
//        closeDrawer(drawerLayout);
        onBackPressed();
    }

}