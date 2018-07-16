package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupBuilder;
import io.github.saltytt.grouptracker.groups.GroupManager;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class ReactionHandler {

    public void handleEvent(MessageReactionAddEvent context) {
        GroupBuilder builder = GroupManager.standard.builderForUser(context.getUser());
        if (builder == null) { handleGroupEvent(context); return; }
        if (!builder.message.getId().equals(context.getMessageId())) return;
        if (builder.creator != context.getUser()) return;
        builder.handleEvent(context);
    }

    public void handleGroupEvent(MessageReactionAddEvent context) {
        Group group = GroupManager.standard.groupForMessage(context.getMessageId());
        if (group == null) return;
        group.handleEvent(context);
    }
}
