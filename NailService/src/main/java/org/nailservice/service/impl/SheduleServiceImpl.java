package org.nailservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.nailservice.dao.OrderDao;
import org.nailservice.entity.Order;
import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class SheduleServiceImpl implements SheduleService {

    private final OrderDao orderDao;

    @Override
    public Shedule createShedule(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusHours(23);
        List<Order> orders = orderDao.findAllByStartBetweenOrderByStart(start, end);
        return new Shedule(date, orders);
    }

    @Override
    public List<Shedule> createWeekShedule(LocalDate date) {
        List<Order> orders = orderDao.findAllByStartBetweenOrderByStart(date.atStartOfDay(),
                date.plusDays(6).atStartOfDay().plusHours(23));
        return fillWeekShedule(orders, date);
    }

    @Override
    public List<Shedule> createMonthShedule(LocalDate date) {
        Month month = date.getMonth();
        LocalDateTime monthStart = LocalDateTime.of(date.getYear(), month, 1, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(date.getYear(), month, month.maxLength(), 23, 0);
        List<Order> orders = orderDao.findAllByStartBetweenOrderByStart(monthStart, monthEnd);
        return fillMonthShedule(orders, date);
    }

    private List<Shedule> fillWeekShedule(List<Order> orders, LocalDate startDay) {
        List<Shedule> weekShedule = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = startDay.plusDays(i);
            List<Order> dayOrders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getStart().getDayOfMonth() == day.getDayOfMonth()) {
                    dayOrders.add(order);
                }
            }
            if (!dayOrders.isEmpty()) {
                Shedule dayShedule = new Shedule(day, dayOrders);
                weekShedule.add(dayShedule);
            }
        }
        return weekShedule;
    }

    private List<Shedule> fillMonthShedule(List<Order> orders, LocalDate date) {
        List<Shedule> monthShedule = new ArrayList<>();
        Month month = date.getMonth();
        for (int i = 1; i <= month.maxLength(); i++) {
            LocalDate day = LocalDate.of(date.getYear(), month, i);
            List<Order> dayOrders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getStart().getDayOfMonth() == i) {
                    dayOrders.add(order);
                }
            }
            if (!dayOrders.isEmpty()) {
                Shedule dayShedule = new Shedule(day, dayOrders);
                monthShedule.add(dayShedule);
            }
        }
        return monthShedule;
    }
}
