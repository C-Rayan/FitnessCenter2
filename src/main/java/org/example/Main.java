package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.addAnnotatedClass(org.example.Admin.class);
        config.addAnnotatedClass(org.example.Trainer.class);
        config.configure("hibernate.cfg.xml");

        // Begin a transaction, where changes will occur in the database//
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        LoginView view = new LoginView(session);

        Member p1 = new Member("bob@gmail.con", "Bob", "Male", LocalDate.now());
        HealthMetric m1 = new HealthMetric("bob@gmail.con", "1", LocalDate.now(), 150,180, 69);
        HealthMetric m2 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 70);
        HealthMetric m3 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 72);
        FitnessGoal goal = new FitnessGoal("Gym", 20);

        p1.addNewGoal(goal);
        //Profile profile = new Profile(p1);
        //profile.getFrame().setVisible(true);
        new ProfileView(p1);

    }
}