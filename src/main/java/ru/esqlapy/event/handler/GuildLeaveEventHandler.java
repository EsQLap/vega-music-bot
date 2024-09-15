package ru.esqlapy.event.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import ru.esqlapy.audio.GlobalMusicManager;

/**
 * Discord guild leaving event handler.
 */
public final class GuildLeaveEventHandler extends GuildEventHandler {

    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    /**
     * Performs actions required when a bot leaves a Discord guild:
     * <li>dispose all activities related to the Discord guild that the bot left.</li>
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     */
    public void onGuildLeave(@Nonnull Guild guild) {
        globalMusicManager.dispose(guild);
    }
}
