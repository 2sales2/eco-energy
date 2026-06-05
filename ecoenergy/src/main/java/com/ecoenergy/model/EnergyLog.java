package com.ecoenergy.model;

import java.math.BigDecimal;
import com.ecoenergy.model.ConsumptionLevel;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="energy_logs")
public class EnergyLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String sector;

    @Column(nullable=false)
    private Date readDate; 

    @Column(nullable=false)
    private double consumption;

    private BigDecimal appliedRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ConsumptionLevel consumptionLevel;

    public Long getId(){return id; }
    public void setId(Long id) { this.id = id; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public Date getReadDate() { return readDate; }
    public void setReadDate(Date readDate) { this.readDate = readDate; }

    public double getConsumption() { return consumption; }
    public void setConsumption(double consumption) { this.consumption = consumption; }

    public BigDecimal getAppliedRate() { return appliedRate; }
    public void setAppliedRate(BigDecimal appliedRate) { this.appliedRate = appliedRate; }

    public ConsumptionLevel getConsumptionLevel() { return consumptionLevel; }
    public void setConsumptionLevel(ConsumptionLevel consumptionLevel) { this.consumptionLevel = consumptionLevel; }
}