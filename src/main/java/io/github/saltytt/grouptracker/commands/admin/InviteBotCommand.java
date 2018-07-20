package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InviteBotCommand extends CommandAdmin {

    public InviteBotCommand() {
        super(
                "invitebot",
                "invitebot",
                "Gets bot invite link (owner only)",
                Settings.prefix + "invitebot"
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        if (GeneralUtils.isBotOwner(context.getAuthor()))
            context.getChannel().sendMessage("https://discordapp.com/oauth2/authorize?client_id=466007702070362123&scope=bot").queue();
    }
}
