package com.example.hitcapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Nhận dữ liệu từ Intent
        String name = getIntent().getStringExtra("PRODUCT_NAME");
        String price = getIntent().getStringExtra("PRODUCT_PRICE");
        int imageRes = getIntent().getIntExtra("PRODUCT_IMAGE", R.drawable.banner_sample);

        // Ánh xạ View
        TextView tvName = findViewById(R.id.tvProductName);
        TextView tvPrice = findViewById(R.id.tvPrice);
        ImageView imgProduct = findViewById(R.id.imgProductLarge);
        
        // Hiển thị dữ liệu
        if (name != null) tvName.setText(name);
        if (price != null) tvPrice.setText(price);
        imgProduct.setImageResource(imageRes);

        // Nút quay lại
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Nút yêu thích
        findViewById(R.id.btnFav).setOnClickListener(v -> 
            Toast.makeText(this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show()
        );

        // Nút Thêm vào giỏ hàng
        findViewById(R.id.btnAddToCart).setOnClickListener(v -> {
            CartManager.addToCart(new CustomAdapter.AppItem(name, price, imageRes));
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            
            // Chuyển sang màn hình Giỏ hàng
            android.content.Intent intent = new android.content.Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}
