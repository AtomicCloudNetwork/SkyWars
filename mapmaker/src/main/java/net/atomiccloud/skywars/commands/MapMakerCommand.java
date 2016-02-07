package net.atomiccloud.skywars.commands;

import org.bukkit.command.CommandExecutor;

public class MapMakerCommand
{
    private String commandName;
    private CommandExecutor executor;

    public MapMakerCommand(String commandName, CommandExecutor executor)
    {
        this.commandName = commandName;
        this.executor = executor;
    }

    public String getName()
    {
        return commandName;
    }

    public CommandExecutor getExecutor()
    {
        return executor;
    }
}
