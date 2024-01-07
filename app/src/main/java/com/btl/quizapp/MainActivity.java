package com.btl.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.btl.quizapp.adapter.QuizCategoryAdapter;
import com.btl.quizapp.database.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, historyQuiz, changePassword,logout;

    RecyclerView recyclerView;
    ArrayList<QuizCategory> quizCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        historyQuiz = findViewById(R.id.history_quiz);
        changePassword = findViewById(R.id.change_password);
        logout = findViewById(R.id.logout);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, MainActivity.class);
            }
        });
        historyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, HistoryQuizActivity.class);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, ChangePasswordActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                redirectActivity(MainActivity.this, MainActivity.class);
                Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
            }
        });




        //nanh
        ArrayList<QuizCategory> quizCategories = getCategoriesFromDatabase(); // Sử dụng phương thức để lấy danh sách categories

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        QuizCategoryAdapter adapter = new QuizCategoryAdapter(this, quizCategories);
        recyclerView.setAdapter(adapter);

        // Bổ sung phần xử lý sự kiện khi người dùng chọn một phân loại quiz
        adapter.setOnItemClickListener(new QuizCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Xử lý khi người dùng chọn một phân loại quiz
                // Chẳng hạn, mở màn hình danh sách các bài quiz trong phân loại này
            }
        });


    }

    //Nanh
    private ArrayList<QuizCategory> getCategoriesFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<QuizCategory> categoriesList = databaseHelper.getAllCategories();
        databaseHelper.close();
        return categoriesList;
    }





    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
//    public void closeDrawer(DrawerLayout drawerLayout){
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
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