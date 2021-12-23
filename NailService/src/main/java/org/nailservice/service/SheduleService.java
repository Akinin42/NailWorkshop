package org.nailservice.service;

import java.time.LocalDate;
import java.util.List;

import org.nailservice.entity.Shedule;

public interface SheduleService {
    
    Shedule createShedule(LocalDate date);
    
    List<Shedule> createWeekShedule(LocalDate date);
    
    List<Shedule> createMonthShedule(LocalDate date);
}
