package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand extends BasicCommand {

    public ShutdownCommand() {
        super("shutdown");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (context.getAuthor().getId().equals("218155764383088640"))
            GeneralUtils.restart(true);
    }
}
