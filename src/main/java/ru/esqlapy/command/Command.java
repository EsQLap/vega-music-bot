package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

public sealed abstract class Command permits ClearCommand, LeaveCommand, PlayCommand, SkipCommand {

    private final String name;
    private final String description;

    Command(String name, String description) {
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
