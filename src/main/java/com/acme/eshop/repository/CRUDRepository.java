package com.acme.eshop.repository;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.BaseModel;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, Long> {
    void create(T t) throws NotFoundException;

    List<T> findAll() throws NotFoundException;

    Optional<T> findByID(Long id) throws NotFoundException;

    boolean update(T t) throws NotFoundException;

    boolean delete(T t) throws NotFoundException;

}
