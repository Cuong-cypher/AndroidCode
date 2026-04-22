package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private ArrayList<CustomAdapter.AppItem> list;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCountChanged();
    }

    public CartAdapter(Context context, ArrayList<CustomAdapter.AppItem> list, OnCartChangeListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CustomAdapter.AppItem item = list.get(position);
        holder.tvName.setText(item.name);
        holder.tvPrice.setText(item.price);
        holder.imgProduct.setImageResource(item.imageRes);

        holder.btnRemove.setOnClickListener(v -> {
            CartManager.removeItem(position);
            notifyDataSetChanged();
            if (listener != null) {
                listener.onCountChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartName);
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
            imgProduct = itemView.findViewById(R.id.imgCartItem);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
