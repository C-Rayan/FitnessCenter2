package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainerSchedulerGUI extends JFrame {
    private TrainerScheduleController controller;
    private Trainer trainer;
    private JComboBox<Member> memberComboBox;
    private JTabbedPane tabbedPane;
    private JTable recurringTable;
    private JTable oneTimeTable;
    private DefaultTableModel recurringTableModel;
    private DefaultTableModel oneTimeTableModel;

    public TrainerSchedulerGUI(Session session, Trainer trainer) {
        controller = new TrainerScheduleController(session);
        this.trainer = trainer;
        controller.addTrainer(trainer);
        initializeUI();
        loadMembers();
        refreshTables();
    }

    private void initializeUI() {
        setTitle("Schedule Manager");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        //Top panel - Trainer selection ( change to member)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Member:"));
        memberComboBox = new JComboBox<>();
        memberComboBox.addActionListener(e -> refreshTables());
        topPanel.add(memberComboBox);

        JButton addTrainerBtn = new JButton("Add New Client");
        addTrainerBtn.addActionListener(e -> showAddClientDialog());
        topPanel.add(addTrainerBtn);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel - Tabs for different availability types
        tabbedPane = new JTabbedPane();

        // Recurring Availability Tab
        JPanel recurringPanel = createRecurringAvailabilityPanel();
        tabbedPane.addTab("Recurring Availability", recurringPanel);

        // One-Time Availability Tab
        JPanel oneTimePanel = createOneTimeAvailabilityPanel();
        tabbedPane.addTab("One-Time Availability", oneTimePanel);



        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createRecurringAvailabilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table for existing recurring availability
        String[] columns = {"ID", "Day", "Start Time", "End Time", "Actions"};
        recurringTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only actions column is editable
            }
        };

        recurringTable = new JTable(recurringTableModel);
        panel.add(new JScrollPane(recurringTable), BorderLayout.CENTER);

        // Add recurring availability form
        JPanel formPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel(new FlowLayout());
        JComboBox<DayOfWeek> dayComboBox = new JComboBox<>(DayOfWeek.values());
        JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);

        JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);

        inputPanel.add(new JLabel("Day:"));
        inputPanel.add(dayComboBox);
        inputPanel.add(new JLabel("Start Time:"));
        inputPanel.add(startTimeSpinner);
        inputPanel.add(new JLabel("End Time:"));
        inputPanel.add(endTimeSpinner);

        JButton addRecurringBtn = getAddRecurringBtn(dayComboBox, startTimeSpinner, endTimeSpinner);

        formPanel.add(inputPanel);
        formPanel.add(addRecurringBtn);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;

    }

    private JButton getAddRecurringBtn(JComboBox<DayOfWeek> dayComboBox, JSpinner startTimeSpinner, JSpinner endTimeSpinner) {
        JButton addRecurringBtn = new JButton("Add Recurring Availability");
        addRecurringBtn.addActionListener(e -> {
            addRecurringAvailability(
                    (DayOfWeek) dayComboBox.getSelectedItem(),
                    ((SpinnerDateModel) startTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime(),
                    ((SpinnerDateModel) endTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            );
        });
        return addRecurringBtn;
    }

    private JPanel createOneTimeAvailabilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        //Table for existing one-time availability
        String[] columns = {"ID", "Start Date/Time", "End Date/Time", "Actions"};
        oneTimeTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only actions column is editable
            }
        };
        oneTimeTable = new JTable(oneTimeTableModel);
        panel.add(new JScrollPane(oneTimeTable), BorderLayout.CENTER);

        // Add one-time availability form
        JPanel formPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel(new FlowLayout());
        JSpinner startDateTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateTimeEditor = new JSpinner.DateEditor(startDateTimeSpinner, "yyyy-MM-dd HH:mm");
        startDateTimeSpinner.setEditor(startDateTimeEditor);

        JSpinner endDateTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateTimeEditor = new JSpinner.DateEditor(endDateTimeSpinner, "yyyy-MM-dd HH:mm");
        endDateTimeSpinner.setEditor(endDateTimeEditor);

        inputPanel.add(new JLabel("Start Date/Time:"));
        inputPanel.add(startDateTimeSpinner);
        inputPanel.add(new JLabel("End Date/Time:"));
        inputPanel.add(endDateTimeSpinner);

        JButton addOneTimeBtn = new JButton("Add One-Time Availability");

        addOneTimeBtn.addActionListener(e -> {
            addOneTimeAvailability(
                    ((SpinnerDateModel) startDateTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    ((SpinnerDateModel) endDateTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            );
        });



        formPanel.add(inputPanel);
        formPanel.add(addOneTimeBtn);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }


    private void loadMembers() {
        List<Member> members = controller.getAllMembers(trainer.getId());
        memberComboBox.removeAllItems();
        for (Member client : members) {
            memberComboBox.addItem(client);
        }
    }

    private void refreshTables() {
        Member selectedMember = (Member) memberComboBox.getSelectedItem();
        if (selectedMember == null)
            return;

        //Refresh recurring table
        recurringTableModel.setRowCount(0);
        List<Availability> recurringSlots = controller.getAllAvailableSlots(trainer.getId());
        for (Availability slot : recurringSlots) {
            recurringTableModel.addRow(new Object[]{
                    slot.getId(),
                    slot.getdate().toString(),
                    slot.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    slot.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    "Delete"
            });
        }

        //Refresh one-time table
        oneTimeTableModel.setRowCount(0);
        List<Availability> oneTimeSlots = controller.getIndividualAvailability(
                trainer.getId(), LocalDate.now().minusMonths(1));
        for (Availability slot : oneTimeSlots) {
            oneTimeTableModel.addRow(new Object[]{
                    slot.getId(),
                    slot.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    slot.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    "Delete"
            });
        }
        // Add delete button functionality
        addTableDeleteFunctionality();
    }

    private void addTableDeleteFunctionality() {
        // Recurring table delete
        recurringTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        recurringTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), true));

        // One-time table delete
        oneTimeTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        oneTimeTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), false));
    }

    private void addRecurringAvailability(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        try {
            controller.addRecurringAvailability(trainer.getId(), day, startTime, endTime);
            refreshTables();
            JOptionPane.showMessageDialog(this, "Recurring availability added successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
    }

    private void addOneTimeAvailability(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            LocalDate date = startTime.toLocalDate();
            controller.addIndividualAvailability(trainer.getId(), date, startTime.toLocalTime(), endTime.toLocalTime());
            refreshTables();
            JOptionPane.showMessageDialog(this, "One-time availability added successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
    }

    private void showAddClientDialog() {
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                //controller.addClient(nameField.getText(), emailField.getText());
                loadMembers();
                JOptionPane.showMessageDialog(this, "Client added successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isRecurring;

        public ButtonEditor(JCheckBox checkBox, boolean isRecurring) {
            super(checkBox);
            this.isRecurring = isRecurring;
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            JButton button = new JButton(label);
            button.addActionListener(e -> {
                fireEditingStopped();
                int modelRow = table.convertRowIndexToModel(row);
                //deleteAvailability(modelRow);
            });
            return button;
        }

        /*
        private void deleteAvailability(int row) {
            Trainer selectedTrainer = (Trainer) trainerComboBox.getSelectedItem();
            if (selectedTrainer == null) return;

            if (isRecurring) {
                int availabilityId = (Integer) recurringTableModel.getValueAt(row, 0);
                scheduleService.removeRecurringAvailability(availabilityId, selectedTrainer.getId());
            } else {
                int availabilityId = (Integer) oneTimeTableModel.getValueAt(row, 0);
                scheduleService.removeOneTimeAvailability(availabilityId, selectedTrainer.getId());
            }
            refreshTables();
        }

         */
    }

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.addAnnotatedClass(org.example.Member.class);
        config.addAnnotatedClass(org.example.Admin.class);
        config.addAnnotatedClass(org.example.Trainer.class);
        config.addAnnotatedClass(org.example.Availability.class);
        config.configure("hibernate.cfg.xml");

        // Begin a transaction, where changes will occur in the database//
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Trainer t = new Trainer("hans@gmail.com","Han", 1);
        new TrainerSchedulerGUI(session,t).setVisible(true);
    }
}


