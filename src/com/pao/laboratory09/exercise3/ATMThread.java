package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private final int idATM;
    private final CoadaTranzactii banda;

    public ATMThread(int idATM, CoadaTranzactii banda) {
        this.idATM = idATM;
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int idTranzactie = idATM * 100 + i;
                Tranzactie t = new Tranzactie(idTranzactie, 100.0 * i, "2024-05-08", "Cont-ATM-" + idATM, "Cont-Dest", TipTranzactie.CREDIT);

                banda.adauga(t, idATM);
                System.out.println("[ATM-" + idATM + "] trimite: Tranzactie #" + idTranzactie + " " + (100.0 * i) + " RON");

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}