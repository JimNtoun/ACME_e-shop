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

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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
                previousOrderItems.removeAll(order.getOrderItems());
                for(OrderItem orderItemToDelete : previousOrderItems) {
                    deleteOrderItem(orderItemToDelete);
                }
                for(OrderItem orderItem : order.getOrderItems()) {
                    createOrUpdateOrderItem(order, orderItem);
                }
            }
        }
        return isUpdated;
    }

    private void createOrUpdateOrderItem(Order order, OrderItem orderItem) throws NotFoundException {
        boolean isUpdated = false;
        if (orderItem.getId() != null && orderItem.getId() >= 0) {
            isUpdated = updateOrderItem(order, orderItem);
        }
        if (!isUpdated) {
            createOrderItem(order, orderItem);
        }
    }

    private void createOrderItem(Order order, OrderItem orderItem) throws NotFoundException {
        log.debug("Create orderItem.");
        orderRepository.createOrderItem(order, orderItem);
    }

    private boolean updateOrderItem(Order order, OrderItem orderItem) throws NotFoundException {
        log.debug("Delete orderItem.");
        deleteOrderItemFromOrder(order);
        return orderRepository.delete(order);
    }

    private boolean deleteOrderItemFromOrder(Order order) throws NotFoundException {
        log.debug("Deleting enrollments by student.");
        return orderRepository.deleteOrderItemFromOrder(order);
    }

    private boolean deleteOrderItem(final OrderItem orderItem) throws NotFoundException{
        log.debug("Delete orderItem.");
        return orderRepository.deleteOrderItem(orderItem);
    }

    private List<OrderItem> findOrderItemByOrder(final Order order) throws NotFoundException {
        log.debug("Find orderItem by order");
        return orderRepository.findOrderItemByOrder(order);
    }

    @Override
    public boolean delete(Order order) throws NotFoundException {
        return false;
    }
}
