package com.nbabiy.db;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DBProducer {

    @Produces
    @PersistenceContext(name = "first_bot")
    private EntityManager em;
}
