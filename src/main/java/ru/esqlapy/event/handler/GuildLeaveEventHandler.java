package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import ru.esqlapy.audio.GlobalMusicManager;

public final class GuildLeaveEventHandler extends GuildEventHandler {

    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onGuildLeave(@Nonnull Guild guild) {
        globalMusicManager.dispose(guild);
    }
}
