package org.nailservice.telegram.bot.service;

import java.util.List;

import org.nailservice.entity.Order;
import org.nailservice.entity.Shedule;
import org.springframework.stereotype.Component;

@Component
public class TelegramSheduleFormatter {

    public String createSheduleTextForCustomer(Shedule shedule) {
        if (shedule.getOrders().isEmpty()) {
            return createFreeShedule(shedule);
        }
        return createShedule(shedule, false);
    }

    public String createSheduleTextForAdmin(Shedule shedule) {
        if (shedule.getOrders().isEmpty()) {
            return createFreeShedule(shedule);
        }
        return createShedule(shedule, true);
    }

    private String createFreeShedule(Shedule shedule) {
        StringBuilder sheduleText = new StringBuilder();
        sheduleText.append(shedule.getDay().toString()).append("\n");
        sheduleText.append(createFreeLine(10));
        sheduleText.append(createFreeLine(12));
        sheduleText.append(createFreeLine(14));
        sheduleText.append(createFreeLine(17));
        return sheduleText.toString();
    }

    private String createShedule(Shedule shedule, Boolean admin) {
        StringBuilder sheduleText = new StringBuilder();
        sheduleText.append(shedule.getDay().toString()).append("\n");
        sheduleText.append(createSheduleLine(shedule, 9, 11, admin));
        sheduleText.append(createSheduleLine(shedule, 11, 13, admin));
        sheduleText.append(createSheduleLine(shedule, 13, 15, admin));
        sheduleText.append(createSheduleLine(shedule, 16, 19, admin));
        return sheduleText.toString();
    }

    private String createSheduleLine(Shedule shedule, int startLine, int endLine, Boolean admin) {
        List<Order> orders = shedule.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            int orderStart = orders.get(i).getStart().getHour();
            if (orderStart >= startLine && orderStart <= endLine) {
                if (Boolean.TRUE.equals(admin)) {
                    return createBusyLine(orders.get(i).getStart().toLocalTime().toString(),
                            orders.get(i).getCustomer().getName());
                } else {
                    return createBusyLine(orders.get(i).getStart().toLocalTime().toString(), "занято");
                }
            }
        }
        return createFreeLine(startLine + 1);
    }

    private String createFreeLine(int time) {
        return String.format("%s-00 свободно%n", time);
    }

    private String createBusyLine(String time, String name) {
        return String.format("%s %s%n", time, name);
    }
}
