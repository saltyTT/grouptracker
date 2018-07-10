package io.github.saltytt.grouptracker.groups.builders;

import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.groups.GroupBuilder;
import io.github.saltytt.grouptracker.groups.constants.activities.BossList;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class BossBuilder extends ActivityBuilder {

    private Message message;

    private int step;
    private int page = 1;


    public BossBuilder (GroupBuilder parent) {
        super(parent);
        this.step = 1;
        this.message = parent.message;
        this.districtList = DistrictManager.standard.getDistrictNames();
        this.districtPages = districtList.length % 5 == 0 ? districtList.length/5 : districtList.length/5 + 1;
    }

    public int handleEvent(MessageReactionAddEvent context) {
        switch (step) {
            case 1:
                chooseBoss(context);
                break;
            case 2:
                chooseDistrict(context);
                break;
            case 3:
                chooseSize(context);
                break;
            case 4:
                confirm(context);
                break;
            default:
                break;
        }
        return step;
    }

    private void chooseBoss(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        switch(reactString) {
            case u_one:
                activity = BossList.VP.activity;
                break;
            case u_two:
                activity = BossList.CFO.activity;
                break;
            case u_three:
                activity = BossList.CJ.activity;
                break;
            case u_four:
                activity = BossList.CEO.activity;
                break;
            case u_back:
                step--;
                message.editMessage(superBuilder.buildActivity()).queue();
                return;
            default:
                return;
        }
        step++;
        message.editMessage(buildNext()).queue();
    }

    private void chooseDistrict(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        final int dc = (page-1)*5;
        try {
            switch (reactString) {
                case u_one:
                    district = districtList[0+dc];
                    break;
                case u_two:
                    district = districtList[1+dc];
                    break;
                case u_three:
                    district = districtList[2+dc];
                    break;
                case u_four:
                    district = districtList[3+dc];
                    break;
                case u_five:
                    district = districtList[4+dc];
                    break;
                case u_left:
                    page = (page - 1 > 0) ? page - 1 : districtPages;
                    message.editMessage(buildNext()).queue();
                    return;
                case u_right:
                    page = (page + 1 > districtPages) ? 1 : page + 1;
                    message.editMessage(buildNext()).queue();
                    return;
                case u_back:
                    step--;
                    message.editMessage(buildNext()).queue();
                    return;
                default:
                    return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {return;}
        step++;
        message.editMessage(buildNext()).queue();

    }

    private void chooseSize(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        switch(reactString) {
            case u_one:
                groupSize = 1;
                break;
            case u_two:
                groupSize = 2;
                break;
            case u_three:
                groupSize = 3;
                break;
            case u_four:
                groupSize = 4;
                break;
            case u_five:
                groupSize = 5;
                break;
            case u_back:
                step--;
                message.editMessage(buildNext()).queue();
                return;
            default:
                return;
        }
        step++;
        message.editMessage(buildNext()).queue();
        message.addReaction(u_check).queue();
    }

    private void confirm(MessageReactionAddEvent context) {
        final String reactString = context.getReaction().getReactionEmote().getName();
        switch(reactString) {
            case u_check:
                break;
            case u_back:
                step--;
                message.editMessage(buildNext()).queue();
                return;
            default:
                return;
        }

        superBuilder.confirmGroup(context);
    }

    /*
                EMBED BUILDERS
                                        */

    public MessageEmbed buildNext() {
        switch (step) {
            case 1:
                return buildBoss();
            case 2:
                return buildDistrict(page);
            case 3:
                return buildSize();
            case 4:
                return buildConfirm();
            default:
                return null;
        }
    }

    public MessageEmbed buildBoss() {
        final String bosses = ":one: Vice President\n:two: Chief Financial Officer" +
                "\n:three: Chief Justice\n:four: Chief Executive Officer";

        MessageEmbed.Field activity = new MessageEmbed.Field("Activity", "Boss", true);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(superBuilder.creator.getName(), null, superBuilder.creator.getAvatarUrl())
                .addField(activity)
                .addField("Choose a boss:", bosses, false)
                .setFooter("Builder will terminate after 30 seconds of inactivity", null);
        return builder.build();
    }

    public MessageEmbed buildDistrict(int page) {
        String dist = "";

        final int dc = (page-1)*5;

        for(int i = 0+dc, j = 0; i < districtList.length && j < 5; i++, j++)
            dist += ":" + GeneralUtils.indexToString(j) + ": " + districtList[i] + "\n";

        dist+="*" + page + "/" + districtPages + "*";

        MessageEmbed.Field activity = new MessageEmbed.Field("Boss", this.activity.name, true);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(superBuilder.creator.getName(), null, superBuilder.creator.getAvatarUrl())
                .addField(activity)
                .addField("Choose a district:", dist, false)
                .setFooter("Builder will terminate after 30 seconds of inactivity", null);
        return builder.build();
    }

    public MessageEmbed buildSize() {
        MessageEmbed.Field activity = new MessageEmbed.Field("Boss", this.activity.name, true);
        MessageEmbed.Field district = new MessageEmbed.Field("District", this.district, true);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(superBuilder.creator.getName(), null, superBuilder.creator.getAvatarUrl())
                .addField(activity)
                .addField(district)
                .addField("Choose your party size", "", false)
                .setFooter("Builder will terminate after 30 seconds of inactivity", null);
        return builder.build();
    }

    public MessageEmbed buildConfirm() {
        MessageEmbed.Field activity = new MessageEmbed.Field("Boss", this.activity.name, true);
        MessageEmbed.Field district = new MessageEmbed.Field("District", this.district, true);
        MessageEmbed.Field size = new MessageEmbed.Field("Party", String.valueOf(this.groupSize), true);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(superBuilder.creator.getName(), null, superBuilder.creator.getAvatarUrl())
                .addField(activity)
                .addField(district)
                .addField(size)
                .addBlankField(false)
                .addField("Click the checkmark to confirm your group", "", false)
                .setFooter("Builder will terminate after 30 seconds of inactivity", null);
        return builder.build();
    }
}
