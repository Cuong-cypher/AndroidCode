package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // KIỂM TRA TRẠNG THÁI ĐĂNG NHẬP
        android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        // Khai báo các View
        TextInputEditText txtEmail = findViewById(R.id.txtEmail);
        TextInputEditText txtPassword = findViewById(R.id.txtPassword);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        TextView btnRegister = findViewById(R.id.btnRegister);

        // Nút Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Chuyển sang trang Đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(it);
        });

        // Xử lý Đăng nhập
        btnLogin.setOnClickListener(v -> {
            String sEmail = txtEmail.getText().toString().trim();
            String sPass = txtPassword.getText().toString().trim();

            if (sEmail.isEmpty() || sPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Email và Mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            // LẤY THÔNG TIN ĐÃ LƯU TỪ SharedPreferences
            String savedEmail = pref.getString("email", "");
            String savedPass = pref.getString("password", "");

            // Debug nhanh: Hiển thị thông tin đang lưu (Bạn có thể xóa sau khi test xong)
            // Toast.makeText(this, "Đang lưu: " + savedEmail, Toast.LENGTH_SHORT).show();

            // KIỂM TRA ĐĂNG NHẬP
            boolean isRegisteredUser = !savedEmail.isEmpty() && sEmail.equalsIgnoreCase(savedEmail) && sPass.equals(savedPass);
            boolean isDefaultUser = sEmail.equals("1@gmail.com") && sPass.equals("123");

            if (isRegisteredUser || isDefaultUser) {
                // LƯU TRẠNG THÁI ĐĂNG NHẬP
                pref.edit().putBoolean("isLoggedIn", true).apply();

                Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}