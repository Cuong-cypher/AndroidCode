package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private Context context;
    private ArrayList<ArrayList<CustomAdapter.AppItem>> orderList;

    public OrderHistoryAdapter(Context context, ArrayList<ArrayList<CustomAdapter.AppItem>> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ArrayList<CustomAdapter.AppItem> orderItems = orderList.get(position);
        
        holder.tvOrderId.setText("Đơn hàng #" + (orderList.size() - position));
        
        StringBuilder productsBuilder = new StringBuilder("Sản phẩm: ");
        double total = 0;
        boolean isUsd = false;

        for (int i = 0; i < orderItems.size(); i++) {
            CustomAdapter.AppItem item = orderItems.get(i);
            productsBuilder.append(item.name).append(" (x").append(item.quantity).append(")");
            if (i < orderItems.size() - 1) {
                productsBuilder.append(", ");
            }

            // Tính tổng tiền
            try {
                String priceStr = item.price;
                if (priceStr != null) {
                    if (priceStr.contains("$")) {
                        isUsd = true;
                        priceStr = priceStr.replace("$", "").replace(",", "").trim();
                        total += Double.parseDouble(priceStr) * item.quantity;
                    } else {
                        priceStr = priceStr.replace(" VND", "").replace(".", "").replace(",", "").trim();
                        total += Double.parseDouble(priceStr) * item.quantity;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.tvOrderProducts.setText(productsBuilder.toString());

        holder.btnDeleteOrder.setOnClickListener(v -> {
            orderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderList.size());
            
            // Cập nhật SharedPreferences
            android.content.SharedPreferences pref = context.getSharedPreferences("Orders", Context.MODE_PRIVATE);
            android.content.SharedPreferences.Editor editor = pref.edit();
            String json = new com.google.gson.Gson().toJson(orderList);
            editor.putString("order_list", json);
            editor.apply();
        });

        if (isUsd) {
            holder.tvOrderTotal.setText("Tổng cộng: " + String.format(Locale.US, "$%.2f", total));
        } else {
            String formatted = String.format(Locale.US, "%,.0f", total).replace(',', '.');
            holder.tvOrderTotal.setText("Tổng cộng: " + formatted + " VND");
        }
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderTotal, tvOrderProducts;
        ImageView btnDeleteOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderProducts = itemView.findViewById(R.id.tvOrderProducts);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
        }
    }
}
