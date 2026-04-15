package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Nút Quay lại (MaterialButton)
        MaterialButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Chuyển sang trang Đăng nhập (Hiện tại là TextView)
        TextView btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        });

        // Nút Đăng ký (MaterialButton)
        MaterialButton btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            // Xử lý đăng ký ở đây (nếu cần)
            finish(); // Đăng ký xong quay lại Login
        });
    }
}