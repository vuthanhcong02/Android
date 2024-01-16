package com.btl.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    TextView sign_up;
    EditText username_input, password_input;
    Button login;
    private boolean isBackPressed = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up = findViewById(R.id.text_switch_signup);
        login = findViewById(R.id.btn_login);
        Intent intent = getIntent();
        username_input = findViewById(R.id.input_username_login);
        password_input = findViewById(R.id.input_password_login);
        username_input.setText(intent.getStringExtra("username"));
        password_input.setText(intent.getStringExtra("password"));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_input.getText().toString();
                String password = password_input.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "Vui lý điền đầy đủ thông tin!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
                    if (databaseHelper.checkUsernamePassword(username, password)) {
                        User user = databaseHelper.getUserData(username, password);
                        if(user != null){
//                            Log.d("UserInfo", "Username: " + user.getUsername() + ", Email: " + user.getEmail());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            String id = String.valueOf(user.getId());
//                            intent.putExtra("id_Infor", id);
                            String usernameInfor = user.getUsername();
//                            intent.putExtra("username_Infor", usernameInfor);
                            String emailInfor = user.getEmail();
//                            intent.putExtra("email_Infor", emailInfor);
                            String passwordInfor = user.getPassword();
//                            intent.putExtra("password_Infor", passwordInfor);
                            SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("id_Infor", id);
                            editor.putString("username_Infor", usernameInfor);
                            editor.putString("email_Infor", emailInfor);
                            editor.putString("password_Infor", passwordInfor);
                            editor.apply();
                            startActivity(intent);
                        }
                    }
                    else {
                        Snackbar.make(v, "Sai tên đăng nhập hoặc mật khẩu!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(isBackPressed){
            showExitConfirmationDialog();
        }
        else {
            super.onBackPressed();
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận thoát");
        builder.setMessage("Bạn có muốn thoát ứng dụng?");
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Đóng ứng dụng nếu người dùng chọn thoát
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng dialog và tiếp tục ở trang đăng nhập
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}