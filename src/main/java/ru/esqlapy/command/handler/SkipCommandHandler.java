package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public final class SkipCommandHandler extends GuildCommandHandler {

    private static final String TRACK_SUCCESSFULLY_SKIPPED = "Track successfully skipped";
    private static final String NO_TRACK_IN_QUEUE = "No more track in the queue";

    public void onSkipCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        boolean result = globalMusicManager.skip(guild);
        String message = result ? TRACK_SUCCESSFULLY_SKIPPED : NO_TRACK_IN_QUEUE;
        replyCallback.reply(message).queue();
    }
}
