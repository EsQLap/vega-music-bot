package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 * Manager of all audio activities related to the specified {@link AudioManager}.
 */
public final class GuildMusicManager {

    /**
     * Audio player manager which is used for creating audio players and loading tracks and playlists
     */
    private final AudioPlayerManager audioPlayerManager;
    /**
     * Adapter for audio event handlers.
     */
    private final MusicEventAdapter musicEventAdapter;
    /**
     * Audio manager related to all audio activities.
     */
    private final AudioManager audioManager;

    /**
     * Creates a manager of all audio activities.
     *
     * @param audioPlayerManager
     *         audio player manager which is used for creating audio players and loading tracks and playlists
     * @param audioManager
     *         audio manager related to all audio activities
     */
    GuildMusicManager(@Nonnull AudioPlayerManager audioPlayerManager, @Nonnull AudioManager audioManager) {
        this.audioPlayerManager = audioPlayerManager;
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.musicEventAdapter = new MusicEventAdapter(audioPlayer);
        this.audioManager = audioManager;
        audioPlayer.addListener(musicEventAdapter);
        audioManager.setSendingHandler(new MusicSendHandler(audioPlayer));
    }

    /**
     * Adds an audio track to the audio track queue.
     *
     * @param track
     *         added audio track
     */
    void addToQueue(@Nonnull AudioTrack track) {
        musicEventAdapter.addToQueue(track);
    }

    /**
     * Downloads an audio track from the Internet and send result to {@link MusicLoadResultHandler}.
     *
     * @param trackUrl
     *         link to audio track in url format
     * @param replyCallback
     *         an object that allows the bot to respond to user messages
     */
    public void loadFromInternet(@Nonnull String trackUrl, @Nonnull IReplyCallback replyCallback) {
        MusicLoadResultHandler handler = new MusicLoadResultHandler(this, replyCallback);
        this.audioPlayerManager.loadItemOrdered(this, trackUrl, handler);
    }

    /**
     * Skips the current audio track.
     *
     * @return {@code true} if the audio track was successfully skipped, otherwise {@code false}
     */
    public boolean skipTrack() {
        return musicEventAdapter.nextTrack();
    }

    /**
     * Changes the loop states of the current audio track.
     *
     * @param enable
     *         {@code boolean} representation of the loop state of the current track
     * @return information about the missed track, {@code null} if the audio track queue was empty
     */
    @Nullable
    public AudioTrackInfo setLoopCurrentTrack(boolean enable) {
        return musicEventAdapter.setLoopCurrentTrack(enable);
    }

    /**
     * Clears the audio track queue.
     */
    public void clearQueue() {
        musicEventAdapter.clear();
    }

    /**
     * Checks if all audio activities can be disposed.
     *
     * @return {@code true} if all audio activities can be disposed, otherwise {@code false}
     */
    public boolean isReadyToDispose() {
        return musicEventAdapter.isReadyToDispose();
    }

    /**
     * Dispose all audio activities.
     */
    public void dispose() {
        clearQueue();
        audioManager.setSendingHandler(null);
        audioManager.closeAudioConnection();
    }
}
