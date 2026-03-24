package com.pao.laboratory05.angajati;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine(); // consumă newline-ul rămas

            if (optiune == 1) {
                System.out.print("Nume: ");
                String nume = scanner.nextLine();
                System.out.print("Departament (nume): ");
                String numeDept = scanner.nextLine();
                System.out.print("Departament (locatie): ");
                String locatie = scanner.nextLine();
                System.out.print("Salariu: ");
                double salariu = scanner.nextDouble();

                service.addAngajat(new Angajat(nume, new Departament(numeDept, locatie), salariu));
            } else if (optiune == 2) {
                service.listBySalary();
            } else if (optiune == 3) {
                System.out.print("Departament: ");
                String dept = scanner.nextLine();
                service.findByDepartament(dept);
            } else if (optiune == 0) {
                System.out.println("La revedere!");
                break;
            }
        }
        scanner.close();
    }
}
