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
    @OneToMany(mappedBy = "trainer")
    private List<Member> clients;

    public Trainer(String email, String name, int pass) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        availabilities = new ArrayList<>();
        clients = new ArrayList<>();
    }
    public Trainer(){}

    //Getters and Setters
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
    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public void setClients(List<Member> clients) {
        this.clients = clients;
    }
    public List<Member> getClients() {
        return clients;
    }

    public void addClient(Member m){
        m.setTrainer(this);
        clients.add(m);
    }
    public boolean checkLogIn(String pass) {
        return Integer.toString(this.pass).equals(pass);
    }
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", email='" + email;
    }

}
