package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView rvWishlist;
    private CustomAdapter adapter;
    private ArrayList<CustomAdapter.AppItem> wishlist;
    private LinearLayout emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        rvWishlist = findViewById(R.id.rvWishlist);
        emptyState = findViewById(R.id.emptyState);

        findViewById(R.id.btnBackWishlist).setOnClickListener(v -> finish());

        wishlist = WishlistManager.getWishlist(this);
        
        updateEmptyState();

        adapter = new CustomAdapter(this, wishlist);
        // Dùng GridLayoutManager (2 cột) để giống giao diện bên ngoài (Home/ProductList)
        rvWishlist.setLayoutManager(new GridLayoutManager(this, 2));
        rvWishlist.setAdapter(adapter);
    }

    private void updateEmptyState() {
        if (wishlist.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvWishlist.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvWishlist.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại danh sách khi quay lại từ màn hình chi tiết
        wishlist.clear();
        wishlist.addAll(WishlistManager.getWishlist(this));
        adapter.notifyDataSetChanged();
        updateEmptyState();
    }
}
