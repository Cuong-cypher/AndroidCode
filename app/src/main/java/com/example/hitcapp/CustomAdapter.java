package com.example.hitcapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<AppItem> list;

    public CustomAdapter(Context context, ArrayList<AppItem> list) {
        this.context = context;
        this.list = list;
    }

    public static class AppItem {
        String name;
        String price;
        String color;

        public AppItem(String name, String price, String color) {
            this.name = name;
            this.price = price;
            this.color = color;
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        AppItem item = list.get(position);
        holder.tvName.setText(item.name);
        holder.tvPrice.setText(item.price);
        
        // Dùng tạm màu nền thay cho ảnh sản phẩm để test
        holder.imgProduct.setBackgroundColor(Color.parseColor(item.color));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        View imgProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
