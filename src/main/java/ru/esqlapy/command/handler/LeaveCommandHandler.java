package ru.esqlapy.command.handler;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * Discord {@link ru.esqlapy.command.LeaveCommand} handler.
 */
public final class LeaveCommandHandler extends GuildCommandHandler {

    /**
     * A response template for a situation where the bot has successfully left the audio channel.
     */
    private static final String GOODBYE = "Goodbye, call me if you need";

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.LeaveCommand}:
     * <li>exit audio channel in linked {@code guild};</li>
     * <li>inform the user in a response message about a result.</li>
     *
     * @param guild
     *         information about the guild from which the command was sent
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void onLeaveCommand(@Nonnull Guild guild, @Nonnull IReplyCallback replyCallback) {
        AudioManager manager = guild.getAudioManager();
        globalMusicManager.clear(guild);
        manager.closeAudioConnection();
        replyCallback.reply(GOODBYE).queue();
    }
}
