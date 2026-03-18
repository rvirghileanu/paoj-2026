package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ("Projramare Avansata pe Obiecte in Java", 5),
    BD("Baze de date", 5),
    SO("Sisteme de Operare", 4),
    RC("Retele de Calculatoare", 5);;
    private String fullName;
    private int credits;

    Subject(String fullName, int credits){
        this.fullName=fullName;
        this.credits=credits;
    }

    public String getfullName(){return fullName;}

    public int getCredits(){return credits;}


    public String toString(Subject s){
        return s.name()+"( "+s.getfullName()+","+ s.getCredits()+" credite)" ;
    }

}



