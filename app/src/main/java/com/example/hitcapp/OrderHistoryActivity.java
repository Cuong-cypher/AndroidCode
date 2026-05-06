package com.example.hitcapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rvOrderHistory;
    private OrderHistoryAdapter adapter;
    private TextView tvEmptyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        rvOrderHistory = findViewById(R.id.rvOrderHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);

        findViewById(R.id.btnBackOrderHistory).setOnClickListener(v -> finish());

        findViewById(R.id.btnClearOrderHistory).setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa toàn bộ lịch sử đơn hàng không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        android.content.SharedPreferences orderPrefs = getSharedPreferences("Orders", MODE_PRIVATE);
                        orderPrefs.edit().remove("order_list").apply();
                        loadOrderHistory(); // Refresh the list
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        android.content.SharedPreferences pref = getSharedPreferences("Orders", MODE_PRIVATE);
        String existingOrders = pref.getString("order_list", "[]");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ArrayList<CustomAdapter.AppItem>>>(){}.getType();
            ArrayList<ArrayList<CustomAdapter.AppItem>> allOrders = gson.fromJson(existingOrders, type);

            if (allOrders == null || allOrders.isEmpty()) {
                tvEmptyHistory.setVisibility(View.VISIBLE);
                rvOrderHistory.setVisibility(View.GONE);
            } else {
                tvEmptyHistory.setVisibility(View.GONE);
                rvOrderHistory.setVisibility(View.VISIBLE);
                adapter = new OrderHistoryAdapter(this, allOrders);
                rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));
                rvOrderHistory.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tvEmptyHistory.setVisibility(View.VISIBLE);
        }
    }
}
