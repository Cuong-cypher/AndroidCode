package com.example.hitcapp;

import java.util.List;

/**
 * Lớp Model để nhận dữ liệu từ API DummyJSON.
 * Cấu trúc này khớp với JSON trả về từ https://dummyjson.com/products
 */
public class ProductResponse {
    // Danh sách các sản phẩm trả về từ API
    public List<Product> products;

    public static class Product {
        public int id;
        public String title;
        public String description;
        public double price;
        public String thumbnail;
    }
}