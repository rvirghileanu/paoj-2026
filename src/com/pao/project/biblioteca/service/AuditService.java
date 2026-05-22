package com.pao.project.biblioteca.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;
    private final String FILE_PATH = "audit.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private AuditService() {
        // La prima initializare ne asiguram ca fisierul exista si scriem header-ul daca este gol
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {
            // Un mic trick: in productie s-ar verifica intai daca fisierul e gol.
            // Aici e suficient sa cream fisierul.
        } catch (IOException e) {
            System.err.println("Eroare la crearea fisierului de audit: " + e.getMessage());
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    // Metoda synchronized asigura thread-safety-ul cerut (Cerinta 4)
    public synchronized void logAction(String actionName) {
        // Parametrul 'true' din FileWriter activeaza modul APPEND
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(formatter);
            pw.println(actionName + "," + timestamp);

        } catch (IOException e) {
            System.err.println("Eroare la scrierea in audit: " + e.getMessage());
        }
    }
}