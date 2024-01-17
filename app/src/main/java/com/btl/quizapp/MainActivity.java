package com.btl.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.quizapp.adapter.QuizCategoryAdapter;
import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.QuizCategory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, historyQuiz, changePassword,logout;
    RecyclerView recyclerView;
    ArrayList<QuizCategory> categoriesList;
    TextView username, email;

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

        // show account = sharedPreferences
        username = findViewById(R.id.txtUsername);
        email = findViewById(R.id.txtEmail);
//        Intent intent = getIntent();
//        Log.d("UserInfo", "Username: " + intent.getStringExtra("username_Infor"));
//        Log.d("UserInfo", "Email: " + intent.getStringExtra("email_Infor"));
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String id = preferences.getString("id_Infor", "");
        String usernameInfor = preferences.getString("username_Infor", "");
        String emailInfor = preferences.getString("email_Infor", "");
        String passwordInfor = preferences.getString("password_Infor", "");
        username.setText(usernameInfor);
        email.setText(emailInfor);
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
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                redirectActivity(MainActivity.this,LoginActivity.class);
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
            public void onItemClick(int categoryId) {
                if (categoryId != -1) {
                    Log.d("MainActivity", "Clicked category ID: " + categoryId);

                    // Chuyển sang QuestionsActivity và truyền ID của category
                    Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                    intent.putExtra("CATEGORY_ID", categoryId);
                    startActivity(intent);
                } else {
                    Log.e("MainActivity", "Invalid category ID");
                }
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
        onBackPressed();
    }
}