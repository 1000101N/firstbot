package com.nbabiy.db;

import com.nbabiy.domain.Pizza;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class PizzaBean {

    @PersistenceContext(unitName = "first")
    private EntityManager em;

    public PizzaBean() {
    }


    public void addPizza(Pizza p) {
//        EntityManager em = emf.createEntityManager();
        try {
            em.persist(p);
            System.out.println("Pizza - " + p.toString() + " was added.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pizza> getAllPizza(){
//        EntityManager em = emf.createEntityManager();
        TypedQuery<Pizza> tq = em.createNamedQuery("getAllPizza", Pizza.class);
        List<Pizza> la = tq.getResultList();
        return la;
    }


}
