package com.pao.project.biblioteca.service;

import com.pao.project.biblioteca.model.Cititor;
import com.pao.project.biblioteca.exception.EntitateNegasitaException;
import com.pao.project.biblioteca.repository.CititorRepository;

import java.util.*;

public class CititorService {
    private static CititorService instance;

    // Injectam Repository-ul pentru a lucra cu Baza de Date
    private CititorRepository cititorRepository;
    private AuditService auditService;

    private CititorService() {
        this.cititorRepository = new CititorRepository();
        this.auditService = AuditService.getInstance();
    }

    public static CititorService getInstance() {
        if (instance == null) instance = new CititorService();
        return instance;
    }

    public void inregistreazaCititor(Cititor c) {
        cititorRepository.save(c);
        auditService.logAction("inregistreaza_cititor");
    }

    public void stergeCititor(int id) throws EntitateNegasitaException {
        // Verificam intai daca exista
        Optional<Cititor> c = cititorRepository.findById(id);
        if (c.isEmpty()) {
            throw new EntitateNegasitaException("Cititorul cu ID " + id + " nu exista!");
        }
        cititorRepository.delete(id);
        auditService.logAction("sterge_cititor");
    }

    public Cititor gasesteDupaId(int id) throws EntitateNegasitaException {
        auditService.logAction("cauta_cititor_dupa_id");
        return cititorRepository.findById(id)
                .orElseThrow(() -> new EntitateNegasitaException("Cititorul cu ID " + id + " nu a fost gasit."));
    }

    public void listeazaToti() {
        List<Cititor> cititori = cititorRepository.findAll();

        // Mentinem functionalitatea de sortare (TreeSet workflow) din Etapa 1
        Set<Cititor> cititoriSortati = new TreeSet<>(cititori);

        System.out.println("--- Lista Cititori (Sortata) ---");
        for (Cititor c : cititoriSortati) {
            System.out.println(c);
        }
        auditService.logAction("listeaza_toti_cititorii");
    }

    // Metoda care apeleaza JOIN-ul cerut la evaluare
    public void afiseazaStatisticiImprumuturi() {
        cititorRepository.listeazaCititoriCuNumarImprumuturi();
        auditService.logAction("afiseaza_statistici_cititori");
    }
}