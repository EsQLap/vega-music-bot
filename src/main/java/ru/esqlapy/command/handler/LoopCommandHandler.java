package ru.esqlapy.command.handler;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.GlobalMusicManager;

public final class LoopCommandHandler extends CommandHandler {

    private static final String THERE_ARE_NO_TRACK_IN_QUEUE = "Sorry, there are no tracks in the queue";
    private static final String LOOP_STATE_FOR_TRACK_TEMPLATE = """
            Looping state for the track "%s": %s
            """;
    private static final String ENABLE_STATE = "enable";
    private static final String DISABLE_STATE = "disable";
    private static final String TRACK_LABEL_TEMPLATE = "%s | %s";
    private final GlobalMusicManager globalMusicManager = GlobalMusicManager.getInstance();

    @Nonnull
    private String extractTrackLabel(@Nonnull AudioTrackInfo audioTrackInfo) {
        return TRACK_LABEL_TEMPLATE.formatted(audioTrackInfo.title, audioTrackInfo.author);
    }

    @Nonnull
    private String getEnableStateStringRepresentation(boolean enable) {
        if (enable) {
            return ENABLE_STATE;
        }
        return DISABLE_STATE;
    }

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
