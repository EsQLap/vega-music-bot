package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import jakarta.annotation.Nonnull;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class MusicEventAdapter extends AudioEventAdapter {

    public final AudioPlayer audioPlayer;
    public final Queue<AudioTrack> queue = new LinkedBlockingQueue<>();

    public MusicEventAdapter(@Nonnull AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void queue(@Nonnull AudioTrack track) {
        if (!this.audioPlayer.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        this.audioPlayer.startTrack(this.queue.poll(), false);
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
