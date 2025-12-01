package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
//Putting all the methods needed in one place
public class TrainerRepo {
    public Trainer findById(Session session, int id){
        return  session.find(Trainer.class, id);
    }

    //CriteriaBuilder is basically a class based version of sql queries
    //Instead of " FROM or WHERE" you would use query.from or query.where
    public List<Member> findAllClients(Session session, int trainerId){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        //root is the entity being queried
        //Root<Member> root = cq.from(Member.class);
        //cq.select(root).orderBy(cb.asc(root.get("name")));
        Root<Member> memberRoot = cq.from(Member.class);
        cq.select(memberRoot)
                .where(cb.equal(memberRoot.get("trainer").get("id"), trainerId));
        return session.createQuery(cq).getResultList();
    }

    public  List<Member> findClients(Session session, int trainerId, String name){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> memberRoot = cq.from(Member.class);
        cq.select(memberRoot)
                .where(cb.and(
                        cb.equal(memberRoot.get("trainer").get("id"), trainerId)),
                        (cb.equal(memberRoot.get("member").get("name"),name))
                );
        return session.createQuery(cq).getResultList();
    }

    public void save(Session session, Trainer trainer){
        Transaction transaction = session.beginTransaction();
        for (Member m: trainer.getClients())
            session.persist(m);
        session.persist(trainer);
        transaction.commit();
    }

    public void update(Session session, Trainer trainer){
        Transaction transaction = session.beginTransaction();
        session.merge(trainer);
        transaction.commit();
    }
    
    public void delete(Session session, Trainer trainer){
        Transaction transaction = session.beginTransaction();
        session.remove(trainer);
        transaction.commit();
    }
}
