package com.pao.project.biblioteca.model;

public abstract class Persoana {
    protected String nume;
    protected String email;

    public Persoana(String nume, String email) {
        this.nume = nume;
        this.email = email;
    }

    public abstract String getRol(); // Metoda abstracta

    public String getNume() { return nume; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return nume + " (" + email + ")"; }
}