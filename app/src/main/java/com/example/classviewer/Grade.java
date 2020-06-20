package com.example.classviewer;

import java.util.HashMap;

public class Grade {
    String subject;

    public Grade() {
        this.grades = new HashMap<String,String>();
    }

    HashMap<String,String> grades;


    @Override
    public String toString() {
        return "Grade{" +
                "subject='" + subject + '\'' +
                ", grades=" + grades +
                '}';
    }

    public void addGrade(String title, String value){
        grades.put(title,value);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<String, String> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<String, String> grades) {
        this.grades = grades;
    }

    public void addSubject(String value){
        this.subject = value;
    }
}
