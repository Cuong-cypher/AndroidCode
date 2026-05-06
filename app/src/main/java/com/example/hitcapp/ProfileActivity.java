package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Hiển thị tên người dùng từ SharedPreferences
        android.widget.TextView tvProfileName = findViewById(R.id.tvProfileName);
        android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = pref.getString("name", "Người dùng");
        if (tvProfileName != null) {
            tvProfileName.setText(name);
        }

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            // XÓA TRẠNG THÁI ĐĂNG NHẬP
            pref.edit().putBoolean("isLoggedIn", false).apply();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Nút Back - Quay về Home
        findViewById(R.id.btnBackProfile).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        // Nút Lịch sử đơn hàng
        findViewById(R.id.btnOrderHistory).setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            startActivity(intent);
        });

        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        findViewById(R.id.btnProducts).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        findViewById(R.id.btnCart).setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}
