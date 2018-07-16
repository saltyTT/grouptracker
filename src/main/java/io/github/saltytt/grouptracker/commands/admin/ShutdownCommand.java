package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.Main;
import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super(
                "shutdown",
                "shutdown",
                "Shuts down the bot (owner only)",
                Settings.prefix + "restart",
                true
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (Main.groupTracker.adminList.contains(context.getAuthor().getId()))
            GeneralUtils.restart(true);
    }
}
