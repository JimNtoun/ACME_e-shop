package com.acme.eshop.repository;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Product;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepository implements CRUDRepository<Product, Long>{
    @Override
    public void create(Product product) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("insert.table.product.000"), new String[]{"id"})) {

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
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.product.000"), new String[]{"id"})) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = Product.builder().id(resultSet.getLong("id"))
                        .productName(resultSet.getString("productName"))
                        .productSize(resultSet.getString("productSize"))
                        .price(resultSet.getBigDecimal("price")).build();
                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new NotFoundException("Could not find all products", e);
        }
    }
    @Override
    public Optional<Product> findByID(Long id) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("select.table.product.001"))) {

            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(Product.builder().id(resultSet.getLong("id"))
                        .productName(resultSet.getString("productName"))
                        .productSize(resultSet.getString("productSize"))
                        .price(resultSet.getBigDecimal("price")).build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new NotFoundException("Could not find product by ID",e);
        }
    }

    @Override
    public boolean update(Product product) throws NotFoundException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SqlCommandRepository.get("update.table.product.000"))) {

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
                     SqlCommandRepository.get("delete.table.product.000"))) {

            preparedStatement.setLong(1, product.getId());

            int rowAffected = preparedStatement.executeUpdate();
            log.trace("{} product {}.", rowAffected == 1 ? "Deleted" : "Failed to delete", product);
            return rowAffected == 1;
        } catch (SQLException e) {
            throw new NotFoundException("Could not delete product",e);
        }
    }
}
