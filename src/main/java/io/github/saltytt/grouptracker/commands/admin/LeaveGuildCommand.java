package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveGuildCommand extends CommandAdmin {

    public LeaveGuildCommand() {
        super(
                "leaveguild",
                "leaveguild <your user id>",
                "Leaves the current guild (owner only)",
                Settings.prefix + "leaveguild 218155764383088640"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (GeneralUtils.isBotOwner(context.getAuthor()) && args != null) {
            if (args.trim().equals(context.getAuthor().getId())) {
                System.out.println("Left Guild ["+context.getGuild().getName()+"] || Called by ["+context.getAuthor().getName()+"]");
                context.getGuild().leave().queue();
            }
        }
    }
}
