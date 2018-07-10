package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BumpCommand extends BasicCommand {

    public BumpCommand() {
        super("bump");
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
