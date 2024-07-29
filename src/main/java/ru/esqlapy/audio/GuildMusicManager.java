package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.annotation.Nonnull;

public final class GuildMusicManager {

    private final MusicEventAdapter musicEventAdapter;
    private final MusicSendHandler sendHandler;

    public GuildMusicManager(@Nonnull AudioPlayerManager audioPlayerManager) {
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.musicEventAdapter = new MusicEventAdapter(audioPlayer);
        audioPlayer.addListener(musicEventAdapter);
        this.sendHandler = new MusicSendHandler(audioPlayer);
    }

    public void addToQueue(@Nonnull AudioTrack track) {
        musicEventAdapter.addToQueue(track);
    }

    public boolean skipTrack() {
        return musicEventAdapter.nextTrack();
    }

    public void clearQueue() {
        musicEventAdapter.clear();
    }

    public MusicSendHandler getSendHandler() {
        return sendHandler;
    }
}
