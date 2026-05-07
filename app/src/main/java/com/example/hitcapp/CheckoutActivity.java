package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private Spinner spnProvince, spnDistrict;
    private ApiService apiService;
    private List<ApiService.Province> provinceList = new ArrayList<>();
    private List<ApiService.District> districtList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/") // Base URL mặc định
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etAddress = findViewById(R.id.etAddress);
        spnProvince = findViewById(R.id.spnProvince);
        spnDistrict = findViewById(R.id.spnDistrict);
        RadioGroup rgPayment = findViewById(R.id.rgPayment);
        TextView tvSubtotal = findViewById(R.id.tvSubtotal);
        TextView tvTotal = findViewById(R.id.tvTotalCheckout);

        // Tải danh sách Tỉnh/Thành phố
        fetchProvinces();

        // Xử lý khi chọn Tỉnh -> Tải Huyện
        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                ApiService.Province selected = provinceList.get(position);
                fetchDistricts(selected.code);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
            String detailAddress = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || detailAddress.isEmpty() || 
                spnProvince.getSelectedItem() == null || spnDistrict.getSelectedItem() == null) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullAddress = detailAddress + ", " + spnDistrict.getSelectedItem().toString() + ", " + spnProvince.getSelectedItem().toString();
            
            // LƯU ĐƠN HÀNG VÀO LỊCH SỬ TRƯỚC KHI XÓA GIỎ
            CartManager.saveOrder(this);

            // Xử lý thành công
            Toast.makeText(this, "Đặt hàng thành công!\nĐịa chỉ: " + fullAddress, Toast.LENGTH_LONG).show();
            
            // Xóa sạch giỏ hàng
            CartManager.clearCart();
            
            // Quay về trang chủ
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void fetchProvinces() {
        apiService.getProvinces().enqueue(new Callback<List<ApiService.Province>>() {
            @Override
            public void onResponse(Call<List<ApiService.Province>> call, Response<List<ApiService.Province>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    provinceList = response.body();
                    ArrayAdapter<ApiService.Province> adapter = new ArrayAdapter<>(CheckoutActivity.this, 
                            android.R.layout.simple_spinner_dropdown_item, provinceList);
                    spnProvince.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<ApiService.Province>> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Lỗi tải danh sách tỉnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDistricts(int provinceCode) {
        apiService.getDistricts(provinceCode).enqueue(new Callback<ApiService.ProvinceResponse>() {
            @Override
            public void onResponse(Call<ApiService.ProvinceResponse> call, Response<ApiService.ProvinceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    districtList = response.body().districts;
                    ArrayAdapter<ApiService.District> adapter = new ArrayAdapter<>(CheckoutActivity.this, 
                            android.R.layout.simple_spinner_dropdown_item, districtList);
                    spnDistrict.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ApiService.ProvinceResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Lỗi tải danh sách quận/huyện", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
