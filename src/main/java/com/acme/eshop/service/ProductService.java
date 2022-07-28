package com.acme.eshop.service;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Customer;
import com.acme.eshop.model.Product;
import com.acme.eshop.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductService implements CRUDService<Product, Long>{
    ProductRepository productRepository;

    @Override
    public void create(Product product) throws NotFoundException {
        log.debug("Creating product.");
        productRepository.create(product);
    }

    @Override
    public List<Product> findAll() throws NotFoundException {
        log.debug("Find all products.");
        List<Product> productsFromDatabase = productRepository.findAll();
        return productsFromDatabase;    }

    @Override
    public Optional<Product> findByID(Long id) throws NotFoundException {
        log.debug("Find product by ID.");
        Optional<Product> studentFromDatabaseOptional = productRepository.findByID(id);
        return studentFromDatabaseOptional;    }

    @Override
    public boolean update(Product product) throws NotFoundException {
        log.debug("Update product.");
        boolean isUpdated = false;
        if (product.getId() != null && product.getId() > 0) {
            isUpdated = productRepository.update(product);
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Product product) throws NotFoundException {
        log.debug("Delete product.");
        return productRepository.delete(product);    }
}
