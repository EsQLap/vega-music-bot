package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import jakarta.annotation.Nonnull;

public final class GuildMusicManager {

    public final AudioPlayer audioPlayer;
    public final MusicEventAdapter musicEventAdapter;
    public final MusicSendHandler sendHandler;

    public GuildMusicManager(@Nonnull AudioPlayerManager audioPlayerManager) {
        this.audioPlayer = audioPlayerManager.createPlayer();
        this.musicEventAdapter = new MusicEventAdapter(this.audioPlayer);
        this.audioPlayer.addListener(musicEventAdapter);
        this.sendHandler = new MusicSendHandler(this.audioPlayer);
    }
}
