package ru.esqlapy.event.listener;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.esqlapy.event.handler.GuildLeaveEventHandler;
import ru.esqlapy.event.handler.GuildVoiceEventHandler;

public class EventListener extends ListenerAdapter {

    private final GuildLeaveEventHandler guildLeaveEventHandler = new GuildLeaveEventHandler();
    private final GuildVoiceEventHandler guildVoiceEventHandler = new GuildVoiceEventHandler();

    @Override
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        guildLeaveEventHandler.onGuildLeave(event.getGuild());
    }

    @Override
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        guildVoiceEventHandler.onGuildVoiceUpdate(event);
    }
}
