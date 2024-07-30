package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.managers.AudioManager;

public final class GuildMusicManager {

    private final MusicEventAdapter musicEventAdapter;
    private final AudioManager audioManager;
    private final MusicSendHandler sendHandler;

    public GuildMusicManager(@Nonnull AudioPlayerManager audioPlayerManager, @Nonnull AudioManager audioManager) {
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.musicEventAdapter = new MusicEventAdapter(audioPlayer);
        this.audioManager = audioManager;
        audioPlayer.addListener(musicEventAdapter);
        this.sendHandler = new MusicSendHandler(audioPlayer);
    }

    public void addToQueue(@Nonnull AudioTrack track) {
        musicEventAdapter.addToQueue(track);
    }

    public boolean skipTrack() {
        return musicEventAdapter.nextTrack();
    }

    @Nullable
    public AudioTrackInfo setLoopCurrentTrack(boolean enable) {
        return musicEventAdapter.setLoopCurrentTrack(enable);
    }

    public void clearQueue() {
        musicEventAdapter.clear();
    }

    public MusicSendHandler getSendHandler() {
        return sendHandler;
    }

    public boolean isReadyToDispose() {
        return musicEventAdapter.isReadyToDispose();
    }

    public void dispose() {
        clearQueue();
        audioManager.closeAudioConnection();
    }
}
