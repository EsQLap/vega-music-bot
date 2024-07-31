package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.source.YoutubeAudioSourceManagerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class GlobalMusicManager {

    private static final long DISPOSE_TASK_DELAY = 5;
    private static final TimeUnit DISPOSE_TASK_TIME_UNIT = TimeUnit.MINUTES;
    private static final GlobalMusicManager INSTANCE = new GlobalMusicManager();
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    {
        scheduledExecutorService.scheduleWithFixedDelay(
                this::disposeFreeManagers,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_TIME_UNIT);
        Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutorService::shutdownNow));
    }

    private GlobalMusicManager() {
        YoutubeAudioSourceManager youtubeSourceManager = YoutubeAudioSourceManagerProvider.getInstance()
                .getYoutubeSourceManager();
        audioPlayerManager.registerSourceManager(youtubeSourceManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
    }

    private void disposeFreeManagers() {
        List<Long> guildIds = musicManagers.keySet().stream().toList();
        for (Long id : guildIds) {
            GuildMusicManager guildMusicManager = musicManagers.get(id);
            if (guildMusicManager.isReadyToDispose()) {
                guildMusicManager.dispose();
                musicManagers.remove(id);
            }
        }
    }

    @Nonnull
    private GuildMusicManager getMusicManager(@Nonnull Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager, guild.getAudioManager());
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

    @Nullable
    public AudioTrackInfo loopCurrentTrack(@Nonnull Guild guild, boolean enable) {
        GuildMusicManager guildMusicManager = this.getMusicManager(guild);
        return guildMusicManager.setLoopCurrentTrack(enable);
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
