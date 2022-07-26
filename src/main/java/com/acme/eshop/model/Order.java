package com.acme.eshop.model;

import com.acme.eshop.util.DataTransferObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements DataTransferObject {
    private long id;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String salespersonFirstName;
    private String salespersonLastName;
    private String salespersonEmail;
    private String status;
    private BigDecimal cost;
    private Date creationDate;
    private List<OrderItem> orderItems;
    public Order() {
    }

    public Order(long id, String customerFirstName, String customerLastName,
                 String customerEmail, String salespersonFirstName, String salespersonLastName,
                 String salespersonEmail, String status, BigDecimal cost,
                 Date creationDate, List<OrderItem> orderItems) {
        this.id = id;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.salespersonFirstName = salespersonFirstName;
        this.salespersonLastName = salespersonLastName;
        this.salespersonEmail = salespersonEmail;
        this.status = status;
        this.cost = cost;
        this.creationDate = creationDate;
        this.orderItems = orderItems;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSalespersonFirstName() {
        return salespersonFirstName;
    }

    public void setSalespersonFirstName(String salespersonFirstName) {
        this.salespersonFirstName = salespersonFirstName;
    }

    public String getSalespersonLastName() {
        return salespersonLastName;
    }

    public void setSalespersonLastName(String salespersonLastName) {
        this.salespersonLastName = salespersonLastName;
    }

    public String getSalespersonEmail() {
        return salespersonEmail;
    }

    public void setSalespersonEmail(String salespersonEmail) {
        this.salespersonEmail = salespersonEmail;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", salespersonFirstName='" + salespersonFirstName + '\'' +
                ", salespersonLastName='" + salespersonLastName + '\'' +
                ", salespersonEmail='" + salespersonEmail + '\'' +
                ", status='" + status + '\'' +
                ", cost=" + cost +
                ", creationDate=" + creationDate +
                '}';
    }
}
