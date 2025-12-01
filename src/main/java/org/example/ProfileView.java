package org.example;

import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        metricScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        metricScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        metricList.ensureIndexIsVisible(p.getMetrics().size() - 1);


        JMenuBar options = new JMenuBar();
        this.setJMenuBar(options);
        JMenu changeDetail = new JMenu("Update Personal Details");
        JMenu Metrics = new JMenu("Update Health Metrics");
        JMenu goals = new JMenu("Update Fitness Goals");

        // Create personal data that needs to be changed, only name and gender
        JMenuItem name = new JMenuItem();
        name.setText("Change Name");
        // Doesn't make sense to change date of birth
        JMenuItem DOF = new JMenuItem();
        JMenuItem gender = new JMenuItem();
        gender.setText("Change Gender");
        JMenuItem email = new JMenuItem();
        email.setText("Change Email");

        // Create fitness data that needs to be updated
        JMenuItem addHealthMetric = new JMenuItem(); addHealthMetric.setText("Add a new health metric");
        JMenuItem viewAllMetrics = new JMenuItem(); viewAllMetrics.setText("View all available metrics");
        JMenuItem addFitnessGoal = new JMenuItem(); addFitnessGoal.setText("Add a new fitness goal");
        JMenuItem BodyWeight = new JMenuItem(); BodyWeight.setText("New Bodyweight target");
        JMenuItem BodyFat = new JMenuItem(); BodyFat.setText("New Bodyfat target");

        // Add everything to the MenuBar
        options.add(changeDetail);
        changeDetail.add(name);
        changeDetail.add(gender);
        changeDetail.add(email);

        options.add(Metrics);
        Metrics.add(addHealthMetric);
        Metrics.add(viewAllMetrics);
        Metrics.add(addFitnessGoal);

        name.addActionListener(e ->{
            String newName = JOptionPane.showInputDialog(options, "Enter your new name", "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
            if (newName.length() > 2) {
                nameLbl.setText(newName);
                JOptionPane.showMessageDialog(options, "Your name was successfully updated");
                session.beginTransaction();
                p.setName(newName);
                session.getTransaction().commit();
            }
            else
                JOptionPane.showMessageDialog(options, "Oops, something went wrong, please try again");
        });

        gender.addActionListener(e ->{
            Object[] genderse = {"Male", "Female"};
            int chosen = JOptionPane.showOptionDialog(options, "Please update your gender", "FitnessCenter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, genderse, genderse[0]);
            genderTxt.setText((String) genderse[chosen]);
            session.beginTransaction();
            p.setGender((String) genderse[chosen]);
            session.getTransaction().commit();
        });

        email.addActionListener(e ->{
            String newEmail = JOptionPane.showInputDialog(options, "Enter your new email", "Customized Dialog", JOptionPane.PLAIN_MESSAGE);
            if (newEmail.length() > 2 && newEmail.contains("@") && newEmail.contains(".")) {
                emailTxt.setText(newEmail);
                JOptionPane.showMessageDialog(options, "Your email was successfully updated");
                session.beginTransaction();
                p.setEmail(newEmail);
                session.getTransaction().commit();
            }
            else
                JOptionPane.showMessageDialog(options, "Oops, something went wrong, please try again");
        });



        addFitnessGoal.addActionListener(e -> {
            JPanel contentPane = new JPanel();
            this.setContentPane(contentPane);
            setSize(300, 300);
            JMenuBar returnToMenu = new JMenuBar();
            this.setJMenuBar(returnToMenu);
            JMenu returnt = new JMenu("Return to main menu");
            JMenuItem item = new JMenuItem("Click to leave");
            returnt.add(item);
            this.setResizable(false);

            returnToMenu.add(returnt);
            item.addActionListener(f ->{
                this.setContentPane(ProfilePanel);
                this.setJMenuBar(options);
                this.setResizable(true);
                this.setSize(600, 600);
            });

            JTextField putTitle = new JTextField(20); putTitle.setText("Enter title of your new goal");
            JTextField putCurr = new JTextField(20); putCurr.setText("Enter current status of goal (As a number)");
            JTextField putNew = new JTextField(20); putNew.setText("Enter what you want to achieve (As a number)");
            putTitle.setMaximumSize(putTitle.getPreferredSize());
            putCurr.setMaximumSize(putCurr.getPreferredSize());
            putNew.setMaximumSize(putNew.getPreferredSize());
            JButton button = new JButton();
            button.setText("Append New Goal");
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            putTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            putCurr.setAlignmentX(Component.CENTER_ALIGNMENT);
            putNew.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            contentPane.add(Box.createRigidArea(new Dimension(0, 25)));
            contentPane.add(putTitle);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(putCurr);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(putNew);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(button);

            putTitle.setOpaque(false); putCurr.setOpaque(false); putNew.setOpaque(false);
            JTextField[] texts = {putTitle, putCurr, putNew};
            for (JTextField buttonOf : texts){
                buttonOf.addMouseListener(new MouseListener() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (!buttonOf.isOpaque()) {
                            buttonOf.setOpaque(true);
                            buttonOf.setText("");
                        }
                    }
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {}
                    @Override public void mouseExited(MouseEvent e) {}
                });
            }

            button.addActionListener(f ->{
                try {
                    String title = putTitle.getText();
                    double curr = Double.parseDouble(putCurr.getText());
                    double newer = Double.parseDouble(putNew.getText());
                    p.setNumGoals(p.getNumGoals() + 1);
                    FitnessGoal newGoal = new FitnessGoal(title, curr, newer, p);
                    p.addNewGoal(newGoal);
                    goalList.setListData(p.getGoals().toArray());
                    //System.out.println(newMetric.toString());
                    session.beginTransaction();
                    session.persist(p);
                    session.getTransaction().commit();
                    JOptionPane.showMessageDialog(contentPane, "New goal was successfully added");
                    JSlider progress = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
                    goalList.add(progress);

                    putTitle.setText("Enter title of your new goal"); putTitle.setOpaque(false);
                    putCurr.setText("Enter current status of goal (As a number)"); putCurr.setOpaque(false);
                    putNew.setText("Enter what you want to achieve (As a number)"); putNew.setOpaque(false);
                }
                catch (NumberFormatException r){
                    JOptionPane.showMessageDialog(contentPane, "Please enter all numbers properly");
                }
            });



        });


        addHealthMetric.addActionListener(e ->{
            JPanel contentPane = new JPanel();
            this.setContentPane(contentPane);
            setSize(300, 300);
            JMenuBar returnToMenu = new JMenuBar();
            this.setJMenuBar(returnToMenu);
            JMenu returnt = new JMenu("Return to main menu");
            JMenuItem item = new JMenuItem("Click to leave");
            returnt.add(item);
            this.setResizable(false);

            returnToMenu.add(returnt);
            item.addActionListener(f ->{
                this.setContentPane(ProfilePanel);
                this.setJMenuBar(options);
                this.setResizable(true);
                this.setSize(600, 600);
            });

            JTextField putHeight = new JTextField(20); putHeight.setText("Enter current height (in cm)");
            JTextField putWeight = new JTextField(20); putWeight.setText("Enter current weight (in kg)");
            JTextField putHeartRate = new JTextField(20); putHeartRate.setText("Enter current heart rate");
            putHeight.setMaximumSize(putWeight.getPreferredSize());
            putWeight.setMaximumSize(putWeight.getPreferredSize());
            putHeartRate.setMaximumSize(putWeight.getPreferredSize());
            JButton button = new JButton();
            button.setText("Append Metric");
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            putHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
            putWeight.setAlignmentX(Component.CENTER_ALIGNMENT);
            putHeartRate.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            contentPane.add(Box.createRigidArea(new Dimension(0, 25)));
            contentPane.add(putHeight);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(putWeight);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(putHeartRate);
            contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPane.add(button);

            putHeight.setOpaque(false); putWeight.setOpaque(false); putHeartRate.setOpaque(false);
            JTextField[] texts = {putHeight, putWeight, putHeartRate};

            this.setResizable(false);

            button.addActionListener(f ->{
                try {
                    int height = Integer.parseInt(putHeight.getText());
                    int weight = Integer.parseInt(putWeight.getText());
                    int heartRate = Integer.parseInt(putHeartRate.getText());
                    p.setNumEntry(p.getNumEntry() + 1);
                    HealthMetric newMetric = new HealthMetric("Entry: " + String.valueOf(p.getNumEntry()), Integer.parseInt(putHeight.getText()), Integer.parseInt(putHeartRate.getText()), Integer.parseInt(putWeight.getText()), p);
                    p.addMetric(newMetric);
                    metricList.setListData(p.getMetrics().toArray());
                    System.out.println(newMetric.toString());
                    session.beginTransaction();
                    session.persist(p);
                    session.getTransaction().commit();
                    JOptionPane.showMessageDialog(contentPane, "New metric was successfully added");
                    putHeight.setText("Enter current height (in cm)"); putHeight.setOpaque(false);
                    putWeight.setText("Enter current weight (in kg)"); putWeight.setOpaque(false);
                    putHeartRate.setText("Enter current heart rate"); putHeartRate.setOpaque(false);

                }
                catch (NumberFormatException r){
                    JOptionPane.showMessageDialog(contentPane, "Please enter all numbers");
                }
            });

            for (JTextField buttonOf : texts){
                buttonOf.addMouseListener(new MouseListener() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (!buttonOf.isOpaque()) {
                            buttonOf.setOpaque(true);
                            buttonOf.setText("");
                        }
                    }
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {}
                    @Override public void mouseExited(MouseEvent e) {}
                });
            }
        });

        setVisible(true);
    }
}
