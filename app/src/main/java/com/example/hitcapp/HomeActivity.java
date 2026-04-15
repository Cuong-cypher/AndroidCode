package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        
        ArrayList<CustomAdapter.AppItem> list = new ArrayList<>();

        // Dữ liệu mẫu đồ Decor bàn làm việc
        list.add(new CustomAdapter.AppItem("Kệ gỗ kê màn hình", "450.000đ", "#D2B48C"));
        list.add(new CustomAdapter.AppItem("Đèn bàn Pixar", "290.000đ", "#2C3E50"));
        list.add(new CustomAdapter.AppItem("Thảm trải bàn Felt", "150.000đ", "#7F8C8D"));
        list.add(new CustomAdapter.AppItem("Chậu cây Terrarium", "120.000đ", "#27AE60"));
        list.add(new CustomAdapter.AppItem("Giá đỡ điện thoại Nhôm", "85.000đ", "#BDC3C7"));
        list.add(new CustomAdapter.AppItem("Loa Bluetooth Retro", "550.000đ", "#E67E22"));
        list.add(new CustomAdapter.AppItem("Đồng hồ LED RGB", "320.000đ", "#8E44AD"));
        list.add(new CustomAdapter.AppItem("Ống cắm bút Gốm", "65.000đ", "#ECF0F1"));

        CustomAdapter adapter = new CustomAdapter(this, list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}