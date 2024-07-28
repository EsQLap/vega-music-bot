package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

public final class MusicLoadResultHandler implements AudioLoadResultHandler {

    private final GuildMusicManager guildMusicManager;
    private final TextChannel textChannel;

    public MusicLoadResultHandler(@Nonnull GuildMusicManager guildMusicManager, @Nonnull TextChannel textChannel) {
        this.guildMusicManager = guildMusicManager;
        this.textChannel = textChannel;
    }

    @Override
    public void trackLoaded(@Nonnull AudioTrack track) {
        guildMusicManager.musicEventAdapter.queue(track);
        sendMessageAddingToQueue(track);
    }

    @Override
    public void playlistLoaded(@Nonnull AudioPlaylist playlist) {
        List<AudioTrack> tracks = playlist.getTracks();
        if (tracks.isEmpty()) {
            return;
        }
        AudioTrack track = tracks.getFirst();
        guildMusicManager.musicEventAdapter.queue(track);
        sendMessageAddingToQueue(track);
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(@Nonnull FriendlyException exception) {

    }

    private void sendMessageAddingToQueue(@Nonnull AudioTrack track) {
        textChannel.sendMessage("Adding to queue: %s | %s".formatted(
                track.getInfo().title,
                track.getInfo().author
        )).queue();
    }
}
