package io.github.saltytt.grouptracker.utilities;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class ChannelUtils {

    public static TextChannel getTextChannelFromID(Guild guild, String id) {
        for ( TextChannel chan : guild.getTextChannels()) {
            if (chan.getId().equals(id)) return chan;
        }
        return null;
    }

    public static TextChannel getTextChannelFromName(Guild guild, String name) {
        for ( TextChannel chan : guild.getTextChannels()) {
            if (chan.getName().equals(name))
                return chan;
        }
        return null;
    }
}
