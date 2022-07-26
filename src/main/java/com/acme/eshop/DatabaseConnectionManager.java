package com.acme.eshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.exit;

public class DatabaseConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);
    private static final    String url = "jdbc:oracle:thin:@//localhost:1522/XEPDB1";
    private static final    String username = "adm";
    private static final    String password = "password";
    public static void main(String[] args) {


         try {
             Connection connection = DriverManager.getConnection(url, username, password);
             logger.info("Connected to Oracle database server");
            } catch (SQLException ex) {
                logger.error("Error while retrieving database connection.", ex);
                exit(-1);
            }

    }
}