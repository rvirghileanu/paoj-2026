package com.pao.project.biblioteca.service;

import com.pao.project.biblioteca.model.Cititor;
import com.pao.project.biblioteca.exception.EntitateNegasitaException;
import java.util.*;

public class CititorService {
    private static CititorService instance;
    private Set<Cititor> cititori = new TreeSet<>(); // Colectie sortata (cerinta 2.2)
    private Map<Integer, Cititor> indexCititori = new HashMap<>(); // Map pentru indexare (cerinta 2.2)

    private CititorService() {}

    public static CititorService getInstance() {
        if (instance == null) instance = new CititorService();
        return instance;
    }

    public void inregistreazaCititor(Cititor c) {
        cititori.add(c);
        indexCititori.put(c.getId(), c);
    }

    public void stergeCititor(int id) throws EntitateNegasitaException {
        if (!indexCititori.containsKey(id)) {
            throw new EntitateNegasitaException("Cititorul cu ID " + id + " nu exista!");
        }
        Cititor c = indexCititori.get(id);
        cititori.remove(c);
        indexCititori.remove(id);
    }

    public Cititor gasesteDupaId(int id) throws EntitateNegasitaException {
        if (!indexCititori.containsKey(id)) {
            throw new EntitateNegasitaException("Cititorul cu ID " + id + " nu a fost gasit.");
        }
        return indexCititori.get(id);
    }

    public void listeazaToti() {
        for (Cititor c : cititori) System.out.println(c);
    }
}