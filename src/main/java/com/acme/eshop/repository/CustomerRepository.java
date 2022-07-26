package com.acme.eshop.repository;

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
    public void create(Customer customer) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""), new String[]{"id"})) {

            preparedStatement.setLong(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCity());


            preparedStatement.executeUpdate();
            log.trace("Created customer {}.", customer);

            // setting id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next(); // we only suppose that there is a single generated key
            customer.setId(generatedKeys.getLong(1));

        } catch (SQLException e) {
        }

    }

    @Override
    public List<Customer> findAll() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Customer> customerList = new ArrayList<>();
            while (resultSet.next()) {
                Customer customer = new Customer();
                        customer.setId(resultSet.getLong("id"));
                        customer.setFirstName(resultSet.getString("firstName"));
                        customer.setLastName(resultSet.getString("lastName"));
                        customer.setEmail(resultSet.getString("email"));
                        customer.setPhone(resultSet.getString("phone"));
                        customer.setAddress(resultSet.getString("address"));
                        customer.setCity(resultSet.getString("city"));
                        customerList.add(customer);
            }

            return customerList;
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public Optional<Customer> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean delete(Customer customer) {
        return false;
    }
}
