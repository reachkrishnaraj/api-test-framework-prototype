package org.example.core.db;

import org.example.core.config.ConfigProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresClient {

    private static PostgresClient instance;

    private Connection connection;

    private PostgresClient(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PostgresClient getInstance() {
        if (instance == null) {
            synchronized (PostgresClient.class) {

                if (instance == null) {
                    instance = new PostgresClient(ConfigProvider.getDatabaseConnectionUri(), ConfigProvider.getDatabaseUsername(), ConfigProvider.getDatabasePassword());
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
