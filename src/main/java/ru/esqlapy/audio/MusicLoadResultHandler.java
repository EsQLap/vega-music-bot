package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.util.List;
import java.util.Objects;

public final class MusicLoadResultHandler implements AudioLoadResultHandler {

    private static final String SUCCESS_ADDING_TO_QUEUE_TEMPLATE = "Adding to queue: %s | %s";
    private static final String CANT_FIND_THE_TRACK = "Sorry, I can't find this track";
    private static final String UNKNOWN_EXCEPTION_MESSAGE = """
            Sorry, I got an unknown error while searching for your track
            """;
    private final GuildMusicManager guildMusicManager;
    private final IReplyCallback replyCallback;

    public MusicLoadResultHandler(@Nonnull GuildMusicManager guildMusicManager, @Nonnull IReplyCallback replyCallback) {
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

    private void sendSuccessReply(@Nonnull AudioTrack track) {
        replyCallback.getHook().sendMessage(SUCCESS_ADDING_TO_QUEUE_TEMPLATE.formatted(
                track.getInfo().title,
                track.getInfo().author
        )).queue();
    }

    private void sendNoFoundReply() {
        replyCallback.reply(CANT_FIND_THE_TRACK).queue();
    }

    private void sendExceptionReply(@Nullable String message) {
        String messageToUser = Objects.requireNonNullElse(message, UNKNOWN_EXCEPTION_MESSAGE);
        replyCallback.getHook().sendMessage(messageToUser).queue();
    }
}
