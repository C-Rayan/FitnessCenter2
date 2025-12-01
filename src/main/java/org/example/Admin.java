package org.example;

import jakarta.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private int pass;



    public Admin(String email, String name, int pass) {
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public Admin() {}

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

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean checkLogIn(String pass) {
        if (Integer.toString(this.pass).equals(pass))
            return true;
        return false;
    }
}
