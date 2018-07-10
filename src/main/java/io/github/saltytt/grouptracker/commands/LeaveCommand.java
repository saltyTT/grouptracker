package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.groups.GroupMember;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LeaveCommand extends BasicCommand {

    public LeaveCommand() {
        super("leave");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        GroupMember member = GroupManager.standard.memberForUser(context.getAuthor());
        if (member == null) {
            context.getChannel().sendMessage("You are not in a group").queue();
            return;
        }
        if (member.group.creator.user == member.user) {
            context.getChannel().sendMessage("You cannot leave a group you lead").queue();
            return;
        }
        member.leave();
        context.getChannel().sendMessage("You left the group").queue();

    }
}
