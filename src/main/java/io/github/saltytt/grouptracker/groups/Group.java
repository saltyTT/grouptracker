package io.github.saltytt.grouptracker.groups;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import java.awt.Color;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class Group {

    public GroupMember creator;
    public Activity activity;
    public String level;
    public String district;
    public String location;
    private int groupMax;
    public int groupSize;
    public ArrayList<GroupMember> party;
    public Message groupMessage;
    private LocalTime lastUpdate;

    private final String addEm = "⬆";
    private final String removeEm = "⬇";
    private final String leaveEm = "❌";
    private final String disbandEm = "\uD83D\uDEAB";

    public Group(MessageChannel channel, User creator, Activity activity, String level, String district, int groupSize) {
        this.creator = new GroupMember(this, creator, groupSize);
        this.activity = activity;
        this.level = level;
        this.district = district;
        this.location = activity.location;
        this.groupMax = activity.maxToons;
        this.groupSize = groupSize;
        this.lastUpdate = LocalTime.now();
        GroupManager.standard.add(this);
        GroupManager.standard.add(this.creator);
        party = new ArrayList<>();

        groupMessage = channel.sendMessage(buildEmbed()).complete();
        groupMessage.addReaction(addEm).queue();
        groupMessage.addReaction(removeEm).queue();
        groupMessage.addReaction(leaveEm).queue();
        groupMessage.addReaction(disbandEm).queue();

    }

    public boolean hasRoomFor(int userCount) {
        return this.groupSize + userCount <= groupMax;
    }

    public void addParty(User user, int partyTotal) {
        GroupMember member = new GroupMember(this, user, partyTotal);
        GroupManager.standard.add(member);
        party.add(member);
        groupSize += partyTotal;
    }

    public void addUser(User user) {
        GroupMember member = new GroupMember(this, user, 1);
        GroupManager.standard.add(member);
        party.add(member);
        groupSize++;
    }

    public void addMember(GroupMember member) {
        GroupManager.standard.add(member);
        party.add(member);
        groupSize += member.party;
    }

    public void removeMember(GroupMember member) {
        party.remove(member);
        groupSize -= member.party;
        refresh();
    }

    public void promote(GroupMember member) {
        party.remove(member);
        party.add(creator);
        creator = member;
    }

    public void disband() {
        GroupManager.standard.remove(this);
        GroupManager.standard.remove(creator);
        for(GroupMember mem : party) GroupManager.standard.remove(mem);
        groupMessage.delete().queue();
        groupMessage = null;
    }

    public void safeDisband() {
        GroupManager.standard.remove(creator);
        for(GroupMember mem : party) GroupManager.standard.remove(mem);
        groupMessage.delete().queue();
        groupMessage = null;
    }

    public String groupString() {
        final String partyTemplate = "%s (x%d)";
        String group = (creator.party > 1) ?
                String.format(partyTemplate, creator.user.getName(), creator.party) : creator.user.getName();
        for (GroupMember mem : party)
            group += ", " + ((mem.party > 1) ?
                    String.format(partyTemplate, mem.user.getName(), mem.party) : mem.user.getName());
        return group;
    }

    public String toonsString() {
        return groupSize + "/" + groupMax;
    }

    public String lastActivityString() {
        int minutes = inactivetime();
        switch (minutes) {
            case 0:
                return "Less than a minute ago";
            case 1:
                return "One minute ago";
            default:
                return minutes + " minutes ago";
        }
    }

    public void bump() {
        lastUpdate = LocalTime.now();
        refresh();
    }

    public void refresh() {
        if (inactivetime() > 20) {
            this.disband();
            return;
        }
        groupMessage.editMessage(buildEmbed()).queue();
    }

    private int inactivetime() {
        return (int) ChronoUnit.MINUTES.between(lastUpdate, LocalTime.now());
    }

    public MessageEmbed buildEmbed() {
        Color colour;
        int key = (int) (100* groupSize /(double) groupMax);
        if (key < 75) colour = Color.green;
        else if (key < 100) colour = Color.yellow;
        else colour = Color.red;

        return new EmbedBuilder()
                .setTitle(level == null ? this.activity.name : this.level)
                .setAuthor(this.creator.user.getName(), null, this.creator.user.getAvatarUrl())
                .addField("District", this.district, true)
                .addField("Location", this.location, true)
                .addField("Toons", this.toonsString(), true)
                .addBlankField(false)
                .addField("Group", this.groupString(), false)
                .addBlankField(false)
                .setFooter("Last leader activity: " + this.lastActivityString(), null)
                .setColor(colour)
                .build();
    }

    public void handleEvent(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        GroupMember member = GroupManager.standard.memberForUser(context.getUser());
        switch(reactString) {
            case addEm:
                if (member == null) {
                    if(!hasRoomFor(1)) break;
                    addUser(context.getUser());
                    break;
                }
                if (member.group != this) break;
                if(!member.group.hasRoomFor(1)) break;
                member.addParty(1);
                break;
            case removeEm:
                if (member == null) break;
                if (member.group != this) break;
                int removed = member.removeParty(1);
                if (removed == 0 && member != member.group.creator) member.leave();
                break;
            case leaveEm:
                if (member == null) break;
                if (member.group.creator.user == member.user) break;
                if (member.group != this) break;
                member.leave();
                break;
            case disbandEm:
                if (context.getUser() == this.creator.user || context.getMember().hasPermission(Permission.ADMINISTRATOR)) disband();
                return;
            default:
                return;
        }
        if (member == null) return;
        else if (member == member.group.creator) member.group.bump();
        else refresh();
        context.getReaction().removeReaction(context.getUser()).complete();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (groupMessage != null) groupMessage.delete().queue();
    }
}
