package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.settings.Database;
import io.github.saltytt.grouptracker.utilities.ChannelUtils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ChannelCommand extends BasicCommand {

    public ChannelCommand() {
        super("channel");
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (args == null) {
            handleNullArgs(context);
        } else {
            final String split[] = args.split(" ", 2);
            if (split[0].equals("set")) {
                if (split.length > 1) {
                    handleSetChannel(context, split[1].trim());
                } else {
                    context.getTextChannel().sendMessage("No channel provided").queue();
                    return;
                }
            }
        }
    }

    private void handleNullArgs(MessageReceivedEvent context) {
        TextChannel channel = null;
        channel = ChannelUtils.getTextChannelFromID(context.getGuild(), Database.standard.getChannelForGuild(context.getGuild()));

        if (channel != null)
            context.getTextChannel().sendMessage("Group Channel: " + channel.getName()).queue();
        else
            context.getTextChannel().sendMessage("No channel specified").queue();
    }

    private void handleSetChannel(MessageReceivedEvent context, String channelName) {
        if (!context.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            context.getTextChannel().sendMessage("You do not have permission to do that").queue();
            return;
        }
        TextChannel channel = ChannelUtils.getTextChannelFromName(context.getGuild(), channelName);
        if (channel != null) {
            if (Database.standard.setChannelForGuild(context.getGuild(), channel))
                context.getTextChannel().sendMessage("Set channel to " + channel.getName()).queue();
            else
                context.getTextChannel().sendMessage("Error").queue();
            return;
        } else {
            context.getTextChannel().sendMessage("Channel not found").queue();
            return;
        }
    }

}
