package com.example.hitcapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("products/category/{categoryName}")
    Call<ProductResponse> getProductsByCategory(@Path("categoryName") String categoryName);

    @GET("products/categories")
    Call<java.util.List<CategoryItem>> getCategories();

    class CategoryItem {
        public String slug;
        public String name;
    }
}