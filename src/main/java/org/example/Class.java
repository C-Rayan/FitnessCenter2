package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
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

    @OneToOne
    private Room cRoom;

    public Class(String title, int capacity, Availability availability){
        this.title = title;
        this.capacity = capacity;
        this.time = availability;
    }
}
