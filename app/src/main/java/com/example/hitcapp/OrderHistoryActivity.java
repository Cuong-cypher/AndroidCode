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
    private ArrayList<ArrayList<CustomAdapter.AppItem>> orderList = new ArrayList<>();
    private TextView tvEmptyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        rvOrderHistory = findViewById(R.id.rvOrderHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);
        View btnBack = findViewById(R.id.btnBackOrderHistory);

        rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        
        loadOrderHistory();

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrderHistory();
    }

    private void loadOrderHistory() {
        android.content.SharedPreferences pref = getSharedPreferences("Orders", MODE_PRIVATE);
        String json = pref.getString("order_list", "[]");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ArrayList<CustomAdapter.AppItem>>>(){}.getType();
            ArrayList<ArrayList<CustomAdapter.AppItem>> savedOrders = gson.fromJson(json, type);
            
            if (savedOrders != null) {
                orderList.clear();
                orderList.addAll(savedOrders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (orderList.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            rvOrderHistory.setVisibility(View.GONE);
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            rvOrderHistory.setVisibility(View.VISIBLE);
            
            if (adapter == null) {
                adapter = new OrderHistoryAdapter(this, orderList);
                rvOrderHistory.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

}
