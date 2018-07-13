package io.github.saltytt.grouptracker.groups;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class GroupManager {

    private final int REFRESH_MIN = 1;
    public static GroupManager standard = new GroupManager();
    private ArrayList<Group> groups;
    private ArrayList<GroupMember> members;
    private ArrayList<GroupBuilder> builders;

    public GroupManager() {
        groups = new ArrayList<>();
        members = new ArrayList<>();
        builders = new ArrayList<>();
        Timer refreshTimer = new Timer();
        refreshTimer.schedule(new RefreshTimer(),REFRESH_MIN*1000*60, REFRESH_MIN*1000*60);
    }

    public void init() {}

    public Group groupForUser(User user) {
        GroupMember member = memberForUser(user);
        return (member != null) ? member.group : null;
    }

    public Group groupForMessage(String id) {
        for(Group group : groups) {
            if (group.groupMessage.getId().equals(id)) return group;
        }
        return null;
    }

    public GroupMember memberForUser(User user) {
        for(GroupMember member : members) {
            if (member.user == user) return member;
        }
        return null;
    }

    public GroupBuilder builderForUser(User user) {
        for(GroupBuilder builder : builders) {
            if (builder.creator == user) return builder;
        }
        return null;
    }

    public ArrayList<Group> getGroups() { return groups; }

    public void remove(Group group) { groups.remove(group); }

    public void add(Group group) { groups.add(group); }

    public void remove(GroupMember member) { members.remove(member); }

    public void add(GroupMember member) { members.add(member); }

    public void remove(GroupBuilder builder) { builders.remove(builder); }

    public void add(GroupBuilder builder) { builders.add(builder); }

    private class RefreshTimer extends TimerTask {
        @Override
        public void run() {
            for (Iterator<Group> iter = GroupManager.standard.getGroups().iterator(); iter.hasNext(); )
                iter.next().refresh();
        }
    }
}
