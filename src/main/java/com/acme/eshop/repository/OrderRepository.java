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
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderRepository implements CRUDRepository<Order,Long>{

    @Override
    public void create(Order order) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""), new String[]{"id"})) {

            preparedStatement.setLong(1, order.getId());
            preparedStatement.setString(2, order.getCustomerFirstName());
            preparedStatement.setString(3, order.getCustomerLastName());
            preparedStatement.setString(4, order.getCustomerEmail());
            preparedStatement.setString(5, order.getSalespersonEmail());
            preparedStatement.setString(6, order.getSalespersonFirstName());
            preparedStatement.setString(7, order.getSalespersonLastName());
            preparedStatement.setString(8, order.getStatus());
            preparedStatement.setBigDecimal(9, order.getCost());
            //preparedStatement.setOrderItems(11, order.getOrderItems());

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
                     SqlCommandRepository.get(""))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = Order.builder().id(resultSet.getLong("id"))
                        .customerFirstName(resultSet.getString("customerfirstName"))
                        .customerLastName(resultSet.getString("customerlastName"))
                        .customerEmail(resultSet.getString("customeremail"))
                        .salespersonEmail(resultSet.getString("salespersonEmail"))
                        .salespersonFirstName(resultSet.getString("salespersonfirstname"))
                        .salespersonLastName(resultSet.getString("salespersonlastname"))
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
                     SqlCommandRepository.get(""))) {

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Order.builder().id(resultSet.getLong("id")).customerFirstName(resultSet.getString("customerFirstName"))
                        .customerLastName(resultSet.getString("customerLastName")).customerEmail(resultSet.getString("customerEmail"))
                        .salespersonEmail(resultSet.getString("salesPersonEmail")).salespersonFirstName(resultSet.getString("salesPersonFirstname"))
                        .salespersonLastName(resultSet.getString("salesPersonLastname"))
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
                     SqlCommandRepository.get(""))) {

            preparedStatement.setLong(1,order.getId());
            preparedStatement.setString(2, order.getCustomerFirstName());
            preparedStatement.setString(3,order.getCustomerLastName());
            preparedStatement.setString(4, order.getCustomerEmail());
            preparedStatement.setString(5, order.getSalespersonFirstName());
            preparedStatement.setString(6,order.getSalespersonLastName());
            preparedStatement.setString(7,order.getStatus());
            preparedStatement.setBigDecimal(9,order.getCost());

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
                     SqlCommandRepository.get(""))) {

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
                     SqlCommandRepository.get(""))) {

            preparedStatement.setLong(1,order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<OrderItem> orderItemList = new ArrayList<>();
            while (resultSet.next()) {
                OrderItem orderItem = OrderItem.builder().id(resultSet.getLong("id"))
                        .productCode(resultSet.getString("productCode"))
                        .productName(resultSet.getString("productName"))
                        .productSize(resultSet.getInt("productSize"))
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
                     SqlCommandRepository.get(""))) {

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
                     SqlCommandRepository.get(""), new String[]{"id"})) {

            preparedStatement.setLong(1, orderItem.getId());
            preparedStatement.setInt(2, orderItem.getQuantity());
            preparedStatement.setString(3, orderItem.getProductCode());
            preparedStatement.setString(3, orderItem.getProductName());
            preparedStatement.setInt(3, orderItem.getProductSize());
            preparedStatement.setBigDecimal(3, orderItem.getProductPrice());



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
