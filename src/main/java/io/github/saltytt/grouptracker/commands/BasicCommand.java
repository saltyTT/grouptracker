package io.github.saltytt.grouptracker.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class BasicCommand {

    public String name;

    public BasicCommand(String name) {
        this.name = name;
    }

    public abstract void execute(MessageReceivedEvent context, String args);
}
