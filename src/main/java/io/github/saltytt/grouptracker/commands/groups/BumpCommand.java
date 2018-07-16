package io.github.saltytt.grouptracker.commands.groups;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BumpCommand extends Command {

    public BumpCommand() {
        super(
                "bump",
                "bump",
                "Refreshes the last activity of the group you lead",
                Settings.prefix + "bump"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        Group group = GroupManager.standard.groupForUser(context.getAuthor());
        if (group.creator.user == context.getAuthor()) {
            context.getChannel().sendMessage("Bumped").queue();
            group.bump();
        }

}
}
