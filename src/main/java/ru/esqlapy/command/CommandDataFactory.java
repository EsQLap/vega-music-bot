package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Collection;

public final class CommandDataFactory {

    @Nonnull
    private CommandData createCommandData(PlayCommand playCommand) {
        return Commands.slash(playCommand.getName(), playCommand.getDescription())
                .addOption(
                        playCommand.getContentOption().type(),
                        playCommand.getContentOption().name(),
                        playCommand.getContentOption().description(),
                        playCommand.getContentOption().isRequired()
                );
    }

    @Nonnull
    private CommandData createCommandData(SkipCommand skipCommand) {
        return Commands.slash(skipCommand.getName(), skipCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(ClearCommand clearCommand) {
        return Commands.slash(clearCommand.getName(), clearCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(LeaveCommand leaveCommand) {
        return Commands.slash(leaveCommand.getName(), leaveCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(Command command) {
        return switch (command) {
            case PlayCommand playCommand -> createCommandData(playCommand);
            case SkipCommand skipCommand -> createCommandData(skipCommand);
            case ClearCommand clearCommand -> createCommandData(clearCommand);
            case LeaveCommand leaveCommand -> createCommandData(leaveCommand);
        };
    }

    @Nonnull
    public Collection<CommandData> createCommandDataCollection(Collection<Command> commandCollection) {
        return commandCollection.stream()
                .map(this::createCommandData)
                .toList();
    }
}
