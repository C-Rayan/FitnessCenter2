package org.example;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(indexes = @Index(name = "idx_traemail", columnList = "email"))
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private int pass;
    @OneToMany
    private List<Availability> availabilities;
    //private  List<Availability> singleAvailabilities;
    @OneToMany(mappedBy = "trainer")
    private List<Member> clients;

    public Trainer(String email, String name, int pass) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        availabilities = new ArrayList<>();
        //singleAvailabilities = new ArrayList<>();
    }

    public Trainer(){}


    public String getName() {
        return name;
    }

    public int getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /*
    public List<Member> getClients() {
        return clients;
    }

    public void setClients(List<Member> clients) {
        this.clients = clients;
    }

     */

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", email='" + email;
    }

    public boolean checkLogIn(String pass) {
        if (Integer.toString(this.pass).equals(pass))
            return true;
        return false;
    }

    public List<Member> getClients() {
        return clients;
    }
}
