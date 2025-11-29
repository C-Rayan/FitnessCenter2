package org.example;

import java.awt.*;

public class Profile {
    private  Frame frame;
    Profile(Member member){
        frame = new Frame();
        Label nameLbl = new Label(member.getName());

        //Create name label
        nameLbl.setAlignment(Label.CENTER);
        nameLbl.setBackground(Color.BLUE);
        frame.add(nameLbl);

        //Create contact detail things
        Label emailLbl = new Label("Email");
        Label emailTxt = new Label(member.getEmail());
        emailLbl.setBounds(50, 100, 50, 80);
        emailTxt.setBounds(100, 100, 300, 80);
        frame.add(emailLbl);
        frame.add(emailTxt);

        Label genderLbl = new Label("Gender");
        Label genderTxt = new Label(member.getGender());
        genderLbl.setBounds(300, 20, 100, 100);
        genderTxt.setBounds(350, 20, 120, 80);
        frame.add(genderLbl);
        frame.add(genderTxt);

        Label dateOfBirthLbl = new Label("Date of Birth");
        Label dateOfBirthTxt = new Label(member.getDateOfBirth()+"");
        dateOfBirthLbl.setBounds(400, 20, 100, 100);
        dateOfBirthTxt.setBounds(450, 20, 120, 80);
        frame.add(dateOfBirthLbl);
        frame.add(dateOfBirthTxt);

        //Create Goals list
        Label goalsLbl = new Label("Goals");
        List goalList = new List();
        for (FitnessGoal goal: member.getGoals()){
            goalList.add(goal.toString());
        }
        goalsLbl.setBounds(100, 300, 100, 100);
        frame.add(goalsLbl);
        frame.add(goalList);

        //Create Metrics list
        Label metricsLbl = new Label("Metrics");
        List metricsList = new List();
        for (HealthMetric metric: member.getMetrics()){
            metricsList.add(metric.toString());
        }
        metricsLbl.setBounds(100,500, 100, 100);
        frame.setSize(600, 600);
        frame.setLayout(null);

    }

    public Frame getFrame() {
        return frame;
    }
}
