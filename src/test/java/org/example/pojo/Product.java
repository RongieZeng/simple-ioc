package org.example.pojo;

import org.example.MyAutowired;
import org.example.MyComponent;

@MyComponent
public class Product {

    @MyAutowired
    ProductCategory category;

    @MyAutowired
    ProductDetail detail;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductDetail getDetail() {
        return detail;
    }

    public void setDetail(ProductDetail detail) {
        this.detail = detail;
    }
}