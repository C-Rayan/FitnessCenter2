package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

//All the database availability methods
public class AvailabiltyRepo {
    public List<Availability> findRepeatingAvailabilityByTrainerAndDay(Session session, int trainerId, DayOfWeek day){
        //See @TrainerRepo for explanation
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);

        Join<Availability, Trainer> trainerJoin = root.join("trainer"); //SQL JOIN

        cq.where(cb.and(
                cb.equal(trainerJoin.get("id"), trainerId),
                cb.equal(root.get("day"), day)
        ));

        cq.orderBy(cb.asc(root.get("startTime")));

        return session.createQuery(cq).getResultList();
    }

    public List<Availability> findAllRepeatingAvailabilitiesByTrainer(Session session, int trainerId){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);
        //Join statement
        Join<Availability, Trainer> trainerJoin = root.join("trainer");
        //WHERE trainer.id = id AND repeats = false
        cq.where(cb.and
                        (cb.equal(trainerJoin.get("id"), trainerId)),
                cb.equal(root.get("repeats"), true)
                );
        cq.orderBy(cb.asc(root.get("day")), cb.asc(root.get("startTime")));

        return session.createQuery(cq).getResultList();
    }

    public List<Availability> findAllIndividualAvailabilitiesByTrainer(Session session, int trainerId){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);

        Join<Availability, Trainer> trainerJoin = root.join("trainer");
        //WHERE trainer.id = id AND repeats = false
        cq.where(cb.and
                        (cb.equal(trainerJoin.get("id"), trainerId)),
                cb.equal(root.get("repeats"), false)
        );
        //ORDER BY day, startTime
        cq.orderBy(cb.asc(root.get("day")), cb.asc(root.get("startTime")));

        return session.createQuery(cq).getResultList();
    }

    public List<Availability> findSingleAvailabilityByTrainerAndDate(Session session, int trainerId, LocalDate date){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Availability> cq = cb.createQuery(Availability.class);
        Root<Availability> root = cq.from(Availability.class);

        Join<Availability, Trainer> trainerJoin = root.join("trainer");

        cq.where(cb.and(
                cb.equal(trainerJoin.get("id"), trainerId),
                cb.equal(root.get("date"), date)
        ));

        cq.orderBy(cb.asc(root.get("startTime")));

        return session.createQuery(cq).getResultList();
    }


    public void saveAvailability(Session session, Availability availability){
        Transaction transaction = session.beginTransaction();
        session.persist(availability);
        transaction.commit();
    }

    public void deleteAvailability(Session session, int id){
        Transaction transaction = session.beginTransaction();
        Availability slot = session.find(Availability.class, id);
        if (slot !=null)
            session.remove(slot);
        transaction.commit();
    }
}
