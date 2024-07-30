package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

public abstract sealed class Command permits GlobalCommand, GuildCommand {

    private final String name;
    private final String description;

    Command(@Nonnull String name, @Nonnull String description) {
        this.name = name;
        this.description = description;
    }

    @Nonnull
    public final String getName() {
        return name;
    }

    @Nonnull
    public final String getDescription() {
        return description;
    }
}
