package com.acme.eshop.model;

import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuperBuilder
public class Order extends BaseModel {
    private String salespersonName;
    private String status;
    private BigDecimal cost;
    private List<OrderItem> orderItems;
    private Customer customer;
    public Order() {
    }

    public Order(String salespersonName, String status, BigDecimal cost, List<OrderItem> orderItems, Customer customer) {
        this.salespersonName = salespersonName;
        this.status = status;
        this.cost = cost;
        this.orderItems = orderItems;
        this.customer = customer;
    }

    public Order(Long id, String salespersonName, String status, BigDecimal cost, List<OrderItem> orderItems, Customer customer) {
        super(id);
        this.salespersonName = salespersonName;
        this.status = status;
        this.cost = cost;
        this.orderItems = orderItems;
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getSalespersonName() {
        return salespersonName;
    }

    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "salespersonName='" + salespersonName + '\'' +
                ", status='" + status + '\'' +
                ", cost=" + cost +
                ", orderItems=" + orderItems +
                ", customer=" + customer +
                '}';
    }
}
