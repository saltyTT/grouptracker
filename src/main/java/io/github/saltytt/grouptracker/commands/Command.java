package io.github.saltytt.grouptracker.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Command {

    boolean ownerOnly = false;
    public String name;
    public String usage;
    public String desc;
    public String examples;

    public Command(String name, String usage, String desc, String examples) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.examples = examples;
    }

    public Command(String name, String usage, String desc, String examples, boolean owner) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.examples = examples;
        this.ownerOnly = owner;
    }

    public abstract void execute(MessageReceivedEvent context, String args);
}
