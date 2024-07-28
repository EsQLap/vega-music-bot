package ru.esqlapy.command.listener;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.esqlapy.command.*;
import ru.esqlapy.command.handler.ClearCommandHandler;
import ru.esqlapy.command.handler.LeaveCommandHandler;
import ru.esqlapy.command.handler.PlayCommandHandler;

import java.util.Collection;

public final class CommandListener extends ListenerAdapter {

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
        switch (command) {
            case PlayCommand playCommand -> playCommandHandler.onPlayCommand(playCommand, event);
            case ClearCommand ignored -> clearCommandHandler.onClearCommand(event);
            case LeaveCommand ignored -> leaveCommandHandler.onLeaveCommand(event);
        }
        event.reply("Success!").queue();
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
