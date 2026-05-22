package com.pao.project.biblioteca.service;

import com.pao.project.biblioteca.model.*;
import com.pao.project.biblioteca.exception.*;
import com.pao.project.biblioteca.repository.*;

public class CarteService {
    private static CarteService instance;

    private CarteRepository carteRepository;
    private SectiuneRepository sectiuneRepository;
    private ImprumutRepository imprumutRepository;
    private AuditService auditService;

    private CarteService() {
        this.carteRepository = new CarteRepository();
        this.sectiuneRepository = new SectiuneRepository();
        this.imprumutRepository = new ImprumutRepository();
        this.auditService = AuditService.getInstance();
    }

    public static CarteService getInstance() {
        if (instance == null) instance = new CarteService();
        return instance;
    }

    public void adaugaSectiune(Sectiune s) {
        sectiuneRepository.save(s);
        auditService.logAction("adauga_sectiune");
    }

    public void adaugaCarte(Carte c, String numeSectiune) throws EntitateNegasitaException {
        carteRepository.save(c);
        auditService.logAction("adauga_carte");
    }

    public Carte cautaDupaTitlu(String titlu) throws EntitateNegasitaException {
        auditService.logAction("cauta_carte_dupa_titlu");
        return carteRepository.findAll().stream()
                .filter(c -> c.getTitlu().equalsIgnoreCase(titlu))
                .findFirst()
                .orElseThrow(() -> new EntitateNegasitaException("Cartea '" + titlu + "' nu a fost gasita."));
    }

    public void imprumutaCarte(String titlu, Cititor cititor) throws EntitateNegasitaException, ImprumutInvalidException {
        Carte carte = cautaDupaTitlu(titlu);
        if (!carte.isDisponibila()) throw new ImprumutInvalidException("Cartea este deja la altcineva!");

        Imprumut imprumut = new Imprumut(cititor, carte);
        // Aici se executa tranzactia JDBC definita in Repository (Cerinta 2)
        imprumutRepository.save(imprumut);
        auditService.logAction("imprumuta_carte");
    }

    public void returneazaCarte(String titlu) throws EntitateNegasitaException {
        Carte carte = cautaDupaTitlu(titlu);
        carte.setDisponibila(true);
        carteRepository.update(carte); // Salvam noul status in BD
        auditService.logAction("returneaza_carte");
    }

    public void listeazaCartiDinSectiune(String numeSectiune) {
        // Aici apelam interogarea noastra cu JOIN
        carteRepository.listeazaCartiCuSectiuneaLor();
        auditService.logAction("listeaza_carti_din_sectiune");
    }

    public void afiseazaToateImprumuturile() {
        imprumutRepository.afiseazaImprumuturiCuDetalii();
        auditService.logAction("afiseaza_istoric_imprumuturi");
    }
}