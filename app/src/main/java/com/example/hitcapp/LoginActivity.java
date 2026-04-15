package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Nút Quay lại
        MaterialButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Chuyển sang trang Đăng ký (Hiện tại là TextView)
        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(it);
        });

        // Xử lý Đăng nhập
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            EditText objEmail = findViewById(R.id.txtEmail);
            String sEmail = objEmail.getText().toString();

            EditText objPass = findViewById(R.id.txtPassword);
            String sPass = objPass.getText().toString();

            if (sEmail.equals("1@gmail.com") && sPass.equals("123")) {
                Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(it);
                finish(); // Đăng nhập xong thì đóng luôn trang Login
            } else {
                Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}