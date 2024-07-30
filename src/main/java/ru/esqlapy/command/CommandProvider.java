package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class CommandProvider {

    private static final String EXCEPTION_MESSAGE = "Commands must have different names";
    private static final CommandProvider INSTANCE = new CommandProvider();
    private final Collection<Command> systemCommands = List.of(
            new AboutCommand(),
            new PlayCommand(),
            new SkipCommand(),
            new LoopCommand(),
            new ClearCommand(),
            new LeaveCommand());

    private CommandProvider() {
        Set<String> commandNameSet = systemCommands.stream()
                .map(Command::getName)
                .collect(Collectors.toSet());
        if (commandNameSet.size() != systemCommands.size()) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }

    @Nonnull
    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    @Nonnull
    public Collection<Command> getSystemCommands() {
        return systemCommands;
    }
}
