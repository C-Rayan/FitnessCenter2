package org.example;

import java.util.Date;

public class HealthMetric {
    private  String email;
    private String id;
    private Date date;
    private  int height;

    public HealthMetric(String email, String id, Date date, int height){
        this.email = email;
        this.id =id;
        this.date = date;
        this.height = height;
    }
    public String getEmail() {
        return email;
    }
    public Date getDate() {
        return date;
    }
    public int getHeight() {
        return height;
    }
}
