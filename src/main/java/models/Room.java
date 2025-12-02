package models;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    private boolean availability;
    private int capacity;

    //@OneToOne
    //private PTSession session;


    public Room(int capacity){
        this.availability = true;
        // default capacity is 30
        this.capacity = capacity;
    }
    // Default capacity is 30
    public Room(){
        new Room(30);
    }

    public boolean isAvailable() {
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

    @Override
    public String toString() {
        return "rid=" + rid +
                ", capacity=" + capacity;
    }
}
