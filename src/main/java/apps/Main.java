package apps;

import models.*;
import models.Class;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Configuration config = new Configuration();
        // Add all the entity classes
        config.addAnnotatedClass(Member.class);
        config.addAnnotatedClass(Admin.class);
        config.addAnnotatedClass(Trainer.class);
        config.addAnnotatedClass(HealthMetric.class);
        config.addAnnotatedClass(FitnessGoal.class);
        config.addAnnotatedClass(Availability.class);
        config.addAnnotatedClass(Class.class);
        config.addAnnotatedClass(Room.class);
        config.addAnnotatedClass(PTSession.class);
        config.configure("hibernate.cfg.xml");

        // Begin a transaction, where changes will occur in the database//
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        new LoginView(session);

        Member p1 = new Member("bob@gmail.com", "Bob", "Male", LocalDate.now(), 1000);
        Member p2 = new Member("sally@gmail.com", "Sally", "Female", LocalDate.now(), 1002);
        Member p3 = new Member("dallas@gmail.com", "Dallas", "Male", LocalDate.now(), 1002);
        Trainer t1 = new Trainer("hans@gmail.com","Han", 1);
        Trainer t2 = new Trainer("Ron@hotmail.com", "Ronny", 12);
        Trainer t3 = new Trainer("Sam@yahoo.com", "Sam", 1203);
        Admin a1 = new Admin("Holly@yahoo.com", "Holly", 2000);
        Admin a2 = new Admin("Will@wiz.com", "Will", 2003);

        Room r1 = new Room(40);
        Room r2 = new Room(20);
        Room r3 = new Room(60);
        Room r4 = new Room(80);
        session.beginTransaction();
        session.persist(p1);
        session.persist(p2);
        session.persist(p3);
        session.persist(t1);
        session.persist(t2);
        session.persist(t3);
        session.persist(a1);
        session.persist(a2);
        session.persist(r1);
        session.persist(r2);
        session.persist(r3);
        session.persist(r4);
        session.getTransaction().commit();

    }
}