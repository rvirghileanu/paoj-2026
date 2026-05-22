package com.pao.project.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Sectiune {
    private String nume;
    private List<Carte> carti;
    private int id;

    public Sectiune(String nume) {
        this.nume = nume;
        this.carti = new ArrayList<>(); // Cerința 2.2: Utilizare List
    }

    public String getNume() { return nume; }
    public List<Carte> getCarti() { return carti; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void adaugaCarte(Carte c) {
        this.carti.add(c);
    }

    @Override
    public String toString() {
        return "Sectiunea " + nume + " are " + carti.size() + " carti.";
    }
}