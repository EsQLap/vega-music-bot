package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

/**
 * Command sent by the user to the bot.
 */
public abstract sealed class Command permits GlobalCommand, GuildCommand {

    /**
     * The command name that is displayed to the user.
     */
    private final String name;
    /**
     * The command description that is displayed to the user.
     */
    private final String description;

    /**
     * Creates a {@link Command} that the user will use to communicate with the bot.
     *
     * @param name the command name that is displayed to the user
     * @param description the command description that is displayed to the user
     */
    Command(@Nonnull String name, @Nonnull String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the command name that is displayed to the user.
     *
     * @return the command name that is displayed to the user
     */
    @Nonnull
    public final String getName() {
        return name;
    }

    /**
     * Returns the command description that is displayed to the user.
     *
     * @return the command description that is displayed to the user
     */
    @Nonnull
    public final String getDescription() {
        return description;
    }
}
