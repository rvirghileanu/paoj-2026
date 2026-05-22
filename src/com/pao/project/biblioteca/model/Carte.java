package com.pao.project.biblioteca.model;

import java.util.Objects;

public class Carte {
    private String titlu;
    private String autor;
    private ISBN isbn;
    private boolean disponibila = true;
    private int idSectiune;

    public Carte(String titlu, String autor, ISBN isbn) {
        this.titlu = titlu;
        this.autor = autor;
        this.isbn = isbn;
    }

    // Getters si Setters
    public String getTitlu() { return titlu; }
    public boolean isDisponibila() { return disponibila; }
    public void setDisponibila(boolean disponibila) { this.disponibila = disponibila; }
    public String getAutor() { return autor; }
    public ISBN getIsbn() { return isbn; }
    public int getIdSectiune() { return idSectiune; }
    public void setIdSectiune(int idSectiune) { this.idSectiune = idSectiune; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carte)) return false;
        Carte carte = (Carte) o;
        return Objects.equals(isbn.getCod(), carte.isbn.getCod());
    }

    @Override
    public int hashCode() { return Objects.hash(isbn.getCod()); }

    @Override
    public String toString() { return "Carte: " + titlu + " de " + autor; }
}