package com.btl.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.quizapp.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class ChangePasswordActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, historyQuiz, changePassword,logout;
    TextView username, email;
    EditText oldPassword, newPassword, confirm_newPassword;
    Button btnChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        username = findViewById(R.id.txtUsername);
        email = findViewById(R.id.txtEmail);
        btnChangePassword = findViewById(R.id.btnChange_password);
        // show account = sharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String id = preferences.getString("id_Infor", "");
        String usernameInfor = preferences.getString("username_Infor", "");
        String emailInfor = preferences.getString("email_Infor", "");
        String passwordInfor = preferences.getString("password_Infor", "");
        username.setText(usernameInfor);
        email.setText(emailInfor);
        //
        historyQuiz = findViewById(R.id.history_quiz);
        changePassword = findViewById(R.id.change_password);
        logout = findViewById(R.id.logout);
        //
        oldPassword = findViewById(R.id.input_password_now);
        oldPassword.setText(passwordInfor);
        oldPassword.setEnabled(false);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChangePasswordActivity.this, MainActivity.class);
            }
        });
        historyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChangePasswordActivity.this, HistoryQuizActivity.class);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ChangePasswordActivity.this, ChangePasswordActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                redirectActivity(ChangePasswordActivity.this, LoginActivity.class);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = findViewById(R.id.input_password_new);
                confirm_newPassword = findViewById(R.id.input_confirm_password_new);
                String new_password = newPassword.getText().toString();
                String confirm_new_password = confirm_newPassword.getText().toString();
                if(new_password.isEmpty() || confirm_new_password.isEmpty()){
                    Snackbar.make(v, "Vui lòng điền đầy đủ thông tin", Snackbar.LENGTH_SHORT).show();
                }
                else if(new_password.length() < 6){
                    Snackbar.make(v, "Mật khẩu phải nhiều hơn 6 ký tự", Snackbar.LENGTH_SHORT).show();
                }
                else if(!new_password.equals(confirm_new_password)){
                    Snackbar.make(v, "Mật khẩu xác nhận không trùng khớp!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    //update password in database
                    DatabaseHelper db = new DatabaseHelper(ChangePasswordActivity.this);
                    SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                    Integer id = Integer.valueOf(preferences.getString("id_Infor", ""));
                    db.updatePassword(id, new_password);
                    ///////////////////////////
                    //update password in sharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("password_Infor", new_password);
                    editor.apply();
                    oldPassword.setText(new_password);
                    Snackbar.make(v, "Đổi mật khẩu thành công", Snackbar.LENGTH_SHORT).show();
                    newPassword.setText("");
                    confirm_newPassword.setText("");
                }
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
            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
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