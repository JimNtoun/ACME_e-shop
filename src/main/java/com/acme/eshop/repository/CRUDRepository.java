package com.acme.eshop.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T,Long> {
    void create(T t);

    List<T> findAll();

    Optional<T> findByID(Long id);

    boolean update(T t);

    boolean delete(T t);

}
