package org.nailservice.telegram.bot.commands;

public enum CommandName {
    
    START("/start"),
    CUSTOMERS("/customers"),
    TODAY("/today"),
    ADMIN_TODAY("/admintoday"),
    WEEK("/week"),
    ADMIN_WEEK("/adminweek"),
    MENU("/menu"),
    ADMIN_MENU("/adminmenu"),
    MONTH("/month"),
    ADMIN_MONTH("/adminmonth"),
    PRICE("/price"),
    CALENDAR("/calendar");

    private final String name;

    CommandName(String commandName) {
        this.name = commandName;
    }

    public String getCommandName() {
        return name;
    }
}
