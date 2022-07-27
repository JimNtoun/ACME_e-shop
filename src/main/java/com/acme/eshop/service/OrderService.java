package com.acme.eshop.service;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Order;
import com.acme.eshop.model.OrderItem;
import com.acme.eshop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderService implements CRUDService<Order, Long> {
    private final OrderRepository orderRepository;

    @Override
    public void create(Order order) throws NotFoundException {
        log.debug("Create Order");
        orderRepository.create(order);
    }

    @Override
    public List<Order> findAll() throws NotFoundException {
        log.debug("Find all orders");
        List<Order> ordersFromDatabase = orderRepository.findAll();
        return ordersFromDatabase;
    }

    @Override
    public Optional<Order> findByID(Long id) throws NotFoundException {
        Optional<Order> orderFromDatabaseOptional = orderRepository.findByID(id);
        return orderFromDatabaseOptional;
    }

    @Override
    public boolean update(Order order) throws NotFoundException {
        log.debug("Update order");
        boolean isUpdated = false;
        if(order.getId() != null && order.getId() > 0){
            isUpdated = orderRepository.update(order);
            if(isUpdated) {
                List<OrderItem> previousOrderItems = findOrderItemByOrder(order);
            }
        }
    }

    private List<OrderItem> findOrderItemByOrder(Order order) {
        log.debug("Find orderItem by order");
        return orderRepository.findOrderItemByOrder(order);
    }

    @Override
    public boolean delete(Order order) throws NotFoundException {
        return false;
    }
}
