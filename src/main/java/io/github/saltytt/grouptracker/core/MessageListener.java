package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE)) return;
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentStripped().startsWith(Settings.prefix))
            Bot.commandHandler.handleEvent(event);
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        Bot.reactionHandler.handleEvent(event);
    }

}
