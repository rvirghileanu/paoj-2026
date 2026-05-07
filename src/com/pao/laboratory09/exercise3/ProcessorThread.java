package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii banda;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            while (activ || !banda.esteGoala()) {
                Tranzactie t;
                synchronized (banda) {
                    while (banda.esteGoala() && activ) {
                        banda.wait();
                    }
                    if (banda.esteGoala() && !activ) break;
                    t = banda.extrage();
                }

                System.out.println("[Processor] Factura #" + t.getId() + " - " + t.getData());
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}