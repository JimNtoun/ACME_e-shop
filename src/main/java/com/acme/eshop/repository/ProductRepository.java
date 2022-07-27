package com.acme.eshop.repository;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Product;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepository implements CRUDRepository<Product, Long>{
    @Override
    public void create(Product product) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""), new String[]{"id"})) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            product.setId(generatedKeys.getLong(1));

        } catch (SQLException e) {
            throw new NotFoundException("Could not create product",e);
        }
    }

    @Override
    public List<Product> findAll() throws NotFoundException {
        return null;
    }

    @Override
    public Optional<Product> findByID(Long id) throws NotFoundException {
        return Optional.empty();
    }

    @Override
    public boolean update(Product product) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""))) {
            preparedStatement.setString(1,product.getProductName());
            preparedStatement.setLong(2, product.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} product {}.", rowAffected == 1 ? "Deleted" : "Failed to delete", product);
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not update product",e);
        }    }

    @Override
    public boolean delete(Product product) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get(""))) {

            preparedStatement.setLong(1, product.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} product {}.", rowAffected == 1 ? "Deleted" : "Failed to delete", product);
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not delete product",e);
        }
    }
}
