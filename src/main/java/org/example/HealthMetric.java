package org.example;

import java.time.LocalDate;

public class HealthMetric {
    private  String email;
    private String id;
    private LocalDate date;
    private  int height;
    private double heartRate;
    private  double weight;

    public HealthMetric(String email, String id, LocalDate date, int height, double heartRate, double weight){
        this.email = email;
        this.id =id;
        this.date = date;
        this.height = height;
        this.heartRate = heartRate;
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return " id='" + id + '\'' +", date=" + date + ", height=" + height
                +", heartRate=" + heartRate +", weight=" + weight;
    }
}
