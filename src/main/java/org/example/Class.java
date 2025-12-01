package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    @Column(unique = true, nullable = false)
    private String title;
    private int capacity;

    @OneToOne(cascade = CascadeType.ALL)
    private Availability time;

    @OneToOne(cascade = CascadeType.ALL)
    private Trainer trainer;

    public String getTitle() {
        return title;
    }

    public int getCid(){
        return cid;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @OneToOne
    private Room cRoom;

    public Class(String title, int capacity, Availability availability){
        this.title = title;
        this.capacity = capacity;
        this.time = availability;
        this.trainer = null;
    }

    public Availability getTime() {
        return time;
    }

    public void setTime(Availability time) {
        this.time = time;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
