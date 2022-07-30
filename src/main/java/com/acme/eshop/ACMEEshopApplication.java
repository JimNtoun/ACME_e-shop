package com.acme.eshop;

import com.acme.eshop.exception.NotFoundException;
import com.acme.eshop.model.Customer;
import com.acme.eshop.model.Order;
import com.acme.eshop.model.OrderItem;
import com.acme.eshop.model.Product;
import com.acme.eshop.repository.*;
import com.acme.eshop.service.CustomerService;
import com.acme.eshop.service.OrderService;
import com.acme.eshop.service.ProductService;
import com.acme.eshop.service.ReportService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.acme.eshop.model.Category.*;
import static com.acme.eshop.model.PaymentMethod.credit_card;
import static java.lang.System.exit;

@Slf4j
public class ACMEEshopApplication {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductService productService;
    private final ReportService reportService;

    public ACMEEshopApplication() {
        this.customerService = new CustomerService(new CustomerRepository());
        this.orderService = new OrderService(new OrderRepository());
        this.productService = new ProductService(new ProductRepository());
        this.reportService = new ReportService(new CustomerRepository(), new OrderRepository(),
                new ProductRepository());
    }

    public static void main(String[] args) {
        var app = new ACMEEshopApplication();
        app.initializeDatabase();
        app.generateSampleData();
        app.runReports();
    }

    public void runReports() {
    }

    public void generateSampleData() {
        List<Customer> customers = customerCreation();
        List<Customer> storedCustomers = storedCustomers(customers);

        log.info("Customers that have been stored in the database.");
        for (final Customer customer : storedCustomers) {
            log.info("{}", customer);
        }

        List<Product> products = productCreation();
        List<Product> storedProducts = storedProducts(products);

        log.info("Products that have been stored in the database.");
        for (final Product product : storedProducts) {
            log.info("{}", product);
        }

        List<OrderItem> orderItems = orderItemCreation();

        List<Order> orders = orderCreation(orderItems);
        List<Order> storedOrders = storedOrders(orders);

        log.info("Orders with orderItems after they have been stored in the database.");
        for (final Order order : storedOrders) {
            log.info("{}", order);
        }

    }

    private List<Customer> customerCreation() {

        List<Customer> customers = List.of(
                Customer.builder().firstName("George").lastName("Pappas").email("gdsfsz").phone("fdfxzd").address("fdcfd").city("fdfdf").category(B2C).paymentMethod(credit_card).build(),
                Customer.builder().firstName("George").lastName("Pappas").email("").phone("").address("").city("").category(B2G).build(),
                Customer.builder().firstName("George").lastName("Pappas").email("").phone("").address("").city("").category(B2B).build(),
                Customer.builder().firstName("George").lastName("Pappas").email("").phone("").address("").city("").category(B2C).build(),
                Customer.builder().firstName("George").lastName("Pappas").email("").phone("").address("").city("").category(B2G).build(),
                Customer.builder().firstName("George").lastName("Pappas").email("").phone("").address("").city("").category(B2C).build());
        for (final Customer customer : customers) {
            log.trace("{}", customer);
        }
        return customers;
    }

    private List<Customer> storedCustomers(List<Customer> customers) {
        try {
            for (final Customer customer : customers) {
                customerService.create(customer);
            }
            return customerService.findAll();
        } catch (NotFoundException e) {
            log.error("An error occurred during customer showcase", e);
        }
        return Collections.emptyList();
    }

    private List<Product> productCreation() {
        List<Product> products = List.of(
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build(),
                Product.builder().productName("").productSize("").price(BigDecimal.valueOf(5)).build());
        for (final Product product : products) {
            log.trace("{}", product);
        }
        return products;
    }

    private List<Product> storedProducts(List<Product> products) {
        try {
            for (final Product product : products) {
                productService.create(product);
            }
            return productService.findAll();
        } catch (NotFoundException e) {
            log.error("An error occurred during product showcase", e);
        }
        return Collections.emptyList();
    }

    private List<OrderItem> orderItemCreation() {
        List<OrderItem> orderItems = List.of(
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build(),
                OrderItem.builder().quantity(1).productCode("").productName("").productPrice(BigDecimal.valueOf(3)).build());

        for (final OrderItem orderItem : orderItems) {
            log.trace("{}", orderItem);
        }
        return orderItems;
    }

    private List<Order> orderCreation(List<OrderItem> orderItems) {
        List<Order> orders = List.of(
                Order.builder().status("").cost(BigDecimal.valueOf(1500))
                        .orderItems(new ArrayList<>(orderItems.subList(0, 3))).build(),
                Order.builder().status("").cost(BigDecimal.valueOf(1500))
                        .orderItems(new ArrayList<>(orderItems.subList(0, 3))).build(),
                Order.builder().status("").cost(BigDecimal.valueOf(1500))
                        .orderItems(new ArrayList<>(orderItems.subList(0, 3))).build(),
                Order.builder().status("").cost(BigDecimal.valueOf(1500))
                        .orderItems(new ArrayList<>(orderItems.subList(0, 3))).build(),
                Order.builder().status("").cost(BigDecimal.valueOf(1500))
                        .orderItems(new ArrayList<>(orderItems.subList(0, 3))).build());
        for (final Order order : orders) {
            log.trace("{}", order);
        }
        return orders;
    }
    private List<Order> storedOrders(List<Order> orders) {
        try {
            for (final Order order : orders) {
                orderService.create(order);
            }
            return orderService.findAll();
        } catch (NotFoundException e) {
            log.error("An error occurred during order showcase", e);
        }
        return Collections.emptyList();
    }

    public void initializeDatabase() {
        log.info("Initialize database tables");
        dropExistingTables();
        createInitialTables();
    }

    private void createInitialTables() {
        List.of(SqlCommandRepository.get("create.table.customer"),
                SqlCommandRepository.get("create.table.order"),
                SqlCommandRepository.get("create.table.orderItem"),
                SqlCommandRepository.get("create.table.product")).forEach(c-> runTableCommands(c, false));
    }

    private void dropExistingTables() {
        List.of(SqlCommandRepository.get("drop.table.customer"),
                SqlCommandRepository.get("drop.table.order"),
                SqlCommandRepository.get("drop.table.orderItem"),
                SqlCommandRepository.get("drop.table.product")).forEach(c-> runTableCommands(c, true));
    }

    private void runTableCommands(String command, boolean drop) {
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(command);
            log.info("{} table command was successful.", drop ? "Drop" : "Create");
        } catch (SQLException ex) {
            if (drop) {
                log.warn("Unable to drop table as it does not probably exist.");
            } else {
                log.error("Error while creating table.", ex);
                exit(-1);
            }
        }
    }
}

