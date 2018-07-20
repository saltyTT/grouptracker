package io.github.saltytt.grouptracker.commands.groups;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DisbandCommand extends CommandGroup {

    public DisbandCommand() {
        super(
                "disband",
                "disband",
                "Disbands your group if you are the leader",
                Settings.prefix + "disband"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        Group group = GroupManager.standard.groupForUser(context.getAuthor());
        if (group != null && group.creator.user == context.getAuthor()) {
            group.disband();
            context.getChannel().sendMessage("Your group was disbanded").queue();
        } else context.getChannel().sendMessage("You do not lead a group").queue();
    }
}
