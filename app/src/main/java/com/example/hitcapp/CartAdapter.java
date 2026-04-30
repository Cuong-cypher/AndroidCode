package com.example.hitcapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
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
        
        if (item.imageUrl != null && !item.imageUrl.isEmpty()) {
            Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.banner_sample)
                .into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(item.imageRes);
        }

        holder.etQuantity.setText(String.valueOf(item.quantity));

        holder.btnPlus.setOnClickListener(v -> {
            item.quantity++;
            holder.etQuantity.setText(String.valueOf(item.quantity));
            if (listener != null) listener.onCountChanged();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                holder.etQuantity.setText(String.valueOf(item.quantity));
                if (listener != null) listener.onCountChanged();
            }
        });

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
        android.widget.EditText etQuantity;
        View btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartName);
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
            imgProduct = itemView.findViewById(R.id.imgCartItem);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            etQuantity = itemView.findViewById(R.id.etQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}