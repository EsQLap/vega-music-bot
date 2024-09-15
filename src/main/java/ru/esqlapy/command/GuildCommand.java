package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

/**
 * A {@link Command} that a user can send to a bot only from a guild.
 */
public sealed abstract class GuildCommand extends Command permits PlayCommand, SkipCommand, LoopCommand,
        ClearCommand, LeaveCommand {

    /**
     * Creates a {@link GuildCommand} that the user will use to communicate with the bot.
     *
     * @param name
     *         the command name that is displayed to the user
     * @param description
     *         the command description that is displayed to the user
     */
    GuildCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);
    }
}
