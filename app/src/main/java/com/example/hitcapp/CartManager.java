package com.example.hitcapp;

import java.util.ArrayList;

public class CartManager {
    private static ArrayList<CustomAdapter.AppItem> cartList = new ArrayList<>();

    public static void addToCart(CustomAdapter.AppItem newItem) {
        if (newItem == null || newItem.name == null) return;
        
        for (CustomAdapter.AppItem item : cartList) {
            if (item.name != null && item.name.equals(newItem.name)) {
                item.quantity += 1;
                return;
            }
        }
        cartList.add(newItem);
    }

    public static ArrayList<CustomAdapter.AppItem> getCartList() {
        return cartList;
    }

    public static void removeItem(int position) {
        if (position >= 0 && position < cartList.size()) {
            cartList.remove(position);
        }
    }

    public static double getTotalPrice() {
        double total = 0;
        for (CustomAdapter.AppItem item : cartList) {
            try {
                String priceStr = item.price;
                if (priceStr == null || priceStr.isEmpty()) continue;

                if (priceStr.contains("$")) {
                    // USD: Giữ lại dấu chấm thập phân, chỉ xóa dấu phẩy và ký hiệu $
                    priceStr = priceStr.replace("$", "").replace(",", "").trim();
                    total += Double.parseDouble(priceStr) * item.quantity;
                } else {
                    // VND: Xóa dấu chấm (ngăn cách hàng nghìn) và chữ VND
                    priceStr = priceStr.replace(" VND", "").replace(".", "").replace(",", "").trim();
                    total += Double.parseDouble(priceStr) * item.quantity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public static void clearCart() {
        cartList.clear();
    }

    public static void saveOrder(android.content.Context context) {
        if (cartList.isEmpty()) return;

        android.content.SharedPreferences pref = context.getSharedPreferences("Orders", android.content.Context.MODE_PRIVATE);
        String existingOrders = pref.getString("order_list", "[]");

        try {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.ArrayList<java.util.ArrayList<CustomAdapter.AppItem>>>(){}.getType();
            java.util.ArrayList<java.util.ArrayList<CustomAdapter.AppItem>> allOrders = gson.fromJson(existingOrders, type);
            if (allOrders == null) allOrders = new java.util.ArrayList<>();

            // Thêm bản sao của giỏ hàng hiện tại vào đầu danh sách
            allOrders.add(0, new java.util.ArrayList<>(cartList));

            pref.edit().putString("order_list", gson.toJson(allOrders)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}