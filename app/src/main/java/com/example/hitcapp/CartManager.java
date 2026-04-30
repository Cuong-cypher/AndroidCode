package com.example.hitcapp;

import java.util.ArrayList;

public class CartManager {
    private static ArrayList<CustomAdapter.AppItem> cartList = new ArrayList<>();

    public static void addToCart(CustomAdapter.AppItem newItem) {
        for (CustomAdapter.AppItem item : cartList) {
            if (item.name.equals(newItem.name)) {
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
}