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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MemberBooking extends  JFrame {
    private JButton profileBtn;
    private JTabbedPane tabbedPane1;
    private JPanel groupPanel;
    private JPanel ptPanel;
    private JPanel reschedulePanel;
    private JComboBox<GroupClass> groupComboBox1;
    private JButton registerButton;
    private JTable groupInfoTable;
    private JComboBox<Trainer> trainerComboBox;
    private JTable trainerSlotsTable;
    private JTable scheduleTable;
    private JPanel mainPanel;
    private DefaultTableModel groupInfoTableModel;
    private Session session;

    public MemberBooking(Session session, Member user){
        this.session = session;

        intializeUI(user);
        loadData();
        refresh();
    }

    private  void intializeUI(Member user){
        setTitle("Main Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Group tab
        intializeGroup();
        //Button listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupClass chosenGroupClass = (GroupClass) groupComboBox1.getSelectedItem();
                if(chosenGroupClass.getCapacity() ==0){
                    JOptionPane.showMessageDialog(groupPanel, "Group is full. Pick Another");
                }
                else {
                    user.addClass(chosenGroupClass);
                    Transaction transaction = session.beginTransaction();
                    session.merge(user);
                    transaction.commit();
                }
            }
        });

        profileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileView(user, session).setVisible(true);
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
        List<GroupClass> groupSessions = getGroupSessions();
        groupComboBox1.removeAllItems();
        for (GroupClass group: groupSessions){
            groupComboBox1.addItem(group);
        }
    }

    private List<GroupClass> getGroupSessions(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GroupClass> cq = cb.createQuery(GroupClass.class);
        Root<GroupClass> root = cq.from(GroupClass.class);
        cq.select(root).orderBy(cb.asc(root.get("title")));
        return session.createQuery(cq).getResultList();
    }

    private void refresh(){
        //Refresh Group
        groupInfoTableModel.setRowCount(0);
        List<GroupClass> groupGroupClasses = getGroupSessions();
        for (GroupClass group: groupGroupClasses){
            groupInfoTableModel.addRow(new Object[]{
                    group.getTitle(),
                    group.getTrainer().getName(),
                    group.getTime().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    group.getTime().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            });
        }

    }
}
