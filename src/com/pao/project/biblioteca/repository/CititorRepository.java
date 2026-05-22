package com.pao.project.biblioteca.repository;

import com.pao.project.biblioteca.model.Cititor;
import com.pao.project.biblioteca.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CititorRepository implements Repository<Cititor, Integer> {
    private final Connection connection;

    public CititorRepository() {
        // Preluam conexiunea Singleton creata anterior
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Cititor entity) {
        String sql = "INSERT INTO Cititori (id, nume, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getId());
            pstmt.setString(2, entity.getNume());
            pstmt.setString(3, entity.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea cititorului: " + e.getMessage());
        }
    }

    @Override
    public Optional<Cititor> findById(Integer id) {
        String sql = "SELECT * FROM Cititori WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Cititor(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la gasirea cititorului: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Cititor> findAll() {
        List<Cititor> cititori = new ArrayList<>();
        String sql = "SELECT * FROM Cititori";
        // Aici folosim Statement simplu conform permisiunilor generale pentru SELECT fara parametri
        // dar tot folosim try-with-resources pentru a inchide obiectele corect
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cititori.add(new Cititor(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la aducerea listei de cititori: " + e.getMessage());
        }
        return cititori;
    }

    @Override
    public void update(Cititor entity) {
        String sql = "UPDATE Cititori SET nume = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setString(2, entity.getEmail());
            pstmt.setInt(3, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea cititorului: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Cititori WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea cititorului: " + e.getMessage());
        }
    }

    // --- INTEROGARE CU JOIN (CERINTA 3 - Partea 2) ---
    public void listeazaCititoriCuNumarImprumuturi() {
        String sql = "SELECT c.nume, COUNT(i.id) as nr_imprumuturi " +
                "FROM Cititori c " +
                "LEFT JOIN Imprumuturi i ON c.id = i.id_cititor " +
                "GROUP BY c.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("--- Statistici Imprumuturi Cititori ---");
            while (rs.next()) {
                System.out.println("Cititor: " + rs.getString("nume") + " | Carti luate: " + rs.getInt("nr_imprumuturi"));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la JOIN cititori: " + e.getMessage());
        }
    }

}