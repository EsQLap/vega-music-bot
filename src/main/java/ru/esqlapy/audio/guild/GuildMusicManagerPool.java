package ru.esqlapy.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import ru.esqlapy.audio.source.YoutubeAudioSourceManagerProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GuildMusicManagerPool {

    private final Map<Long, GuildMusicManager> musicManagers = new ConcurrentHashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    private static final GuildMusicManagerPool INSTANCE = new GuildMusicManagerPool();

    private GuildMusicManagerPool() {
        YoutubeAudioSourceManager youtubeSourceManager = YoutubeAudioSourceManagerProvider.getInstance()
                .getYoutubeSourceManager();
        audioPlayerManager.registerSourceManager(youtubeSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
    }

    public void disposeFreeMusicManagers() {
        List<Long> guildIds = musicManagers.keySet().stream().toList();
        for (Long id : guildIds) {
            GuildMusicManager guildMusicManager = musicManagers.get(id);
            if (guildMusicManager != null && guildMusicManager.isReadyToDispose()) {
                disposeMusicManager(id);
            }
        }
    }

    private void disposeMusicManager(long id) {
        GuildMusicManager guildMusicManager = musicManagers.remove(id);
        if (guildMusicManager != null) {
            guildMusicManager.dispose();
        }
    }

    @Nonnull
    public GuildMusicManager getMusicManager(@Nonnull Guild guild) {
        return this.musicManagers.computeIfAbsent(
                guild.getIdLong(),
                guildId -> new GuildMusicManager(audioPlayerManager, guild.getAudioManager())
        );
    }

    public void disposeMusicManager(@Nonnull Guild guild) {
        disposeMusicManager(guild.getIdLong());
    }

    @Nonnull
    public static GuildMusicManagerPool getInstance() {
        return INSTANCE;
    }
}
