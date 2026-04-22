package com.example.hitcapp;

import java.util.ArrayList;

public class CartManager {
    private static ArrayList<CustomAdapter.AppItem> cartList = new ArrayList<>();

    public static void addToCart(CustomAdapter.AppItem item) {
        cartList.add(item);
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
            String priceStr = item.price.replace(".", "").replace(" VND", "").trim();
            try {
                total += Double.parseDouble(priceStr);
            } catch (Exception e) {}
        }
        return total;
    }
}
