package com.pao.project.biblioteca.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;

    private DatabaseConnection() {
        incarcaProprietati();
        conectare();
        creeazaTabeleAutomate(); // Initializeaza baza de date automat
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void incarcaProprietati() {
        Properties prop = new Properties();

        // Cautam in toate locatiile posibile
        Path p1 = Paths.get("src/com/pao/project/resources/db.properties");
        Path p2 = Paths.get("resources/db.properties");
        Path p3 = Paths.get("src/resources/db.properties");

        Path caleGasita = Files.exists(p1) ? p1 : (Files.exists(p2) ? p2 : (Files.exists(p3) ? p3 : null));

        if (caleGasita != null) {
            try (InputStream input = Files.newInputStream(caleGasita)) {
                prop.load(input);
                this.url = prop.getProperty("db.url");
            } catch (Exception e) {
                System.err.println("Eroare la citirea db.properties: " + e.getMessage());
            }
        } else {
            // Daca ruleaza din fisierul .jar compilat
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input != null) {
                    prop.load(input);
                    this.url = prop.getProperty("db.url");
                } else {
                    System.err.println("Eroare critica: Fisierul db.properties nu a fost gasit in structura!");
                }
            } catch (Exception e) {
                System.err.println("Eroare la incarcarea prin ClassLoader: " + e.getMessage());
            }
        }
    }

    private void conectare() {
        if (this.url == null) return;
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
    }

    private void creeazaTabeleAutomate() {
        if (this.connection == null) return;

        // Executam scripturile SQL direct pentru a ne asigura ca BD-ul e creat complet si corect la prima rulare
        String sqlSectiuni = "CREATE TABLE IF NOT EXISTS Sectiuni (id INTEGER PRIMARY KEY AUTOINCREMENT, nume TEXT NOT NULL UNIQUE);";
        String sqlCititori = "CREATE TABLE IF NOT EXISTS Cititori (id INTEGER PRIMARY KEY, nume TEXT NOT NULL, email TEXT NOT NULL);";
        String sqlCarti = "CREATE TABLE IF NOT EXISTS Carti (isbn TEXT PRIMARY KEY, titlu TEXT NOT NULL, autor TEXT NOT NULL, disponibila BOOLEAN NOT NULL CHECK (disponibila IN (0, 1)), id_sectiune INTEGER, FOREIGN KEY (id_sectiune) REFERENCES Sectiuni(id) ON DELETE SET NULL);";
        String sqlImprumuturi = "CREATE TABLE IF NOT EXISTS Imprumuturi (id INTEGER PRIMARY KEY AUTOINCREMENT, id_cititor INTEGER NOT NULL, isbn_carte TEXT NOT NULL, data_imprumut TEXT NOT NULL, FOREIGN KEY (id_cititor) REFERENCES Cititori(id) ON DELETE CASCADE, FOREIGN KEY (isbn_carte) REFERENCES Carti(isbn) ON DELETE CASCADE);";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlSectiuni);
            stmt.execute(sqlCititori);
            stmt.execute(sqlCarti);
            stmt.execute(sqlImprumuturi);
        } catch (SQLException e) {
            System.err.println("Eroare la generarea automata a tabelelor: " + e.getMessage());
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