package ru.esqlapy.command.listener;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.esqlapy.command.*;
import ru.esqlapy.command.handler.*;

import java.util.Collection;

/**
 * Listener for commands related to the Discord-bot.
 */
public final class CommandListener extends ListenerAdapter {

    /**
     * A response template for a situation when the bot cannot find the required option in the received command.
     */
    private static final String NO_PARAMETERS_TEMPLATE = """
            Sorry, I can't find the following parameters: "%s"
            Please make sure you have filled in all required fields correctly and send the command "%s" again
            """;
    /**
     * A response template for the situation when the bot receives a command with an option that must be of
     * {@link net.dv8tion.jda.api.interactions.commands.OptionType#BOOLEAN } type.
     */
    private static final String OPTION_MUST_BE_OF_BOOLEAN_TYPE = "Option must be of type \"boolean\"";
    /**
     * A response template for a situation when a bot receives a command that it can only execute in a Discord guild.
     */
    private static final String COMMAND_WORK_ONLY_IN_CHANNEL_TEMPLATE = """
            Sorry, the "%s" command work only in channel
            """;
    /**
     * A response template for the situation when the bot receives an unknown command.
     */
    private static final String COMMAND_UNKNOWN_TEMPLATE = "Sorry, I don't know the \"%s\" command";
    /**
     * Collection of commands used in the system.
     */
    private final Collection<Command> systemCommands = CommandProvider.getInstance().getSystemCommands();
    private final AboutCommandHandler aboutCommandHandler = new AboutCommandHandler();
    private final PlayCommandHandler playCommandHandler = new PlayCommandHandler();
    private final SkipCommandHandler skipCommandHandler = new SkipCommandHandler();
    private final LoopCommandHandler loopCommandHandler = new LoopCommandHandler();
    private final ClearCommandHandler clearCommandHandler = new ClearCommandHandler();
    private final LeaveCommandHandler leaveCommandHandler = new LeaveCommandHandler();

    /**
     * Called when a slash command is sent to the bot.
     *
     * @param event
     *         an event with extra data that carries information related to sending a slash command
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Command command = findCommandByName(event.getName());
        if (command == null) {
            event.reply(COMMAND_UNKNOWN_TEMPLATE.formatted(commandName)).queue();
            return;
        }
        switch (command) {
            case GlobalCommand globalCommand -> onGlobalCommand(globalCommand, event);
            case GuildCommand guildCommand -> onGuildCommand(guildCommand, event);
        }
    }

    /**
     * Called when a command sent by the user is {@link GlobalCommand}.
     *
     * @param globalCommand
     *         information about the received command
     * @param event
     *         an event with extra data that carries information related to sending a slash command
     */
    private void onGlobalCommand(@Nonnull GlobalCommand globalCommand, @Nonnull SlashCommandInteractionEvent event) {
        switch (globalCommand) {
            case AboutCommand ignored -> aboutCommandHandler.onAboutCommand(event);
        }
    }

    /**
     * Called when a command sent by the user is {@link GuildCommand}.
     *
     * @param guildCommand
     *         information about the received command
     * @param event
     *         an event with extra data that carries information related to sending a slash command
     */
    private void onGuildCommand(@Nonnull GuildCommand guildCommand, @Nonnull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply(COMMAND_WORK_ONLY_IN_CHANNEL_TEMPLATE.formatted(guildCommand.getName())).queue();
            return;
        }
        switch (guildCommand) {
            case PlayCommand playCommand -> onPlayCommandHandle(playCommand, guild, event);
            case SkipCommand ignored -> skipCommandHandler.onSkipCommand(guild, event);
            case LoopCommand loopCommand -> onLoopCommandHandle(loopCommand, guild, event);
            case ClearCommand ignored -> clearCommandHandler.onClearCommand(guild, event);
            case LeaveCommand ignored -> leaveCommandHandler.onLeaveCommand(guild, event);
        }
    }

    /**
     * Called when the user sends a {@link PlayCommand}
     *
     * @param playCommand
     *         information about the received command
     * @param guild
     *         information about the guild from which the command was sent
     * @param event
     *         an event with extra data that carries information related to sending a slash command
     */
    private void onPlayCommandHandle(
            @Nonnull PlayCommand playCommand,
            @Nonnull Guild guild,
            @Nonnull SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) {
            event.reply(COMMAND_WORK_ONLY_IN_CHANNEL_TEMPLATE).queue();
            return;
        }
        String requestOptionName = playCommand.getContentOption().name();
        OptionMapping option = event.getOption(requestOptionName);
        if (option == null) {
            event.reply(NO_PARAMETERS_TEMPLATE.formatted(requestOptionName, playCommand.getName())).queue();
            return;
        }
        playCommandHandler.onPlayCommand(guild, member, option.getAsString(), event);
    }

    /**
     * Called when the user sends a {@link LoopCommand}
     *
     * @param loopCommand
     *         information about the received command
     * @param guild
     *         information about the guild from which the command was sent
     * @param event
     *         an event with extra data that carries information related to sending a slash command
     */
    private void onLoopCommandHandle(
            @Nonnull LoopCommand loopCommand,
            @Nonnull Guild guild,
            @Nonnull SlashCommandInteractionEvent event
    ) {
        String enableOptionName = loopCommand.getEnableOption().name();
        OptionMapping option = event.getOption(enableOptionName);
        if (option == null) {
            event.reply(NO_PARAMETERS_TEMPLATE.formatted(enableOptionName, loopCommand.getName())).queue();
            return;
        }
        try {
            loopCommandHandler.onLoopCommand(guild, option.getAsBoolean(), event);
        } catch (IllegalStateException e) {
            event.reply(OPTION_MUST_BE_OF_BOOLEAN_TYPE).queue();
        }
    }

    /**
     * Searches for a Discord command in {@link CommandListener#systemCommands} by its name.
     *
     * @param name
     *         name of the command looking for
     * @return command with matching name, {@code null} if command not found
     */
    @Nullable
    private Command findCommandByName(@Nonnull String name) {
        for (Command command : systemCommands) {
            if (command.getName().equals(name)) {
                return command;
            }
        }
        return null;
    }
}
