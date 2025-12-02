package models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HealthMetric {
    @Id
    // Weak ID, can't be fully identified by it's own
    private String title;
    private LocalDate date;
    private  int height;
    private double heartRate;
    private  double weight;

    // Needs each member's ID added as another PK column, to create a composite key
    @ManyToOne
    @EmbeddedId
    private Member member;


    public HealthMetric(LocalDate date, int height, double heartRate, double weight, Member member){
        this.date = date;
        this.height = height;
        this.heartRate = heartRate;
        this.weight = weight;
        this.member = member;
    }

    public HealthMetric(String title, int height, int heartRate, int weight, Member member){
        this.title = title;
        this.height = height;
        this.heartRate = heartRate;
        this.weight = weight;
        this.date = LocalDate.now();
        this.member = member;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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
        return  title +", date=" + date + ", height=" + height +", heartRate=" + heartRate + ", weight=" + weight;
    }
}
