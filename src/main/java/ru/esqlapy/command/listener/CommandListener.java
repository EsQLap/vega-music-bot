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

public final class CommandListener extends ListenerAdapter {

    private static final String NO_PARAMETERS_TEMPLATE = """
            Sorry, I can't find the following parameters: "%s"
            Please make sure you have filled in all required fields correctly and send the command "%s" again
            """;
    private static final String OPTION_MUST_BE_OF_BOOLEAN_TYPE = "Option must be of type \"boolean\"";
    private static final String WORK_ONLY_IN_CHANNEL = "Sorry, I can work only in channel";
    private static final String COMMAND_UNKNOWN_TEMPLATE = "Sorry, I don't know the \"%s\" command";
    private final Collection<Command> systemCommands = CommandProvider.getInstance().getSystemCommands();
    private final PlayCommandHandler playCommandHandler = new PlayCommandHandler();
    private final SkipCommandHandler skipCommandHandler = new SkipCommandHandler();
    private final LoopCommandHandler loopCommandHandler = new LoopCommandHandler();
    private final ClearCommandHandler clearCommandHandler = new ClearCommandHandler();
    private final LeaveCommandHandler leaveCommandHandler = new LeaveCommandHandler();

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Command command = findCommandByName(event.getName());
        if (command == null) {
            event.reply(COMMAND_UNKNOWN_TEMPLATE.formatted(commandName)).queue();
            return;
        }
        Guild guild = event.getGuild();
        if (guild == null) {
            event.reply(WORK_ONLY_IN_CHANNEL).queue();
            return;
        }
        switch (command) {
            case PlayCommand playCommand -> onPlayCommandHandle(playCommand, guild, event);
            case SkipCommand ignored -> skipCommandHandler.onSkipCommand(guild, event);
            case LoopCommand loopCommand -> onLoopCommandHandle(loopCommand, guild, event);
            case ClearCommand ignored -> clearCommandHandler.onClearCommand(guild, event);
            case LeaveCommand ignored -> leaveCommandHandler.onLeaveCommand(guild, event);
        }
    }

    private void onPlayCommandHandle(
            @Nonnull PlayCommand playCommand,
            @Nonnull Guild guild,
            @Nonnull SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        if (member == null) {
            event.reply(WORK_ONLY_IN_CHANNEL).queue();
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
