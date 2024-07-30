package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import ru.esqlapy.command.option.CommandOption;

import java.util.Collection;

public final class CommandDataFactory {

    @Nonnull
    private OptionData createOptionData(@Nonnull CommandOption commandOption) {
        return new OptionData(
                commandOption.type(),
                commandOption.name(),
                commandOption.description(),
                commandOption.isRequired()
        );
    }
    @Nonnull
    private CommandData createCommandData(@Nonnull AboutCommand aboutCommand) {
        return Commands.slash(aboutCommand.getName(), aboutCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull PlayCommand playCommand) {
        OptionData contentOption = createOptionData(playCommand.getContentOption());
        return Commands.slash(playCommand.getName(), playCommand.getDescription())
                .addOptions(contentOption);
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull SkipCommand skipCommand) {
        return Commands.slash(skipCommand.getName(), skipCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull LoopCommand loopCommand) {
        OptionData enableOption = createOptionData(loopCommand.getEnableOption());
        return Commands.slash(loopCommand.getName(), loopCommand.getDescription())
                .addOptions(enableOption);
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull ClearCommand clearCommand) {
        return Commands.slash(clearCommand.getName(), clearCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull LeaveCommand leaveCommand) {
        return Commands.slash(leaveCommand.getName(), leaveCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull GuildCommand guildCommand) {
        return switch (guildCommand) {
            case PlayCommand playCommand -> createCommandData(playCommand);
            case SkipCommand skipCommand -> createCommandData(skipCommand);
            case LoopCommand loopCommand -> createCommandData(loopCommand);
            case ClearCommand clearCommand -> createCommandData(clearCommand);
            case LeaveCommand leaveCommand -> createCommandData(leaveCommand);
        };
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull GlobalCommand globalCommand) {
        return switch (globalCommand) {
            case AboutCommand aboutCommand -> createCommandData(aboutCommand);
        };
    }

    @Nonnull
    private CommandData createCommandData(@Nonnull Command command) {
        return switch (command) {
            case GlobalCommand globalCommand -> createCommandData(globalCommand);
            case GuildCommand guildCommand -> createCommandData(guildCommand);
        };
    }

    @Nonnull
    public Collection<CommandData> createCommandDataCollection(Collection<Command> commandCollection) {
        return commandCollection.stream()
                .map(this::createCommandData)
                .toList();
    }
}
