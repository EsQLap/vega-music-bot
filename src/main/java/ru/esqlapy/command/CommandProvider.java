package ru.esqlapy.command;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class that provides list of {@link Command} used in the system.
 */
public final class CommandProvider {

    /**
     * An error message that the commands must have different names.
     */
    private static final String EXCEPTION_MESSAGE = "Commands must have different names";
    /**
     * Instance of {@link CommandProvider}.
     */
    private static final CommandProvider INSTANCE = new CommandProvider();
    /**
     * Commands used by the user when interacting with the bot.
     */
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

    /**
     * Returns the instance of {@link CommandProvider}.
     *
     * @return instance of {@link CommandProvider}
     */
    @Nonnull
    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the collection of {@link Command} used by the user when interacting with the bot.
     *
     * @return collection of {@link Command} used by the user when interacting with the bot.
     */
    @Nonnull
    public Collection<Command> getSystemCommands() {
        return systemCommands;
    }
}
