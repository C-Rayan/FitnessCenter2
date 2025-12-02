package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.CompositeType;

@Entity
public class FitnessGoal {
    // Title is a weak PK
    @Id
    private String title;
    private double progress; // as a percentage ( subject to change)
    private double curr;
    private double newer;

    public FitnessGoal(){}
    public double getCurr() {
        return curr;
    }

    public double getNewer() {
        return newer;
    }

    public void setNewer(double newer) {
        this.newer = newer;
    }

    public void setCurr(double curr) {
        this.curr = curr;
    }

    // Needs each member's ID added as another PK column, to create a composite key
    @ManyToOne
    @EmbeddedId
    private Member member;

    public  FitnessGoal(String title, double curr, double newer, Member member){
        this.curr = curr;
        this.newer = newer;
        this.title = title;
        this.member = member;
        this.progress = 0;
    }

    public double getProgress() {
        return progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return title + '\'' +
                ", Now=" + curr +
                ", Goal=" + newer +
                ", Progress=" + progress;
    }
}
