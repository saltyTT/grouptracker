package io.github.saltytt.grouptracker.commands.groups;

import io.github.saltytt.grouptracker.commands.Command;

import java.awt.*;

public abstract class CommandGroup extends Command {

    protected CommandGroup(String name, String usage, String desc, String examples) {
        super(
                name,
                usage,
                desc,
                examples,
                Color.blue,
                false
        );
    }
}
