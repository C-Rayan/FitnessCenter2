package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Admin {
    @Id
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
