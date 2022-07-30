package com.acme.eshop.model;

import java.math.BigDecimal;

public enum PaymentMethod {
    credit_card(0.15),
    wire_transfer(0.1);

    private final double discount;

    PaymentMethod(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

}
