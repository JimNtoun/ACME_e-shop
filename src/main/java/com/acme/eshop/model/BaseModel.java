package com.acme.eshop.model;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BaseModel {
    private Long id;

    public BaseModel() {
    }

    public BaseModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
