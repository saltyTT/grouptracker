package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DownCommand extends BasicCommand {

    public DownCommand() {
        super("down");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        DistrictManager.standard.authDownCount++;
    }

}
