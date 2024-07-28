package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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
    public GuildMusicManager getMusicManager(@Nonnull Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.sendHandler);
            return guildMusicManager;
        });
    }

    public void loadAndPlay(@Nonnull TextChannel textChannel, @Nonnull String trackUrl) {
        GuildMusicManager guildMusicManager = this.getMusicManager(textChannel.getGuild());
        MusicLoadResultHandler handler = new MusicLoadResultHandler(guildMusicManager, textChannel);
        this.audioPlayerManager.loadItemOrdered(guildMusicManager, trackUrl, handler);
    }

    public void clear(@Nonnull TextChannel textChannel) {
        GuildMusicManager guildMusicManager = this.getMusicManager(textChannel.getGuild());
        guildMusicManager.musicEventAdapter.clear();
    }

    @Nonnull
    public static GlobalMusicManager getInstance() {
        return INSTANCE;
    }
}
