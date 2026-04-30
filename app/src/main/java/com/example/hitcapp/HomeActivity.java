package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private ArrayList<CustomAdapter.AppItem> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hiển thị tên người dùng từ SharedPreferences
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        if (tvWelcome != null) {
            android.content.SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String name = pref.getString("name", "Góc Tìm Tòi");
            tvWelcome.setText("Chào, " + name + "!");
        }

        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        if (recyclerView != null) {
            productList = new ArrayList<>();
            adapter = new CustomAdapter(this, productList);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);

            // Gọi API lấy sản phẩm từ DummyJSON
            fetchProductsFromApi();
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

        // Điều hướng từ Bottom Navigation và Top Bar
        setupNavigation();
    }

    private void fetchProductsFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    for (ProductResponse.Product p : response.body().products) {
                        // Map dữ liệu từ API về form AppItem của bạn
                        String formattedPrice = String.format(java.util.Locale.US, "$%.2f", p.price);
                        productList.add(new CustomAdapter.AppItem(p.title, p.description, formattedPrice, p.thumbnail));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Không thể tải sản phẩm từ API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNavigation() {
        // Nút Profile ở Top Bar
        View btnUserProfile = findViewById(R.id.btnUserProfile);
        if (btnUserProfile != null) {
            btnUserProfile.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            });
        }

        View btnProductsNav = findViewById(R.id.btnProductsNav);
        if (btnProductsNav != null) {
            btnProductsNav.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            });
        }

        View btnCartNav = findViewById(R.id.btnCartNav);
        if (btnCartNav != null) {
            btnCartNav.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            });
        }

        View btnProfileNav = findViewById(R.id.btnProfileNav);
        if (btnProfileNav != null) {
            btnProfileNav.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            });
        }
    }

    private void filter(String text) {
        if (productList == null) return;
        ArrayList<CustomAdapter.AppItem> filteredList = new ArrayList<>();
        for (CustomAdapter.AppItem item : productList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}