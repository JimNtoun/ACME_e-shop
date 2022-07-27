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
            preparedStatement.setDate(10, new Date(order.getCreationDate().getTime()));
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
                Order order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setCustomerFirstName(resultSet.getString("customerfirstName"));
                order.setCustomerLastName(resultSet.getString("customerlastName"));
                order.setCustomerEmail(resultSet.getString("customeremail"));
                order.setSalespersonEmail(resultSet.getString("salespersonEmail"));
                order.setSalespersonFirstName(resultSet.getString("salespersonfirstname"));
                order.setSalespersonLastName(resultSet.getString("salespersonlastname"));
                order.setCost(resultSet.getBigDecimal("cost"));
                order.setStatus(resultSet.getString("status"));
                order.setCreationDate(resultSet.getDate("Date"));
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
                        .cost(resultSet.getBigDecimal("cost"))
                        .creationDate(resultSet.getDate("creationDate")).build());
            } else {
                return Optional.empty();
            }
        }

        catch (SQLException e) {
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
            preparedStatement.setDate(8, (Date) order.getCreationDate());
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


}
