package com.pao.project.biblioteca;

import com.pao.project.biblioteca.model.*;
import com.pao.project.biblioteca.service.*;

public class Main {
    public static void main(String[] args) {
        CarteService carteService = CarteService.getInstance();
        CititorService cititorService = CititorService.getInstance();

        try {
            System.out.println("PROIECT PAOJ BIBLIOTECA - ETAPA 2");
            System.out.println("Virghileanu Maria-Roberta, grupa 233\n");

            // 1. Adauga sectiune
            carteService.adaugaSectiune(new Sectiune("Beletristica"));

            // 2. Adauga carti
            carteService.adaugaCarte(new Carte("Ion", "Liviu Rebreanu", new ISBN("978-606-1")), "Beletristica");
            carteService.adaugaCarte(new Carte("Maitreyi", "Mircea Eliade", new ISBN("978-606-2")), "Beletristica");

            // 3. Inregistreaza cititori
            cititorService.inregistreazaCititor(new Cititor(1, "Popescu Ion", "ion@email.com"));
            cititorService.inregistreazaCititor(new Cititor(2, "Ionescu Ana", "ana@email.com"));

            // 4. Listeaza cititori
            cititorService.listeazaToti();

            // 5. Cauta cititor dupa ID
            Cititor c = cititorService.gasesteDupaId(1);
            System.out.println("\nCititor gasit: " + c.getNume());

            // 6. Imprumuta o carte (Executa TRANZACTIA)
            carteService.imprumutaCarte("Ion", c);
            System.out.println("\n[Tranzactie Executata] Cartea 'Ion' a fost imprumutata cu succes.");

            // 7. Cauta carte dupa titlu
            Carte carteGasita = carteService.cautaDupaTitlu("Ion");
            System.out.println("Status 'Ion' dupa imprumut (disponibila = false): " + carteGasita.isDisponibila());

            // 8. Testam interogarile cu JOIN
            System.out.println();
            carteService.afiseazaToateImprumuturile(); // JOIN Imprumuturi

            System.out.println();
            cititorService.afiseazaStatisticiImprumuturi(); // JOIN Cititori

            System.out.println();
            carteService.listeazaCartiDinSectiune("Beletristica"); // JOIN Carti

            // 9. Returneaza cartea
            carteService.returneazaCarte("Ion");
            System.out.println("\nCartea 'Ion' a fost returnata.");

            // 10. Sterge un cititor
            cititorService.stergeCititor(2);
            System.out.println("Cititorul cu ID 2 a fost eliminat.");

        } catch (Exception e) {
            System.err.println("Eroare in sistem: " + e.getMessage());
        }
    }
}