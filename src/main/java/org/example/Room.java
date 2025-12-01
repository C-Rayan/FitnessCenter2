package org.example;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    private boolean availability;
    private int capacity;

    @OneToOne
    private PTSession session;

    public Room(){
        this.availability = true;
        // default capacity is 30
        this.capacity = 30;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
