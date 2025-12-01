package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Column;

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
    private JComboBox<String> groupComboBox1;
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
        //refresh();
    }

    private  void intializeUI(Member user){
        setTitle("Main Menu");
        setSize(800, 600);
        //setLocationRelativeTo(null);

        //Group tab
        intializeGroup();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Class getClass = session.createQuery("From Class c where c.title = :title", Class.class).setParameter("title", groupComboBox1.getSelectedItem()).uniqueResult();
                if(getClass.getCapacity() <= 0){
                    JOptionPane.showMessageDialog(groupPanel, "Group is full. Pick Another");
                }
                else {
                    user.addClass(getClass);
                    for (int i = 0; i<groupInfoTableModel.getRowCount(); i++){
                        Object val = groupInfoTable.getValueAt(i, 0);
                        if (val.equals(getClass.getTitle())){
                            groupInfoTableModel.removeRow(i);
                            groupComboBox1.removeItem(val);
                            break;
                        }
                    }
                    Transaction transaction = session.beginTransaction();
                    //groupComboBox1.remove(getClass);
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
        this.setVisible(true);
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
        for (int i = 0; i < groupSessions.size(); i++){
            groupInfoTableModel.addRow(groupSessions.get(i).getObj());
        }
        groupComboBox1.removeAllItems();
        for (Class group: groupSessions){
            groupComboBox1.addItem(group.getTitle());
        }
    }

    private List<Class> getGroupSessions(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Class> cq = cb.createQuery(Class.class);
        Root<Class> root = cq.from(Class.class);
//        cq.select(cb.and(
//                cq.where(root.get()),
//                cq.where()
//        ))
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
}
