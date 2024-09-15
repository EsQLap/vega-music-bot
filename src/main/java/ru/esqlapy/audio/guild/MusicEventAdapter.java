package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Adapter for different audio event handlers.
 */
final class MusicEventAdapter extends AudioEventAdapter {

    /**
     * A timeout available between commands.
     */
    private static final Duration WAITING_TIME_DURATION = Duration.ofMinutes(4);
    /**
     * Audio track queue.
     */
    private final Queue<AudioTrack> queue = new LinkedBlockingQueue<>();
    /**
     * Map containing looping states of audio tracks.
     */
    private final Map<String, Boolean> trackLoopStateMap = new ConcurrentHashMap<>();
    /**
     * An audio player that is capable of playing audio tracks and provides audio frames
     * from the currently playing track.
     */
    private final AudioPlayer audioPlayer;
    /**
     * Time of sending the last command.
     */
    private LocalDateTime waitingTime = LocalDateTime.now();

    /**
     * Creates an adapter for different audio event handlers
     *
     * @param audioPlayer
     *         an audio player that is capable of playing audio tracks and provides audio frames from
     *         the currently playing track.
     */
    MusicEventAdapter(@Nonnull AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    /**
     * Checks if the timeout for the next command has expired.
     *
     * @return {@code true} if the timeout for the next command has expired, otherwise {@code false}
     */
    private boolean isWaitingTimeout() {
        return Duration.between(waitingTime, LocalDateTime.now()).minus(WAITING_TIME_DURATION).isPositive();
    }

    /**
     * * Starts playing the specified audio track.
     *
     * @param audioTrack
     *         audio track being played
     * @param noInterrupt
     *         should the currently playing track not be interrupted
     * @return {@code true} if the track started playing successfully, otherwise {@code false}
     */
    private boolean nextTrack(@Nullable AudioTrack audioTrack, boolean noInterrupt) {
        return audioPlayer.startTrack(audioTrack, noInterrupt);
    }

    void addToQueue(@Nonnull AudioTrack track) {
        if (!audioPlayer.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Starts playing the next audio track in the audio track queue.
     *
     * @return {@code true} if the track started playing successfully, otherwise {@code false}
     */
    boolean nextTrack() {
        return nextTrack(queue.poll(), false);
    }

    /**
     * Changes the loop states of the current audio track.
     *
     * @param enable
     *         {@code boolean} representation of the loop state of the current track
     * @return information about the missed track, {@code null} if the audio track queue was empty
     */
    @Nullable
    AudioTrackInfo setLoopCurrentTrack(boolean enable) {
        AudioTrack audioTrack = audioPlayer.getPlayingTrack();
        if (audioTrack == null) {
            return null;
        }
        trackLoopStateMap.put(audioTrack.getInfo().uri, enable);
        return audioTrack.getInfo();
    }

    /**
     * Clears the audio track queue.
     */
    void clear() {
        audioPlayer.destroy();
        queue.clear();
    }

    /**
     * Called when the audio track stops playing.
     *
     * @param player
     *         audio player
     * @param track
     *         audio track that ended
     * @param endReason
     *         the reason why the track stopped playing
     */
    @Override
    public void onTrackEnd(
            @Nonnull AudioPlayer player,
            @Nonnull AudioTrack track,
            @Nonnull AudioTrackEndReason endReason) {
        if (!endReason.mayStartNext) {
            return;
        }
        if (trackLoopStateMap.getOrDefault(track.getInfo().uri, false)) {
            nextTrack(track.makeClone(), true);
            return;
        }
        trackLoopStateMap.remove(track.getInfo().uri);
        boolean result = nextTrack();
        if (!result) {
            waitingTime = LocalDateTime.now();
        }
    }

    /**
     * Checks audio activity can be disposed.
     *
     * @return {@code true} if the audio tracks are not playing and the timeout for the next command has expired,
     * otherwise {@code false}
     */
    boolean isReadyToDispose() {
        return audioPlayer.getPlayingTrack() == null && isWaitingTimeout();
    }
}
