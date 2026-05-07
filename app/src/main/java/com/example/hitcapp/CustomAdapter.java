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
        public String name;
        public String description;
        public String price;
        public int imageRes;      // Dùng cho ảnh trong máy (local)
        public String imageUrl;   // Dùng cho ảnh từ API (link)
        public int quantity = 1;

        // Constructor mặc định cho GSON
        public AppItem() {
        }

        // Constructor cho ảnh local
        public AppItem(String name, String price, int imageRes) {
            this.name = name;
            this.price = price;
            this.imageRes = imageRes;
            this.description = "";
            this.quantity = 1;
        }

        // Constructor cho ảnh API
        public AppItem(String name, String description, String price, String imageUrl) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageUrl = imageUrl;
            this.quantity = 1;
        }

        public AppItem(String name, String description, String price, int imageRes) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.imageRes = imageRes;
            this.quantity = 1;
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
        
        // Load ảnh: Ưu tiên imageUrl từ API dùng Glide, nếu không có thì dùng imageRes local
        if (item.imageUrl != null && !item.imageUrl.isEmpty()) {
            com.bumptech.glide.Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.banner_sample)
                .into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(item.imageRes);
        }

        // Xử lý click vào sản phẩm để mở Detail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(context, ProductDetailActivity.class);
                intent.putExtra("PRODUCT_NAME", item.name);
                intent.putExtra("PRODUCT_DESCRIPTION", item.description);
                intent.putExtra("PRODUCT_PRICE", item.price);
                
                // Gửi cả ID ảnh local và URL ảnh mạng sang màn hình Detail
                intent.putExtra("PRODUCT_IMAGE", item.imageRes);
                intent.putExtra("PRODUCT_IMAGE_URL", item.imageUrl);
                
                // Kiểm tra nếu context là WishlistActivity thì gửi thêm flag
                if (context instanceof WishlistActivity) {
                    intent.putExtra("FROM_WISHLIST", true);
                }

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
