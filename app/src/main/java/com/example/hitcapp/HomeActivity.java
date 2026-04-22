package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private ArrayList<CustomAdapter.AppItem> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        if (recyclerView != null) {
            productList = new ArrayList<>();

            // Danh sách sản phẩm thật từ ảnh đã lưu
            productList.add(new CustomAdapter.AppItem("Bảng treo kính", "250.000 VND", R.drawable.bang_treo_kinh));
            productList.add(new CustomAdapter.AppItem("Đèn Ngủ Momonga", "420.000 VND", R.drawable.den_ngu_momonga));
            productList.add(new CustomAdapter.AppItem("Sưởi tay đa năng", "185.000 VND", R.drawable.suoi_tay_da_nang));
            productList.add(new CustomAdapter.AppItem("Đèn heo Minecraft", "350.000 VND", R.drawable.den_heo_minecraft));
            productList.add(new CustomAdapter.AppItem("Đèn Sứa Trang Trí", "590.000 VND", R.drawable.den_sua_trang_tri));
            productList.add(new CustomAdapter.AppItem("Tranh đèn hoàng hôn", "280.000 VND", R.drawable.tranh_den_hoang_hon));
            productList.add(new CustomAdapter.AppItem("Giá trang trí katana", "120.000 VND", R.drawable.gia_trang_tri_katana));
            productList.add(new CustomAdapter.AppItem("Đồ chơi lắp ráp WallE", "450.000 VND", R.drawable.do_choi_lap_rap_walle));

            adapter = new CustomAdapter(this, productList);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
        }

        EditText etSearch = findViewById(R.id.etSearch);
        if (etSearch != null) {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        // Điều hướng từ Bottom Navigation
        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.btnProductsNav).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProductListActivity.class));
            finish();
        });

        findViewById(R.id.btnCartNav).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
            finish();
        });

        findViewById(R.id.btnProfileNav).setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            finish();
        });
    }

    private void filter(String text) {
        ArrayList<CustomAdapter.AppItem> filteredList = new ArrayList<>();
        for (CustomAdapter.AppItem item : productList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}
