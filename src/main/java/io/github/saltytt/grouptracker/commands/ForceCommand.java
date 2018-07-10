package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ForceCommand extends BasicCommand {

    public ForceCommand() { super("forceupdate"); }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (!context.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        DistrictManager.standard.refreshDistricts();

    }
}
