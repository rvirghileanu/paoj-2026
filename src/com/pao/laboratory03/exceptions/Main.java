package com.pao.laboratory03.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercițiul 3 — Excepții (checked, unchecked, custom)
 *
 * Creează în acest pachet (lângă Main.java) două clase de excepții custom,
 * apoi demonstrează-le aici.
 *
 * PASUL 1 — Creează InvalidAgeException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 2 — Creează DuplicateEntryException.java (fișier separat):
 *   - Extinde RuntimeException (unchecked)
 *   - Constructor cu String message → apelează super(message)
 *
 * PASUL 3 — În acest Main.java, implementează și demonstrează:
 *
 *   a) UNCHECKED EXCEPTIONS — NullPointerException, ArrayIndexOutOfBoundsException:
 *      - Creează o metodă riskyMethod() care aruncă NullPointerException
 *      - Prinde-o cu try-catch, afișează mesajul erorii
 *      - Adaugă un bloc finally care se execută mereu
 *
 *   b) CUSTOM EXCEPTIONS — InvalidAgeException, DuplicateEntryException:
 *      - Creează o metodă validateAge(int age) care aruncă InvalidAgeException
 *        dacă age < 0 sau age > 150
 *      - Creează o metodă addToList(List<String> list, String name) care aruncă
 *        DuplicateEntryException dacă name există deja în listă
 *      - Demonstrează ambele cu try-catch
 *
 *   c) MULTI-CATCH:
 *      - Prinde InvalidAgeException | DuplicateEntryException într-un singur catch
 *
 *   d) CATCH ORDERING:
 *      - Demonstrează că prinderea specifică (InvalidAgeException) trebuie
 *        să fie ÎNAINTE de cea generală (RuntimeException)
 *
 *   e) THROW vs THROWS:
 *      - Creează o metodă cu semnătura: void process(int age) throws InvalidAgeException
 *      - Apeleaz-o din main cu try-catch
 *
 * Output așteptat:
 *
 * === a) Unchecked — NullPointerException ===
 * Prins: Cannot invoke "String.length()" because "s" is null
 * Finally se execută mereu!
 *
 * === b) Custom exceptions ===
 * InvalidAgeException: Vârsta -5 nu este validă (0-150)
 * DuplicateEntryException: 'Ana' există deja în listă
 *
 * === c) Multi-catch ===
 * Excepție prinsă: Vârsta 200 nu este validă (0-150)
 *
 * === d) Catch ordering (specific → general) ===
 * InvalidAgeException prinsă specific: Vârsta -1 nu este validă (0-150)
 *
 * === e) Throw vs throws ===
 * Metoda process() a aruncat: Vârsta 999 nu este validă (0-150)
 */
public class Main {
    public static void main(String[] args) {
        // TODO: implementează pașii de mai sus

        try {
            riskymethod();
        } catch (NullPointerException p) {
            System.out.println("Nu a resusit metoda riskymethod: " + p.getMessage());

        } finally {
            System.out.println("Finally — se execută MEREU, chiar și cu return!");
        }

        try {
            validateAge(200); // invalid
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        List<String> list = new ArrayList<>();
        list.add("Ana");

        try {
            addToList(list, "Ana"); // duplicat
        } catch (DuplicateEntryException e) {
            System.out.println(e.getMessage());
        }

        //multicatch
        try {
            addToList(list, "Ana");
            validateAge(200);
            // duplicat
        } catch (DuplicateEntryException | InvalidAgeException e) {
            System.out.println("Multicatch : "+ e.getMessage());
        }

        //ordering
        try {
            addToList(list, "Ana");
            validateAge(200);
            // duplicat
        } catch (DuplicateEntryException e) {
            System.out.println("Specififc : "+ e.getMessage());

        }catch(RuntimeException e){
            System.out.println("General: "+ e.getMessage());
        }

        //metoda process
        try {
            process(200); // invalid
        } catch (InvalidAgeException e) {
            System.out.println("Metoda process() a aruncat:  " + e.getMessage());
        }

    }


    public static void riskymethod() {

        String s = null;
        int n = s.length();

        // Hint: creează mai întâi InvalidAgeException.java și DuplicateEntryException.java
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Varsta invalida: " + age);
        }
    }

    static void addToList(List<String> list, String name) throws DuplicateEntryException {
        if (list.contains(name)) {
            throw new DuplicateEntryException("Numele există deja: " + name);
        }
        list.add(name);
    }

    static void process(int age){

        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Varsta invalida: " + age);
        }
        System.out.println("Varsta este valida: " + age);

        // Hint: creează mai întâi InvalidAgeException.java și DuplicateEntryException.java
    }
}

