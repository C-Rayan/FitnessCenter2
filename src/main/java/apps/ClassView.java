package apps;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import models.Availability;
import models.Class;
import models.Room;
import models.Trainer;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static apps.GeneralController.generalFunctions.beautifyTexts;

public class ClassView extends JFrame {
    private Session session;
    private JScrollPane classScroll;
    private JMenuBar createClass;
    private JMenu addClass;
    private JMenu goToPt;
    private boolean recurringTime;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;
    private JTextField title;
    private JTextField size;
    private AvailabiltyRepo availabiltyRepo;
    private Class myClass;
    private Availability newSlot;
    private JPanel classPanel;
    private JPanel listClass;



    public ClassView(Session session){
        availabiltyRepo = new AvailabiltyRepo();
        this.myClass = myClass;
        this.setResizable(true);
        this.session = session;

        listClass = new JPanel();
        listClass.setLayout(new BoxLayout(listClass, BoxLayout.Y_AXIS));
        classScroll = new JScrollPane(listClass);
        this.setSize(800, 500);
        createClass = new JMenuBar();
        addClass = new JMenu();
        goToPt = new JMenu();
        addClass.setText("Add a new class"); goToPt.setText("View PT sessions");
        createClass.add(addClass);
        createClass.add(goToPt);

        JMenuItem addWeeklyClass = new JMenuItem("Add a new weekly class");
        JMenuItem addOnceOverClass = new JMenuItem("Add a one-time class");
        addClass.add(addWeeklyClass);
        addClass.add(addOnceOverClass);

        this.setJMenuBar(createClass);
        refreshClassTables();
        addWeeklyClass.addActionListener(f -> {
            recurringTime = true;
            createAddClass(addClass);
            addClass.setVisible(false);
        });
        addOnceOverClass.addActionListener(f -> {
            recurringTime = false;
            createAddClass(addClass);
            addClass.setVisible(false);
        });

        this.setVisible(true);
        classScroll.setVisible(true);
    }

    // If there are classes on startup, addThem
    public void refreshClassTables(){
        List<Class> allClasses = getAllClasses();
        for (Class allClass : allClasses) {
            addToGui(allClass);
        }
    }

    // Adds/Defines a new row to the gui with a new class
    public void addToGui(Class newClass) {
        JPanel classRow = new JPanel();
        classRow.setName(String.valueOf(newClass.getCid()));
        classRow.setLayout(new BoxLayout(classRow, BoxLayout.X_AXIS));
        JLabel lTitle = new JLabel("Name: " + newClass.getTitle());
        JLabel lSize = new JLabel("Capacity: " + String.valueOf(newClass.getCapacity()));
        JLabel availL = new JLabel(newClass.getTime().toString());
        JButton addTrainer = new JButton("Assign a trainer to this class");
        JButton addRoom = new JButton("Assign a room to this class");

        classRow.add(lTitle); classRow.add(Box.createHorizontalGlue());
        classRow.add(lSize); classRow.add(Box.createHorizontalGlue());
        classRow.add(availL); classRow.add(Box.createHorizontalGlue());
        classRow.add(addTrainer); classRow.add(Box.createHorizontalGlue());
        classRow.add(addRoom);

        addTrainer.addActionListener(e ->{
            // The name of each row is the ID it corresponds to
            int classID = Integer.parseInt(classRow.getName());
            Class checkClass = session.find(Class.class, classID);
            boolean tSucc = assignTrainer(checkClass);
            if (tSucc) {
                addTrainer.setVisible(false);
                availL.setText(checkClass.getTime().toString());
            }
        });

        addRoom.addActionListener(e ->{
            int classID = Integer.parseInt(classRow.getName());
            Class checkClass = session.find(Class.class, classID);
            boolean rSucc = assignRoom(checkClass);
            if (rSucc) {
                addRoom.setVisible(false);
                JLabel rLbl = new JLabel(checkClass.getRoom().toString());
                classRow.add(rLbl);
            }
        });
        listClass.add(classRow);
    }

