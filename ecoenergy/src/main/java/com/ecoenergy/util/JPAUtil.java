package com.ecoenergy.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static EntityManagerFactory factory;

    static {
        Map<String, String> props = new HashMap<>();

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url != null) props.put("jakarta.persistence.jdbc.url", url);
        if (user != null) props.put("jakarta.persistence.jdbc.user", user);
        if (password != null) props.put("jakarta.persistence.jdbc.password", password);

        factory = Persistence.createEntityManagerFactory("ecoenergy-pu", props);
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        factory.close();
    }
}