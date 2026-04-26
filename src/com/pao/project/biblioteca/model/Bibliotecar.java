package com.pao.project.biblioteca.model;

public class Bibliotecar extends Angajat {
    private int tura;

    public Bibliotecar(String nume, String email, double salariu, int tura) {
        super(nume, email, salariu);
        this.tura = tura;
    }

    @Override
    public String getRol() { return "Bibliotecar"; }
}