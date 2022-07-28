package com.acme.eshop.model;

import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
public class OrderItem extends BaseModel{
    private int quantity;
    private String productCode;
    private String productName;
    private int productSize;
    private BigDecimal productPrice;
    public OrderItem() {
    }

    public OrderItem(int quantity, String productCode, String productName,
                     int productSize, BigDecimal productPrice) {
        this.quantity = quantity;
        this.productCode = productCode;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductSize() {
        return productSize;
    }

    public void setProductSize(int productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productSize=" + productSize +
                ", productPrice=" + productPrice +
                '}';
    }
}
