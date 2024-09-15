package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

/**
 * Discord {@link ru.esqlapy.command.ClearCommand} handler.
 */
public final class ClearCommandHandler extends GuildCommandHandler {

    /**
     * A response template for when the audio track queue has been successfully cleared.
     */
    private static final String QUEUE_SUCCESSFULLY_CLEARED = "Queue successfully cleared";

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.ClearCommand}:
     * <li>clear the audio track queue in the linked {@code guild};</li>
     * <li>inform the user in a response message about a result.</li>
     * @param guild information about the guild from which the command was sent
     * @param replyCallback an object that allows the bot to respond to user messages
     */
    public void onClearCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        globalMusicManager.clear(guild);
        replyCallback.reply(QUEUE_SUCCESSFULLY_CLEARED).queue();
    }
}
