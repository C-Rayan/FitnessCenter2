package models;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Entity
public class Availability { //Possible entity class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    //@JoinColumn(name = "trainer_id", nullable = false)
    @JoinColumn(name = "trainer_id", nullable = true)
    private Trainer trainer;
    private LocalDate date;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean repeats;
    private boolean reserved;

    public Availability(){}
    public Availability(Trainer trainer, LocalDate date, LocalTime start, LocalTime end){
        //this.id = id;
        this.trainer = trainer;
        this.date = date;
        day = date.getDayOfWeek();
        this.startTime = start;
        this.endTime = end;
        this.repeats = false;
        this.reserved = false;
    }

    public Availability(Trainer trainer, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        //this.id = id;
        this.trainer = trainer;
        this.day = day;
        date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day)); // gets the LocalDate of the next occurance of the day
        this.startTime = startTime;
        this.endTime = endTime;
        repeats = true;
        this.reserved = false;
    }


    //Getters
    public int getId() {
        return id;
    }
    public Trainer getTrainer() {
        return trainer;
    }
    public LocalDate getdate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isRepeats() {
        return repeats;
    }
    public void setRepeats(boolean repeats) {
        this.repeats = repeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setdate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean overlaps(Availability slot){
        return  (slot.getdate() == date || slot.getDay() == day) &&(startTime.isBefore(slot.endTime) && endTime.isAfter(slot.startTime));
    }

    public DayOfWeek getDay() {
        return day;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isWithin(Availability aClass){
        // Checks if the class interval is inside the trainer's allowed time
        return (aClass.getdate() == date || aClass.getDay() == day) && (!aClass.startTime.isBefore(startTime) && !aClass.endTime.isAfter(endTime));
    }

    @Override
    public String toString() {
        return "Trainer=" + trainer +
                ", date=" + date +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime;
    }
}
