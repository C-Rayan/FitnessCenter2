package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.Date;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Member  {
    @Id
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String gender;
    private int pass;
    private LocalDate dateOfBirth;
    private ArrayList<FitnessGoal> goals;

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public int getPass() {
        return pass;
    }

    private ArrayList<HealthMetric> metrics;

    public Member(String email, String name, String gender, LocalDate date, int pass){
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.pass = pass;
        //super(email, name, gender);
        goals = new ArrayList<>();
        metrics = new ArrayList<>();
        dateOfBirth = date;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ArrayList<FitnessGoal> getGoals() {
        return goals;
    }

    public ArrayList<HealthMetric> getMetrics() {
        return metrics;
    }

    public void addNewGoal(FitnessGoal goal){
        if(!goals.contains(goal))
            goals.add(goal);
    }

    public boolean checkLogIn(String pass) {
        if (Integer.toString(this.pass).equals(pass))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }
}
