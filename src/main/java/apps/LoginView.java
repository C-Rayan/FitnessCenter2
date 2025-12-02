package apps;

import models.Admin;
import models.Member;
import models.Trainer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame  {
    JFrame loginFrame;
    JPanel mainPanel;
    JButton registrationButton;

    public LoginView(Session session) {
        loginFrame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setName("I'm the main panel");
        loginFrame.setTitle("Fitness Center Registration");
        loginFrame.setResizable(false);


        // Create some labels and buttons
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Please enter your email below");
        JLabel passLabel = new JLabel();
        passLabel.setText("Please enter your  pin below");

        JButton registerButton = new JButton();
        JButton loginButton = new JButton();
        loginButton.setText("Press to login");
        registerButton.setText("Become a member today!");

        JTextField putEmail = new JTextField(30);
        JTextField putPass = new JTextField(5);
        putEmail.setMaximumSize(putEmail.getPreferredSize());
        putPass.setMaximumSize(putPass.getPreferredSize());

        // Set the boundaries
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        putEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        putPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 500);


        //  Add everything to the main panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(putEmail);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(putPass);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 130)));
        mainPanel.add(registerButton);
        loginFrame.add(mainPanel);

        mainPanel.setVisible(true);
        loginFrame.setVisible(true);
        System.out.println(loginFrame.getName());
        registerButton.addActionListener(l -> {
            mainPanel.setVisible(false);
            RegistrationView createView = new RegistrationView(session, mainPanel);
            createView.setVisible(true);
            loginFrame.add(createView);
        });

        loginButton.addActionListener(e -> {
            Transaction checkLogin = session.beginTransaction();
            Member checkMember = session.createQuery("From Member tra where tra.email = :email", Member.class).setParameter("email", putEmail.getText()).uniqueResult();
            // Have to use an SQL query to find by email, but use an index to increase lookup time
            Trainer checkTrainer = session.createQuery("From Trainer tra where tra.email = :email", Trainer.class).setParameter("email", putEmail.getText()).uniqueResult();
            Admin checkAdmin = session.createQuery("From Admin tra where tra.email = :email", Admin.class).setParameter("email", putEmail.getText()).uniqueResult();
            boolean loggedIn = false;
            String userType = "";
            // Check if any user with those details exists
            if (checkMember != null){
                loggedIn = checkMember.checkLogIn(String.valueOf(putPass.getText()));
                userType = "Member";
            }
           else if (checkTrainer != null){
                loggedIn = checkTrainer.checkLogIn(String.valueOf(putPass.getText()));
                userType = "Trainer";
           }
           else if (checkAdmin != null){
                loggedIn = checkAdmin.checkLogIn(String.valueOf(putPass.getText()));
                userType = "Admin";
           }

            // Close transaction
            if (loggedIn){
                checkLogin.isComplete();
                if (userType.equals("Member")){
                    loginFrame.dispose();
                    new ProfileView(checkMember,session);
                }
                else if (userType.equals("Trainer")){
                    loginFrame.dispose();
                    new TrainerSchedulerGUI(session, checkTrainer).setVisible(true);
                }
                else if (userType.equals("Admin")) {
                    loginFrame.dispose();
                    new ClassView(session);
                }
            }
        });
    }
}
