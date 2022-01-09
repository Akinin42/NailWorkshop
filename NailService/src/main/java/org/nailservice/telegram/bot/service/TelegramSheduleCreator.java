package org.nailservice.telegram.bot.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nailservice.entity.Order;
import org.nailservice.entity.Shedule;
import org.springframework.stereotype.Component;

@Component
public class TelegramSheduleCreator {

    public Map<String, String> createSheduleTextForCustomer(Shedule shedule) {
        if (shedule.getOrders().isEmpty()) {
            return createFreeShedule(shedule);
        }
        return createShedule(shedule, false);
    }

    public Map<String, String> createSheduleTextForAdmin(Shedule shedule) {
        if (shedule.getOrders().isEmpty()) {
            return createFreeShedule(shedule);
        }
        return createShedule(shedule, true);
    }

    private Map<String, String> createFreeShedule(Shedule shedule) {
        Map<String, String> orders = new TreeMap<>();
        orders.put(createFreeLine(10), "/order");
        orders.put(createFreeLine(12), "/order");
        orders.put(createFreeLine(14), "/order");
        orders.put(createFreeLine(17), "/order");
        return orders;
    }

    private Map<String, String> createShedule(Shedule shedule, Boolean admin) {
        Map<String, String> orders = new TreeMap<>();        
        orders.put(createSheduleLine(shedule, 9, 11, admin), "/order");
        orders.put(createSheduleLine(shedule, 11, 13, admin), "/order");
        orders.put(createSheduleLine(shedule, 13, 15, admin), "/order");
        orders.put(createSheduleLine(shedule, 16, 19, admin), "/order");
        return orders;
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
        return String.format("%s-00 свободно", time);
    }

    private String createBusyLine(String time, String name) {
        return String.format("%s %s", time, name);
    }
}
