package org.example;

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
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
    private LocalDate date;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean repeats;

    public  Availability(Trainer trainer, LocalDate date, LocalTime start, LocalTime end){
        //this.id = id;
        this.trainer = trainer;
        this.date = date;
        day = date.getDayOfWeek();
        this.startTime = start;
        this.endTime = end;
        this.repeats = false;
    }

    public Availability(Trainer trainer, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        //this.id = id;
        this.trainer = trainer;
        this.day = day;
        date = LocalDate.now().with(TemporalAdjusters.nextOrSame(day)); // gets the LocalDate of the next occurance of the day
        this.startTime = startTime;
        this.endTime = endTime;
        repeats = true;
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
        return  (slot.getdate() == date) &&(startTime.isBefore(slot.endTime) && endTime.isAfter(slot.startTime));
    }

}
