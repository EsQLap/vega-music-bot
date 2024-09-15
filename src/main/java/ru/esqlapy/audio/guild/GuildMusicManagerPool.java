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

/**
 * Class containing set of initialized {@link GuildMusicManager} kept ready to use.
 */
public final class GuildMusicManagerPool {

    /**
     * Map of initialized {@link GuildMusicManager} kept ready to use.
     */
    private final Map<Long, GuildMusicManager> musicManagers = new ConcurrentHashMap<>();
    /**
     * Audio player manager which is used for creating audio players and loading tracks and playlists.
     */
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    /**
     * Instance of {@link GuildMusicManagerPool}.
     */
    private static final GuildMusicManagerPool INSTANCE = new GuildMusicManagerPool();

    /**
     * Creates a new instance and configures the used sources.
     */
    private GuildMusicManagerPool() {
        YoutubeAudioSourceManager youtubeSourceManager = YoutubeAudioSourceManagerProvider.getInstance()
                .getYoutubeSourceManager();
        audioPlayerManager.registerSourceManager(youtubeSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
    }

    /**
     * Clears all free instances of {@link GuildMusicManager}.
     */
    public void disposeFreeMusicManagers() {
        List<Long> guildIds = musicManagers.keySet().stream().toList();
        for (Long id : guildIds) {
            GuildMusicManager guildMusicManager = musicManagers.get(id);
            if (guildMusicManager != null && guildMusicManager.isReadyToDispose()) {
                disposeMusicManager(id);
            }
        }
    }

    /**
     * Clears instance of the {@link GuildMusicManager} related with specified {@code guild}.
     *
     * @param id
     *         id of the specified {@code guild}
     */
    private void disposeMusicManager(long id) {
        GuildMusicManager guildMusicManager = musicManagers.remove(id);
        if (guildMusicManager != null) {
            guildMusicManager.dispose();
        }
    }

    /**
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     * @return instance of the {@link GuildMusicManager} related with specified {@code guild}
     */
    @Nonnull
    public GuildMusicManager getMusicManager(@Nonnull Guild guild) {
        return this.musicManagers.computeIfAbsent(
                guild.getIdLong(),
                guildId -> new GuildMusicManager(audioPlayerManager, guild.getAudioManager())
        );
    }

    /**
     * Clears instance of the {@link GuildMusicManager} related with specified {@code guild}.
     *
     * @param guild
     *         an object containing all the information provided by Discord about the guild
     */
    public void disposeMusicManager(@Nonnull Guild guild) {
        disposeMusicManager(guild.getIdLong());
    }

    /**
     * Returns the instance of {@link GuildMusicManagerPool}.
     *
     * @return instance of {@link GuildMusicManagerPool}
     */
    @Nonnull
    public static GuildMusicManagerPool getInstance() {
        return INSTANCE;
    }
}
