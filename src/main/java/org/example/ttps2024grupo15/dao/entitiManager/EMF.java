package org.example.ttps2024grupo15.dao.entitiManager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class EMF {
    private static final EntityManagerFactory em =
            Persistence.createEntityManagerFactory("buffet");

    public static EntityManagerFactory getEMF() {
        return em;
    }
}

