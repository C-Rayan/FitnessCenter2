package org.example;

import org.hibernate.Session;

import javax.swing.*;

public class ProfileView extends  JFrame {
    private JLabel nameLbl;
    private JLabel emailLbl;
    private JLabel goalsLbl;
    private JLabel metricsLbl;
    private JLabel emailTxt;
    private JLabel genderlbl;
    private JLabel genderTxt;
    private JLabel dateOfBirthLbl;
    private JLabel dateOfBirthtxt;
    private JPanel ProfilePanel;
    private JScrollPane goalScrollPane;
    private JScrollPane metricScrollPane;

    public ProfileView(Member p, Session session){
        Member currMember = session.find(Member.class,  p.getEmail());
        setContentPane(ProfilePanel);
        setTitle("Profile Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        //Sets the correct labels to the user values
        nameLbl.setText(p.getName());
        emailTxt.setText(p.getEmail());
        genderTxt.setText(p.getGender());
        dateOfBirthtxt.setText(p.getDateOfBirth().toString());

        //Goals and current health metrics
        //DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<Object> goalList = new JList<>(p.getGoals().toArray());
        JList<Object> metricList = new JList<>(p.getMetrics().toArray());
        goalScrollPane.setViewportView(goalList);
        metricScrollPane.setViewportView(metricList);

        JMenuBar options = new JMenuBar();
        this.setJMenuBar(options);
        JMenu changeDetail = new JMenu("Update Personal Details");

        JMenuItem name = new JMenuItem();
        name.setText("Change Name");
        // Doesn't make sense to change date of birth
        JMenuItem DOF = new JMenuItem();
        JMenuItem gender = new JMenuItem();
        gender.setText("Change Gender");
        JMenuItem email = new JMenuItem();
        email.setText("Change Email");

        options.add(changeDetail);
        changeDetail.add(name);
        changeDetail.add(gender);
        changeDetail.add(email);


        name.addActionListener(e ->{
            String newName = JOptionPane.showInputDialog(options, "Enter your new name", "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
            if (newName.length() > 1) {
                nameLbl.setText(newName);
                JOptionPane.showMessageDialog(options, "Your name was successfully updated");
                session.beginTransaction();
                p.setName(newName);
                session.getTransaction().commit();
            }
            else
                JOptionPane.showMessageDialog(options, "Oops, something went wrong, please try again");
        });

        email.addActionListener(e ->{
            String newEmail = JOptionPane.showInputDialog(options, "Enter your new email", "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
            if (newEmail.length() > 1) {
                try{
                    session.beginTransaction();
                    p.setEmail(newEmail);
                    session.getTransaction().commit();
                    JOptionPane.showMessageDialog(options, "Your email was succesfully updated");
                    emailTxt.setText(newEmail);
                }
                catch (jakarta.persistence.EntityExistsException fe){
                    JOptionPane.showMessageDialog(options, "This email already exists, Sorry");
                }
            }
            else
                JOptionPane.showMessageDialog(options, "Oops, something went wrong, please try again");
        });

        gender.addActionListener(e ->{
            Object[] genderse = {"Male", "Female"};
            int chosen = JOptionPane.showOptionDialog(options, "Please update your gender", "FitnessCenter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, genderse, genderse[0]);
            session.beginTransaction();
            p.setGender((String) genderse[chosen]);
            session.getTransaction().commit();
        });

        setVisible(true);
    }
}
