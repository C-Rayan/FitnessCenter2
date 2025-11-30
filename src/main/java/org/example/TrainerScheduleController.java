package org.example;

import org.hibernate.Session;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TrainerScheduleController {
    private TrainerRepo trainerRepo;
    private  AvailabiltyRepo availabiltyRepo;
    private Session session;

    public TrainerScheduleController(Session session){
        trainerRepo = new TrainerRepo();
        availabiltyRepo = new AvailabiltyRepo();
        this.session =session;
    }
    public  void addTrainer(Trainer trainer){
        trainerRepo.save(session, trainer);
    }
    public  void addRecurringAvailability(int trainerId, DayOfWeek day, LocalTime startTime, LocalTime endTime){

            Trainer t = trainerRepo.findById(session, trainerId);
            Availability newSlot = new Availability(t, day, startTime, endTime);
            //check for overlaps
            List<Availability> existingSlots = availabiltyRepo.findRepeatingAvailabilityByTrainerAndDay(session, trainerId, day);
            for (Availability current: existingSlots){
                if(newSlot.overlaps(current))
                    throw new IllegalArgumentException("Overlaps with existing recurring slot");
            }
            availabiltyRepo.saveAvailability(session, newSlot);
    }


    public void addIndividualAvailability(int trainerId, LocalDate date, LocalTime startTime, LocalTime endTime){


        Trainer t = trainerRepo.findById(session, trainerId);
        Availability newSlot = new Availability(t, date, startTime, endTime);
        //check for overlaps
        List<Availability> existingSlots = availabiltyRepo.findSingleAvailabilityByTrainerAndDate(session, trainerId, date);
        for (Availability current: existingSlots){
            if(newSlot.overlaps(current))
                throw new IllegalArgumentException("Overlaps with existing recurring slot");
        }
        availabiltyRepo.saveAvailability(session, newSlot);
    }

    public List<Availability> getIndividualAvailability(int trainerId, LocalDate date){
        return availabiltyRepo.findSingleAvailabilityByTrainerAndDate(session, trainerId, date);
    }
    public List<Availability> getAllRepeatingAvailableSlots(int trainerId){
        return  availabiltyRepo.findAllRepeatingAvailabilitiesByTrainer(session, trainerId);
    }

    public  List<Availability> getAllIndividualAvailableSlots(int trainerId){
        return  availabiltyRepo.findAllIndividualAvailabilitiesByTrainer(session,trainerId);
    }

    public List<Availability> getRepeatingAvailability(int trainerId, DayOfWeek day){
        return  availabiltyRepo.findRepeatingAvailabilityByTrainerAndDay(session, trainerId, day);
    }

    public  List<Member> getAllMembers(int trainerId){
        return trainerRepo.findAllClients(session, trainerId );
    }

    public void removeAvailability(Integer availabilityId, int trainerId) {
        // Verify ownership before deletion
        Availability availability = findRecurringById(availabilityId);
        if (availability != null && availability.getTrainer().getId() == trainerId) {
            availabiltyRepo.deleteAvailability(session, availabilityId);
        } else {
            throw new IllegalArgumentException("Availability not found or access denied");
        }
    }

    private  Availability findRecurringById(int id){
        return session.find(Availability.class, id);
    }
}
