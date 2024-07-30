package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

public sealed abstract class GuildCommand extends Command permits PlayCommand, SkipCommand, LoopCommand,
        ClearCommand, LeaveCommand {

    GuildCommand(@Nonnull String name, @Nonnull String description) {
        super(name, description);
    }
}
