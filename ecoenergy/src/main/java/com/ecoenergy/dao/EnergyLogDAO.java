package com.ecoenergy.dao;

import com.ecoenergy.model.ConsumptionLevel;
import com.ecoenergy.model.EnergyLog;
import com.ecoenergy.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EnergyLogDAO {

    public void save(EnergyLog log) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(log);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void update(EnergyLog log) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(log);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public List<EnergyLog> findHighConsumptionLogs() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            return entityManager.createQuery(
                "SELECT log FROM EnergyLog log WHERE log.consumptionLevel = :level",
                EnergyLog.class
            ).setParameter("level", ConsumptionLevel.HIGH)
             .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public double getAverageConsumptionBySector(String sector){
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            return entityManager.createQuery(
                "SELECT AVG(log.consumption) as average_by_sector FROM EnergyLog log WHERE log.sector = :sector",
                 Double.class
            ).setParameter("sector", sector).getSingleResult();
        } finally {
            entityManager.close();
        }

    }

    public List<EnergyLog>findLogsAboveTariff(Double tariffLimit){
        EntityManager entityManager = JPAUtil.getEntityManager();
        try{
            return entityManager.createQuery(
                "SELECT log FROM EnergyLog log WHERE log.appliedRate > :limit",
                 EnergyLog.class
            ).setParameter("limit", tariffLimit).getResultList();
        } finally {
            entityManager.close();
        }
    }
    public EnergyLog findById(Long id) {
    EntityManager entityManager = JPAUtil.getEntityManager();
    try {
        return entityManager.find(EnergyLog.class, id);
    } finally {
        entityManager.close();
    }
    }

    
}
