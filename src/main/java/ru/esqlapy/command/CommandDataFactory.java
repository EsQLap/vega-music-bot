package ru.esqlapy.command;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import ru.esqlapy.command.option.CommandOption;

import java.util.Collection;

/**
 * Creates {@link CommandData} instances used in creating a {@link net.dv8tion.jda.api.JDA} application.
 */
public final class CommandDataFactory {

    /**
     * Creates a {@link CommandData} based on instructions from a {@link CommandOption} instance.
     *
     * @param commandOption
     *         command with {@link CommandOption} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private OptionData createOptionData(@Nonnull CommandOption commandOption) {
        return new OptionData(
                commandOption.type(),
                commandOption.name(),
                commandOption.description(),
                commandOption.isRequired()
        );
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link AboutCommand} instance.
     *
     * @param aboutCommand
     *         command with {@link AboutCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull AboutCommand aboutCommand) {
        return Commands.slash(aboutCommand.getName(), aboutCommand.getDescription());
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link PlayCommand} instance.
     *
     * @param playCommand
     *         command with {@link PlayCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull PlayCommand playCommand) {
        OptionData contentOption = createOptionData(playCommand.getContentOption());
        return Commands.slash(playCommand.getName(), playCommand.getDescription())
                .addOptions(contentOption);
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link SkipCommand} instance.
     *
     * @param skipCommand
     *         command with {@link SkipCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull SkipCommand skipCommand) {
        return Commands.slash(skipCommand.getName(), skipCommand.getDescription());
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link LoopCommand} instance.
     *
     * @param loopCommand
     *         command with {@link LoopCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull LoopCommand loopCommand) {
        OptionData enableOption = createOptionData(loopCommand.getEnableOption());
        return Commands.slash(loopCommand.getName(), loopCommand.getDescription())
                .addOptions(enableOption);
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link ClearCommand} instance.
     *
     * @param clearCommand
     *         command with {@link ClearCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull ClearCommand clearCommand) {
        return Commands.slash(clearCommand.getName(), clearCommand.getDescription());
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link LeaveCommand} instance.
     *
     * @param leaveCommand
     *         command with {@link LeaveCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull LeaveCommand leaveCommand) {
        return Commands.slash(leaveCommand.getName(), leaveCommand.getDescription());
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link GuildCommand} instance.
     *
     * @param guildCommand
     *         command with {@link GuildCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
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

    /**
     * Creates a {@link CommandData} based on instructions from a {@link GlobalCommand} instance.
     *
     * @param globalCommand
     *         command with {@link GlobalCommand} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull GlobalCommand globalCommand) {
        return switch (globalCommand) {
            case AboutCommand aboutCommand -> createCommandData(aboutCommand);
        };
    }

    /**
     * Creates a {@link CommandData} based on instructions from a {@link Command} instance.
     *
     * @param command
     *         command with {@link Command} class used by the user when interacting with the bot
     * @return command with {@link CommandData} class used by the user when interacting with the bot
     */
    @Nonnull
    private CommandData createCommandData(@Nonnull Command command) {
        return switch (command) {
            case GlobalCommand globalCommand -> createCommandData(globalCommand);
            case GuildCommand guildCommand -> createCommandData(guildCommand);
        };
    }

    /**
     * Creates a {@link CommandData} collection from a collection of {@link Command}.
     *
     * @param commandCollection
     *         collection of {@link Command} used by the user when interacting with the bot.
     * @return collection of {@link CommandData} used by the user when interacting with the bot.
     */
    @Nonnull
    public Collection<CommandData> createCommandDataCollection(@Nonnull Collection<Command> commandCollection) {
        return commandCollection.stream()
                .map(this::createCommandData)
                .toList();
    }
}
