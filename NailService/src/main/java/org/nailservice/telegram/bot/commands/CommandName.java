package org.nailservice.telegram.bot.commands;

public enum CommandName {
    
    START("/start"),
    CUSTOMERS("/customers"),
    TODAY("/today"),
    MY_TODAY("/mytoday"),
    WEEK("/week"),
    MY_WEEK("/myweek");

    private final String name;

    CommandName(String commandName) {
        this.name = commandName;
    }

    public String getCommandName() {
        return name;
    }
}
