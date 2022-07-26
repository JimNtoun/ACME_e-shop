package com.acme.eshop.repository;

import com.acme.eshop.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T,Long> {
    void create(T t) throws  BusinessException;

    List<T> findAll() throws BusinessException;

    Optional<T> findByID(Long id) throws BusinessException;

    boolean update(T t) throws BusinessException;

    boolean delete(T t) throws BusinessException;

}
