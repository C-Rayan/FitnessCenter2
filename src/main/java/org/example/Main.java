package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args){
        //Member alien = new Member();
        //alien.setName("Jamal");
        //alien.setEmail("Jamal@gmail.com");
        //Testing commit work? a

        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.configure("hibernate.cfg.xml");

        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

        session.persist(alien);

        transaction.commit();
    }
}