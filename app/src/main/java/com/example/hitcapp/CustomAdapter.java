package com.example.hitcapp;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
        String description;
        String price;
        int imageRes;

        public AppItem(String name, String price, int imageRes) {
            this.name = name;
            this.price = price;
            this.imageRes = imageRes;
            this.description = "";
        }

        public AppItem(String name, String description, String price, int imageRes) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageRes = imageRes;
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
        
        // Hiển thị ảnh sản phẩm thật
        holder.imgProduct.setImageResource(item.imageRes);

        // Xử lý click vào sản phẩm để mở Detail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(context, ProductDetailActivity.class);
                intent.putExtra("PRODUCT_NAME", item.name);
                intent.putExtra("PRODUCT_DESCRIPTION", item.description);
                intent.putExtra("PRODUCT_PRICE", item.price);
                intent.putExtra("PRODUCT_IMAGE", item.imageRes);

                if (context instanceof Activity) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) context, holder.imgProduct, "product_image");
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<AppItem> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
