package org.nailservice.service.validator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.nailservice.entity.Order;
import org.nailservice.exception.InvalidOrderTimeException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator implements Validator<Order> {

    private static final int ORDERS_START_TIME = 8;
    private static final int ORDERS_FINISH_TIME = 18;

    @Override
    public void validate(Order order) {

        LocalDateTime inputStart = order.getStart();
        LocalDateTime inputEnd = order.getEnd();
        if (inputStart.getDayOfWeek().equals(DayOfWeek.TUESDAY) || inputStart.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            throw new InvalidOrderTimeException("day off");
        }
        if (inputStart.getHour() < ORDERS_START_TIME || inputStart.getHour() > ORDERS_FINISH_TIME) {
            throw new InvalidOrderTimeException("notworktime");
        }
        if (inputEnd.isBefore(inputStart) || inputEnd.isEqual(inputStart)) {
            throw new InvalidOrderTimeException("endearlierstart");
        }
    }
}
