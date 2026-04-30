package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etAddress = findViewById(R.id.etAddress);
        RadioGroup rgPayment = findViewById(R.id.rgPayment);
        TextView tvSubtotal = findViewById(R.id.tvSubtotal);
        TextView tvTotal = findViewById(R.id.tvTotalCheckout);

        // Hiển thị số tiền
        double total = CartManager.getTotalPrice();
        boolean isUsd = false;
        for (CustomAdapter.AppItem item : CartManager.getCartList()) {
            if (item.price != null && item.price.contains("$")) {
                isUsd = true;
                break;
            }
        }

        String formattedPrice;
        if (isUsd) {
            formattedPrice = String.format(Locale.US, "$%.2f", total);
        } else {
            DecimalFormat formatter = new DecimalFormat("#,###");
            formattedPrice = formatter.format(total).replace(",", ".") + " VND";
        }
        
        tvSubtotal.setText(formattedPrice);
        tvTotal.setText(formattedPrice);

        // Nút quay lại
        findViewById(R.id.btnBackCheckout).setOnClickListener(v -> finish());

        // Nút xác nhận đặt hàng
        findViewById(R.id.btnConfirmOrder).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xử lý thành công
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
            
            // Xóa sạch giỏ hàng
            CartManager.clearCart();
            
            // Quay về trang chủ và xóa các màn hình trước đó khỏi Stack
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
