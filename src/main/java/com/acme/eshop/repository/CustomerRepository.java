package com.acme.eshop.repository;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Customer;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
public class CustomerRepository implements CRUDRepository<Customer,Long>{

    @Override
    public void create(final Customer customer) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("insert.table.customer.000"), new String[]{"id"})) {

            preparedStatement.setLong(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCity());


            preparedStatement.executeUpdate();
            log.trace("Created customer {}.", customer);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            customer.setId(generatedKeys.getLong(1));

        } catch (SQLException e) {
            throw new NotFoundException("Could not create customer",e);
        }
    }


    @Override
    public List<Customer> findAll() throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.customer.000"))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Customer> customerList = new ArrayList<>();
            while (resultSet.next()) {
                Customer customer = Customer.builder().id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName")).email(resultSet.getString("email"))
                        .phone(resultSet.getString("phone")).address(resultSet.getString("address"))
                        .city(resultSet.getString("city")).build();
                customerList.add(customer);
            }

            return customerList;
        } catch (SQLException e) {
            throw new NotFoundException("Could not find customer",e);
        }
    }

    @Override
    public Optional<Customer> findByID(Long id) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.customer.001"))) {

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Customer.builder().id(resultSet.getLong("id")).firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName")).email(resultSet.getString("email"))
                        .phone(resultSet.getString("phone")).address(resultSet.getString("address"))
                        .city(resultSet.getString("city")).build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new NotFoundException("Could not find customer by ID",e);
        }
    }

    @Override
    public boolean update(Customer customer) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("update.table.customer.000"))) {

             preparedStatement.setLong(1,customer.getId());
             preparedStatement.setString(2, customer.getFirstName());
             preparedStatement.setString(3,customer.getLastName());
             preparedStatement.setString(4, customer.getEmail());
             preparedStatement.setString(5, customer.getPhone());
             preparedStatement.setString(6,customer.getAddress());
             preparedStatement.setString(7,customer.getCity());

             int rowAffected = preparedStatement.executeUpdate();
             log.trace("{} customer with id:{}",rowAffected == 1 ? "Updated" : "Failed to update", customer.getId());
             return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not update customer",e);
        }
    }

    @Override
    public boolean delete(Customer customer) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("delete.table.customer.000"))) {

            preparedStatement.setLong(1,customer.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} customer with id:{}",rowAffected == 1 ? "Deleted" : "Failed to delete", customer.getId());
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not delete customer",e);
        }
    }
}
