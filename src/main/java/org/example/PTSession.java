package org.example;

import jakarta.persistence.*;

@Entity
public class PTSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String title;

    @OneToOne
    private Room room;

}
