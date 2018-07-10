package io.github.saltytt.grouptracker.groups;

import net.dv8tion.jda.core.entities.User;

public class GroupMember {

    public User user;
    public int party;
    public Group group;

    public GroupMember(Group group, User user, int party) {
        this.group = group;
        this.user = user;
        this.party = party;
    }

    public void leave() {
        GroupManager.standard.remove(this);
        group.removeMember(this);
    }

    public void addParty(int toAdd) {
        party+= toAdd;
        group.groupSize += toAdd;
        group.refresh();
    }

    public int removeParty(int toRemove) {
        final int remove = (party-toRemove > 1) ? toRemove : party-1;
        party-=remove;
        group.groupSize -=remove;
        group.refresh();
        return remove;
    }
}
