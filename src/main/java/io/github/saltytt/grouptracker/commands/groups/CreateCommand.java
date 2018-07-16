package io.github.saltytt.grouptracker.commands.groups;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.groups.GroupBuilder;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.settings.Database;
import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.ChannelUtils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CreateCommand extends Command {

    public CreateCommand() {
        super(
                "create",
                "create",
                "Starts the process of creating a group",
                Settings.prefix + "create"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {

        TextChannel channel = ChannelUtils.getTextChannelFromID(
                context.getGuild(),
                Database.standard.getChannelForGuild(context.getGuild())
        );

        if (channel != null)
            createGroup(context, channel, context.getAuthor());

    }

    private void createGroup(MessageReceivedEvent context, MessageChannel channel, User user) {
        if (GroupManager.standard.groupForUser(user) != null || GroupManager.standard.builderForUser(user) != null) {
            context.getChannel().sendMessage("You're already in a group").queue();
            return;
        }

        new GroupBuilder(context, channel);

    }

}
