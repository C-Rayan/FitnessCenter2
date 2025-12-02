package models;

import jakarta.persistence.*;

@Entity
public class PTSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @Column(unique = true, nullable = false)
    private String title;
    private int capacity;

    @OneToOne(cascade = CascadeType.ALL)
    private Availability time;

    @OneToOne(cascade = CascadeType.ALL)
    private Trainer trainer;

    @OneToOne(cascade =  CascadeType.ALL)
    private Room room;

    public String getTitle() {
        return title;
    }

    public int getCid(){
        return pid;
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

    public PTSession(String title, int capacity, Availability availability){
        this.title = title;
        this.capacity = 1;
        this.time = availability;
        this.trainer = null;
        this.room = null;
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
