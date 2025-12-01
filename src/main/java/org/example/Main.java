package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        Configuration config = new Configuration();
        // Add all the entity classes
        config.addAnnotatedClass(org.example.Member.class);
        config.addAnnotatedClass(org.example.Admin.class);
        config.addAnnotatedClass(org.example.Trainer.class);
        config.addAnnotatedClass(org.example.HealthMetric.class);
        config.addAnnotatedClass(org.example.FitnessGoal.class);
        config.addAnnotatedClass(org.example.Availability.class);
        config.addAnnotatedClass(org.example.Class.class);
        config.addAnnotatedClass(org.example.Room.class);
        config.addAnnotatedClass(org.example.PTSession.class);
        config.configure("hibernate.cfg.xml");

        // Begin a transaction, where changes will occur in the database//
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        new LoginView(session);

        /*
        Member p1 = new Member("bob@gmail.com", "Bob", "Male", LocalDate.now(), 1000);
        Member p2 = new Member("sally@gmail.com", "Sally", "Female", LocalDate.now(), 1002);
        Trainer t1 = new Trainer("hans@gmail.com","Han", 1);
        Trainer t2 = new Trainer("Ron@hotmail.com", "Ronny", 12);
        Admin a1 = new Admin("Holly@yahoo.com", "Holly", 2000);
        Admin a2 = new Admin("Will@wiz.com", "Will", 2003);

        Availability av1 = new Availability(t1, DayOfWeek.MONDAY, LocalTime.of(9, 20, 30), LocalTime.of(10, 20, 30));
        Room r1 = new Room(40);
        t1.getAvailabilities().add(av1);*/

    }
}