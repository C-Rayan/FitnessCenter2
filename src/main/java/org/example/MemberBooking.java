package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MemberBooking extends  JFrame {
    private JButton profileBtn;
    private JTabbedPane tabbedPane1;
    private JPanel groupPanel;
    private JPanel ptPanel;
    private JPanel reschedulePanel;
    private JComboBox<Class> groupComboBox1;
    private JButton registerButton;
    private JTable groupInfoTable;
    private JComboBox<Trainer> trainerComboBox;
    private JTable trainerSlotsTable;
    private JTable scheduleTable;
    private JPanel mainPanel;
    private DefaultTableModel groupInfoTableModel;
    private Session session;

    public MemberBooking(Session session, Member user){
        Trainer t = new Trainer("hans@gmail.com","Han", 1);
        Availability slot = new Availability(t, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(6));
        Class group1 = new Class("James GUn Party", 2, slot);
        this.session = session;

        intializeUI(user);
        loadData();
        refresh();
    }

    private  void intializeUI(Member user){
        setTitle("Main Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);

        //Group tab
        intializeGroup();
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Class chosenClass = (Class) groupComboBox1.getSelectedItem();
                if(chosenClass.getCapacity() ==0){
                    JOptionPane.showMessageDialog(groupPanel, "Group is full. Pick Another");
                }
                else {
                    user.addClass(chosenClass);
                    Transaction transaction = session.beginTransaction();
                    session.merge(user);
                    transaction.commit();
                }
            }
        });

        //PT tab
        //intializePT();

        //Reschedule tab
        //intializeReschedule();

        add(mainPanel);
    }

    private void intializeGroup(){
        //Table for sessions
        String[] columns = {"Title", "Trainer","Date ", "Start Time", "End Time"};
        groupInfoTableModel= new DefaultTableModel(columns, 0){};
        groupInfoTable.setModel(groupInfoTableModel);
    }

    private void loadData(){
        TrainerRepo repo = new TrainerRepo();
        List<Trainer> trainers = repo.findAllTrainers(session);
        //Group Booking
        List<Class> groupSessions = getGroupSessions();
        groupComboBox1.removeAllItems();
        for (Class group: groupSessions){
            groupComboBox1.addItem(group);
        }
    }

    private List<Class> getGroupSessions(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Class> cq = cb.createQuery(Class.class);
        Root<Class> root = cq.from(Class.class);
        cq.select(root).orderBy(cb.asc(root.get("title")));
        return session.createQuery(cq).getResultList();
    }

    private void refresh(){
        //Refresh Group
        groupInfoTableModel.setRowCount(0);
        List<Class> groupClasses = getGroupSessions();
        for (Class group: groupClasses){
            groupInfoTableModel.addRow(new Object[]{
                    group.getTitle(),
                    group.getTrainer().getName(),
                    group.getTime().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    group.getTime().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            });
        }

    }

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.addAnnotatedClass(org.example.Admin.class);
        config.addAnnotatedClass(org.example.Trainer.class);
        config.addAnnotatedClass(org.example.Availability.class);
        config.addAnnotatedClass(org.example.FitnessGoal.class);
        config.addAnnotatedClass(org.example.HealthMetric.class);
        config.addAnnotatedClass(org.example.Class.class);
        config.addAnnotatedClass(org.example.Room.class);

        config.configure("hibernate.cfg.xml");


        // Begin a transaction, where changes will occur in the database//
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Member jane = new Member("ho@gmail.com", "Jane", "Male", LocalDate.now(), 1);

    }
}
