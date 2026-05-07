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

    // API Tỉnh/Thành phố Việt Nam
    @GET("https://provinces.open-api.vn/api/p/")
    Call<java.util.List<Province>> getProvinces();

    @GET("https://provinces.open-api.vn/api/p/{code}?depth=2")
    Call<ProvinceResponse> getDistricts(@Path("code") int provinceCode);

    class CategoryItem {
        public String slug;
        public String name;
    }

    class Province {
        public String name;
        public int code;
        @Override
        public String toString() { return name; }
    }

    class District {
        public String name;
        public int code;
        @Override
        public String toString() { return name; }
    }

    class ProvinceResponse {
        public java.util.List<District> districts;
    }
}