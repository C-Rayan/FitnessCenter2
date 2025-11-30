package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.CompositeType;

@Entity
public class FitnessGoal {
    private double progress; // as a percentage ( subject to change)
    private double curr;
    private double newer;
    @Id
    private String title;

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
        return "Title='" + title + '\'' +
                ", Current=" + curr +
                ", Goal=" + newer +
                ", Progress=" + progress;
    }
}
