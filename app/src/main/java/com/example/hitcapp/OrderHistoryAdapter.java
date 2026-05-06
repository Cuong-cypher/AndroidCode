package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

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
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderProducts, tvOrderTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderProducts = itemView.findViewById(R.id.tvOrderProducts);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ArrayList<CustomAdapter.AppItem> items = orderList.get(position);
        holder.tvOrderId.setText("Đơn hàng #" + (orderList.size() - position));

        StringBuilder sb = new StringBuilder();
        double total = 0;
        boolean isUsd = false;

        for (int i = 0; i < items.size(); i++) {
            CustomAdapter.AppItem item = items.get(i);
            sb.append(item.name).append(" (x").append(item.quantity).append(")");
            if (i < items.size() - 1) sb.append(", ");
            
            // Tính tổng tiền đơn hàng này
            try {
                String priceStr = item.price;
                if (priceStr.contains("$")) {
                    isUsd = true;
                    priceStr = priceStr.replace("$", "").replace(",", "").trim();
                    total += Double.parseDouble(priceStr) * item.quantity;
                } else {
                    priceStr = priceStr.replace(" VND", "").replace(".", "").replace(",", "").trim();
                    total += Double.parseDouble(priceStr) * item.quantity;
                }
            } catch (Exception e) {}
        }
        
        holder.tvOrderProducts.setText("Sản phẩm: " + sb.toString());

        if (isUsd) {
            holder.tvOrderTotal.setText(String.format(java.util.Locale.US, "$%.2f", total));
        } else {
            java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,###");
            holder.tvOrderTotal.setText(formatter.format(total).replace(",", ".") + " VND");
        }
    }
}