    public boolean assignTrainer(Class checkClass) {
        try{
            // Bit of wasted space, but couldn't figure out another way
            ArrayList<Availability> validBilities = new ArrayList<>();
            // Need to get all the trainer's availability slots
            ArrayList<Trainer> validTrainers = new ArrayList<>();
            List<Availability> AVT = getALlTrainsAvails();
            // Go thru each availability that belongs to a trainer
            for (Availability avt : AVT){
                // If the class can fit completely in a trainer's time, then that trainer is a valid choice
                if (avt.isWithin(checkClass.getTime()) && !avt.isReserved()){
                    validTrainers.add(avt.getTrainer());
                    validBilities.add(avt);
                }
            }
            // Long line, but justs shows a dialog that lets you pick a trainer that is available
            Trainer trainer = (Trainer) JOptionPane.showInputDialog(this, "Please choose the preferred available trainers", "Something",  JOptionPane.QUESTION_MESSAGE, null, validTrainers.toArray(), validTrainers.getFirst());
            int index = -1;
            // This is slow, should be changed later on for faster lookup
            for (int i = 0; i < validTrainers.size(); i++){
                    if (validTrainers.get(i).getId() == trainer.getId()) {
                        index = i;
                        break;
                    }
            }
            if (index != -1) {
                session.beginTransaction();
                AVT.get(index).setReserved(true);
                checkClass.setTrainer(trainer);
                checkClass.setTime(AVT.get(index));
                session.getTransaction().commit();
                return true;
            }
            return false;
        }
        // Catch unnacounted for exceptions
        catch (Exception e){
            new JOptionPane("Uh oh, something went wrong :(");
            return false;
        }
    }

    public boolean assignRoom(Class checkClass){
        try{
            ArrayList<Room> availableRooms = (ArrayList<Room>) getAllAvailRooms();
            // Only keep rooms that have enough capacity to accomodate
            for (Room r : availableRooms){
                if (checkClass.getCapacity() > r.getCapacity()){
                    availableRooms.remove(r);
                }
            }
            Room room = (Room) JOptionPane.showInputDialog(this, "Please choose an available room", "Something",  JOptionPane.QUESTION_MESSAGE, null, availableRooms.toArray(), availableRooms.getFirst());
            // Add room and make it unavailable for next person
            session.beginTransaction();
            checkClass.setRoom(room);
            checkClass.getRoom().setAvailability(false);
            session.getTransaction().commit();
            return true;
        }
        // Maybe something went wrong, so don't remove the button
        catch (Exception e){
            return false;
        }
    }


    public void createAddClass(JMenu addclass){
        this.setSize(400, 400);
        this.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        title = new JTextField(15);
        title.setText("Enter class name");
        size = new JTextField(7);
        size.setText("Enter size");
        JButton addButton = new JButton();
        addButton.setText("Add it!");


        title.setMaximumSize(title.getPreferredSize());
        size.setMaximumSize(size.getPreferredSize());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        size.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(title); panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(size); panel.add(Box.createRigidArea(new Dimension(0, 20)));
        if (recurringTime)
            RecurringTimeView(panel);
        else
            onceOverView(panel);

        this.setContentPane(panel);
        goBackToMainMenu();

        JTextField[] texts = {title, size};
        // Just makes text fields look nice, that's it
        beautifyTexts(texts);
    }


    // Add the class if it doesn't overlap any other classes
    public void addClass(){
        if (title.getText().trim().length() <= 8 || title.getText().trim().length() >= 40){
                JOptionPane.showMessageDialog(this, "Please keep your title between 8-40 characters");
        }
        else if (newSlot == null){
            JOptionPane.showMessageDialog(this, "This time overlaps another class, try again");
        }
        else {
            try {
                    // This will still update the PK, even if it didn't add
                    Class newClass = new Class(title.getText().trim(), Integer.parseInt(size.getText().trim()), newSlot);
                    session.beginTransaction();
                    session.persist(newClass);
                    session.getTransaction().commit();
                    title.setText("Enter class name");
                    title.setOpaque(false);
                    size.setText("Enter size");
                    size.setOpaque(false);
                    JOptionPane.showMessageDialog(this, "This class has been succesfully created!");
                    addToGui(newClass);
                } catch (org.hibernate.exception.ConstraintViolationException s) {
                    // Couldn't persist it since its not unique, so revert to previous state
                    session.getTransaction().rollback();
                    JOptionPane.showMessageDialog(this, "You already have this class!");
                } catch (NumberFormatException s) {
                    JOptionPane.showMessageDialog(this, "Please enter a proper size!");
                }
            }
        }


    public void addRecAvailability(DayOfWeek day, LocalTime startTime, LocalTime endTime){
        //Checks that slot is at least 10min
        Duration diff = Duration.between(startTime, endTime);
        if (diff.toMinutes() < 10 || diff.toMinutes() > 180) {
            throw new IllegalArgumentException("Time slot should be at least 10min");
        }
        // Don't allocate trainer initially, only after
        newSlot = new Availability(null, day, startTime, endTime);
        //check for overlaps
        List<Availability> existingSlots = findRepeatingAvailibilityByClassAndDay(day);
        for (Availability current: existingSlots){
            if(newSlot.overlaps(current)) {
                newSlot = null;
            }
        }
        // Don't remvoe this line, doesn't work without
        System.out.println(newSlot == null);
        addClass();
    }

