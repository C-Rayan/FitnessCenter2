package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupClass {
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

    @OneToOne(cascade =  CascadeType.ALL)
    private Room room;
    @ManyToMany
    private List<Member> participants;

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

    public GroupClass(String title, int capacity, Availability availability){
        this.title = title;
        this.capacity = capacity;
        this.time = availability;
        this.trainer = null;
        this.room = null;
        participants = new ArrayList<>();
    }

    public  GroupClass(){}

    public void addPerson(Member person){
        participants.add(person);
        capacity--;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
