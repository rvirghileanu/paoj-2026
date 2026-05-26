package com.pao.project.biblioteca.repository;

import com.pao.project.biblioteca.model.Imprumut;
import com.pao.project.biblioteca.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImprumutRepository implements Repository<Imprumut, Integer> {
    private final Connection connection;

    public ImprumutRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Imprumut entity) {
        String insertImprumut = "INSERT INTO Imprumuturi (id_cititor, isbn_carte, data_imprumut) VALUES (?, ?, ?)";
        String updateCarte = "UPDATE Carti SET disponibila = 0 WHERE isbn = ?";

        try {
            // DEZACTIVAM AUTO-COMMIT PENTRU TRANZACTIE (CERINTA 2)
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt1 = connection.prepareStatement(insertImprumut, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstmt2 = connection.prepareStatement(updateCarte)) {

                // 1. Inseram imprumutul
                pstmt1.setInt(1, entity.getCititor().getId());
                pstmt1.setString(2, entity.getCarte().getIsbn().getCod());
                pstmt1.setString(3, entity.getDataImprumut().toString());
                pstmt1.executeUpdate();

                // Preluam ID-ul generat
                try (ResultSet rs = pstmt1.getGeneratedKeys()) {
                    if (rs.next()) entity.setId(rs.getInt(1));
                }

                // 2. Actualizam disponibilitatea cartii
                pstmt2.setString(1, entity.getCarte().getIsbn().getCod());
                pstmt2.executeUpdate();

                // DACA TOTUL E OK, DAM COMMIT
                connection.commit();

            } catch (SQLException e) {
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                System.err.println("Eroare la tranzactie (rollback executat): " + e.getMessage());
            } finally {
                try { connection.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- INTEROGARE CU JOIN (CERINTA 3 - Partea 1) ---
    public void afiseazaImprumuturiCuDetalii() {
        String sql = "SELECT i.id, c.nume AS nume_cititor, k.titlu AS titlu_carte, i.data_imprumut " +
                "FROM Imprumuturi i " +
                "JOIN Cititori c ON i.id_cititor = c.id " +
                "JOIN Carti k ON i.isbn_carte = k.isbn";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("--- Lista Imprumuturi Active ---");
            while (rs.next()) {
                System.out.println("ID Imprumut: " + rs.getInt("id") +
                        " | Cititor: " + rs.getString("nume_cititor") +
                        " | Carte: " + rs.getString("titlu_carte") +
                        " | Data: " + rs.getString("data_imprumut"));
            }
        } catch (SQLException e) {
            System.err.println("Eroare la JOIN imprumuturi: " + e.getMessage());
        }
    }

    @Override
    public Optional<Imprumut> findById(Integer id) { return Optional.empty(); /* Simplificat pentru etapa curenta */ }

    @Override
    public List<Imprumut> findAll() { return new ArrayList<>(); /* Simplificat */ }

    @Override
    public void update(Imprumut entity) { /* Simplificat */ }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Imprumuturi WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Eroare la stergere imprumut: " + e.getMessage());
        }
    }
}