package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        List<Tranzactie> listaInitiala = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            String src = sc.next();
            String dest = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());

            Tranzactie t = new Tranzactie(id, suma, data, src, dest, tip);
            t.setNote("procesat");
            listaInitiala.add(t);
        }

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(listaInitiala);
        }

        List<Tranzactie> listaDeserializata;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            listaDeserializata = (List<Tranzactie>) ois.readObject();
        }

        while (sc.hasNext()) {
            String comanda = sc.next();
            switch (comanda) {
                case "LIST":
                    for (Tranzactie t : listaDeserializata) {
                        System.out.println(t);
                    }
                    break;
                case "FILTER":
                    String prefix = sc.next();
                    boolean gasitFilter = false;
                    for (Tranzactie t : listaDeserializata) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            gasitFilter = true;
                        }
                    }
                    if (!gasitFilter) {
                        System.out.println("Niciun rezultat.");
                    }
                    break;
                case "NOTE":
                    if (sc.hasNextInt()) {
                        int idCautat = sc.nextInt();
                        boolean gasitNote = false;
                        for (Tranzactie t : listaDeserializata) {
                            if (t.getId() == idCautat) {
                                System.out.println("NOTE[" + idCautat + "]: " + t.getNote());
                                gasitNote = true;
                                break;
                            }
                        }
                        if (!gasitNote) {
                            System.out.println("NOTE[" + idCautat + "]: not found");
                        }
                    }
                    break;
            }
        }
    }
}