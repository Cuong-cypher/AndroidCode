package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
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

public class ProductListActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private ArrayList<CustomAdapter.AppItem> productList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<String> categoryList;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Khởi tạo Retrofit và ApiService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Thiết lập RecyclerView Sản phẩm
        RecyclerView rv = findViewById(R.id.rvAllProducts);
        productList = new ArrayList<>();
        adapter = new CustomAdapter(this, productList);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);

        // Thiết lập RecyclerView Danh mục
        RecyclerView rvCategories = findViewById(R.id.rvCategoriesList);
        if (rvCategories != null) {
            categoryList = new ArrayList<>();
            categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
                fetchProductsByCategory(category);
            });
            rvCategories.setAdapter(categoryAdapter);
            fetchCategories();
        }

        // Kiểm tra xem có danh mục được truyền từ trang Home không
        handleIntent(getIntent());

        // Nút Back - Quay về Home
        findViewById(R.id.btnBackProducts).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        setupSearch();
        setupNavigation();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String category = intent.getStringExtra("CATEGORY");
        if (category != null && !category.isEmpty()) {
            fetchProductsByCategory(category);
            if (categoryAdapter != null) {
                categoryAdapter.setSelectedCategory(category);
            }
        } else {
            fetchProductsFromApi();
            if (categoryAdapter != null) {
                categoryAdapter.setSelectedCategory("all");
            }
        }
    }

    private void fetchCategories() {
        apiService.getCategories().enqueue(new Callback<java.util.List<ApiService.CategoryItem>>() {
            @Override
            public void onResponse(Call<java.util.List<ApiService.CategoryItem>> call, Response<java.util.List<ApiService.CategoryItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList.clear();
                    categoryList.add("all");
                    for (ApiService.CategoryItem item : response.body()) {
                        categoryList.add(item.slug);
                    }
                    categoryAdapter.notifyDataSetChanged();

                    // Sau khi danh sách danh mục đã tải xong, highlight mục đang chọn
                    String category = getIntent().getStringExtra("CATEGORY");
                    if (category != null && !category.isEmpty()) {
                        categoryAdapter.setSelectedCategory(category);
                    } else {
                        categoryAdapter.setSelectedCategory("all");
                    }
                }
            }

            @Override
            public void onFailure(Call<java.util.List<ApiService.CategoryItem>> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductsByCategory(String category) {
        if ("all".equals(category)) {
            fetchProductsFromApi();
            return;
        }
        apiService.getProductsByCategory(category).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    for (ProductResponse.Product p : response.body().products) {
                        String formattedPrice = String.format(java.util.Locale.US, "$%.2f", p.price);
                        productList.add(new CustomAdapter.AppItem(p.title, p.description, formattedPrice, p.thumbnail));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, "Không thể tải sản phẩm theo danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearch() {
        android.widget.EditText etSearch = findViewById(R.id.etSearchList);
        if (etSearch != null) {
            etSearch.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filter(s.toString());
                }
                @Override
                public void afterTextChanged(android.text.Editable s) {}
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

    private void fetchProductsFromApi() {
        apiService.getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    for (ProductResponse.Product p : response.body().products) {
                        String formattedPrice = String.format(java.util.Locale.US, "$%.2f", p.price);
                        productList.add(new CustomAdapter.AppItem(p.title, p.description, formattedPrice, p.thumbnail));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, "Không thể tải sản phẩm từ API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNavigation() {
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
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
        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}
