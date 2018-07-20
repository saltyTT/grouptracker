package io.github.saltytt.grouptracker.commands.districts;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DownCommand extends CommandDistrict {

    public DownCommand() {
        super(
                "down",
                "down",
                "Reports the log in / authentication servers offline",
                Settings.prefix + "down"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        DistrictManager.standard.loginDownCount++;
    }

}
