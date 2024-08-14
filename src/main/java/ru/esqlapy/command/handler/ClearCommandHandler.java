package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.GlobalMusicManager;

public final class ClearCommandHandler extends GuildCommandHandler {

    private static final String QUEUE_SUCCESSFULLY_CLEARED = "Queue successfully cleared";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    public void onClearCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        globalMusicManager.clear(guild);
        replyCallback.reply(QUEUE_SUCCESSFULLY_CLEARED).queue();
    }
}
