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
            if (isUpdated) {
                List<Product> previousProducts = findProductsByCustomer(customer);
                previousProducts.removeAll(customer.getProducts());

                for(Product productToDelete : previousProducts){
                    deleteProduct(productToDelete);
                }
                
                for (Product product : customer.getProducts()){
                    createOrUpdateProduct(customer,product);
                }
            }
        }
        return isUpdated;
    }
    @Override
    public boolean delete(final Customer customer) throws NotFoundException {
        log.debug("Delete customer {}", customer);
        deleteProductsFromCustomer(customer);
        return customerRepository.delete(customer);
    }

    private void createProduct(Customer customer, Product product) throws NotFoundException {
        log.debug("Create Product {}", product);
        customerRepository.createProduct(customer, product);
    }

    private void createOrUpdateProduct(Customer customer, Product product) throws NotFoundException {
        boolean isUpdated = false;
        if (product.getId() != null && product.getId() >= 0){
            isUpdated = updateProduct(customer,product);
        }
        if(!isUpdated){
            createProduct(customer, product);
        }
    }
    private boolean updateProduct(final Customer customer, final Product product) throws NotFoundException {
        log.debug("Update Product");
        return customerRepository.updateProduct(customer, product);
    }

    private boolean deleteProduct(Product product) throws NotFoundException {
        log.debug("Delete Product");
        return customerRepository.deleteProduct(product);
    }

    private boolean deleteProductsFromCustomer(final Customer customer) throws NotFoundException {
        log.debug("Delete products from by customer {}.", customer);
        return customerRepository.deleteProductsFromCustomer(customer);
    }

    private List<Product> findProductsByCustomer(final Customer customer) throws NotFoundException {
        log.debug("Find products by customer {}.", customer);
        List<Product> products = customerRepository.findProductsByCustomer(customer);
        return products;
    }
}
