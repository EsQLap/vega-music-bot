package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public final class MusicEventAdapter extends AudioEventAdapter {

    private final Queue<AudioTrack> queue = new LinkedBlockingQueue<>();
    private final Map<String, Boolean> trackLoopStateMap = new ConcurrentHashMap<>();
    private final AudioPlayer audioPlayer;

    public MusicEventAdapter(@Nonnull AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    private boolean nextTrack(AudioTrack audioTrack, boolean noInterrupt) {
        return audioPlayer.startTrack(audioTrack, noInterrupt);
    }

    public void addToQueue(@Nonnull AudioTrack track) {
        if (!audioPlayer.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public boolean nextTrack() {
        return nextTrack(queue.poll(), false);
    }

    @Nullable
    public AudioTrackInfo setLoopCurrentTrack(boolean enable) {
        AudioTrack audioTrack = audioPlayer.getPlayingTrack();
        if (audioTrack == null) {
            return null;
        }
        trackLoopStateMap.put(audioTrack.getInfo().uri, enable);
        return audioTrack.getInfo();
    }

    public void clear() {
        audioPlayer.destroy();
        queue.clear();
    }

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
        } else {
            trackLoopStateMap.remove(track.getInfo().uri);
            nextTrack();
        }
    }
}
