package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity {

    private RecyclerView rvSuggestions;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<CustomAdapter.AppItem> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Nhận dữ liệu từ Intent
        String name = getIntent().getStringExtra("PRODUCT_NAME");
        String description = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        String price = getIntent().getStringExtra("PRODUCT_PRICE");
        int imageRes = getIntent().getIntExtra("PRODUCT_IMAGE", R.drawable.banner_sample);
        String imageUrl = getIntent().getStringExtra("PRODUCT_IMAGE_URL");

        // Ánh xạ View
        TextView tvName = findViewById(R.id.tvProductName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvPrice = findViewById(R.id.tvPrice);
        ImageView imgProduct = findViewById(R.id.imgProductLarge);
        
        // Hiển thị dữ liệu
        if (name != null) tvName.setText(name);
        if (description != null) tvDescription.setText(description);
        if (price != null) tvPrice.setText(price);
        
        // Hiển thị ảnh: Ưu tiên link từ API, nếu không có dùng ảnh local
        if (imageUrl != null && !imageUrl.isEmpty()) {
            com.bumptech.glide.Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.banner_sample)
                .into(imgProduct);
        } else {
            imgProduct.setImageResource(imageRes);
        }

        // Nút quay lại
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Nút yêu thích
        findViewById(R.id.btnFav).setOnClickListener(v -> 
            Toast.makeText(this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show()
        );

        // Nút Thêm vào giỏ hàng
        findViewById(R.id.btnAddToCart).setOnClickListener(v -> {
            CustomAdapter.AppItem currentItem;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                currentItem = new CustomAdapter.AppItem(name, description, price, imageUrl);
            } else {
                currentItem = new CustomAdapter.AppItem(name, description, price, imageRes);
            }
            CartManager.addToCart(currentItem);
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });

        // Nút Mua ngay
        findViewById(R.id.btnBuyNow).setOnClickListener(v -> {
            CustomAdapter.AppItem currentItem;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                currentItem = new CustomAdapter.AppItem(name, description, price, imageUrl);
            } else {
                currentItem = new CustomAdapter.AppItem(name, description, price, imageRes);
            }
            CartManager.addToCart(currentItem);
            
            // Chuyển thẳng sang màn hình Thanh toán
            android.content.Intent intent = new android.content.Intent(ProductDetailActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Thiết lập sản phẩm gợi ý
        rvSuggestions = findViewById(R.id.rvSuggestions);
        suggestionList = new ArrayList<>();
        suggestionAdapter = new SuggestionAdapter(this, suggestionList);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSuggestions.setAdapter(suggestionAdapter);

        fetchSuggestions();
    }

    private void fetchSuggestions() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    suggestionList.clear();
                    int count = 0;
                    for (ProductResponse.Product p : response.body().products) {
                        if (count >= 10) break;
                        String formattedPrice = String.format(Locale.US, "$%.2f", p.price);
                        suggestionList.add(new CustomAdapter.AppItem(p.title, p.description, formattedPrice, p.thumbnail));
                        count++;
                    }
                    suggestionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {}
        });
    }
}
