package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.commands.BasicCommand;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandHandler {

    HashMap<String, BasicCommand> commands = new HashMap<>();

    public void handleEvent(MessageReceivedEvent e) {
        final String[] split = e.getMessage().getContentRaw().split(" ", 2);
        final String cmd = split[0].replace(Settings.prefix, "");
        final String args = (split.length > 1) ? split[1] : null;

        BasicCommand command = commandFromName(cmd);
        if (command != null) command.execute(e, args);

    }

    public CommandHandler registerCommand(BasicCommand command) {
        commands.put(command.name, command);
        return this;
    }

    public BasicCommand commandFromName(String name) {
        return commands.get(name.toLowerCase());
    }

}
