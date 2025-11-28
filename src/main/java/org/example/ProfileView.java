package org.example;

import javax.swing.*;

public class ProfileView extends  JFrame {
    private JLabel nameLbl;
    private JLabel emailLbl;
    private JLabel goalsLbl;
    private JLabel metricsLbl;
    private JList list1;
    private JList list2;
    private JLabel emailTxt;
    private JLabel genderlbl;
    private JLabel genderTxt;
    private JLabel dateOfBirthLbl;
    private JLabel dateOfBirthtxt;
    private JPanel ProfilePanel;

    public ProfileView(Member p){
        setContentPane(ProfilePanel);
        setTitle("Profile Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);

        setVisible(true);
    }
}
