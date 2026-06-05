package com.ecoenergy;

import java.math.BigDecimal;
import java.util.List;
import com.ecoenergy.dao.EnergyLogDAO;
import com.ecoenergy.model.ConsumptionLevel;
import com.ecoenergy.model.EnergyLog;
import java.util.Date;
/**
 * Hello world!
 *
 */
public class App 
{

    //consumption < 1000 kWh      → LOW
    //1000 >= consumption < 3000  → MODERATE
    //consumption >= 3000 kWh     → HIGH
    public static void main( String[] args )
    {
        EnergyLog log1 = createLog("Human Resources", 500.0, new BigDecimal("0.75"), ConsumptionLevel.LOW);
        EnergyLog log2 = createLog("Data Center", 2500.0, new BigDecimal("0.85"), ConsumptionLevel.MODERATE);
        EnergyLog log3 = createLog("Manufacturing Floor", 8200.5, new BigDecimal("0.90"), ConsumptionLevel.HIGH);
        EnergyLog log4 = createLog("Headquarters", 3100.0, new BigDecimal("0.78"), ConsumptionLevel.HIGH);
        EnergyLogDAO dao = new EnergyLogDAO();
        dao.save(log1);
        dao.save(log2);
        dao.save(log3);
        dao.save(log4);

        EnergyLog logToUpdate = dao.findById(1l);
        logToUpdate.setAppliedRate(new BigDecimal("0.99"));
        dao.update(logToUpdate);

        List<EnergyLog> highLogs = dao.findHighConsumptionLogs();
        for (EnergyLog log : highLogs) {
            System.out.println("Sector: " + log.getSector() + 
                       " | Consumption: " + log.getConsumption() +
                       " | Level: " + log.getConsumptionLevel());
                    }
        
        double average = dao.getAverageConsumptionBySector("Data Center");
        System.out.println("Average: " + average);
        
        List<EnergyLog> aboveTariff = dao.findLogsAboveTariff(0.80);
        for (EnergyLog log : aboveTariff) {
            System.out.println("Sector: " + log.getSector() +
                       " | Tariff: " + log.getAppliedRate());
}
    }

    private static EnergyLog createLog(String sector, double consumption, BigDecimal rate, ConsumptionLevel level) {
    EnergyLog log = new EnergyLog();
    log.setSector(sector);
    log.setReadDate(new Date());
    log.setConsumption(consumption);
    log.setAppliedRate(rate);
    log.setConsumptionLevel(level);
    return log;
}
}
