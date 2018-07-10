package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.groups.GroupMember;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RemoveCommand extends BasicCommand{
    public RemoveCommand() {
        super("remove");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        GroupMember member = GroupManager.standard.memberForUser(context.getAuthor());
        if (member == null) {
            context.getChannel().sendMessage("You are not in a group").queue();
            return;
        }
        if (member == member.group.creator)
            member.group.bump();
        if (args == null) {
            int removed = member.removeParty(1);
            if (removed == 0)
                context.getChannel().sendMessage(String.format("You are the only one in your party")).queue();
            else
                context.getChannel().sendMessage(String.format("Removed 1 from your party")).queue();
            return;
        }
        try {
            int toRemove = Integer.parseInt(args.trim());
            if (toRemove < 1) throw new NumberFormatException();
            int removed = member.removeParty(toRemove);
            if (removed == 0)
                context.getChannel().sendMessage(String.format("You are the only one in your party")).queue();
            else
                context.getChannel().sendMessage(String.format("Removed %d from your party", removed)).queue();
            return;
        } catch (NumberFormatException e) {
            context.getChannel().sendMessage("Invalid arguments").queue();
        }
    }
}
