package ru.esqlapy.command.option;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public record CommandOption(
        OptionType type,
        String name,
        String description,
        boolean isRequired
) {
}
