package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ForceUpdateCommand extends CommandAdmin {

    public ForceUpdateCommand() {
        super(
                "forceupdate",
                "forceupdate",
                "Refreshes distict information (owner only)",
                Settings.prefix + "forceupdate"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (GeneralUtils.isBotOwner(context.getAuthor()))
            DistrictManager.standard.refreshDistricts();
    }
}
