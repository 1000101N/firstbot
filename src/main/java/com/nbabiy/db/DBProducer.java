package com.nbabiy.db;

import com.nbabiy.bot.BotData;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class DBProducer {

    @Produces
    @PersistenceUnit(unitName = "first")
    @BotData
    private EntityManagerFactory emf;
}
