package com.pao.project.biblioteca.repository;

import com.pao.project.biblioteca.model.Sectiune;
import com.pao.project.biblioteca.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SectiuneRepository implements Repository<Sectiune, Integer> {
    private final Connection connection;

    public SectiuneRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Sectiune entity) {
        String sql = "INSERT INTO Sectiuni (nume) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getNume());
            pstmt.executeUpdate();

            // Preluam ID-ul autogenerat de baza de date si il setam in obiectul nostru
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea sectiunii: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sectiune> findById(Integer id) {
        String sql = "SELECT * FROM Sectiuni WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Sectiune sectiune = new Sectiune(rs.getString("nume"));
                    sectiune.setId(rs.getInt("id"));
                    return Optional.of(sectiune);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la gasirea sectiunii: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Sectiune> findAll() {
        List<Sectiune> sectiuni = new ArrayList<>();
        String sql = "SELECT * FROM Sectiuni";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sectiune sectiune = new Sectiune(rs.getString("nume"));
                sectiune.setId(rs.getInt("id"));
                sectiuni.add(sectiune);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la listarea sectiunilor: " + e.getMessage());
        }
        return sectiuni;
    }

    @Override
    public void update(Sectiune entity) {
        String sql = "UPDATE Sectiuni SET nume = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setInt(2, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizare: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Sectiuni WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergere: " + e.getMessage());
        }
    }
}