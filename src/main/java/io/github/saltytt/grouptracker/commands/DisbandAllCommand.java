package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class DisbandAllCommand extends BasicCommand {

    public DisbandAllCommand() {
        super("disbandall");
    }

    public void execute(MessageReceivedEvent context, String args) {
        if (!context.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            context.getChannel().sendMessage("You do not have permission to do that").queue();
            return;
        }
        ArrayList<Group> groups = GroupManager.standard.getGroups();
        for (Group group : groups) group.safeDisband();
        groups.removeAll(groups);

        context.getChannel().sendMessage("All groups were disbanded").queue();
    }

}
