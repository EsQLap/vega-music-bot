package ru.esqlapy.command.listener;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import ru.esqlapy.command.*;
import ru.esqlapy.command.handler.ClearCommandHandler;
import ru.esqlapy.command.handler.LeaveCommandHandler;
import ru.esqlapy.command.handler.PlayCommandHandler;

import java.util.Collection;

public final class CommandListener extends ListenerAdapter {

    private static final String NO_REQUEST_TEMPLATE = """
            Sorry, I can't find the content of you request.
            Please, make sure you filled in the field "%s" correctly and send the "%s" command again
            """;
    private static final String WORK_ONLY_IN_CHANNEL = "Sorry, I can work only in channel";
    private static final String COMMAND_UNKNOWN_TEMPLATE = "Sorry, I don't know the \"%s\" command";
    private final Collection<Command> systemCommands = CommandProvider.getInstance().getSystemCommands();
    private final PlayCommandHandler playCommandHandler = new PlayCommandHandler();
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
            event.reply(NO_REQUEST_TEMPLATE.formatted(requestOptionName, playCommand.getName())).queue();
            return;
        }
        playCommandHandler.onPlayCommand(guild, member, option.getAsString(), event);
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
