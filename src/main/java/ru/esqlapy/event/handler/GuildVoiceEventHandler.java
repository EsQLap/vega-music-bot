package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

public final class GuildVoiceEventHandler extends GuildEventHandler {

    private boolean isChanelWasLeft(@Nonnull GuildVoiceUpdateEvent event) {
        return event.getChannelLeft() != null && event.getChannelJoined() == null;
    }

    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        if (isItMe(event.getMember()) && isChanelWasLeft(event)) {
            globalMusicManager.dispose(event.getGuild());
        }
    }
}
