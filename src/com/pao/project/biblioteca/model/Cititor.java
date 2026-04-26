package com.pao.project.biblioteca.model;

import java.util.Objects;

public class Cititor extends Persoana implements Comparable<Cititor> {
    private int id;

    public Cititor(int id, String nume, String email) {
        super(nume, email);
        this.id = id;
    }

    public int getId() { return id; }

    @Override
    public String getRol() { return "Cititor"; }

    // implementare Comparable pentru sortare (cerinta 2.2)
    @Override
    public int compareTo(Cititor o) {
        return this.nume.compareTo(o.nume);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cititor cititor = (Cititor) o;
        return id == cititor.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Cititor [ID=" + id + ", Nume=" + nume + ", Email=" + email + "]";
    }
}