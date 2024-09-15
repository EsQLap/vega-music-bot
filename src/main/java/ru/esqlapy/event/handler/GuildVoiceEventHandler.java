package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import ru.esqlapy.audio.GlobalMusicManager;

/**
 * Discord voice channel update event handler.
 */
public final class GuildVoiceEventHandler extends GuildEventHandler {

    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    /**
     * Checks if the event is related to leaving a Discord voice channel.
     *
     * @param event
     *         аn event with extra data containing information about a user joining or leaving a Discord voice channel
     * @return {@code true} if the event is related to leaving a Discord voice channel, {@code false} otherwise
     */
    private boolean isChanelWasLeft(@Nonnull GuildVoiceUpdateEvent event) {
        return event.getChannelLeft() != null && event.getChannelJoined() == null;
    }

    /**
     * Performs actions required when a some user joins or leaves a Discord voice channel:
     * <li>if the bot has left the Discord voice channel, then dispose all activities related with this channel.</li>
     *
     * @param event
     *         аn event with extra data containing information about a user joining or leaving a Discord voice channel
     */
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        if (isItMe(event.getMember()) && isChanelWasLeft(event)) {
            globalMusicManager.dispose(event.getGuild());
        }
    }
}