    // Almost the top as same function, except we cross references dates
    public void addOnceAvailability(LocalDate date, LocalTime startTime, LocalTime endTime){
        //Checks that slot is at least 10min
        Duration diff = Duration.between(startTime, endTime);
        if (diff.toMinutes() < 10 || diff.toMinutes() > 180) {
            throw new IllegalArgumentException("Time slot should be at least 10min");
        }
        // Don't allocate trainer initially, only after
        newSlot = new Availability(null, date, startTime, endTime);
        //check for overlaps
        List<Availability> existingSlots = findRepeatingAvailibilityByClassAndDate(date);
        for (Availability current: existingSlots){
            if(newSlot.overlaps(current)) {
                newSlot = null;
            }
        }
        // Don't remvoe this line, doesn't work without
        System.out.println(newSlot == null);
        addClass();
    }


        public void RecurringTimeView(JPanel inputPanel){
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
            JButton addButton = new JButton();
            addButton.setText("Add it!");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputPanel.add(addButton);
            addButton.addActionListener(e ->{
                getRecurringTime(dayComboBox, startTimeSpinner, endTimeSpinner);
            });

        }

        public void getRecurringTime(JComboBox<DayOfWeek> dayComboBox, JSpinner startTimeSpinner, JSpinner endTimeSpinner){
            addRecAvailability(
                    (DayOfWeek) dayComboBox.getSelectedItem(),
                    ((SpinnerDateModel) startTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime(),
                    ((SpinnerDateModel) endTimeSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            );
        }

        // Returns all rows from the availability table that match the day
        public List<Availability> findRepeatingAvailibilityByClassAndDay(DayOfWeek day){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
            Root<Availability> root = cq.from(Availability.class);
            // Gets all rows with the same days and sorts them by start time
            // Make sure that the time belongs to a class, and not a trainer
            cq.where(
                    cb.and(
                            cb.equal(root.get("day"), day),
                            cb.isNull(root.get("trainer"))
                    )
            );
            cq.orderBy(cb.asc(root.get("startTime")));

            return session.createQuery(cq).getResultList();
    }

    public List<Availability> findRepeatingAvailibilityByClassAndDate(LocalDate date){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);
        // Gets all rows with the same days and sorts them by start time
        // Also makes sure that the time belongs to a trainer, and not a class
        cq.where(
                cb.and(
                        cb.equal(root.get("date"), date),
                        cb.isNull(root.get("trainer"))
                )
        );
        cq.orderBy(cb.asc(root.get("startTime")));

        return session.createQuery(cq).getResultList();
    }

    public void onceOverView(JPanel inputPanel){
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
            JButton addButton = new JButton();
            addButton.setText("Add it!");
            addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputPanel.add(addButton);
            addButton.addActionListener(e ->{
                getOnceTime(startDateTimeSpinner, endDateTimeSpinner);
            });
    }

    public void getOnceTime(JSpinner startSpinner, JSpinner endSpinner){
        LocalDateTime spin = ((SpinnerDateModel) startSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDate day = spin.toLocalDate();
        addOnceAvailability(
                day,
                spin.toLocalTime(),
                ((SpinnerDateModel) endSpinner.getModel()).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().toLocalTime()
        );
    }


    public List<Class> getAllClasses(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Class> cq = cb.createQuery(Class.class);
        Root<Class> root = cq.from(Class.class);
        return session.createQuery(cq).getResultList();
    }

    public List<Availability> getALlTrainsAvails(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);
        // SELECT from all availabilities WHERE trainer_id is set
        // This means the availability belongs to a trainer, rather than a class
        cq.select(root).where(cb.isNotNull(root.get("trainer")));
        return session.createQuery(cq).getResultList();
    }

    public List<Room> getAllAvailRooms(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        // SELECT from all availabilities WHERE trainer_id is set
        // This means the availability belongs to a trainer, rather than a class
        cq.select(root).where(cb.isTrue(root.get("availability")));
        System.out.println(session.createQuery(cq).getResultList().size());
        return session.createQuery(cq).getResultList();
    }


    public void goBackToMainMenu(){
        JMenu returnt = new JMenu("Return to main menu");
        JMenuItem item = new JMenuItem("Click to leave");
        returnt.add(item);
        this.getJMenuBar().add(returnt);

        item.addActionListener(f ->{
            this.setContentPane(classScroll);
            addClass.setVisible(true);
            this.setResizable(true);
            this.setSize(800, 500);
            this.getJMenuBar().remove(returnt);
        });
    }
}

