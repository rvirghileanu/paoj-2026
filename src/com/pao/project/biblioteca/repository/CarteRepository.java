package com.pao.project.biblioteca.repository;

import com.pao.project.biblioteca.model.Carte;
import com.pao.project.biblioteca.model.ISBN;
import com.pao.project.biblioteca.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteRepository implements Repository<Carte, String> {
    private final Connection connection;

    public CarteRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Carte entity) {
        String sql = "INSERT INTO Carti (isbn, titlu, autor, disponibila) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIsbn().getCod());
            pstmt.setString(2, entity.getTitlu());
            pstmt.setString(3, entity.getAutor());
            pstmt.setInt(4, entity.isDisponibila() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la salvarea cartii: " + e.getMessage());
        }
    }

    @Override
    public Optional<Carte> findById(String isbnStr) {
        String sql = "SELECT * FROM Carti WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbnStr);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Carte carte = new Carte(
                            rs.getString("titlu"),
                            rs.getString("autor"),
                            new ISBN(rs.getString("isbn"))
                    );
                    carte.setDisponibila(rs.getInt("disponibila") == 1);
                    return Optional.of(carte);
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la gasirea cartii: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Carte> findAll() {
        List<Carte> carti = new ArrayList<>();
        String sql = "SELECT * FROM Carti";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carte carte = new Carte(
                        rs.getString("titlu"),
                        rs.getString("autor"),
                        new ISBN(rs.getString("isbn"))
                );
                carte.setDisponibila(rs.getInt("disponibila") == 1);
                carti.add(carte);
            }
        } catch (SQLException e) {
            System.err.println("Eroare la aducerea listei de carti: " + e.getMessage());
        }
        return carti;
    }

    @Override
    public void update(Carte entity) {
        String sql = "UPDATE Carti SET titlu = ?, autor = ?, disponibila = ? WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getTitlu());
            pstmt.setString(2, entity.getAutor());
            pstmt.setInt(3, entity.isDisponibila() ? 1 : 0);
            pstmt.setString(4, entity.getIsbn().getCod());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la actualizarea cartii: " + e.getMessage());
        }
    }

    @Override
    public void delete(String isbnStr) {
        String sql = "DELETE FROM Carti WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbnStr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergerea cartii: " + e.getMessage());
        }
    }

    // --- INTEROGARE CU JOIN (CERINTA 3 - Partea 3) ---
    public void listeazaCartiCuSectiuneaLor() {
        String sql = "SELECT c.titlu, s.nume as nume_sectiune " +
                "FROM Carti c " +
                "JOIN Sectiuni s ON c.id_sectiune = s.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("--- Carti si Sectiuni ---");
            while (rs.next()) {
                System.out.println("Cartea: " + rs.getString("titlu") + " | Sectiunea: " + rs.getString("nume_sectiune"));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la JOIN carti: " + e.getMessage());
        }
    }

}