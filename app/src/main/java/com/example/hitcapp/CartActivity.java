package com.example.hitcapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCart;
    private CartAdapter adapter;
    private TextView tvTotalPrice;
    private android.view.View btnBack;
    private MaterialButton btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnBack = findViewById(R.id.btnBackCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        adapter = new CartAdapter(this, CartManager.getCartList(), () -> updateTotalPrice());
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);

        updateTotalPrice();

        btnBack.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, HomeActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getCartList().isEmpty()) {
                Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            } else {
                android.content.Intent intent = new android.content.Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, HomeActivity.class));
            finish();
        });
        findViewById(R.id.btnProducts).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, ProductListActivity.class));
            finish();
        });
        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, ProfileActivity.class));
            finish();
        });
    }

    private void updateTotalPrice() {
        double total = CartManager.getTotalPrice();
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalPrice.setText(formatter.format(total).replace(",", ".") + " VND");
    }
}
