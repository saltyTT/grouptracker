package io.github.saltytt.grouptracker.commands.districts;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class NotifyCommand extends Command {

    public NotifyCommand() {
        super(
                "notify",
                "notify",
                "Gives or takes the gam role to notify when servers are up",
                Settings.prefix + "notify"
        );
    }
    @Override
    public void execute(MessageReceivedEvent context, String args) {
        List<Role> roles = context.getGuild().getRolesByName("gam",false);
        if (roles.isEmpty()) return;

        Role role = roles.get(0);

        if(context.getMember().getRoles().contains(role))
            context.getGuild().getController().removeSingleRoleFromMember(context.getMember(), role).queue();
        else context.getGuild().getController().addSingleRoleToMember(context.getMember(), role).queue();
    }
}
