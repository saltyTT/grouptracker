package io.github.saltytt.grouptracker.groups.builders;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.GroupBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public abstract class ActivityBuilder {

    GroupBuilder superBuilder;
    protected String[] districtList;
    protected int districtPages;
    public Activity activity;
    public String level;
    public String district;
    public int groupSize;

    protected final String u_one = "1⃣";
    protected final String u_two = "2⃣";
    protected final String u_three = "3⃣";
    protected final String u_four = "4⃣";
    protected final String u_five = "5⃣";
    protected final String u_left = "\u25C0";
    protected final String u_right = "\u25B6";
    protected final String u_back = "↩";
    protected final String u_check = "\u2705";

    public ActivityBuilder (GroupBuilder parent) {
        this.superBuilder = parent;
    }

    public abstract int handleEvent(MessageReactionAddEvent context);
    public abstract MessageEmbed buildNext();


}
