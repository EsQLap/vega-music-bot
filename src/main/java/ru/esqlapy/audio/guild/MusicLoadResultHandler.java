package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.util.List;
import java.util.Objects;

/**
 * Handler for audio track loading result from audio player manager.
 */
final class MusicLoadResultHandler implements AudioLoadResultHandler {

    /**
     * A response template for the situation when the audio track was successfully downloaded
     * and added to the audio track queue.
     */
    private static final String SUCCESS_ADDING_TO_QUEUE_TEMPLATE = "Adding to queue: %s | %s";
    /**
     * The response for the situation when the audio track could not be found.
     */
    private static final String CANT_FIND_THE_TRACK = "Sorry, I can't find this track";
    /**
     * The response for the situation when an unknown error occurred while searching for an audio track.
     */
    private static final String UNKNOWN_EXCEPTION_MESSAGE = """
            Sorry, I got an unknown error while searching for your track
            """;
    private final GuildMusicManager guildMusicManager;
    /**
     * An object that allows the bot to respond to user messages.
     */
    private final IReplyCallback replyCallback;

    /**
     * Creates an object for audio track loading result from audio player manager.
     *
     * @param guildMusicManager
     *         manager of all activities related to the Discord guild from which the request was sent
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    MusicLoadResultHandler(@Nonnull GuildMusicManager guildMusicManager, @Nonnull IReplyCallback replyCallback) {
        this.guildMusicManager = guildMusicManager;
        this.replyCallback = replyCallback;
        replyCallback.deferReply().queue();
    }

    @Override
    public void trackLoaded(@Nonnull AudioTrack track) {
        guildMusicManager.addToQueue(track);
        sendSuccessReply(track);
    }

    @Override
    public void playlistLoaded(@Nonnull AudioPlaylist playlist) {
        List<AudioTrack> tracks = playlist.getTracks();
        if (tracks.isEmpty()) {
            return;
        }
        AudioTrack track = tracks.getFirst();
        guildMusicManager.addToQueue(track);
        sendSuccessReply(track);
    }

    @Override
    public void noMatches() {
        sendNoFoundReply();
    }

    @Override
    public void loadFailed(@Nonnull FriendlyException exception) {
        sendExceptionReply(exception.getMessage());
    }

    /**
     * Sends a message to the user that an audio {@code track} has been successfully added to the audio track queue.
     *
     * @param track
     *         found audio track
     */
    private void sendSuccessReply(@Nonnull AudioTrack track) {
        replyCallback.getHook().sendMessage(SUCCESS_ADDING_TO_QUEUE_TEMPLATE.formatted(
                track.getInfo().title,
                track.getInfo().author
        )).queue();
    }

    /**
     * Sends a message to the user that the audio track was not found.
     */
    private void sendNoFoundReply() {
        replyCallback.reply(CANT_FIND_THE_TRACK).queue();
    }

    /**
     * Sends a message to the user that an error occurred while searching for a track.
     *
     * @param message
     *         information about the error that occurred, available to the user
     */
    private void sendExceptionReply(@Nullable String message) {
        String messageToUser = Objects.requireNonNullElse(message, UNKNOWN_EXCEPTION_MESSAGE);
        replyCallback.getHook().sendMessage(messageToUser).queue();
    }
}
