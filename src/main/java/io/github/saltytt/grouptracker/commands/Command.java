package io.github.saltytt.grouptracker.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public abstract class Command {

    boolean ownerOnly;
    public String name;
    public String usage;
    public String desc;
    public String examples;
    public Color colour;

    public Command(String name, String usage, String desc, String examples, Color col, boolean owner) {
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.examples = examples;
        this.colour = col;
        this.ownerOnly = owner;
    }

    public abstract void execute(MessageReceivedEvent context, String args);
}
