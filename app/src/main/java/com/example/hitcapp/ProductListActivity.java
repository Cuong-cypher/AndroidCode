package com.example.hitcapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        RecyclerView rv = findViewById(R.id.rvAllProducts);
        // Lấy danh sách sản phẩm nổi bật từ Home sang
        ArrayList<CustomAdapter.AppItem> list = new ArrayList<>();
        list.add(new CustomAdapter.AppItem("Bảng treo kính", "Phụ kiện trang trí hiện đại", "250.000 VND", R.drawable.bang_treo_kinh));
        list.add(new CustomAdapter.AppItem("Đèn Ngủ Momonga", "Ánh sáng vàng ấm áp cho phòng ngủ", "420.000 VND", R.drawable.den_ngu_momonga));
        list.add(new CustomAdapter.AppItem("Sưởi tay đa năng", "Sưởi ấm nhanh chóng, tích hợp sạc dự phòng", "185.000 VND", R.drawable.suoi_tay_da_nang));
        list.add(new CustomAdapter.AppItem("Đèn heo Minecraft", "Đèn ngủ trang trí phong cách pixel", "350.000 VND", R.drawable.den_heo_minecraft));
        list.add(new CustomAdapter.AppItem("Đèn Sứa Trang Trí", "Tạo không gian lung linh huyền ảo", "590.000 VND", R.drawable.den_sua_trang_tri));
        list.add(new CustomAdapter.AppItem("Tranh đèn hoàng hôn", "Ánh sáng hoàng hôn lãng mạn", "280.000 VND", R.drawable.tranh_den_hoang_hon));
        list.add(new CustomAdapter.AppItem("Giá trang trí katana", "Kệ trưng bày gỗ cao cấp", "120.000 VND", R.drawable.gia_trang_tri_katana));
        list.add(new CustomAdapter.AppItem("Đồ chơi lắp ráp WallE", "Mô hình lắp ráp thông minh", "450.000 VND", R.drawable.do_choi_lap_rap_walle));

        CustomAdapter adapter = new CustomAdapter(this, list);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(adapter);

        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.btnHome).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        findViewById(R.id.btnCart).setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.btnProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}
