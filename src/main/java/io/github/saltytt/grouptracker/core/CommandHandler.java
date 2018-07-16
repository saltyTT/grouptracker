package io.github.saltytt.grouptracker.core;

import io.github.saltytt.grouptracker.commands.Command;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class CommandHandler {

    public HashMap<String, Command> commands = new HashMap<>();

    public void handleEvent(MessageReceivedEvent e) {
        final String[] split = e.getMessage().getContentRaw().split(" ", 2);
        final String cmd = split[0].replace(Settings.prefix, "");
        final String args = (split.length > 1) ? split[1] : null;

        Command command = commandFromName(cmd);
        if (command != null) command.execute(e, args);

    }

    public CommandHandler registerCommand(Command command) {
        commands.put(command.name, command);
        return this;
    }

    public Command commandFromName(String name) {
        return commands.get(name.toLowerCase());
    }

}
