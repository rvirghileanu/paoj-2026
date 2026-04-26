package com.pao.project.biblioteca.service;

import com.pao.project.biblioteca.model.*;
import com.pao.project.biblioteca.exception.*;
import java.util.*;

public class CarteService {
    private static CarteService instance;
    private List<Carte> toateCartile = new ArrayList<>();
    private List<Sectiune> sectiuni = new ArrayList<>();
    private List<Imprumut> imprumuturiActive = new ArrayList<>();

    private CarteService() {}

    public static CarteService getInstance() {
        if (instance == null) instance = new CarteService();
        return instance;
    }

    public void adaugaSectiune(Sectiune s) { sectiuni.add(s); }

    public void adaugaCarte(Carte c, String numeSectiune) throws EntitateNegasitaException {
        Sectiune s = sectiuni.stream()
                .filter(sec -> sec.getNume().equalsIgnoreCase(numeSectiune))
                .findFirst()
                .orElseThrow(() -> new EntitateNegasitaException("Sectiunea nu exista!"));

        s.adaugaCarte(c);
        toateCartile.add(c);
    }

    public Carte cautaDupaTitlu(String titlu) throws EntitateNegasitaException {
        return toateCartile.stream()
                .filter(c -> c.getTitlu().equalsIgnoreCase(titlu))
                .findFirst()
                .orElseThrow(() -> new EntitateNegasitaException("Cartea '" + titlu + "' nu a fost gasita."));
    }

    public void imprumutaCarte(String titlu, Cititor cititor) throws EntitateNegasitaException, ImprumutInvalidException {
        Carte carte = cautaDupaTitlu(titlu);
        if (!carte.isDisponibila()) throw new ImprumutInvalidException("Cartea este deja la altcineva!");

        carte.setDisponibila(false);
        imprumuturiActive.add(new Imprumut(cititor, carte));
    }

    public void returneazaCarte(String titlu) throws EntitateNegasitaException {
        Carte carte = cautaDupaTitlu(titlu);
        carte.setDisponibila(true);
        imprumuturiActive.removeIf(i -> i.getCarte().equals(carte));
    }

    public void listeazaCartiDinSectiune(String numeSectiune) {
        sectiuni.stream()
                .filter(s -> s.getNume().equalsIgnoreCase(numeSectiune))
                .findFirst()
                .ifPresent(s -> s.getCarti().forEach(System.out::println));
    }


    public void stergeCarte(String titlu) throws EntitateNegasitaException {
        Carte carte = cautaDupaTitlu(titlu);


        toateCartile.remove(carte);


        for (Sectiune s : sectiuni) {
            s.getCarti().remove(carte);
        }

        imprumuturiActive.removeIf(i -> i.getCarte().equals(carte));
    }

}