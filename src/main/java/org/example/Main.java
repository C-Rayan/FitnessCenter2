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
        //LoginView view = new LoginView(session);
        new ClassView(session);
        Member p1 = new Member("bob@gmail.com", "Bob", "Male", LocalDate.now(), 1000);
        Trainer t = new Trainer("hans@gmail.com","Han", 1);
        Availability av1 = new Availability(t, DayOfWeek.MONDAY, LocalTime.of(9, 20, 30), LocalTime.of(10, 20, 30));
        t.getAvailabilities().add(av1);
        session.beginTransaction();
        session.persist(av1);
        session.persist(p1); session.persist(t);
        session.getTransaction().commit();
        //HealthMetric m1 = new HealthMetric("bob@gmail.con", "1", LocalDate.now(), 150,180, 69);
        //HealthMetric m2 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 70);
        //HealthMetric m3 = new HealthMetric("bob@gmail.con", "2", LocalDate.now(), 150,180, 72);
        //FitnessGoal goal = new FitnessGoal("Gym", 20);

        //p1.addNewGoal(goal);
        //Profile profile = new Profile(p1);
        //profile.getFrame().setVisible(true);
        //new ProfileView(p1);

    }
}