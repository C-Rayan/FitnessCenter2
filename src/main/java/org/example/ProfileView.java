package org.example;

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

    public ProfileView(Member p){
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

        setVisible(true);
    }
}
