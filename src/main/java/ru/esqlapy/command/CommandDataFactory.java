package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import ru.esqlapy.command.option.CommandOption;

import java.util.Collection;

public final class CommandDataFactory {

    private OptionData createOptionData(CommandOption commandOption) {
        return new OptionData(
                commandOption.type(),
                commandOption.name(),
                commandOption.description(),
                commandOption.isRequired()
        );
    }

    @Nonnull
    private CommandData createCommandData(PlayCommand playCommand) {
        OptionData contentOption = createOptionData(playCommand.getContentOption());
        return Commands.slash(playCommand.getName(), playCommand.getDescription())
                .addOptions(contentOption);
    }

    @Nonnull
    private CommandData createCommandData(SkipCommand skipCommand) {
        return Commands.slash(skipCommand.getName(), skipCommand.getDescription());
    }

    @Nonnull
    private CommandData createCommandData(LoopCommand loopCommand) {
        OptionData enableOption = createOptionData(loopCommand.getEnableOption());
        return Commands.slash(loopCommand.getName(), loopCommand.getDescription())
                .addOptions(enableOption);
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
            case LoopCommand loopCommand -> createCommandData(loopCommand);
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
