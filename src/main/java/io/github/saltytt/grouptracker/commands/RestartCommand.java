package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RestartCommand extends BasicCommand {

    public RestartCommand() {
        super("restart");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (context.getAuthor().getId().equals("218155764383088640") ||
            context.getAuthor().getId().equals("111608457290895360") ||
            context.getAuthor().getId().equals("260971965429448715"))
            GeneralUtils.restart(false);
    }
}
