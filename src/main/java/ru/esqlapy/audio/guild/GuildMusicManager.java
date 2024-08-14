package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.managers.AudioManager;

public final class GuildMusicManager {

    private final AudioPlayerManager audioPlayerManager;
    private final MusicEventAdapter musicEventAdapter;
    private final AudioManager audioManager;

    GuildMusicManager(@Nonnull AudioPlayerManager audioPlayerManager, @Nonnull AudioManager audioManager) {
        this.audioPlayerManager = audioPlayerManager;
        AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
        this.musicEventAdapter = new MusicEventAdapter(audioPlayer);
        this.audioManager = audioManager;
        audioPlayer.addListener(musicEventAdapter);
        audioManager.setSendingHandler(new MusicSendHandler(audioPlayer));
    }

    void addToQueue(@Nonnull AudioTrack track) {
        musicEventAdapter.addToQueue(track);
    }

    public void loadFromInternet(@Nonnull String trackUrl, @Nonnull IReplyCallback replyCallback) {
        MusicLoadResultHandler handler = new MusicLoadResultHandler(this, replyCallback);
        this.audioPlayerManager.loadItemOrdered(this, trackUrl, handler);
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

    public boolean isReadyToDispose() {
        return musicEventAdapter.isReadyToDispose();
    }

    public void dispose() {
        clearQueue();
        audioManager.setSendingHandler(null);
        audioManager.closeAudioConnection();
    }
}
