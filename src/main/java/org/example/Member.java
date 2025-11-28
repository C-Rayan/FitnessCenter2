package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Member extends Person {
    private LocalDate dateOfBirth;
    private ArrayList<FitnessGoal> goals;
    private ArrayList<HealthMetric> metrics;

    public  Member(String email, String name, String gender, LocalDate date){
        super(email, name, gender);
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
}
