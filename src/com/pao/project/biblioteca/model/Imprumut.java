package com.pao.project.biblioteca.model;

import java.time.LocalDate;

public class Imprumut {
    private Cititor cititor;
    private Carte carte;
    private LocalDate dataImprumut;
    private int id;

    public Imprumut(Cititor cititor, Carte carte) {
        this.cititor = cititor;
        this.carte = carte;
        this.dataImprumut = LocalDate.now();
    }

    public Cititor getCititor() { return cititor; }
    public Carte getCarte() { return carte; }
    public LocalDate getDataImprumut() { return dataImprumut; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Imprumut: " + cititor.getNume() + " a luat '" + carte.getTitlu() + "' la data de " + dataImprumut;
    }
}