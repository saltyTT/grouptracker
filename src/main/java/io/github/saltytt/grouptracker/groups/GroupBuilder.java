package io.github.saltytt.grouptracker.groups;

import io.github.saltytt.grouptracker.groups.builders.ActivityBuilder;
import io.github.saltytt.grouptracker.groups.builders.BossBuilder;
import io.github.saltytt.grouptracker.groups.builders.FacilityBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.util.Timer;
import java.util.TimerTask;

public class GroupBuilder {
    public ActivityBuilder activityBuilder;
    public User creator;

    private int step = 0;
    public Message message;
    private MessageChannel postTo;

    protected Timer timeoutTimer;

    private final String u_one = "1⃣";
    private final String u_two = "2⃣";
    private final String u_three = "3⃣";
    private final String u_four = "4⃣";
    private final String u_five = "5⃣";
    private final String u_left = "\u25C0";
    private final String u_right = "\u25B6";
    private final String u_back = "↩";

    public GroupBuilder(MessageReceivedEvent context, MessageChannel channel) {
        this.creator = context.getAuthor();
        this.postTo = channel;

        timeoutTimer = new Timer();
        timeoutTimer.schedule(new InactiveTimer(this),30*1000);

        message = context.getChannel().sendMessage(buildActivity()).complete();
        message.addReaction(u_one).queue();
        message.addReaction(u_two).queue();
        message.addReaction(u_three).queue();
        message.addReaction(u_four).queue();
        message.addReaction(u_five).queue();
        message.addReaction(u_left).queue();
        message.addReaction(u_right).queue();
        message.addReaction(u_back).queue();
        GroupManager.standard.add(this);
    }

    public void handleEvent(MessageReactionAddEvent context) {
        timeoutTimer.cancel();
        timeoutTimer = new Timer();
        timeoutTimer.schedule(new InactiveTimer(this),30*1000);

        switch (step) {
            case 0:
                chooseActivity(context);
                break;
            default:
                step = activityBuilder.handleEvent(context);
        }

        try { context.getReaction().removeReaction(context.getUser()).queue(); }
        catch (ErrorResponseException e) { return; }
    }

    private void chooseActivity(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        switch(reactString) {
            case u_one:
                activityBuilder = new BossBuilder(this);
                message.editMessage(activityBuilder.buildNext()).queue();
                break;
            case u_two:
                activityBuilder = new FacilityBuilder(this);
                message.editMessage(activityBuilder.buildNext()).queue();
                break;
            default:
                return;
        }
        step++;
    }

    public void confirmGroup(MessageReactionAddEvent context) {
        new Group(postTo, creator, activityBuilder.activity, activityBuilder.level, activityBuilder.district, activityBuilder.groupSize);
        timeoutTimer.cancel();
        GroupManager.standard.remove(this);
        message.delete().queue();
        context.getChannel().sendMessage("Your group was created").queue();
    }

    public MessageEmbed buildActivity() {
        final String activities = ":one: Boss\n:two: Facility";

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(creator.getName(), null, creator.getAvatarUrl())
                .addField("Choose an activity:",activities,false)
                .setFooter("Builder will terminate after 30 seconds of inactivity", null);
        return builder.build();
    }

    private class InactiveTimer extends TimerTask {
        private GroupBuilder builder;
        private InactiveTimer(GroupBuilder builder) {
            this.builder = builder;
        }
        @Override
        public void run() {
            GroupManager.standard.remove(builder);
            message.delete().queue();
            builder.timeoutTimer.cancel();
        }
    }
}
