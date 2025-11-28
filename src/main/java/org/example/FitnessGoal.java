package org.example;

public class FitnessGoal {
    private double progress; // as a percentage ( subject to change)
    private String title;

    public  FitnessGoal(String title, double progress){
        this.progress =progress;
        this.title = title;
    }

    public double getProgress() {
        return progress;
    }

    public String getTitle() {
        return title;
    }

    public  void update(double num){
        progress += num;
    }
}
