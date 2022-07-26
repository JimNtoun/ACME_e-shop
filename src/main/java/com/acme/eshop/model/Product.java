package com.acme.eshop.model;

public class Product {

    private int id;
    private String productName;
    private String productSize;
    private int price;

    public Product() {
    }

    public Product(int id, String productName, String productSize, int price) {
        this.id = id;
        this.productName = productName;
        this.productSize = productSize;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", price=" + price +
                '}';
    }
}
