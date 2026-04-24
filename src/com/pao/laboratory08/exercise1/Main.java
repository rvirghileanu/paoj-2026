package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;

                String[] date = linie.split(",");
                String nume = date[0].trim();
                int varsta = Integer.parseInt(date[1].trim());
                String oras = date[2].trim();
                String strada = date[3].trim();

                Adresa adresa = new Adresa(oras, strada);
                studenti.add(new Student(nume, varsta, adresa));
            }
        }

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String linieComanda = scanner.nextLine();
        String[] partiComanda = linieComanda.split(" ", 2);
        String comanda = partiComanda[0];

        if (comanda.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else if (comanda.equals("SHALLOW") || comanda.equals("DEEP")) {
            String numeCautat = partiComanda[1];
            Student tinta = null;

            for (Student s : studenti) {
                if (s.getNume().equals(numeCautat)) {
                    tinta = s;
                    break;
                }
            }

            if (tinta != null) {
                Student clona;
                if (comanda.equals("SHALLOW")) {
                    clona = tinta.shallowClone();
                } else {
                    clona = tinta.deepClone();
                }

                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + tinta);
                System.out.println("Clona: " + clona);
            }
        }
    }
}