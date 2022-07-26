package com.acme.eshop.service;

import com.acme.eshop.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T,Long> {
    void create(T t) throws NotFoundException;
    List<T> findAll() throws NotFoundException;
    Optional<T> findByID(Long id) throws NotFoundException;
    boolean update(T t) throws NotFoundException;
    boolean delete(T t) throws NotFoundException;
}
