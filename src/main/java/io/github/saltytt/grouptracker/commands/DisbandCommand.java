package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DisbandCommand extends BasicCommand {

    public DisbandCommand() {
        super("disband");
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
