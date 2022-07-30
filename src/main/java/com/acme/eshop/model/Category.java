package com.acme.eshop.model;

public enum Category {
    B2C("Individual", 0),
    B2B("Business", 0.2),
    B2G("Government", 0.5);
    private final double discount;
    private final String description;

    Category(String description, double discount) {
        this.discount = discount;
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }
}
