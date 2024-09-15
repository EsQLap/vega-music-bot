package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

/**
 * A {@link Command} that a user can send to a bot from both private messages and from a guild.
 */
public abstract sealed class GlobalCommand extends Command permits AboutCommand {

    /**
     * Creates a {@link GlobalCommand} that the user will use to communicate with the bot.
     *
     * @param name
     *         the command name that is displayed to the user
     * @param description
     *         the command description that is displayed to the user
     */
    GlobalCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);
    }
}
