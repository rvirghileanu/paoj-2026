package com.pao.project.biblioteca.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;

    private DatabaseConnection() {
        incarcaProprietati();
        conectare();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void incarcaProprietati() {
        try (InputStream input = Files.newInputStream(Paths.get("resources/db.properties"))) {
            Properties prop = new Properties();
            prop.load(input);
            this.url = prop.getProperty("db.url");
        } catch (Exception e) {
            System.err.println("Eroare la incarcarea db.properties: " + e.getMessage());
        }
    }

    private void conectare() {
        try {
            // Asiguram incarcarea driver-ului JDBC pentru SQLite
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                conectare();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}