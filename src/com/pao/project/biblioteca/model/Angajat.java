package com.pao.project.biblioteca.model;

public class Angajat extends Persoana {
    protected double salariu;

    public Angajat(String nume, String email, double salariu) {
        super(nume, email);
        this.salariu = salariu;
    }

    @Override
    public String getRol() { return "Angajat General"; }
}