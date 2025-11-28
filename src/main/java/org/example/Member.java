package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Member extends Person {
    @Id
    private int aid;
    private Date dateOfBirth;
    private ArrayList<FitnessGoal> goals;
    private ArrayList<HealthMetric> metrics;

    public  Member(String email, String name, String gender, Date date, HealthMetric metric){
        super(email, name, gender);
        dateOfBirth = date;
        metrics.add(metric);
    }

    public int getAid() {
        return aid;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void addNewGoal(FitnessGoal goal){
        if(!goals.contains(goal))
            goals.add(goal);
    }
}
