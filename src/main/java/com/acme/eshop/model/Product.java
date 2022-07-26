package com.acme.eshop.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder

public class Product extends BaseModel{
    private String productName;
    private String productSize;
    private int price;

    public Product() {
    }

    public Product(String productName, String productSize, int price) {
        this.productName = productName;
        this.productSize = productSize;
        this.price = price;
    }

    public Product(Long id, String productName, String productSize, int price) {
        super(id);
        this.productName = productName;
        this.productSize = productSize;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productName='" + productName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", price=" + price +
                '}';
    }
}
