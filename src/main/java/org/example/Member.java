package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_mememail", columnList = "email"))
public class Member  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String name;
    private String gender;
    private int pass;
    private LocalDate dateOfBirth;
    private int numEntry = 0;
    private int numGoals = 0;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<HealthMetric> metrics;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FitnessGoal> goals;
    @ManyToMany
    private List<GroupClass> groupGroupClasses;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

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
    public Member(){}

    public int getNumGoals() {
        return numGoals;
    }

    public void setNumGoals(int numGoals) {
        this.numGoals = numGoals;
    }

    public void setNumEntry(int numEntry) {
        this.numEntry = numEntry;
    }

    public int getNumEntry() {
        return numEntry;
    }
    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public int getPass() {
        return pass;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<FitnessGoal> getGoals() {
        return goals;
    }

    public List<HealthMetric> getMetrics() {
        return metrics;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setGender(String newGender){
        this.gender = newGender;
    }
    public int getId() {
        return id;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void addNewGoal(FitnessGoal goal){
        if(!goals.contains(goal))
            goals.add(goal);
    }

    public void addMetric(HealthMetric metric){
        this.metrics.add(metric);
    }

    public void addClass(GroupClass groupClass){
        groupGroupClasses.add(groupClass);
        groupClass.addPerson(this);
    }

    public boolean checkLogIn(String pass) {
        if (Integer.toString(this.pass).equals(pass))
            return true;
        return false;
    }


}
