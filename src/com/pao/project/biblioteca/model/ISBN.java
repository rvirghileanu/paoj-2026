package com.pao.project.biblioteca.model;

public final class ISBN {
    private final String cod;

    public ISBN(String cod) { this.cod = cod; }
    public String getCod() { return cod; }

    @Override
    public String toString() { return cod; }
}