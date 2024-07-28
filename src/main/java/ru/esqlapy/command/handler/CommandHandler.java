package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public abstract class CommandHandler {

    protected static final String WORK_ONLY_IN_CHANNEL = "Sorry, I can work only in channel";

    protected final void sendMessage(@Nonnull MessageChannelUnion channel, @Nonnull String message) {
        channel.sendMessage(message).queue();
    }
}
