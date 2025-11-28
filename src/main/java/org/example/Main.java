package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.addAnnotatedClass(org.example.Admin.class);
        config.addAnnotatedClass(org.example.Trainer.class);
        config.configure("hibernate.cfg.xml");

        // Begin a transaction, where changes will occur in the database
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        transaction.isComplete();
        LoginView view = new LoginView(session);



       /* int input = 2;
        String userType = null;
        boolean loggedIn = false;
        while (!loggedIn){
            switch(input){
                case 0:
                    System.out.println("Please enter your username and password (seperated by whitespace) to log in");
                    Scanner login = new Scanner(System.in);
                    String email = login.next().trim();
                    String password = login.next().trim();
                    Transaction checkLogin = session.beginTransaction();
                    Member checkMember = session.find(Member.class, email);
                    Trainer checkTrainer = session.find(Trainer.class, email);
                    Admin checkAdmin = session.find(Admin.class, email);

                    // Check if any user with those details exists
                    if (checkMember != null)
                        loggedIn = checkMember.checkLogIn(password); userType = "Member";
                    if (checkTrainer != null)
                        loggedIn = checkTrainer.checkLogIn(password); userType = "Trainer";
                    if (checkAdmin != null)
                        loggedIn = checkAdmin.checkLogIn(password); userType = "Admin";

                    // Close transaction
                    if (loggedIn){
                        checkLogin.isComplete();
                            break;
                    }
                    else{
                        System.out.println("Maybe you entered the username or email wrong?");
                    }

                    break;

                // Case for adding a member
                case 1:
//                    System.out.println("Please enter your name");
//                    Scanner details = new Scanner(System.in);
//                    String name = details.nextLine().trim();
//                    System.out.println("Please enter your password (must be 4 digits)");
//                    int pass = 0;
//                    while (!details.hasNextInt()){
//                        System.out.println("Please input a 4 number password");
//                        details.nextLine();
//                    }
//                    pass = details.nextInt();
//                    System.out.println("Please enter your email");
//                    String emaill = details.next();

                    // Have to try this somehow
                   // Member newMember = new Member(emaill, name, pass);
                    Transaction addUser = session.beginTransaction();
                    try{
                     //   session.persist(newMember);
                        addUser.commit();
                    }
                    catch (org.hibernate.exception.ConstraintViolationException e) {
                        System.out.println("Your pin was too long or too short, please try again");
                    }
                    catch (jakarta.persistence.EntityExistsException e){
                        System.out.println("This email was already taken, please try another email");
                    }

                    input = 2;
                    break;

                case 2:
                    System.out.println("What would you like to do today");
                    System.out.println("-------------------------------");
                    System.out.println("Press 0 to proceed to the login page");
                    System.out.println("Press 1 if you would like to register a new account");
                    Scanner checkInput = new Scanner(System.in);
                    if (checkInput.hasNextInt()){
                        input = checkInput.nextInt();
                    }
                    else{
                        System.out.println("Please enter 0 or 1");
                    }
                    break;
                default:
                    input = 2;
                    break;
            }
        }*/
    }
}