package com.nbabiy.db;

import com.nbabiy.bot.BotData;
import com.nbabiy.domain.Drink;
import com.nbabiy.domain.Pizza;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DrinkBean {

    @PersistenceUnit(unitName = "first")
    private EntityManagerFactory emf;

    public DrinkBean() {
    }

    public void addDrink(Drink d) {
        EntityManager em = emf.createEntityManager();
        try {
            em.persist(d);
            System.out.println("Drink - "+ d.toString() + " was added.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Drink> getAllDrink(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Drink> tq = em.createNamedQuery("getAllDrink", Drink.class);
        List<Drink> la = tq.getResultList();
        return la;
    }
}
