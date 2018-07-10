package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class JoinCommand extends BasicCommand {

    public JoinCommand() {
        super("join");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (GroupManager.standard.groupForUser(context.getAuthor()) != null) {
            context.getChannel().sendMessage("You are already in a group").queue();
            return;
        }
        List<User> mentions = context.getMessage().getMentionedUsers();
        if (mentions.isEmpty()) {
            context.getChannel().sendMessage("No user mentioned").queue();
            return;
        }
        Group group = GroupManager.standard.groupForUser(mentions.get(0));
        if (group != null) {
            String[] split = args.split(" ");

            try {
                joinGroupParty(context, group, context.getAuthor(), group.creator.user, Integer.parseInt(split[0].trim()));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e ) {
                joinGroupSingle(context, group, context.getAuthor(), group.creator.user);
            }
            return;
        }

        context.getChannel().sendMessage("Mentioned user is not in a group ").queue();
    }

    private void joinGroupSingle(MessageReceivedEvent context, Group group, User user, User leader) {
        if (!group.hasRoomFor(1)) {
            context.getChannel().sendMessage("Not enough room").queue();
            return;
        }
        group.addUser(user);
        group.refresh();
        context.getChannel().sendMessage(String.format("Joined %s's group", leader.getName())).queue();
    }

    private void joinGroupParty(MessageReceivedEvent context, Group group, User user, User leader, int party) {
        if (!group.hasRoomFor(party)) {
            context.getChannel().sendMessage("Not enough room").queue();
            return;
        }
        group.addParty(user, party);
        group.refresh();
        context.getChannel().sendMessage(String.format("Joined %s's group", leader.getName())).queue();
    }

}
