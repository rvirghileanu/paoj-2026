package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;


public class StudentService {
    private List<Student> students = new ArrayList<>();

    private StudentService(){
        this.students=  new ArrayList<>();
    }

    private static class Holder {
        private static final StudentService INSTANCE = new StudentService();
    }

    public static StudentService getInstance() {
        return StudentService.Holder.INSTANCE;
    }

    public void addStudent(String name, int age){
        Student s= new Student(name, age);

        for (Student stud: students)
            if (name.equals(stud.getName()))
                throw new RuntimeException();

        students.add(s);


    }

    Student findByName (String nume){
        for (Student s: students){
            if (s.getName().equals(nume)){
                return s;

            }

        }
        throw new StudentNotFoundException("Nu am gasit studentul cu numele"+ nume);
    }

    public void addGrade(String studentName, Subject subject, double grade){
        Student s;

        s=findByName(studentName);
        s.addGrade(subject, grade);

    }
    public void printAllStudents(){
        int cnt=1;
        for (Student s: students){

            System.out.println( cnt+ ". "+s.toString());
            cnt++;
            if(s.getGrades() != null)
                System.out.println(s.getGrades());
            System.out.println();


        }

    }

    public void printTopStudents(){
        TreeMap<String, Double> treesort = new TreeMap<>(Comparator.reverseOrder());

        for (Student s: students){
            treesort.put(s.getName(), s.getAverage());

        }

        System.out.println("Studentii sortati dupa medie: "+ treesort);





    }
    public Map<Subject, Double> getAveragePerSubject(){

        Map<Subject, Double> suma = new HashMap<>();
        Map<Subject,Double> cnt = new HashMap<>();


        for (Student s: students){
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()){
                Subject subject= entry.getKey();
                double grade= entry.getValue();


                suma.put(subject, suma.getOrDefault(subject, 0.0)+grade);
                cnt.put(subject, cnt.getOrDefault(subject, 0.0)+1);



            }

        }

        Map<Subject, Double> note = new HashMap<>();

        for(Subject s: suma.keySet()){
            note.put (s, suma.get(s)/cnt.get(s));

        }

        return note;


    }

}