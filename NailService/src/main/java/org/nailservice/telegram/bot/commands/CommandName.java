package org.nailservice.telegram.bot.commands;

public enum CommandName {
    
    START("/start"),
    CUSTOMERS("/customers"),
    TODAY("/today"),
    ADMIN_TODAY("/admintoday"),
    WEEK("/week"),
    ADMIN_WEEK("/adminweek"),
    MENU("/menu"),
    ADMIN_MENU("/adminmenu");

    private final String name;

    CommandName(String commandName) {
        this.name = commandName;
    }

    public String getCommandName() {
        return name;
    }
}
