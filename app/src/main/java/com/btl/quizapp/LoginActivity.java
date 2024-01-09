package com.btl.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.quizapp.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    TextView sign_up;
    EditText username_input, password_input;
    Button login;
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
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
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
}