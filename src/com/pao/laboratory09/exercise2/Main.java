package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        File outputDir = new File("output");
        if (!outputDir.exists()) outputDir.mkdirs();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                String tipStr = sc.next();

                byte[] record = new byte[RECORD_SIZE];
                ByteBuffer bb = ByteBuffer.wrap(record).order(ByteOrder.LITTLE_ENDIAN);

                bb.putInt(id);
                bb.putDouble(suma);

                byte[] dataBytes = data.getBytes();
                for (int j = 0; j < 10; j++) {
                    bb.put(j + 12, (j < dataBytes.length) ? dataBytes[j] : (byte) ' ');
                }

                bb.put(22, (byte) (TipTranzactie.valueOf(tipStr) == TipTranzactie.CREDIT ? 0 : 1));
                bb.put(23, (byte) 0); // PENDING

                dos.write(record);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (sc.hasNext()) {
                String cmd = sc.next();
                if (cmd.equals("READ")) {
                    int idx = sc.nextInt();
                    printRecord(raf, idx);
                } else if (cmd.equals("UPDATE")) {
                    int idx = sc.nextInt();
                    String statusStr = sc.next();
                    byte statusByte = (byte) (statusStr.equals("PENDING") ? 0 : statusStr.equals("PROCESSED") ? 1 : 2);
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                } else if (cmd.equals("PRINT_ALL")) {
                    long numRecords = raf.length() / RECORD_SIZE;
                    for (int i = 0; i < numRecords; i++) {
                        printRecord(raf, i);
                    }
                }
            }
        }
    }

    private static void printRecord(RandomAccessFile raf, int idx) throws IOException {
        byte[] buffer = new byte[RECORD_SIZE];
        raf.seek((long) idx * RECORD_SIZE);
        raf.readFully(buffer);

        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
        int id = bb.getInt(0);
        double suma = bb.getDouble(4);

        byte[] dataBytes = new byte[10];
        bb.position(12);
        bb.get(dataBytes);
        String data = new String(dataBytes).trim();

        int tipByte = bb.get(22);
        String tip = (tipByte == 0) ? "CREDIT" : "DEBIT";

        int statusByte = bb.get(23);
        String status = (statusByte == 0) ? "PENDING" : (statusByte == 1) ? "PROCESSED" : "REJECTED";

        System.out.printf(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, status);
    }
}