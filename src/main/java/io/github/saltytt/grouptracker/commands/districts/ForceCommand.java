package io.github.saltytt.grouptracker.commands.districts;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ForceCommand extends Command {

    public ForceCommand() {
        super(
                "forceupdate",
                "forceupdate",
                "Refreshes distict information (requires ADMIN permission)",
                Settings.prefix + "forceupdate"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (!context.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        DistrictManager.standard.refreshDistricts();

    }
}
