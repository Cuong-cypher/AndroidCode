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

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {

    private Context context;
    private ArrayList<CustomAdapter.AppItem> list;

    public SuggestionAdapter(Context context, ArrayList<CustomAdapter.AppItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_small, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_NAME", item.name);
            intent.putExtra("PRODUCT_DESCRIPTION", item.description);
            intent.putExtra("PRODUCT_PRICE", item.price);
            intent.putExtra("PRODUCT_IMAGE", item.imageRes);
            intent.putExtra("PRODUCT_IMAGE_URL", item.imageUrl);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SuggestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct;

        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductNameSmall);
            tvPrice = itemView.findViewById(R.id.tvProductPriceSmall);
            imgProduct = itemView.findViewById(R.id.imgProductSmall);
        }
    }
}
