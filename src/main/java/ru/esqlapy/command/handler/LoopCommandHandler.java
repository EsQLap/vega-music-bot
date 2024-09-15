package ru.esqlapy.command.handler;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

/**
 * Discord {@link ru.esqlapy.command.LoopCommand} handler.
 */
public final class LoopCommandHandler extends GuildCommandHandler {

    /**
     * A response template for a situation when the audio track queue is empty.
     */
    private static final String THERE_ARE_NO_TRACK_IN_QUEUE = "Sorry, there are no tracks in the queue";
    /**
     * A response template with information about loop state of the audio track
     */
    private static final String LOOP_STATE_FOR_TRACK_TEMPLATE = """
            Looping state for the track "%s": %s
            """;
    /**
     * a human-readable string describing the {@code enabled} loop state of audio track
     */
    private static final String ENABLE_STATE = "enable";
    /**
     * a human-readable string describing the {@code disabled} loop state of audio track
     */
    private static final String DISABLE_STATE = "disable";
    /**
     * A response template with main information about the audio track
     */
    private static final String TRACK_LABEL_TEMPLATE = "%s | %s";

    /**
     * Extracts basic information from an audio track.
     *
     * @param audioTrackInfo
     *         information about an audio track
     * @return a human-readable string containing basic information about the audio track
     */
    @Nonnull
    private String extractTrackLabel(@Nonnull AudioTrackInfo audioTrackInfo) {
        return TRACK_LABEL_TEMPLATE.formatted(audioTrackInfo.title, audioTrackInfo.author);
    }

    /**
     * Returns string with the loop state information of the current audio track.
     *
     * @param enable
     *         {@code boolean} representation of the loop state of the current track
     * @return a human-readable string describing the loop state of the current audio track
     */
    @Nonnull
    private String getEnableStateStringRepresentation(boolean enable) {
        if (enable) {
            return ENABLE_STATE;
        }
        return DISABLE_STATE;
    }

    /**
     * Performs actions required when a bot receives the {@link ru.esqlapy.command.LoopCommand}:
     * <li>if value is {@code true} then loop current audio track;</li>
     * <li>if value is {@code false} then stop looping current audio track;</li>
     * <li>inform the user in a response message about a result.</li>
     *
     * @param guild
     *         information about the guild from which the command was sent
     * @param enable
     *         {@code boolean} representation of the loop state of the current track
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void onLoopCommand(
            @Nonnull Guild guild,
            boolean enable,
            @Nonnull IReplyCallback replyCallback) {
        AudioTrackInfo currentTrackInfo = globalMusicManager.loopCurrentTrack(guild, enable);
        String message;
        if (currentTrackInfo == null) {
            message = THERE_ARE_NO_TRACK_IN_QUEUE;
        } else {
            message = LOOP_STATE_FOR_TRACK_TEMPLATE.formatted(
                    extractTrackLabel(currentTrackInfo), getEnableStateStringRepresentation(enable)
            );
        }
        replyCallback.reply(message).queue();
    }
}
