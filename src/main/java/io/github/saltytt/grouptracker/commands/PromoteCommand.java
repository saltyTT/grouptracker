package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.groups.GroupMember;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class PromoteCommand extends BasicCommand {

    public PromoteCommand() { super("promote"); }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        Group group = GroupManager.standard.groupForUser(context.getAuthor());
        if (group == null) return;
        if (group.creator.user != context.getAuthor()) {
            context.getChannel().sendMessage("You are not the leader").queue();
            return;
        }

        List<User> mentions = context.getMessage().getMentionedUsers();
        if (mentions.isEmpty()) {
            context.getChannel().sendMessage("No user mentioned").queue();
            return;
        }
        Group groupOther = GroupManager.standard.groupForUser(mentions.get(0));
        if (groupOther != group) {
            context.getChannel().sendMessage("That user is not in your group").queue();
            return;
        }

        GroupMember newLeader = GroupManager.standard.memberForUser(mentions.get(0));

        group.promote(newLeader);
        group.refresh();
        context.getChannel().sendMessage(mentions.get(0).getName() + " was promoted to the group leader").queue();
    }
}
