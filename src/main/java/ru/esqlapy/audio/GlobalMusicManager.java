package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.source.YoutubeAudioSourceManagerProvider;

import java.util.HashMap;
import java.util.Map;

public final class GlobalMusicManager {

    private static final GlobalMusicManager INSTANCE = new GlobalMusicManager();
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    private GlobalMusicManager() {
        YoutubeAudioSourceManager youtubeSourceManager = YoutubeAudioSourceManagerProvider.getInstance()
                .getYoutubeSourceManager();
        audioPlayerManager.registerSourceManager(youtubeSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
    }

    @Nonnull
    private GuildMusicManager getMusicManager(@Nonnull Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(@Nonnull Guild guild, @Nonnull String trackUrl, @Nonnull IReplyCallback replyCallback) {
        GuildMusicManager guildMusicManager = this.getMusicManager(guild);
        MusicLoadResultHandler handler = new MusicLoadResultHandler(guildMusicManager, replyCallback);
        this.audioPlayerManager.loadItemOrdered(guildMusicManager, trackUrl, handler);
    }

    public boolean skip(@Nonnull Guild guild) {
        GuildMusicManager guildMusicManager = this.getMusicManager(guild);
        return guildMusicManager.skipTrack();
    }

    public void clear(@Nonnull Guild guild) {
        GuildMusicManager guildMusicManager = this.getMusicManager(guild);
        guildMusicManager.clearQueue();
    }

    @Nonnull
    public static GlobalMusicManager getInstance() {
        return INSTANCE;
    }
}
