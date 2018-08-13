package io.github.saltytt.grouptracker.commands;

import io.github.saltytt.grouptracker.Main;
import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class HelpCommand extends Command {

    public HelpCommand() {
        super(
            "help",
            "help (command name)",
            "Shows a list of all commands or information about a specific command",
                Settings.prefix + "help\n" +
                        Settings.prefix + "help create",
                Color.white,
                false
        );
    }

    @Override
    public void execute(MessageReceivedEvent context, String args) {
        context.getChannel().sendMessage(args == null ? cmdList() : cmdHelp(args)).queue();
    }

    private MessageEmbed cmdList() {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Command List")
                .setColor(java.awt.Color.WHITE)
                .setFooter("Use " + Main.bot.prefix + "help (command name) to get more information about a single command", null);
        for(Command c : Bot.commandHandler.commands.values()) {
            if (c.ownerOnly) continue;
            builder.addField(c.name, c.desc, false);
        }
        return builder.build();

    }

    private MessageEmbed cmdHelp(String name) {
        Command cmd = Bot.commandHandler.commandFromName(name.trim());
        if (cmd == null) return cmdList();
        return new EmbedBuilder()
                .setTitle(cmd.name.toUpperCase())
                .addField("Description", cmd.desc, false)
                .addField("Usage", cmd.usage, false)
                .addField("Examples", cmd.examples, false)
                .setColor(cmd.colour)
                .setFooter("Variables: (optional) <required>",null)
                .build();
    }
}
