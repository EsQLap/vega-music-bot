package ru.esqlapy.command.option;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.OptionType;

/**
 * A command parameter sent by the user when interacting with the Discord-bot.
 *
 * @param type
 *         type for Discord command option
 * @param name
 *         the option name that is displayed to the user
 * @param description
 *         the option description that is displayed to the user
 * @param isRequired
 *         parameter indicating whether the user is required to send the option when sending a Discord command
 *         or it is optional
 */
public record CommandOption(
        @Nonnull OptionType type,
        @Nonnull String name,
        @Nonnull String description,
        boolean isRequired
) {
}
