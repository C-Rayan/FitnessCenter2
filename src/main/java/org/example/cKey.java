package org.example;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class cKey implements Serializable {
    private int id;
    private String email;

    public cKey(int id, String email){
        this.id = id;
        this.email = email;
    }

}
