package io.github.saltytt.grouptracker.commands.admin;

import io.github.saltytt.grouptracker.commands.Command;

import java.awt.*;

public abstract class CommandAdmin extends Command {

    protected CommandAdmin(String name, String usage, String desc, String examples) {
        super(
                name,
                usage,
                desc,
                examples,
                Color.red,
                true
        );
    }
}
