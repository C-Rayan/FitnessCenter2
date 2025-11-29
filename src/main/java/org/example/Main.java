package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        /*
        Member alien = new Member();
        alien.setName("Jamal");
        alien.setEmail("Jamal@gmail.com");
        //Testing commit work? a

        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.configure("hibernate.cfg.xml");

        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

       // session.persist(alien);

        transaction.commit();
        */
        Member p1 = new Member("bob@gmail.con", "Bob", "Male", LocalDate.now());
        HealthMetric m1 = new HealthMetric("bob@gmail.con", "1", LocalDate.now(), 150,180, 69);
        HealthMetric m2 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 70);
        HealthMetric m3 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 72);
        FitnessGoal goal = new FitnessGoal("Gym", 20);

        p1.addNewGoal(goal);
        new ProfileView(p1);

    }
}