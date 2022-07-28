package com.acme.eshop.service;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Customer;
import com.acme.eshop.model.Product;
import com.acme.eshop.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CustomerService implements CRUDService<Customer,Long> {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void create(Customer customer) throws NotFoundException {
        log.debug("Creating customer");
        customerRepository.create(customer);
    }

    @Override
    public List<Customer> findAll() throws NotFoundException {
        log.debug("Find all customers");
        List<Customer> customersFromDatabase = customerRepository.findAll();
        return customersFromDatabase;
    }

    @Override
    public Optional<Customer> findByID(Long id) throws NotFoundException {
        log.debug("Find customer by ID");
        Optional<Customer> customersFromDatabaseID = customerRepository.findByID(id);
        return customersFromDatabaseID;
    }

    @Override
    public boolean update(final Customer customer) throws NotFoundException {
        log.debug("Update customer {}", customer);
        boolean isUpdated = false;
        if (customer.getId() != null && customer.getId() > 0) {
            isUpdated = customerRepository.update(customer);
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Customer customer) throws NotFoundException {
        return customerRepository.delete(customer);
    }

}
