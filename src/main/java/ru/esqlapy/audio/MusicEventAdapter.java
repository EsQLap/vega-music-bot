package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import jakarta.annotation.Nonnull;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class MusicEventAdapter extends AudioEventAdapter {

    private final Queue<AudioTrack> queue = new LinkedBlockingQueue<>();
    private final AudioPlayer audioPlayer;

    public MusicEventAdapter(@Nonnull AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void addToQueue(@Nonnull AudioTrack track) {
        if (!this.audioPlayer.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public boolean nextTrack() {
        AudioTrack track = this.queue.poll();
        if (track == null) {
            return false;
        }
        return this.audioPlayer.startTrack(track, false);
    }

    public void clear() {
        this.audioPlayer.destroy();
        queue.clear();
    }

    @Override
    public void onTrackEnd(
            @Nonnull AudioPlayer player,
            @Nonnull AudioTrack track,
            @Nonnull AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}
