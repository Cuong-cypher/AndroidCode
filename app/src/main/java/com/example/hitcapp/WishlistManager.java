package com.example.hitcapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class WishlistManager {
    private static final String PREF_NAME = "WishlistPrefs";
    private static final String KEY_WISHLIST = "wishlist_items";
    private static ArrayList<CustomAdapter.AppItem> wishlist = new ArrayList<>();

    public static void toggleWishlist(Context context, CustomAdapter.AppItem item) {
        loadWishlist(context);
        boolean exists = false;
        int index = -1;
        for (int i = 0; i < wishlist.size(); i++) {
            if (wishlist.get(i).name.equals(item.name)) {
                exists = true;
                index = i;
                break;
            }
        }

        if (exists) {
            wishlist.remove(index);
        } else {
            wishlist.add(item);
        }
        saveWishlist(context);
    }

    public static boolean isFavorite(Context context, String productName) {
        loadWishlist(context);
        for (CustomAdapter.AppItem item : wishlist) {
            if (item.name.equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<CustomAdapter.AppItem> getWishlist(Context context) {
        loadWishlist(context);
        return wishlist;
    }

    private static void saveWishlist(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(wishlist);
        pref.edit().putString(KEY_WISHLIST, json).apply();
    }

    private static void loadWishlist(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = pref.getString(KEY_WISHLIST, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CustomAdapter.AppItem>>() {}.getType();
        wishlist = gson.fromJson(json, type);
        if (wishlist == null) wishlist = new ArrayList<>();
    }
}
