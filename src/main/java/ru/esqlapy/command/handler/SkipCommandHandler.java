package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

/**
 * Discord {@link ru.esqlapy.command.SkipCommand} handler.
 */
public final class SkipCommandHandler extends GuildCommandHandler {

    /**
     * A response template for a situation where the bot successfully skipped an audio track.
     */
    private static final String TRACK_SUCCESSFULLY_SKIPPED = "Track successfully skipped";
    /**
     * A response template for a situation where the bot was unable to skip an audio track
     * because the audio track queue is empty.
     */
    private static final String NO_TRACK_IN_QUEUE = "No more track in the queue";

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.SkipCommand}:
     * <li>skip audio track if audio track queue is not empty;</li>
     * <li>inform the user in a response message about a result.</li>
     *
     * @param guild
     *         information about the guild from which the command was sent
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void onSkipCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        boolean result = globalMusicManager.skip(guild);
        String message = result ? TRACK_SUCCESSFULLY_SKIPPED : NO_TRACK_IN_QUEUE;
        replyCallback.reply(message).queue();
    }
}
