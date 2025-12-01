package org.example;

import org.hibernate.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberBooking extends  JFrame {
    private JButton profileBtn;
    private JTabbedPane tabbedPane1;
    private JPanel groupPanel;
    private JPanel ptPanel;
    private JPanel reschedulePanel;
    private JComboBox groupComboBox1;
    private JButton registerButton;
    private JTable groupInfoTable;
    private JComboBox trainerComboBox;
    private JTable trainerSlotsTable;
    private JTable scheduleTable;
    private JPanel mainPanel;
    private DefaultTableModel groupInfoTableModel;
    private TrainerScheduleController controller;

    public MemberBooking(Session session, Member user){
        controller = new TrainerScheduleController(session);
        intializeUI();
        loadData();
        refresh();
    }

    private  void intializeUI(){
        setTitle("Main Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);

        intializeGroup();
        add(mainPanel);
    }

    private void intializeGroup(){
        //Table for sessions
        String[] columns = {"Name", "Date", "Start Time", "End Time", "Book"};
        groupInfoTableModel= new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Book column is editable
            }
        };
        groupInfoTable.setModel(groupInfoTableModel);
    }

    private void loadData(){

    }

}
