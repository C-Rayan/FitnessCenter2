package org.example;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class hKey implements Serializable {
    private String title;
    private String email;

    public hKey(String title, String email){
        this.title = title;
        this.email = email;
    }

}
