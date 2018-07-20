package io.github.saltytt.grouptracker.commands.districts;

import io.github.saltytt.grouptracker.commands.Command;

import java.awt.*;

public abstract class CommandDistrict extends Command {

    protected CommandDistrict(String name, String usage, String desc, String examples) {
        super(
                name,
                usage,
                desc,
                examples,
                Color.green,
                false
        );
    }
}
