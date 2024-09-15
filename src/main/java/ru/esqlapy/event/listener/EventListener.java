package ru.esqlapy.event.listener;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.esqlapy.event.handler.GuildLeaveEventHandler;
import ru.esqlapy.event.handler.GuildVoiceEventHandler;

/**
 * Listener for events related to the Discord-bot.
 */
public final class EventListener extends ListenerAdapter {

    private final GuildLeaveEventHandler guildLeaveEventHandler = new GuildLeaveEventHandler();
    private final GuildVoiceEventHandler guildVoiceEventHandler = new GuildVoiceEventHandler();

    /**
     * Called when bot leaves the Discord guild.
     *
     * @param event
     *         an event with extra data that carries information related to the bot leaving the channel
     */
    @Override
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        guildLeaveEventHandler.onGuildLeave(event.getGuild());
    }

    /**
     * Called when a some user joins or leaves a Discord voice channel.
     *
     * @param event
     *         аn event with extra data containing information related to a user joining or leaving
     *         a Discord voice channel
     */
    @Override
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        guildVoiceEventHandler.onGuildVoiceUpdate(event);
    }
}
