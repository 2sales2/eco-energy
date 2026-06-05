package com.ecoenergy.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    // Singleton
    private static EntityManagerFactory factory = 
        Persistence.createEntityManagerFactory("ecoenergy-pu");


    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close(){factory.close();}
}