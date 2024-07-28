package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.esqlapy.audio.GlobalMusicManager;

public final class ClearCommandHandler extends CommandHandler {

    private static final String QUEUE_SUCCESSFULLY_CLEARED = "Queue successfully cleared";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onClearCommand(@Nonnull SlashCommandInteractionEvent event) {
        MessageChannelUnion channel = event.getChannel();
        globalMusicManager.clear(channel.asTextChannel());
        sendMessage(channel, QUEUE_SUCCESSFULLY_CLEARED);
    }
}
