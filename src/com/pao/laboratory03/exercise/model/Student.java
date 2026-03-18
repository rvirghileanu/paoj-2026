package com.pao.laboratory03.exercise.model;

import com.pao.laboratory03.exceptions.InvalidAgeException;
import com.pao.laboratory03.exercise.exception.InvalidGradeException;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private String name;
    private int age;
    private Map<Subject, Double> grades;

    public Student(String name, int age){
        this.name=name;
        if (age<18 || age >60){
            throw new InvalidAgeException("Varsta invalida a studentului: ");


        }else {
            this.age = age;
        }

        this.grades= new HashMap<>();

    }

    public String getName(){ return name;}
    public int getAge(){ return age;}

    public Map<Subject, Double> getGrades() {
        return grades;
    }

    public void addGrade(Subject subject, double grade){
        if (grade <1 || grade > 10)
            throw new InvalidGradeException("Nota trebuie sa fie intre 1 si 10!");
        grades.put(subject, grade);


    }

    public double getAverage(){
        if (grades.isEmpty()) {
            return 0;
        }
        double sum=0;
        for (double nota: grades.values()) {
            sum = sum + nota;
        }

        return sum/grades.size();

    }
    @Override
    public String toString(){
        return "Student {nume= "+getName()+" , varsta = "+ getAge()+ ", avg= "+ getAverage()+" }";

    }


}





