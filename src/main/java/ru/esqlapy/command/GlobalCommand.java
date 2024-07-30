package ru.esqlapy.command;

import org.jetbrains.annotations.NotNull;

public abstract sealed class GlobalCommand extends Command permits AboutCommand {

    GlobalCommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }
}
