package ru.esqlapy.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import ru.esqlapy.audio.guild.GuildMusicManagerPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class GlobalMusicManager {

    private static final long DISPOSE_TASK_DELAY = 5;
    private static final TimeUnit DISPOSE_TASK_TIME_UNIT = TimeUnit.MINUTES;
    private static final GlobalMusicManager INSTANCE = new GlobalMusicManager();
    private final GuildMusicManagerPool guildMusicManagerPool = GuildMusicManagerPool.getInstance();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    {
        scheduledExecutorService.scheduleWithFixedDelay(
                guildMusicManagerPool::disposeFreeMusicManagers,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_DELAY,
                DISPOSE_TASK_TIME_UNIT);
        Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutorService::shutdownNow));
    }

    private GlobalMusicManager() {
    }

    public void loadAndPlay(@Nonnull Guild guild, @Nonnull String trackUrl, @Nonnull IReplyCallback replyCallback) {
        guildMusicManagerPool.getMusicManager(guild).loadFromInternet(trackUrl, replyCallback);
    }

    public boolean skip(@Nonnull Guild guild) {
        return guildMusicManagerPool.getMusicManager(guild).skipTrack();
    }

    @Nullable
    public AudioTrackInfo loopCurrentTrack(@Nonnull Guild guild, boolean enable) {
        return guildMusicManagerPool.getMusicManager(guild).setLoopCurrentTrack(enable);
    }

    public void clear(@Nonnull Guild guild) {
        guildMusicManagerPool.getMusicManager(guild).clearQueue();
    }

    public void dispose(@Nonnull Guild guild) {
        guildMusicManagerPool.disposeMusicManager(guild);
    }

    @Nonnull
    public static GlobalMusicManager getInstance() {
        return INSTANCE;
    }
}
