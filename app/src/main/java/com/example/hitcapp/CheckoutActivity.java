package com.example.hitcapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etAddress = findViewById(R.id.etAddress);
        TextView tvSubtotal = findViewById(R.id.tvSubtotal);
        TextView tvTotal = findViewById(R.id.tvTotalCheckout);

        // Hiển thị số tiền
        double total = CartManager.getTotalPrice();
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedPrice = formatter.format(total).replace(",", ".") + " VND";
        
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
            Toast.makeText(this, "Đặt hàng thành công! Cảm ơn bạn " + name, Toast.LENGTH_LONG).show();
            
            // Xóa giỏ hàng và quay về trang chủ
            CartManager.getCartList().clear();
            android.content.Intent intent = new android.content.Intent(this, HomeActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
