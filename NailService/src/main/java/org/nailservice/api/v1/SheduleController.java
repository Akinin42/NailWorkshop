package org.nailservice.api.v1;

import java.time.LocalDate;
import java.util.List;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/shedules")
public class SheduleController {

    private final SheduleService sheduleService;

    @GetMapping
    public Shedule getShedule() {
        return sheduleService.createShedule(LocalDate.now());
    }

    @GetMapping("/date")
    public Shedule getSheduleOnDay(@RequestParam("localDate") LocalDate date) {
        return sheduleService.createShedule(date);
    }

    @GetMapping("/week")
    public List<Shedule> getWeekShedule() {
        return sheduleService.createWeekShedule(LocalDate.now());
    }

    @GetMapping("/month")
    public List<Shedule> getMonthShedule() {
        return sheduleService.createMonthShedule(LocalDate.now());
    }
}
