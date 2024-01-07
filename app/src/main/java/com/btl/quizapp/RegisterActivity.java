package com.btl.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btl.quizapp.database.DatabaseHelper;
import com.btl.quizapp.model.User;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    EditText username_input,email_input,password_input,confirm_password_input;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.text_switch_login);
        btnRegister = findViewById(R.id.btn_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_input = findViewById(R.id.input_username);
                email_input = findViewById(R.id.input_email);
                password_input = findViewById(R.id.input_password);
                confirm_password_input = findViewById(R.id.input_confirmPassword);
                String username = username_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();
                String confirm_password = confirm_password_input.getText().toString();
                boolean validationPassed = true;
                if(username.equals("") || email.equals("") || password.equals("") || confirm_password.equals("")) {
                    Snackbar.make(v, "Vui lòng điền đầy đủ thông tin !", Snackbar.LENGTH_SHORT).show();
                    validationPassed = false;
                }
                if (username.isEmpty()) {
                    username_input.setError("Tên đăng nhập không được để trống!");
                    username_input.requestFocus();
                    validationPassed = false;
                } else if (username.length() < 3) {
                    username_input.setError("Tên đăng nhập phải có ít nhất 3 ký tự!");
                    username_input.requestFocus();
                } else if (username.contains(" ") || username.matches(".*[!@#$%&*()_+].*")) {
                    username_input.setError("Tên đăng nhập không được chứa khoảng trắng hoặc ký tự đặc biệt!");
                    username_input.requestFocus();
                    validationPassed = false;
                }

                if (email.isEmpty() || !email.contains("@") || !email.contains(".com")) {
                    email_input.setError("Email không đúng định dạng!");
                    email_input.requestFocus();
                    validationPassed = false;
                }

                else if(password.length() < 6) {
                    password_input.setError("Mật khẩu phải nhiều hơn 6 ký tự !");
                    password_input.requestFocus();
                    validationPassed = false;
                }
                else if(!password.equals(confirm_password)) {
                    confirm_password_input.setError("Mật khẩu xác nhận không trùng khớp !");
                    confirm_password_input.requestFocus();
                    validationPassed = false;
                }
                if (validationPassed) {
                    DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);
                    db.addUser(new User(username, email, password));
                    showSuccessDialog();
                }

            }
        });
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng ký thành công");
        builder.setMessage("Bạn đã đăng ký thành công!\nNhấn OK để đăng nhập.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = username_input.getText().toString();
                String password = password_input.getText().toString();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }


}