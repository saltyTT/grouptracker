package io.github.saltytt.grouptracker.commands.groups;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.groups.GroupMember;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AddCommand extends Command {

    public AddCommand() {
        super(
                "add",
                "add (number to add)",
                "Adds one or more members to your party",
                Settings.prefix + "add\n" +
                        Settings.prefix + "add 2"
        );
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
            if(!member.group.hasRoomFor(1)) {
                context.getChannel().sendMessage("Not enough room in group").queue();
                return;
            }
            member.addParty(1);
            context.getChannel().sendMessage("Added 1 to your party").queue();
            return;
        }
        try {
            int toAdd = Integer.parseInt(args);
            if (toAdd < 1) throw new NumberFormatException();
            if(!member.group.hasRoomFor(toAdd)) {
                context.getChannel().sendMessage("Not enough room in group").queue();
                return;
            }
            member.addParty(toAdd);
            context.getChannel().sendMessage(String.format("Added %d to your party", toAdd)).queue();
        } catch (NumberFormatException e) {
            context.getChannel().sendMessage("Invalid arguments").queue();
        }

    }
}
