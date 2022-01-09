package org.nailservice.telegram.bot.commands;

public enum CommandName {
    
    START("/start"),
    CUSTOMERS("/customers"),    
    DAY("/day"),
    ADMIN_DAY("/adminday"),
    MENU("/menu"),
    ADMIN_MENU("/adminmenu"),
    PRICE("/price"),
    CALENDAR("/calendar"),
    ADMIN_CALENDAR("/admincalendar");

    private final String name;

    CommandName(String commandName) {
        this.name = commandName;
    }

    public String getCommandName() {
        return name;
    }
}
