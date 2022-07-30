package com.acme.eshop.repository;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Order;
import com.acme.eshop.model.OrderItem;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderRepository implements CRUDRepository<Order,Long>{

    @Override
    public void create(Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("insert.table.order.000"), new String[]{"id"})) {

            preparedStatement.setLong(1, order.getId());
            preparedStatement.setString(2, order.getStatus());
            preparedStatement.setBigDecimal(3, order.getCost());


            preparedStatement.executeUpdate();
            log.trace("Created customer {}.", order);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            order.setId(generatedKeys.getLong(1));

        } catch (SQLException e) {
            throw new NotFoundException("Could not create order",e);
        }
    }
    @Override
    public List<Order> findAll() throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.order.000"))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = Order.builder().id(resultSet.getLong("id"))
                        .cost(resultSet.getBigDecimal("cost"))
                        .status(resultSet.getString("status")).build();
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new NotFoundException("Could not find order",e);
        }
    }
    @Override

    public Optional<Order> findByID(Long id) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.order.001"))) {

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Order.builder().id(resultSet.getLong("id"))
                        .status(resultSet.getString("status"))
                        .cost(resultSet.getBigDecimal("cost")).build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new NotFoundException("Could not find order by ID",e);
        }
    }
    @Override
    public boolean update(Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("update.table.order.000"))) {

            preparedStatement.setLong(1,order.getId());
            preparedStatement.setString(2,order.getStatus());
            preparedStatement.setBigDecimal(3,order.getCost());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} order with id:{}",rowAffected == 1 ? "Updated" : "Failed to update", order.getId());
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not update order",e);
        }
    }

    @Override
    public boolean delete(Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("delete.table.order.000"))) {

            preparedStatement.setLong(1,order.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} order with id:{}",rowAffected == 1 ? "Deleted" : "Failed to delete", order.getId());
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not update order",e);
        }
    }


    public List<OrderItem> findOrderItemByOrder(final Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.orderItem.000"))) {

            preparedStatement.setLong(1,order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<OrderItem> orderItemList = new ArrayList<>();
            while (resultSet.next()) {
                OrderItem orderItem = OrderItem.builder().id(resultSet.getLong("id"))
                        .productCode(resultSet.getString("productCode"))
                        .productName(resultSet.getString("productName"))
                        .productPrice(resultSet.getBigDecimal("productPrice"))
                        .quantity(resultSet.getInt("quantity")).build();
                orderItemList.add(orderItem);
            }

            return orderItemList;
        } catch (SQLException e) {
            throw new NotFoundException("Could not update orderItem",e);
        }
    }

    public boolean deleteOrderItem(final OrderItem orderItem) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("delete.table.orderItem.000"))) {

            preparedStatement.setLong(1, orderItem.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} orderItem {}.", rowAffected == 1 ? "Deleted" : "Failed to delete", orderItem);
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not delete orderItem.", e);
        }
    }

    public void createOrderItem(Order order, OrderItem orderItem) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("insert.table.orderItem.000"), new String[]{"id"})) {

            preparedStatement.setLong(1, orderItem.getId());
            preparedStatement.setInt(2, orderItem.getQuantity());
            preparedStatement.setString(3, orderItem.getProductCode());
            preparedStatement.setString(4, orderItem.getProductName());
            preparedStatement.setBigDecimal(5, orderItem.getProductPrice());



            preparedStatement.executeUpdate();
            log.debug("Created enrollment {} with student id {}.", orderItem, order.getId());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next(); // we only suppose that there is a single generated key
            orderItem.setId(generatedKeys.getLong(1));

        } catch (SQLException e) {
            throw new NotFoundException("Could not create orderItem.", e);
        }
    }

    public boolean deleteOrderItemFromOrder(Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("delete.table.enrollment.001"))) {

            preparedStatement.setLong(1, order.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} all orderItems for order id:{}.", rowAffected > 0 ? "Deleted" : "Failed to delete",
                    order.getId());
            return rowAffected > 0;
        } catch (SQLException e) {
            throw new NotFoundException("Could not delete orderItem(s) by order.", e);
        }
    }
}
