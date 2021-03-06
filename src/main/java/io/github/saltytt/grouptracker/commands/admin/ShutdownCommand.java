package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand extends CommandAdmin {

    public ShutdownCommand() {
        super(
                "shutdown",
                "shutdown",
                "Shuts down the bot (owner only)",
                Settings.prefix + "restart"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (GeneralUtils.isBotOwner(context.getAuthor()))
            GeneralUtils.restart(true);
    }
}
