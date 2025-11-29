package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

// A child of JPanel, so that it can be added to the main frame
public class RegistrationView extends JPanel{
    JButton registrationButton;

    public RegistrationView(Session session, JPanel myPanel){
        JLabel label = new JLabel();
        label.setText("Please fill out the information below");
        label.setBounds(90, 10, 300, 20);

        this.setLayout(null);
        this.add(label);
        JLabel label2 = new JLabel("Enter date of birth here");
        label2.setBounds(100, 150, 200, 30);
        this.add(label2);
        JLabel label3 = new JLabel("Enter first name here");
        label3.setBounds(100, 50, 200, 30);
        this.add(label3);
        JLabel label4 = new JLabel("Enter last name here");
        label4.setBounds(100, 100, 200, 30);
        this.add(label4);
        JLabel label5 = new JLabel("Enter your email here");
        label5.setBounds(100, 200, 200, 30);
        this.add(label5);
        JLabel label6 = new JLabel("Enter your pin (4 numbers)");
        label6.setBounds(100, 250, 250, 30);
        this.add(label6);




        JTextField firstName = new JTextField();
        firstName.setBounds(100, 50, 200, 30);
        JTextField lastName = new JTextField();
        lastName.setBounds(100, 100, 200, 30);
        JTextField dateOfBirth = new JTextField();
        dateOfBirth.setBounds(100, 150, 200, 30);
        JTextField email = new JTextField();
        email.setBounds(100, 200, 200, 30);
        JTextField pass = new JTextField();
        pass.setBounds(100, 250, 200, 30);

        JTextField[] array = {firstName, lastName, dateOfBirth, email, pass};
        JLabel[] arrayL = {label3, label4, label2, label5, label6};
        for (int i = 0; i < array.length; i++){
            int finalI = i;
            array[i].setOpaque(false);
            array[i].addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    array[finalI].setOpaque(true);
                    arrayL[finalI].setVisible(false);
                }

                @Override
                public void keyPressed(KeyEvent e) {}
                @Override
                public void keyReleased(KeyEvent e) {}
            });
        }

        JComboBox<String> genders = new JComboBox<>(new String[]{"Male", "Female"});
        genders.setBounds(100, 300, 200, 30);

        registrationButton = new JButton();
        registrationButton.setBounds(150, 350, 100, 40);
        registrationButton.setText("Register Me!");

        this.add(firstName); this.add(lastName); this.add(dateOfBirth); this.add(email); this.add(genders); this.add(pass); this.add(registrationButton);
        this.setVisible(true);

        registrationButton.addActionListener(e ->{
            String firstNameValue = firstName.getText();
            String lastNameValue = lastName.getText();
            String emailofMe = email.getText();
            String gender = (String) genders.getSelectedItem();
            int pin = 0;
            if (!pass.getText().isBlank())
                try{
                    pin = Integer.parseInt(pass.getText());
                } catch (NumberFormatException ex) {
                    label.setText("You did not input a correct pin");
                }


            try{
                if (firstNameValue.isBlank() || lastNameValue.isBlank() || emailofMe.isBlank() || dateOfBirth.getText().isBlank() || pass.getText().isBlank()){
                    label.setText("You left something blank, please fill all the details");
                }
                else {
                    LocalDate DOF = LocalDate.parse(dateOfBirth.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    Member newMember = new Member(emailofMe, (firstNameValue + lastNameValue), gender, DOF, pin);
                    Transaction transac = session.beginTransaction();
                    session.persist(newMember);
                    transac.commit();
                    this.setVisible(false);
                    // Can get the login Frame using this
                    myPanel.setVisible(true);
                }
            }
            catch (jakarta.persistence.EntityExistsException c){
                System.out.println("This email was already taken, please try another email");
                label.setText("This email was already taken, please try another email");
            }
            catch (org.hibernate.exception.ConstraintViolationException c) {
                System.out.println("Your pin was too long or too short, please try again");
                label.setText("Your pin was too long or too short, please try again");

            }
            catch (DateTimeParseException d){
                label.setText("You wrote the date in the inccorect format");
            }
        });

    }
}
