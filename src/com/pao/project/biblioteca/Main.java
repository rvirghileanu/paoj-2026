package com.pao.project.biblioteca;

import com.pao.project.biblioteca.model.*;
import com.pao.project.biblioteca.service.*;

public class Main {
    public static void main(String[] args) {
        // Initializam serviciile Singleton
        CarteService carteService = CarteService.getInstance();
        CititorService cititorService = CititorService.getInstance();

        try {
            System.out.println("PROIECT PAOJ BIBLIOTECA");
            System.out.println("Virghileanu Maria-Roberta, grupa 233");

            // 1. adauga o sectiune noua
            carteService.adaugaSectiune(new Sectiune("Beletristica"));

            // 2. adauga o carte noua
            ISBN isbn1 = new ISBN("978-606-1");
            carteService.adaugaCarte(new Carte("Ion", "Liviu Rebreanu", isbn1), "Beletristica");
            carteService.adaugaCarte(new Carte("Maitreyi", "Mircea Eliade", new ISBN("978-606-2")), "Beletristica");

            // 3. inregistreaza un cititor nou
            cititorService.inregistreazaCititor(new Cititor(1, "Popescu Ion", "ion@email.com"));
            cititorService.inregistreazaCititor(new Cititor(2, "Ionescu Ana", "ana@email.com"));
            cititorService.inregistreazaCititor(new Cititor(3, "Andreescu Dan", "dan@email.com"));

            // 4. afiseaza toti cititorii ordonati alfabetic (TreeSet workflow)
            cititorService.listeazaToti();

            // 5. cauta un cititor dupa ID (Map workflow)
            Cititor c = cititorService.gasesteDupaId(1);
            System.out.println("\nCititor gasit dupa ID: " + c.getNume());

            // 6. cauta o carte dupa titlu
            Carte carteGasita = carteService.cautaDupaTitlu("Ion");
            System.out.println("Carte gasita: " + carteGasita);

            // 7. imprumuta o carte catre un cititor
            carteService.imprumutaCarte("Ion", c);
            System.out.println("Status 'Ion' dupa imprumut: " + carteGasita.isDisponibila());

            // 8. listeaza toate cartile dintr-o sectiune
            carteService.listeazaCartiDinSectiune("Beletristica");

            // 9. returneaza o carte
            carteService.returneazaCarte("Ion");
            System.out.println("\nCartea 'Ion' a fost returnata.");

            // 10. sterge un cititor din sistem
            cititorService.stergeCititor(3);
            System.out.println("Cititorul cu ID 3 a fost eliminat.");
            cititorService.listeazaToti();

        } catch (Exception e) {
            System.err.println("Eroare in sistem: " + e.getMessage());
        }
    }
}