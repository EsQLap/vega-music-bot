package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import ru.esqlapy.audio.GlobalMusicManager;

public final class LeaveCommandHandler extends CommandHandler {

    private static final String GOODBYE = "Goodbye, call me if you need";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onLeaveCommand(@Nonnull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        MessageChannelUnion channel = event.getChannel();
        if (guild == null) {
            sendMessage(
                    channel,
                    WORK_ONLY_IN_CHANNEL
            );
            return;
        }
        AudioManager manager = guild.getAudioManager();
        globalMusicManager.clear(channel.asTextChannel());
        manager.closeAudioConnection();
        sendMessage(channel, GOODBYE);
    }
}
